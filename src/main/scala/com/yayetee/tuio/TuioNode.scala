package com.yayetee.tuio

/**
 * TuioNode
 *
 * Base class for TuioSymbol and TuioCursor
 */
abstract class TuioNode(var xpos: Double, var ypos: Double) {
	/**
	 * Update node data, called by client on Tuio update message
	 *
	 * @author teamon
	 */
	def update(xp: Double, yp: Double) {
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
class TuioCursor(xp: Double, yp: Double) extends TuioNode(xp, yp)

/**
 * TuioSymbol class
 *
 * @author teamon
 */
class TuioSymbol(xp: Double, yp: Double, var angle: Double) extends TuioNode(xp, yp){
		/**
	 * Update node data, called by client on Tuio update message
	 *
	 * @author teamon
	 */
	def update(xp: Double, yp: Double, a: Double) {
		super.update(xp, yp)
		xpos = xp
		ypos = yp
		angle = a
	}
}
