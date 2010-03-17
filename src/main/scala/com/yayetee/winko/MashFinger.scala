package com.yayetee.winko

import com.yayetee.winko.engine.Hooks
import com.yayetee.tuio._

class DebugCursorGfx(parent: MashFinger) extends GfxNode {
	def display(v: View) {
		v.translate(parent.x, parent.y)
		v.fill(0, 0, 0xFF)
		v.rect(0, 0, 20, 20)
	}
}

/**
 * MashCursor
 *
 * Subclass of TuioCursor that adds Hooks and OpenGL nodes support
 *
 * @author teamon
 */
class MashFinger(pos: Pos = Pos()) extends Finger(pos) with Hooks with GfxNodesManager {
	// when cursor down
	Mash.logger.debug(x + " x " + y)
	Mash.logger.debug(Mash.app.gfxNodes.map(_.contains(this)))
	Mash.app.gfxNodes.find(_.contains(this)).map(_.runOnCursorDownHooks(this))
	Mash.emblems.foreach {case (sid, emb) => emb.gfxNodes.find(_.contains(this)).map(_.runOnCursorDownHooks(this))}

	addGfxNode(new DebugCursorGfx(this))

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
