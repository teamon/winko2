package com.yayetee.tuio.examples

import com.yayetee.tuio.{TuioCursor, TuioClient, TuioSymbol, TuioFactory}
import com.yayetee.Tools

/**
 * TuioDump
 *
 * Tuio usage example
 */

class MySymbol(xpos: Float, ypos: Float) extends TuioSymbol(xpos, ypos) {
	TuioDump.logger.info(this + " created (" + xpos + ", " + ypos + ")")

	override def update(xp: Float, yp: Float){
		super.update(xp, yp)
		TuioDump.logger.info(this + " updated (" + xpos + ", " + ypos + ")")
	}

	override def remove {
		TuioDump.logger.info(this + " removed")
	}
}

class MyCursor(xpos: Float, ypos: Float) extends TuioCursor(xpos, ypos) {
	TuioDump.logger.info(this + " created (" + xpos + ", " + ypos + ")")

	override def update(xp: Float, yp: Float){
		super.update(xp, yp)
		TuioDump.logger.info(this + " updated (" + xpos + ", " + ypos + ")")
	}

	override def remove {
		TuioDump.logger.info(this + " removed")
	}
}

object MyFactory extends TuioFactory[MySymbol, MyCursor] {
	def createSymbol(symbolID: Int, xpos: Float, ypos: Float) = new MySymbol(xpos, ypos)

	def createCursor(xpos: Float, ypos: Float) = new MyCursor(xpos, ypos)
}



object TuioDump {
	def logger = Tools.logger(TuioDump.getClass)
	
	def main(args: Array[String]){
		val client = new TuioClient[MySymbol, MyCursor](3333, MyFactory)
		client.connect
	}
}
