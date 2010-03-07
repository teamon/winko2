package com.yayetee.opengl

import javax.media.opengl.{GLAutoDrawable, GLEventListener, GL}
import javax.media.opengl.glu.GLU

/**
 * OpenGL 2D Event listener
 *
 * @author Tymon Tobolski
 */
abstract class OpenGL2DEventListener extends GLEventListener {
	var drawable: GLAutoDrawable = _
	var gl: GL = _
	var glu: GLU = _

	// GLEventListener requirements

	def init(dr: GLAutoDrawable) {
		drawable = dr
		gl = drawable.getGL
		glu = new GLU
		init
	}

	def reshape(dr: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
		reshape(x, y, width, height)
	}

	def displayChanged(dr: GLAutoDrawable, modeChanged: Boolean, deviceChanged: Boolean) {
		displayChanged(modeChanged, deviceChanged)
	}

	def display(dr: GLAutoDrawable) {
		display
	}

	// client interface

	def init

	def reshape(x: Int, y: Int, width: Int, height: Int)

	def displayChanged(modeChanged: Boolean, deviceChanged: Boolean)

	def display

	// usefull stuff
	protected def enable2D {
		val vPort = java.nio.IntBuffer.allocate(4)
		gl.glGetIntegerv(GL_VIEWPORT, vPort)

		gl.glMatrixMode(GL_PROJECTION)
		gl.glPushMatrix
		gl.glLoadIdentity
		gl.glOrtho(vPort.get(0), vPort.get(0) + vPort.get(2), vPort.get(1), vPort.get(1) + vPort.get(3), -1, 1)

		gl.glMatrixMode(GL_MODELVIEW)
		gl.glPushMatrix
		gl.glLoadIdentity

		gl.glPushAttrib(GL_DEPTH_BUFFER_BIT)
		gl.glDisable(GL_DEPTH_TEST)
	}

	protected def disable2D {
		gl.glPopAttrib
		gl.glMatrixMode(GL_PROJECTION)
		gl.glPopMatrix
		gl.glMatrixMode(GL_MODELVIEW)
		gl.glPopMatrix
	}
}

