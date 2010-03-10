package com.yayetee.tuio

/**
 * TuioFactory
 *
 * Generic interface for Tuio objects factory
 *
 * @author teamon
 */

trait TuioFactory[S, C] {
	def createSymbol(sessionID: Long, symbolID: Int, xpos: Float, ypos: Float): S

	def createCursor(sessionID: Long, xpos: Float, ypos: Float): C
}
