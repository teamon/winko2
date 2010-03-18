package com.yayetee.winko

import com.yayetee.winko.engine.Hooks
import com.yayetee.tuio._

class DebugCursorGfx(parent: MashFinger) extends GfxNode {
	def display(v: View) {
		v.translate(parent.position.x, parent.position.y)
		v.fill(0, 0, 0xFF)
		v.arc(0, 0, 40, 10, 2*Math.Pi)
	}
}

/**
 * MashCursor
 *
 * Subclass of TuioCursor that adds Hooks and OpenGL nodes support
 *
 * @author teamon
 */
class MashFinger(pos: Pos = Pos(), sp: Speed = Speed()) extends Finger(pos, sp) with Hooks with GfxNodesManager {
	// when cursor down
//	Mash.logger.debug(x + " x " + y)
//	Mash.logger.debug(Mash.app.gfxNodes.map(_.contains(this)))
	Mash.app.gfxNodes.find(_.contains(this)).map(_.runOnCursorDownHooks(this))
	Mash.emblems.foreach {case (sid, emb) => emb.gfxNodes.find(_.contains(this)).map(_.runOnCursorDownHooks(this))}

//	addGfxNode(new DebugCursorGfx(this))

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

	def x = position.x

	def y = position.y
}
