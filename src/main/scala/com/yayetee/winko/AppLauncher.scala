package com.yayetee.winko

import com.yayetee.Rectangle
import com.yayetee.Tools._

class Button(val parent: Application) extends GfxNode {
	val boundingRect = new Rectangle(100, 100, 100 x 100)
	
	def display(v: View){
		v.fill(0xFF, 0, 0)
		v.rect(boundingRect)
	}

	override def contains(cursor: MashCursor) = boundingRect.contains(cursor.x, cursor.y)
}

object AppLauncher extends Application {
	def start {
		addGfxNode(new Button(this){
			onCursorDown(cur => Mash.logger.debug("DON`T TOUCH ME!"))		
		})
	}
	
	def createSymbol(symbolID: Int, xpos: Float, ypos: Float) = new MashSymbol(xpos, ypos)

	def createCursor(xpos: Float, ypos: Float) = new MashCursor(xpos, ypos)
}
