package com.yayetee.winko

import com.yayetee.opengl.OpenGL2DView
import javax.media.opengl.GL
import collection.mutable.ListBuffer


/**
 * OpenGL 2D View
 *
 * @author teamon
 */
class OpenGLView extends OpenGL2DView {
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

		Mash.symbols.foreach {
			case (sid, sym) => {
				pushMatrix
				sym.displayGfxNodes(this)
				popMatrix
			}
		}

		gl.glFinish
		gl.glFlush
	}
}

trait OpenGLNodesManager {
	val gfxNodes = new ListBuffer[OpenGLNode]

	def addGfxNode(node: OpenGLNode){
		gfxNodes += node
	}

	def displayGfxNodes(view: OpenGLView) {
		gfxNodes.foreach(_.display(view))
	}
}

trait OpenGLNode {


	def display(view: OpenGLView)
}
