package com.yayetee.tuio

case class Pos(var x: Double = 0.0, var y: Double = 0.0, var angle: Double = 0.0) {
	def update(pos: Pos) {
		x = pos.x
		y = pos.y
		angle = pos.angle
	}

	def distanceTo(pos: Pos) = Math.sqrt(Math.pow(x-pos.x, 2) + Math.pow(y-pos.y, 2))

	def angleTo(pos: Pos) = {
//		if (y - pos.y < 0) 2.0 * Math.Pi - (Math.asin((x - pos.x) / distanceTo(pos)) + Math.Pi / 2)
//		else Math.asin((x - pos.x) / distanceTo(pos)) + Math.Pi / 2
		val side = x - pos.x
		val height = y - pos.y
		val distance = distanceTo(pos)
		var angle = Math.asin(side/distance)+Math.Pi/2
		if(height < 0) angle = 2.0 * Math.Pi - angle

		angle
	}

//			float side = xpos-xp;
//		float height = ypos-yp;
//		float distance = getDistance(xp,yp);
//
//		float angle = (float)(Math.asin(side/distance)+Math.PI/2);
//		if (height<0) angle = 2.0f*(float)Math.PI-angle;
//
//		return angle;
}
case class Speed(var x: Double = 0.0, var y: Double = 0.0, var rotation: Double = 0.0) {
	def update(sp: Speed) {
		x = sp.x
		y = sp.y
		rotation = sp.rotation
	}
}

/**
 * Node
 *
 * Base class for TuioSymbol and TuioCursor
 */
abstract class Node(val position: Pos = Pos(), val speed: Speed = Speed()) {
	/**
	 * Update node data, called by client on Tuio update message
	 *
	 * @author teamon
	 */
	def update(pos: Pos, sp: Speed) {
		position.update(pos)
		speed.update(sp)
	}

	/**
	 * Remove node, called by client on Tuio update message
	 *
	 * @author teamon
	 */
	def remove {}
}

/**
 * Finger class
 *
 * @author teamon
 */
class Finger(pos: Pos = Pos(), sp: Speed = Speed()) extends Node(pos, sp)

/**
 * Emblem class
 *
 * @author teamon
 */
class Emblem(pos: Pos = Pos(), sp: Speed = Speed()) extends Node(pos, sp)
