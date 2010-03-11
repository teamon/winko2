package com.yayetee.winko

import com.yayetee.opengl.OpenGLDisplay

class Button(val parent: MashSymbol) extends OpenGLNode {
	def display(v: OpenGLView){
		v.fill(0xFF, 0, 0)
		Mash.logger.debug("x = " + parent.xpos + ", y = " + parent.ypos)
//		Mash.logger.debug("x = " + parent.x + ", y = " + parent.y)
		v.rect(parent.x, parent.y, 100, 100)
	}
}

class T extends MashSymbol(0f, 0f){
	addGfxNode(new Button(this))
}

object AppLauncher extends Application {
	def start {
		
	}
	
	def createSymbol(symbolID: Int, xpos: Float, ypos: Float) = new T

	def createCursor(xpos: Float, ypos: Float) = new MashCursor(xpos, ypos)
}
