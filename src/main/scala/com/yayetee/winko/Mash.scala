package com.yayetee.winko

import com.yayetee.tuio.{TuioFactory, TuioClient}
import com.yayetee.opengl.OpenGLDisplay
import collection.mutable.ListBuffer
import com.yayetee.Tools
import com.yayetee.Tools._

/**
 * Main object
 *
 * @author teamon
 */
object Mash {
	val resolution = 640 x 480
	val logger = Tools.logger(Mash.getClass)
	val client = new TuioClient[MashSymbol, MashCursor](3333, AppLauncher)

	val gfxNodes = new ListBuffer[OpenGLNode]

	def main(args: Array[String]) {
		client.connect
		val display = new OpenGLDisplay("w.i.n.k.o", resolution.width, resolution.height, new OpenGLView)
		display.start
		AppLauncher.start
	}

	def symbols = client.symbols

	def cursors = client.cursors

	scala.Predef
}

/**
 * Application class
 *
 * @author teamon
 */

abstract class Application extends TuioFactory[MashSymbol, MashCursor] {
	def start
}




