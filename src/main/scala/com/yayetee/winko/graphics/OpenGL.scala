package com.yayetee.winko.graphics

import com.yayetee.opengl.{OpenGL2DEventListener}
import javax.media.opengl.GL

/**
 * OpenGL 2D Event listener
 *
 * @author Tymon Tobolski
 */
class EventListener extends OpenGL2DEventListener {
	import GL._

	def init {
		gl.glShadeModel(GL_SMOOTH)
		gl.glClearColor(0f, 0f, 0f, 0f)
		gl.glDepthFunc(GL_LEQUAL)
		//		gl.glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST)

		//		gl.glEnable(GL.GL_POINT_SMOOTH);
		//    gl.glEnable(GL.GL_LINE_SMOOTH);
		//    gl.glEnable(GL.GL_POLYGON_SMOOTH);

		//		gl.glBlendFunc(GL_SRC_ALPHA_SATURATE, GL_ONE)
		//		gl.glEnable(GL_BLEND)
		//		gl.glEnable(GL_POLYGON_SMOOTH)


		enable2D
	}

	def reshape(x: Int, y: Int, width: Int, height: Int) {
		enable2D
	}

	def displayChanged(modeChanged: Boolean, deviceChanged: Boolean) {}

	def display {
		clear(GL_COLOR_BUFFER_BIT)
		loadIdentity

		pushMatrix
//		translate(100, 100)
//		fill(0xFF, 0, 0)
//		rect(0, 0, 300, 300)
//		rotate(63)
//		fill(0f, 0f, 1f)
//		rect(0, 0, 300, 300)
		popMatrix

		gl.glFinish
		gl.glFlush
	}
}

