package com.yayetee.winko

import com.yayetee.opengl.OpenGLDisplay

class Button(val parent: Application) extends GfxNode {
	def display(v: View){
		v.fill(0xFF, 0, 0)
		v.rect(100, 100, 100, 100)
	}
}

object AppLauncher extends Application {
	def start {
		addGfxNode(new Button(this))
	}
	
	def createSymbol(symbolID: Int, xpos: Float, ypos: Float) = new MashSymbol(xpos, ypos)

	def createCursor(xpos: Float, ypos: Float) = new MashCursor(xpos, ypos)
}
