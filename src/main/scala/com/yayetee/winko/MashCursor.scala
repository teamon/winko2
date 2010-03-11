package com.yayetee.winko

import com.yayetee.winko.engine.Hooks
import com.yayetee.tuio.TuioCursor

class DebugCursorGfx(parent: MashCursor) extends GfxNode {
	def display(v: View){
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
class MashCursor(xp: Float, yp: Float) extends TuioCursor(xp, yp) with Hooks with GfxNodesManager {
	// when cursor down
	Mash.logger.debug(x + " x " + y)
	Mash.logger.debug(Mash.app.gfxNodes.map(_.contains(this)))
	Mash.app.gfxNodes.find(_.contains(this)).map(_.runOnCursorDownHooks(this))

	addGfxNode(new DebugCursorGfx(this))

	override def update(xp: Float, yp: Float) {
		super.update(xp, yp)
		runOnUpdateHooks
	}

	override def remove {
		super.remove
		runOnRemoveHooks
	}

	def x = xpos * Mash.resolution.width

	def y = ypos * Mash.resolution.height
}
