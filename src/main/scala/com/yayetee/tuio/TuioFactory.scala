package com.yayetee.tuio

/**
 * TuioFactory
 *
 * Generic interface for Tuio objects factory
 *
 * @author teamon
 */


trait TuioFactory[S, C] {
	def createSymbol(symbolID: Int, xpos: Double, ypos: Double, angle: Double): S

	def createCursor(xpos: Double, ypos: Double): C
}
