package com.yayetee.winko

import com.yayetee.winko.engine.Hooks
import com.yayetee.tuio.TuioCursor

/**
 * MashCursor
 *
 * Subclass of TuioCursor that adds Hooks and OpenGL nodes support
 *
 * @author teamon
 */
class MashCursor(xpos: Float, ypos: Float) extends TuioCursor(xpos, ypos) with Hooks with OpenGLNodesManager {
	override def update(xp: Float, yp: Float) {
		super.update(xp, yp)
		runOnUpdateHooks
	}

	override def remove {
		super.remove
		runOnRemoveHooks
	}

	def x = xpos * Mash.resolution.width

	def y = (1f - ypos) * Mash.resolution.height
}
