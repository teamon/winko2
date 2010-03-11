//package com.yayetee.apps.demo
//
//import com.yayetee.winko.engine.{WSymbol, Application}
//import com.yayetee.tuio.{Symbol, Cursor, Logger}
//import com.yayetee.winko.graphics.{View}
//
///**
// * User: teamon
// * Date: 2010-03-04
// * Time: 23:37:57
// */
//
//class Square(sym: Symbol) extends WSymbol(sym) {
//	Logger.debug("Square added")
//
//	onUpdate {
//		Logger.debug("Square updated!")
//	}
//
//	onRemove {
//		Logger.debug("Square removed")
//	}
//
//	def display(v: View){
//		Logger.debug("(%f, %f)", x, y)
//
//		v.translate(x, y)
//		v.rotate(30)
//		v.rect(-50, -50, 100, 100)
//	}
//}
//
//class Circle(sym: Symbol) extends WSymbol(sym) {
//	Logger.debug("Circle added")
//
//	onUpdate {
//		Logger.debug("Circle updated")
//	}
//
//	onRemove {
//		Logger.debug("Circle removed")
//	}
//
//	def display(v: View){
//
//	}
//}
//
//object Demo extends Application {
//	def createSymbol(symbol: Symbol) = new Square(symbol)
//	def createCursor(cursor: MashCursor) = cursor
//}
