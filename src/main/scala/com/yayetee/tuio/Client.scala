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
 * @see Symbol
 * @see Cursor
 *
 * @todo Take care of frame loss
 */

class Client[E <: Emblem, F <: Finger](val port: Int, var factory: Factory[E, F]) extends OSCListener {
	def this(factory: Factory[E, F]) = this (3333, factory)

	def logger = Tools.logger(this.getClass)

	val emblems = new HashMap[Long, E]
	val aliveEmblems = new ListBuffer[Long]

	val fingers = new HashMap[Long, F]
	val aliveFingers = new ListBuffer[Long]
	val osc = new OSCPortIn(port)


	/**
	 * Connect to Tuio OSC port
	 *
	 * @author teamon
	 */
	def connect {
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
	 * Disconnect from Tuio OSC port
	 *
	 * @author teamon
	 */
	def disconnect {
		osc.stopListening
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
			// emblems
			case "/tuio/2Dobj" => command match {
				case "set" => {
					val sid = args(1).asInstanceOf[Int].toLong
					val cid = args(2).asInstanceOf[Int]

					val dargs = (3 to 10).map(args(_).asInstanceOf[Float].toDouble)
					val pos = Pos(dargs(0), dargs(1), dargs(2))
					val sp = Speed(dargs(3), dargs(4), dargs(5))

					// TODO: Add more fields
//				float maccel = ((Float)args[9]).floatValue();
//				float raccel = ((Float)args[10]).floatValue();
					
					emblems.get(sid) map (_.update(pos, sp)) getOrElse {
						emblems(sid) = factory.createEmblem(cid, pos, sp)
					}

					aliveEmblems += sid

				}
				case "alive" => {
					aliveEmblems.clear
					args.drop(1).foreach(aliveEmblems += _.asInstanceOf[Int].toLong)
				}
				case "fseq" => {
					emblems.filter(s => !aliveEmblems.contains(s._1)).foreach(s => {
						s._2.remove
						emblems -= s._1
					})
					aliveEmblems.clear
				}
			}

			// fingers
			case "/tuio/2Dcur" => command match {
				case "set" => {
					val sid = args(1).asInstanceOf[Int].toLong

					val dargs = (2 to 6).map(args(_).asInstanceOf[Float].toDouble)
					val pos = Pos(dargs(0), dargs(1))
					val sp = Speed(dargs(2), dargs(3))
					
					// TODO: Add more fields
//				float maccel = ((Float)args[6]).floatValue();

					fingers.get(sid).map(_.update(pos, sp)) getOrElse {
						fingers(sid) = factory.createFinger(pos, sp)
					}

					aliveFingers += sid
				}
				case "alive" => {
					aliveFingers.clear
					args.drop(1).foreach(aliveFingers += _.asInstanceOf[Int].toLong)
				}
				case "fseq" => {
					fingers.filter(c => !aliveFingers.contains(c._1)).foreach(c => {
						c._2.remove
						fingers -= c._1
					})
					aliveFingers.clear
				}
			}
		}

	}

}
