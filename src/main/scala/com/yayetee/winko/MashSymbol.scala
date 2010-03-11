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
class MashSymbol(xp: Float, yp: Float) extends TuioSymbol(xp, yp) with Hooks with OpenGLNodesManager {

	override def update(xp: Float, yp: Float) {
		super.update(xp, yp)
		runOnUpdateHooks
	}

	override def remove {
		super.remove
		runOnRemoveHooks
	}

	def x = {
		Mash.logger.debug(xpos)
		Mash.logger.debug(Mash.resolution.width)
		xpos * Mash.resolution.width
	}

	def y = (1f - ypos) * Mash.resolution.height
}
