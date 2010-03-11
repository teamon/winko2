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
class MashCursor(sessionID: Long, xpos: Float, ypos: Float) extends TuioCursor(sessionID, xpos, ypos) with Hooks with OpenGLNodesManager {
	Mash.logger.debug("MashCursor #" + sessionID + " created (" + xpos + ", " + ypos + ")")

	override def update(xp: Float, yp: Float) {
		super.update(xp, yp)
		Mash.logger.debug("MashCursor #" + sessionID + " updated (" + xpos + ", " + ypos + ")")
		runOnUpdateHooks
	}

	override def remove {
		super.remove
		Mash.logger.debug("MashCursor #" + sessionID + " removed")
		runOnRemoveHooks
	}

	def x = xpos * Mash.resolution.width

	def y = (1f - ypos) * Mash.resolution.height
}
