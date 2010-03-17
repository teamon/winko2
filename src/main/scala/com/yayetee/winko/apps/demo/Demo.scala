package com.yayetee.winko.apps.demo

import com.yayetee.Rectangle
import com.yayetee.winko._
import com.yayetee.tuio.Pos
import com.yayetee.Tools._

class DemoEmblem(pos: Pos) extends MashEmblem(pos) {
	Mash.logger.debug("DemoEmblem create")
	addGfxNode(new Square(this))
}

class Square(val parent: MashEmblem) extends GfxNode {
	def boundingRect = new Rectangle(parent.x, parent.y, 100 x 100)

	onFingerDown(cur => Mash.logger.debug("DON`T TOUCH ME!"))

	def display(v: View) {
		v.fill(0xFF, 0, 0)
		//		v.rotate(30)
		v.rect(boundingRect)
	}

	override def contains(finger: MashFinger) = {
		Mash.logger.debug("cursor: " + finger.x + ", " + finger.y)
		Mash.logger.debug("parent: " + parent.x + ", " + parent.y)
		boundingRect.contains(finger.x, finger.y)
	}
}

object Demo extends Application {
	override def name = "Demo"

	override def createEmblem(symbolID: Int, pos: Pos) = symbolID match {
		case _ => new DemoEmblem(pos)
	}

}


