package com.yayetee

import tuio.{Client, Logger}

/**
 * User: teamon
 * Date: 2010-03-04
 * Time: 21:33:35
 */

object Dump {
	def main(args: Array[String]){
		Logger.info("Connecting...")
		val client = new Client(3333)
		Logger.info("Connected")
	}
}
