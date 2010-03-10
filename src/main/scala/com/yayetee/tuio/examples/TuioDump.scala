package com.yayetee.tuio.examples

import com.yayetee.tuio.{TuioCursor, TuioClient, TuioSymbol, TuioFactory}
import com.yayetee.Tools

/**
 * TuioDump
 *
 * Tuio usage example
 */

class MySymbol(sessionID: Long, symbolID: Int, xpos: Float, ypos: Float) extends TuioSymbol(sessionID, symbolID, xpos, ypos) {
	TuioDump.logger.info("MySymbol #" + sessionID + " created (" + xpos + ", " + ypos + ")")

	override def update(xp: Float, yp: Float){
		super.update(xp, yp)
		TuioDump.logger.info("MySymbol #" + sessionID + " updated (" + xpos + ", " + ypos + ")")
	}

	override def remove {
		TuioDump.logger.info("MySymbol #" + sessionID + " removed")
	}
}

class MyCursor(sessionID: Long, xpos: Float, ypos: Float) extends TuioCursor(sessionID, xpos, ypos) {
	TuioDump.logger.info("MyCursor #" + sessionID + " created (" + xpos + ", " + ypos + ")")

	override def update(xp: Float, yp: Float){
		super.update(xp, yp)
		TuioDump.logger.info("MyCursor #" + sessionID + " updated (" + xpos + ", " + ypos + ")")
	}

	override def remove {
		TuioDump.logger.info("MyCursor #" + sessionID + " removed")
	}
}

object MyFactory extends TuioFactory[MySymbol, MyCursor] {
	def createSymbol(sessionID: Long, symbolID: Int, xpos: Float, ypos: Float) = new MySymbol(sessionID, symbolID, xpos, ypos)

	def createCursor(sessionID: Long, xpos: Float, ypos: Float) = new MyCursor(sessionID, xpos, ypos)
}



object TuioDump {
	def logger = Tools.logger(TuioDump.getClass)
	
	def main(args: Array[String]){
		val client = new TuioClient[MySymbol, MyCursor](3333, MyFactory)
		client.connect
	}
}
