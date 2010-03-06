package com.yayetee.winko.graphics

import javax.media.opengl.glu.GLU
import javax.media.opengl.{GL, DebugGL, GLAutoDrawable, GLEventListener}
import demos.common.GLDisplay

/**
 * User: teamon
 * Date: 2010-03-06
 * Time: 17:10:43
 */

class Renderer extends GLEventListener {
	var glu = new GLU

	def init(drawable: GLAutoDrawable){
		val gl = drawable.getGL

		gl.glShadeModel(GL.GL_SMOOTH)
		gl.glClearColor(0f, 0f, 0f, 0.5f)
	}


	def reshape(drawable: GLAutoDrawable, x:Int, y:Int, width:Int, height: Int){
		val gl = drawable.getGL

		val h = width.toFloat / height.toFloat
		gl.glViewport(0, 0, width, height)
		gl.glMatrixMode(GL.GL_PROJECTION)
		gl.glLoadIdentity
		glu.gluPerspective(45.0f, h, 1.0, 20.0)
		gl.glMatrixMode(GL.GL_MODELVIEW)
		gl.glLoadIdentity
	}


	def displayChanged(drawable: GLAutoDrawable, modeChanged: Boolean, deviceChanged: Boolean){

	}


	def display(drawable: GLAutoDrawable){
		val gl = drawable.getGL
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT)
		gl.glLoadIdentity
	}
}


object OpenGL {
	def main(args: Array[String]) {
		val display = GLDisplay.createGLDisplay("TEST")
		display.addGLEventListener(new Renderer)
		display.start

	}
}
