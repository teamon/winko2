package com.yayetee.winko.apps.demo

import com.yayetee.Rectangle
import com.yayetee.Tools._
import com.yayetee.winko._

class DemoSymbol(xp: Float, yp: Float, a:Float) extends MashSymbol(xp, yp, a) {
	Mash.logger.debug("DemoSymbol create")
	addGfxNode(new Square(this))
}

class Square(val parent: MashSymbol) extends GfxNode {
	def boundingRect = new Rectangle(parent.x, parent.y, 100 x 100)

	onCursorDown(cur => Mash.logger.debug("DON`T TOUCH ME!"))

	def display(v: View) {
		v.fill(0xFF, 0, 0)
//		v.rotate(30)
		v.rect(boundingRect)
	}

	override def contains(cursor: MashCursor) = {
		Mash.logger.debug("cursor: " + cursor.x + ", " + cursor.y)
		Mash.logger.debug("parent: " + parent.x + ", " + parent.y)
		boundingRect.contains(cursor.x, cursor.y)
	}
}

object Demo extends Application {
	override def name = "Demo"

	def createSymbol(symbolID: Int, xpos: Float, ypos: Float, angle: Float) = symbolID match {
		case _ => new DemoSymbol(xpos, ypos, angle)
	}

	def createCursor(xpos: Float, ypos: Float) = new MashCursor(xpos, ypos)
}


