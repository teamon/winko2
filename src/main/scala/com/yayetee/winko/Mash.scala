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
	var application: Option[Application] = None
	var client: TuioClient[MashSymbol, MashCursor] = null

	def main(args: Array[String]) {
		val display = new OpenGLDisplay("w.i.n.k.o", resolution.width, resolution.height, new View)
		display.start

		run(AppLauncher)
	}

	def app = application.get

	def symbols = client.symbols

	def cursors = client.cursors

	def run(newApp: Application){
		if(client == null) client = new TuioClient[MashSymbol, MashCursor](3333, newApp)
		client.factory = newApp

		application.map(_.stop)
		application = Some(newApp)

		client.disconnect
		client.connect
		app.start
	}

}

/**
 * Application class
 *
 * @author teamon
 */

abstract class Application extends TuioFactory[MashSymbol, MashCursor] with GfxNodesManager {
	def start {}
	def stop {}

	def name = "Application"
}




