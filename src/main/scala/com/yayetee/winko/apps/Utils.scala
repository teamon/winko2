package com.yayetee.winko.apps


abstract class Loop(var timeout: Int) extends Thread {
	var keep = true

	def stopIt {keep = false}

	override def run {
		while (keep) {
			tick
			Thread.sleep(timeout)
		}
	}

	def tick
}

trait Loops {
	def loop(timeout: Int)(f: => Unit) = {
		val l = new Loop(timeout) {def tick {f}}
		l.start
		l
	}
}
