package com.yayetee.winko

import com.yayetee.winko.engine.Hooks
import com.yayetee.tuio.{Pos, Emblem}

/**
 * MashEmblem
 *
 * Subclass of TuioSymbol that adds Hooks and OpenGL nodes support
 *
 * @author teamon
 */
class MashEmblem(pos: Pos = Pos()) extends Emblem(pos) with Hooks with GfxNodesManager {
	override def update(pos: Pos) {
		super.update(pos)
		runOnUpdateHooks
	}

	override def remove {
		super.remove
		runOnRemoveHooks
	}

	def x = position.x * Mash.resolution.width

	def y = position.y * Mash.resolution.height
}
