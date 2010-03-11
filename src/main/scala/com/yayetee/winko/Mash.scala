package com.yayetee.winko

import com.yayetee.tuio.{TuioFactory, TuioSymbol, TuioCursor, TuioClient}
import com.yayetee.opengl.OpenGLDisplay
import com.yayetee.Tools
import engine.Hooks

/**
 * Application class
 *
 * @author teamon
 */

abstract class Application extends TuioFactory[MashSymbol, MashCursor]

/**
 * Main object
 *
 * @author teamon
 */
object Mash {
	val logger = Tools.logger(Mash.getClass)
	val client = new TuioClient[MashSymbol, MashCursor](3333, new Application {
			def createSymbol(sessionID: Long, symbolID: Int, xpos: Float, ypos: Float) = new MashSymbol(sessionID, symbolID, xpos, ypos)

	def createCursor(sessionID: Long, xpos: Float, ypos: Float) = new MashCursor(sessionID, xpos, ypos)
	})

	def main(args: Array[String]){
		client.connect

		val display = new OpenGLDisplay("w.i.n.k.o", new OpenGLView)
		display.start
	}

	def symbols = client.symbols
	def cursors = client.cursors
}

class MashSymbol(sessionID: Long, symbolID: Int, xpos: Float, ypos: Float) extends TuioSymbol(sessionID, symbolID, xpos, ypos) with Hooks {
	Mash.logger.info("MashSymbol #" + sessionID + " created (" + xpos + ", " + ypos + ")")

	override def update(xp: Float, yp: Float){
		super.update(xp, yp)
		Mash.logger.info("MashSymbol #" + sessionID + " updated (" + xpos + ", " + ypos + ")")
	}

	override def remove {
		Mash.logger.info("MashSymbol #" + sessionID + " removed")
	}
}

class MashCursor(sessionID: Long, xpos: Float, ypos: Float) extends TuioCursor(sessionID, xpos, ypos) with Hooks {
	Mash.logger.info("MashCursor #" + sessionID + " created (" + xpos + ", " + ypos + ")")

	override def update(xp: Float, yp: Float){
		super.update(xp, yp)
		Mash.logger.info("MashCursor #" + sessionID + " updated (" + xpos + ", " + ypos + ")")
	}

	override def remove {
		Mash.logger.info("MashCursor #" + sessionID + " removed")
	}
}
