package com.yayetee.tuio

/**
 * TuioNode
 *
 * Base class for TuioSymbol and TuioCursor
 */
abstract class TuioNode(var xpos: Float, var ypos: Float) {
	/**
	 * Update node data, called by client on Tuio update message
	 *
	 * @author teamon
	 */
	def update(xp: Float, yp: Float) {
		xpos = xp
		ypos = yp
	}

	/**
	 * Remove node, called by client on Tuio update message
	 *
	 * @author teamon
	 */
	def remove {}
}

/**
 * TuioCursor class
 *
 * @author teamon
 */
class TuioCursor(xp: Float, yp: Float) extends TuioNode(xp, yp)

/**
 * TuioSymbol class
 *
 * @author teamon
 */
class TuioSymbol(xp: Float, yp: Float) extends TuioNode(xp, yp)
