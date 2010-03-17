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
class MashSymbol(xp: Float, yp: Float, a: Float) extends TuioSymbol(xp, yp, a) with Hooks with GfxNodesManager {

	override def update(xp: Float, yp: Float, a: Float) {
		super.update(xp, yp, a)
		runOnUpdateHooks
	}

	override def remove {
		super.remove
		runOnRemoveHooks
	}

	def x = xpos * Mash.resolution.width
	def y = ypos * Mash.resolution.height
}
