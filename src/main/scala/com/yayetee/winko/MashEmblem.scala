package com.yayetee.winko

import com.yayetee.winko.engine.Hooks
import com.yayetee.tuio.{Speed, Pos, Emblem}

/**
 * MashEmblem
 *
 * Subclass of TuioSymbol that adds Hooks and OpenGL nodes support
 *
 * @author teamon
 */
class MashEmblem(pos: Pos = Pos(), sp: Speed = Speed()) extends Emblem(pos, sp) with Hooks with GfxNodesManager {
	override def update(pos: Pos, sp: Speed) {
		pos.x *= Mash.resolution.width
		pos.y *= Mash.resolution.height
		super.update(pos, sp)
		runOnUpdateHooks
	}

	override def remove {
		super.remove
		runOnRemoveHooks
	}

	@deprecated
	def x = position.x

	@deprecated
	def y = position.y

	def closeEmblems[T](distance: Double) = Mash.emblems.filter {
		case (sid, emb) => emb != this && emb.isInstanceOf[T] && this.position.distanceTo(emb.position) < distance
	}.map {  case(sid, emb) => emb.asInstanceOf[T] }
}
