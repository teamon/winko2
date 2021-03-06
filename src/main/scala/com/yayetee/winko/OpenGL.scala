package com.yayetee.winko

import com.yayetee.opengl.OpenGL2DView
import collection.mutable.ListBuffer
import com.yayetee.winko.engine.Hooks
import javax.media.opengl.GL


/**
 * OpenGL 2D View
 *
 * @author teamon
 */
class View extends OpenGL2DView {
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

		translate(240, 0)
		fill(255, 255, 255)
		rect(320, 240, 640, 480)

		Mash.app.displayGfxNodes(this)

		Mash.emblems.foreach {
			case (sid, emb) => {
				pushMatrix
				emb.displayGfxNodes(this)
				popMatrix
			}
		}

		Mash.fingers.foreach {
			case (sid, fin) => {
				pushMatrix
				fin.displayGfxNodes(this)
				popMatrix
			}
		}

		gl.glFinish
		gl.glFlush
	}
}

trait GfxNodesManager {
	val gfxNodes = new ListBuffer[GfxNode]

	def addGfxNode(node: GfxNode) {
		Mash.logger.debug("addGfxNode " + node)
		gfxNodes += node
	}

	def removeGfxNode(node: GfxNode) {
		gfxNodes -= node
	}

	def displayGfxNodes(view: View) {
		gfxNodes.foreach(gfx => {
			view.pushMatrix
			gfx.display(view)
			view.popMatrix
		})
	}
}

trait GfxNode extends Hooks {
	def display(view: View)

	def contains(finger: MashFinger) = false
}
