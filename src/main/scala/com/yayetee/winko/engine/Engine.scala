package com.yayetee.winko.engine

import com.yayetee.tuio._
import com.yayetee.apps.demo.Demo
import com.yayetee.opengl.OpenGLDisplay
import com.yayetee.winko.graphics.EventListener


abstract class Application extends Factory

object Engine {
	val client = new Client(3333, Demo)

	def main(args: Array[String]){
		client.connect

		val display = new OpenGLDisplay("w.i.n.k.o", new EventListener)
		display.start
	}

	def symbols = client.symbols
	def cursors = client.cursors
}
