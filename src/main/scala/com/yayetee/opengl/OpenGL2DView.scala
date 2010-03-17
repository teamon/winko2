package com.yayetee.opengl

import javax.media.opengl.{GLAutoDrawable, GLEventListener, GL}
import javax.media.opengl.glu.GLU
import com.yayetee.{RectMode, Rectangle}
import Math._

/**
 * OpenGL 2D View 
 *
 * @author teamon
 */
abstract class OpenGL2DView extends GLEventListener {
	import GL._
	
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
		gl.glOrtho(vPort.get(0), vPort.get(0) + vPort.get(2), vPort.get(1) + vPort.get(3), vPort.get(1), -1, 1)

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
	

	// helpers



	var rectMode = RectMode.Center

	def rect(r: Rectangle) { rect(r.x, r.y, r.width, r.height) }

	def rect(x: Double, y: Double, width: Double, height: Double) {
		if(rectMode == RectMode.Center) drawRect(x-width/2, y-height/2, width, height)
		else drawRect(x, y, width, height)
	}

	protected def drawRect(x: Double, y: Double, width: Double, height: Double){
		gl.glBegin(GL_QUADS)
		gl.glVertex2d(x, y + height)
		gl.glVertex2d(x, y)
		gl.glVertex2d(x + width, y)
		gl.glVertex2d(x + width, y + height)
		gl.glEnd
	}

	/**
	 * Draws a circle with center at the (x,y) point
	 *
	 * @author teamon
	 *
	 * @param x x-coordinate of circle center
	 * @param y y-coordinate of circle center
	 * @param radius radius of circle
	 */
	def circle(x: Double, y: Double, radius: Double) {
		ellipse(x, y, radius, radius)
	}

	def ring(x: Double, y: Double, radius: Double, weight: Double) {
		ellipseRing(x, y, radius, radius, weight)
	}

	def arc(x: Double, y: Double, radius: Double, weight: Double, angle: Double){
		ellipseArc(x, y, radius, radius, weight, angle)
	}

	def ellipse(x: Double, y: Double, rx: Double, ry: Double) {
		gl.glBegin(GL.GL_LINES)

		(0.0 to Pi).by(0.001).foreach(a => {
			val cosa = cos(a)
			val sina = sin(a)
			gl.glVertex2d(x + cosa*rx, y + sina*ry)
			gl.glVertex2d(x - cosa*rx, y - sina*ry)
		})

		gl.glEnd
	}

	def ellipseRing(x: Double, y: Double, rx :Double, ry: Double, weight: Double) {
		ellipseArc(x, y, rx, ry, weight, 2*Pi)
	}

	def ellipseArc(x: Double, y: Double, rx: Double, ry: Double, weight: Double, angle: Double) {
		val rx_ = rx + weight
		val ry_ = ry + weight

		gl.glBegin(GL.GL_LINES)

		(0.0 to angle).by(0.001).foreach(a => {
			val cosa = cos(a)
			val sina = sin(a)
			gl.glVertex2d(x + cosa*rx, y + sina*ry)
			gl.glVertex2d(x + cosa*rx_, y + sina*ry_)
		})

		gl.glEnd
	}

	def pushMatrix = gl.glPushMatrix

	def popMatrix = gl.glPopMatrix

	def clear(c: Int) = gl.glClear(c)

	def loadIdentity = gl.glLoadIdentity

	def translate(x: Double, y: Double) = gl.glTranslated(x, y, 0)

	def rotate(a: Double) = gl.glRotated(a, 0, 0, 1)

	def fill(r: Double, g: Double, b: Double) = gl.glColor3d(r, g, b)


	def fill(r: Int, g: Int, b: Int) = gl.glColor3f(r / 255f, g / 255f, b / 255f)

	def fill(rgb: Int):Unit = fill(rgb/(256*256), (rgb/256)%256 , rgb%256)

}

