package com.yayetee.winko

import apps.demo.Midi
import com.yayetee.tuio.{Factory, Client}
import com.yayetee.opengl.OpenGLDisplay
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
	var client: Client[MashEmblem, MashFinger] = null

	def main(args: Array[String]) {
		val display = new OpenGLDisplay("w.i.n.k.o", resolution.width, resolution.height, new View)
		display.start

		run(Midi)
	}

	def app = application.get

	def emblems = client.emblems

	def fingers = client.fingers

	def run(newApp: Application) {
		if (client == null) client = new Client[MashEmblem, MashFinger](3333, newApp)
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

abstract class Application extends Factory[MashEmblem, MashFinger] with GfxNodesManager {
	def start {}

	def stop {}

	def name = "Application"
}




