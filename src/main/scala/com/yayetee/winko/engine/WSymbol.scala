package com.yayetee.winko.engine

import com.yayetee.winko.graphics.OpenGLNode
import com.yayetee.tuio.{Logger, Symbol}


abstract class WSymbol(sym: Symbol) extends Symbol(sym) with Hooks with OpenGLNode {
	final val WINDOW_WIDTH = 1024
	final val WINDOW_HEIGHT = 768
	
	def x = xpos * WINDOW_WIDTH
	def y = (1f-ypos) * WINDOW_HEIGHT

	override def update(xp: Float, yp: Float){
		super.update(xp, yp)
    runOnUpdateHooks
	}

	override def remove {
		super.remove
    runOnRemoveHooks
	}
}
