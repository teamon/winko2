package com.yayetee.apps.demo

import com.yayetee.winko.engine.{WSymbol, Application}
import com.yayetee.tuio.{Symbol, Cursor, Logger}
import com.yayetee.winko.graphics.EventListener

/**
 * User: teamon
 * Date: 2010-03-04
 * Time: 23:37:57
 */

class Square(sym: Symbol) extends WSymbol(sym) {
	Logger.debug("Square added")

	onUpdate {
		Logger.debug("Square updated!")
	}

	onRemove {
		Logger.debug("Square removed")
	}

	def display(e: EventListener){
		
	}
}

class Circle(sym: Symbol) extends WSymbol(sym) {
	Logger.debug("Circle added")

	onUpdate {
		Logger.debug("Circle updated")
	}

	onRemove {
		Logger.debug("Circle removed")
	}

	def display(e: EventListener){

	}
}

object Demo extends Application {
	def createSymbol(symbol: Symbol) = new Square(symbol)
	def createCursor(cursor: Cursor) = cursor
}
