package com.yayetee.winko.graphics

import javax.media.opengl.GL
import com.yayetee.opengl.{OpenGLDisplay, OpenGL2DEventListener}

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
		gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST)

		enable2D
	}

	def reshape(x: Int, y: Int, width: Int, height: Int) {}

	def displayChanged(modeChanged: Boolean, deviceChanged: Boolean) {}

	def display {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT)
		gl.glLoadIdentity


		def drawRectangle_ZPlane(posX: Float, posY: Float, sizeX: Float, sizeY:Float) {
			gl.glBegin(GL_QUADS)
			gl.glVertex2d(posX,       posY+sizeY)
			gl.glVertex2d(posX,       posY)
			gl.glVertex2d(posX+sizeX, posY)
			gl.glVertex2d(posX+sizeX, posY+sizeY)
			gl.glEnd
		}

		gl.glPushMatrix
		gl.glTranslated(100, 100, 0)
		gl.glColor3d(1,0,0)
		drawRectangle_ZPlane(0, 0, 30, 30)

		gl.glRotated(60, 0, 0, 1)

		gl.glColor3d(0, 0, 1)
		drawRectangle_ZPlane(0, 0, 30, 30)
		
		gl.glPopMatrix
		
		//drawRectangle_ZPlane(100, 100, 30, 30)

//		glu.gluOrtho2D()

//		gl.glPushMatrix
//			gl.glTranslated(0.5, 0.5, 0)
//			gl.glRotated(30, 0, 0, 1)
//			gl.glBegin(GL.GL_LINES)
//				gl.glColor3d(1, 0, 0)
//				gl.glVertex2d(-1.0, 0.0)
//        gl.glVertex2d(1, 0.0)
//
//				gl.glColor3d(0, 0, 1)
//        gl.glVertex2d(0.0, -1.0)
//        gl.glVertex2d(0.0, 1.0)
//      gl.glEnd
//		gl.glPopMatrix

//		gl.glPushMatrix
//			gl.glRotated(30, 0, 0, 1)
//			gl.glBegin(GL.GL_QUADS)
//				gl.glColor3d(0, 1, 0)
//				gl.glVertex2d(0.2, 0.2)
//				gl.glVertex2d(0.4, 0.2)
//				gl.glVertex2d(0.4, 0.4)
//				gl.glVertex2d(0.2, 0.4)
//			gl.glEnd
//		gl.glPopMatrix


//		disable2D
	}
}


object OpenGL {
	def main(args: Array[String]) {
		val display = new OpenGLDisplay("TEST", new EventListener)
		display.start
	}
}
