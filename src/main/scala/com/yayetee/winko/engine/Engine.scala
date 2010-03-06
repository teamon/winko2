package com.yayetee.winko.engine

import com.yayetee.tuio._
import com.yayetee.apps.demo.Demo
import com.yayetee.winko.graphics.OpenGL

/**
 * User: teamon
 * Date: 2010-03-04
 * Time: 23:28:44
 */

abstract class Application extends Factory

object Engine {
	val client = new Client(0, Demo)

//	def main(args: Array[String]){
////		OpenGL.init
//	}

	def symbols = client.symbols
	def cursors = client.cursors
}
