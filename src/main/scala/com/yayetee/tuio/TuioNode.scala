package com.yayetee.tuio

/**
 * TuioNode
 *
 * Base class for TuioSymbol and TuioCursor
 */
abstract class TuioNode(val sessionID: Long, var xpos: Float, var ypos: Float) {
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
class TuioCursor(sid: Long, xp: Float, yp: Float) extends TuioNode(sid, xp, yp)

/**
 * TuioSymbol class
 *
 * @author teamon
 */
class TuioSymbol(sid: Long, val symbolID: Int, xp: Float, yp: Float) extends TuioNode(sid, xp, yp)
