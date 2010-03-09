package com.yayetee.tuio

/**
 * User: teamon
 * Date: 2010-03-04
 * Time: 21:36:30
 */

class Symbol(sid: Long, val symbolID: Int, xp: Float, yp: Float) extends Node(sid, xp, yp){
	def this(sym: Symbol) = this(sym.sessionID, sym.symbolID, sym.xpos, sym.ypos)

	def update(xp:Float, yp:Float){
		xpos = xp
		ypos = yp
	}

	def remove {
		Logger.debug("Symbol removed sid=%d class=%s", sessionID, getClass.getName)
	}
}
