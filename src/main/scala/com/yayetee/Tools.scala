package com.yayetee

import java.io.OutputStreamWriter
import org.apache.log4j._

/**
 * Misc tools for everithing
 *
 */

object Tools {
	def logger[T](c: Class[T]) = Logger.getLogger(c)

	val appender = new ConsoleAppender
	val level = Level.DEBUG

	/**
	 * logger initialization. It's perormed automatically
	 * @author dmilith
	 */
	def initLogger = {
		appender.setName(ConsoleAppender.SYSTEM_OUT);
		appender.setWriter(new OutputStreamWriter(System.out))
		appender.setThreshold(level)
		appender.setLayout(new PatternLayout)
		if (Logger.getRootLogger.getAppender(ConsoleAppender.SYSTEM_OUT) == null) {
			Logger.getRootLogger.addAppender(appender)
		}
	}

	/**
	 * set logger level
	 * @author dmilith
	 */
	def setLoggerLevel(arg: Priority) = appender.setThreshold(arg)


	initLogger

	// implicit wrappers
	implicit def Int2Resolution(i: Int) = new Size(i)
}

class Size(val width: Int, val height: Int) {
	def this(w: Int) = this (w, 0)

	def x(h: Int) = new Size(width, h)
}

object RectMode extends Enumeration {
	type RectMode = Value
	val Corner, Center = Value
}

class Rectangle(val x: Float, val y: Float, val width: Float, val height: Float, val mode: RectMode.RectMode) {
	def this(xp: Float, yp: Float, size: Size) = this (xp, yp, size.width, size.height, RectMode.Center)

	def contains(xp: Int, yp: Int) = {
		if (mode == RectMode.Center) x + (width / 2) > xp && xp > x - (width / 2) && y + (height / 2) > yp && yp > y - (height / 2)
		else x + width > xp && xp > x && y + height > yp && yp > y
	}


	def contains(xp: Float, yp: Float): Boolean = contains(xp.toInt, yp.toInt)

}
