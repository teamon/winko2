package com.yayetee.winko

import apps.demo.Demo
import com.yayetee.Rectangle
import com.yayetee.Tools._

class Button(index: Int) extends GfxNode {
	val boundingRect = new Rectangle(100 + index*150, 100, 100 x 100)
	
	def display(v: View){
		v.fill(0xFF, 0, 0)
		v.rect(boundingRect)

		v.translate(200, 200)
		v.fill(255, 0, 0)
		v.rect(0, 0, 30, 30)

		v.fill(0xFF, 0, 0)
		v.ring(0, 0, 60, 10)
		v.fill(0, 0, 0xFF)
		v.arc(0, 0, 60, 10, 2)

	}

	override def contains(cursor: MashCursor) = boundingRect.contains(cursor.x, cursor.y)
}

object AppLauncher extends Application {
	val applications = List(Demo)
	
	override def start {
		applications.zipWithIndex.foreach {
			case (app, i) => addGfxNode(new Button(i) {
				onCursorDown(cur => {
					Mash.logger.debug("Run " + app.name + " application")
					Mash.run(Demo)
				})
			})
		}

	}
	
	def createSymbol(symbolID: Int, xpos: Double, ypos: Double, angle: Double) = {
		Mash.logger.debug("createSymbol appLauncher")		
		new MashSymbol(xpos, ypos, angle)
	}

	def createCursor(xpos: Double, ypos: Double) = new MashCursor(xpos, ypos)
}
