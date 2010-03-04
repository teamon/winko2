package com.yayetee.tuio

import com.illposed.osc.{OSCMessage, OSCPortIn, OSCListener}
import java.util.Date
import collection.mutable.{ListBuffer, HashMap}

class Client(val port:Int, val factory: Factory) extends OSCListener {
	val symbols= new HashMap[Long, Symbol]
	val aliveSymbols = new ListBuffer[Long]

	val cursors = new HashMap[Long, Cursor]
	val aliveCursors = new ListBuffer[Long]

	val osc = new OSCPortIn(port)

	try {
		osc.addListener("/tuio/2Dobj", this)
		osc.addListener("/tuio/2Dcur", this)
		osc.startListening
	} catch {
		case _ => Logger.info("Failed to connect")
	}


	def acceptMessage(date: Date, message: OSCMessage){
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

					symbols.get(sid) match {
						case Some(sym) => {
							sym.update(xpos, ypos)
							aliveSymbols += sid
						}
						case None => {
							val sym = factory.createSymbol(new Symbol(sid, cid, xpos, ypos))
							symbols(sid) = sym
							aliveSymbols += sid
						}
					}

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

					cursors.get(sid) match {
						case Some(cur) => {
							cur.update(xpos, ypos)
							aliveCursors += sid
						}
						case None => {
							val cur = factory.createCursor(new Cursor(sid, xpos, ypos))
							cursors(sid) = cur
							aliveCursors += sid
						}
					}

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
