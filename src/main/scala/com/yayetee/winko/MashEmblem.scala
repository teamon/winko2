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
		super.update(pos, sp)
		runOnUpdateHooks
	}

	override def remove {
		super.remove
		runOnRemoveHooks
	}

	def x = position.x * Mash.resolution.width

	def y = position.y * Mash.resolution.height
}
