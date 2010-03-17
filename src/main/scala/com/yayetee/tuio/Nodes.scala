package com.yayetee.tuio

case class Pos(var x: Double = 0.0, var y: Double = 0.0, var angle: Double = 0.0){
	def update(pos: Pos){
		x = pos.x
		y = pos.y
		angle = pos.angle
	}
}
case class Speed(var x: Double = 0.0, var y: Double = 0.0, var rotation: Double = 0.0){
	def update(sp: Speed){
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
