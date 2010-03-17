package com.yayetee.tuio

case class Pos(var x: Double = 0.0, var y: Double = 0.0, var angle: Double = 0.0){
	def update(pos: Pos){
		x = pos.x
		y = pos.y
		angle = pos.angle
	}
}
case class Speed(x: Double = 0.0, y: Double = 0.0, rotation: Double = 0.0)

/**
 * Node
 *
 * Base class for TuioSymbol and TuioCursor
 */
abstract class Node(val position: Pos = Pos()) {
	/**
	 * Update node data, called by client on Tuio update message
	 *
	 * @author teamon
	 */
	def update(pos: Pos) {
		pos.update(pos)
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
class Finger(pos: Pos = Pos()) extends Node(pos)

/**
 * Emblem class
 *
 * @author teamon
 */
class Emblem(pos: Pos = Pos()) extends Node(pos)
