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
	implicit def Int2Resolution(i: Int) = new Resolution(i)
}

class Resolution(val width: Int, val height: Int){
  def this(w: Int) = this(w, 0)
  def x(h: Int) = new Resolution(width, h)
}
