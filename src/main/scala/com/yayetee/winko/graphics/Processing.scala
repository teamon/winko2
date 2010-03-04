package com.yayetee.winko.graphics

import processing.core.{PConstants, PApplet}
import java.awt.event.{WindowEvent, WindowAdapter}
import javax.swing.JFrame
import com.yayetee.winko.engine.{Engine}


/**
 * Basic GUI object that uses Processing API
 */

object Processing {
	var useOpenGL = false
	val size = (1024, 768)
	val frame = new JFrame("winko")

	def init {
		frame.addWindowListener(new WindowAdapter {
			override def windowClosing(e: WindowEvent) {System.exit(0)}
		})

		setupApplet
	}

	def setupApplet {
		val applet = new MainWindow
		applet.init
		frame.setVisible(false)
		frame.getContentPane.removeAll
		frame.getContentPane.add(applet)
		frame.pack
		frame.setVisible(true)
	}

	def switchOpenGL {
		useOpenGL = !useOpenGL
		setupApplet
	}
}

trait ProcessingNode {
	def draw(p: PApplet)
}


class MainWindow extends PApplet {
	override def setup {
		if (Processing.useOpenGL) size(Processing.size._1, Processing.size._2, PConstants.OPENGL)
		else size(Processing.size._1, Processing.size._2)

		smooth
		noStroke

		rectMode(PConstants.CENTER)
		ellipseMode(PConstants.CENTER)

		textFont(loadFont("QuicksandBook.vlw"))

		frameRate(100)
	}

	override def keyPressed {
		key.toString.toLowerCase match {
			case "o" => Processing.switchOpenGL
			case "q" => exit
			case _ => ()
		}
	}

	override def draw {
		background(51)

//		Engine.symbols.foreach {
//			case (sid, block) => {
//				pushMatrix
//				pushStyle
//				block.asInstanceOf[ProcessingNode].draw(this)
//				popMatrix
//				popStyle
//			}
//		}

		// info
//		textSize(15)
//		text("fps = " + frameRate.toInt, 20, 20)
//		text("OpenGL:  " + Processing.useOpenGL, 100, 20)
//		text("[O]penGL   |   [Q]uit", 250, 20)
	}
}

