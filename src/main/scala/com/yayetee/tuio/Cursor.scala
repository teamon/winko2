package com.yayetee.tuio

/**
 * User: teamon
 * Date: 2010-03-04
 * Time: 22:54:27
 */

class Cursor(sid: Long, xp: Float, yp: Float) extends Node(sid, xp, yp){

	def update(xp:Float, yp:Float){
		xpos = xp
		ypos = yp
	}

	def remove {}
	
}
