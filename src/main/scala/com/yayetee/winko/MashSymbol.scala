package com.yayetee.winko

import com.yayetee.winko.engine.Hooks
import com.yayetee.tuio.TuioSymbol

/**
 * MashSymbol
 *
 * Subclass of TuioSymbol that adds Hooks and OpenGL nodes support
 *
 * @author teamon
 */
class MashSymbol(sessionID: Long, symbolID: Int, xp: Float, yp: Float) extends TuioSymbol(sessionID, symbolID, xp, yp) with Hooks with OpenGLNodesManager {
//	Mash.logger.debug("MashSymbol #" + sessionID + " created (" + xpos + ", " + ypos + ")")

	override def update(xp: Float, yp: Float) {
		super.update(xp, yp)
//		Mash.logger.debug("MashSymbol #" + sessionID + " updated (" + xpos + ", " + ypos + ")")
		runOnUpdateHooks
	}

	override def remove {
		super.remove
//		Mash.logger.debug("MashSymbol #" + sessionID + " removed")
		runOnRemoveHooks
	}

	def x = {
		Mash.logger.debug(xpos)
		Mash.logger.debug(Mash.resolution.width)
		xpos * Mash.resolution.width
	}

	def y = (1f - ypos) * Mash.resolution.height
}
