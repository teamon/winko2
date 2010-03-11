package com.yayetee.tuio

import com.illposed.osc.{OSCMessage, OSCPortIn, OSCListener}
import java.util.Date
import collection.mutable.{ListBuffer, HashMap}
import com.yayetee.Tools

/**
 * TuioClient
 *
 * Main class of Tuio library
 *
 * @param port Tuio port for connection (default 3333)
 * @author teamon
 * @see TuioSymbol
 * @see TuioCursor
 *
 * @todo Take care of frame loss
 */

class TuioClient[S <: TuioSymbol, C <: TuioCursor](val port: Int, val factory: TuioFactory[S, C]) extends OSCListener {
	def this(factory: TuioFactory[S, C]) = this (3333, factory)

	def logger = Tools.logger(this.getClass)

	val symbols = new HashMap[Long, S]
	val aliveSymbols = new ListBuffer[Long]

	val cursors = new HashMap[Long, C]
	val aliveCursors = new ListBuffer[Long]


	/**
	 * Connect to Tuio OSC port
	 *
	 * @author teamon
	 */
	def connect {
		val osc = new OSCPortIn(port)

		try {
			osc.addListener("/tuio/2Dobj", this)
			osc.addListener("/tuio/2Dcur", this)
			osc.startListening
			logger.info("Tuio client connected on port " + port)
		} catch {
			case _ => logger.error("Failed to connect to TUIO on port " + port)
		}
	}

	/**
	 * Accepts OSC message
	 *
	 * @author teamon
	 *
	 * @todo Add more fields from Tuio
	 */

	def acceptMessage(date: Date, message: OSCMessage) {
		val args = message.getArguments
		val command = args(0).toString
		val address = message.getAddress

		address match {
			case "/tuio/2Dobj" => command match {
				case "set" => {
					val sid = args(1).asInstanceOf[Int].toLong
					val cid = args(2).asInstanceOf[Int]
					val xpos = args(3).asInstanceOf[Float]
					val ypos = args(4).asInstanceOf[Float]
					// TODO: Add more fields

					symbols.get(sid) map (_.update(xpos, ypos)) getOrElse {
						symbols(sid) = factory.createSymbol(cid, xpos, ypos)
					}

					aliveSymbols += sid

				}
				case "alive" => {
					aliveSymbols.clear
					args.drop(1).foreach(aliveSymbols += _.asInstanceOf[Int].toLong)
				}
				case "fseq" => {
					symbols.filter(s => !aliveSymbols.contains(s._1)).foreach(s => {
						s._2.remove
						symbols -= s._1
					})
					aliveSymbols.clear
				}
			}
			case "/tuio/2Dcur" => command match {
				case "set" => {
					val sid = args(1).asInstanceOf[Int].toLong
					val xpos = args(2).asInstanceOf[Float]
					val ypos = args(3).asInstanceOf[Float]
					// TODO: Add more fields

					cursors.get(sid).map(_.update(xpos, ypos)) getOrElse {
						cursors(sid) = factory.createCursor(xpos, ypos)
					}

					aliveCursors += sid
				}
				case "alive" => {
					aliveCursors.clear
					args.drop(1).foreach(aliveCursors += _.asInstanceOf[Int].toLong)
				}
				case "fseq" => {
					cursors.filter(c => !aliveCursors.contains(c._1)).foreach(c => {
						c._2.remove
						cursors -= c._1
					})
					aliveCursors.clear
				}
			}
		}

	}

}
