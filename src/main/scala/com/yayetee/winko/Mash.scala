package com.yayetee.winko

import apps.demo.Midi
import com.yayetee.opengl.OpenGLDisplay
import com.yayetee.Tools
import com.yayetee.Tools._
import com.yayetee.tuio.{Pos, Speed, Factory, Client}
import org.apache.log4j.Level

/**
 * Main object
 *
 * @author teamon
 */
object Mash {
//	val resolution = 1024 x 768
	val resolution = 640 x 480
	val logger = Tools.logger(Mash.getClass)
	logger.setLevel(Level.ERROR)
	var application: Option[Application] = None
	var client: Client[MashEmblem, MashFinger] = null

	def main(args: Array[String]) {
		val display = new OpenGLDisplay("w.i.n.k.o", resolution.width, resolution.height, new View)
//		val display = new OpenGLDisplay("w.i.n.k.o", new View, true)
		display.start

		run(Midi)
	}

	def app = application.get

	def emblems = client.emblems

	def fingers = client.fingers

	def run(newApp: Application) {
		if (client == null) client = new Client[MashEmblem, MashFinger](3333, newApp)
		client.factory = newApp

		application.foreach(_.stop)
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

	def createEmblem(symbolID: Int, pos: Pos, sp: Speed) = {
		pos.x *= Mash.resolution.width
		pos.y *= Mash.resolution.height
		emblem(symbolID, pos, sp)
	}

	def createFinger(pos: Pos, sp: Speed) = {
		pos.x *= Mash.resolution.width
		pos.y *= Mash.resolution.height
		finger(pos, sp)
	}

	def emblem(symbolID: Int, pos: Pos, sp: Speed) = new MashEmblem(pos, sp)

	def finger(pos: Pos, sp: Speed) = new MashFinger(pos, sp)
}




