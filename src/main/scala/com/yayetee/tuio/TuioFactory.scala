package com.yayetee.tuio

/**
 * TuioFactory
 *
 * Generic interface for Tuio objects factory
 *
 * @author teamon
 */

trait TuioFactory[S, C] {
	def createSymbol(symbolID: Int, xpos: Float, ypos: Float, angle: Float): S

	def createCursor(xpos: Float, ypos: Float): C
}
