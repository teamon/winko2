package com.yayetee.tuio

/**
 * User: teamon
 * Date: 2010-03-04
 * Time: 23:41:17
 */

trait Factory {
	def createSymbol(symbol: Symbol): Symbol
	def createCursor(cursor: Cursor): Cursor
}
