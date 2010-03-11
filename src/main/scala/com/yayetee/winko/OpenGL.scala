package com.yayetee.winko

import com.yayetee.opengl.OpenGL2DView
import javax.media.opengl.GL
import collection.mutable.ListBuffer
import com.yayetee.winko.engine.Hooks


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

		Mash.app.displayGfxNodes(this)

		Mash.symbols.foreach {
			case (sid, sym) => {
				pushMatrix
				sym.displayGfxNodes(this)
				popMatrix
			}
		}

		Mash.cursors.foreach {
			case (sid, cur) => {
				pushMatrix
				cur.displayGfxNodes(this)
				popMatrix
			}
		}

		gl.glFinish
		gl.glFlush
	}
}

trait GfxNodesManager {
	val gfxNodes = new ListBuffer[GfxNode]

	def addGfxNode(node: GfxNode){
		gfxNodes += node
	}

	def displayGfxNodes(view: View) {
		gfxNodes.foreach(_.display(view))
	}
}

trait GfxNode extends Hooks {
	def display(view: View)
	def contains(cursor: MashCursor) = false
}
