package com.yayetee.opengl

import javax.swing.JFrame
import java.awt._
import java.awt.event.{WindowEvent, WindowAdapter}
import com.sun.opengl.util.FPSAnimator
import javax.media.opengl.{GLCapabilities, GLCanvas, GLEventListener}

/**
 * Main OpenGL display class
 *
 * @author Tymon Tobolski
 */

class OpenGLDisplay(title: String, val width: Int, val height: Int, val eventListener: GLEventListener, val fullscreen: Boolean, caps: GLCapabilities) {
	final val DONT_CARE = -1
	var usedDevice: GraphicsDevice = _

	val glCanvas = new GLCanvas(caps)
	glCanvas.setSize(width, height)
	glCanvas.setIgnoreRepaint(true)
	glCanvas.addGLEventListener(eventListener)

	val frame = new JFrame(title)
	frame.getContentPane.setLayout(new BorderLayout)
	frame.getContentPane.add(glCanvas, BorderLayout.CENTER)

	val animator = new FPSAnimator(glCanvas, 500)
	animator.setRunAsFastAsPossible(true)

	def this(title: String, width: Int, height: Int, eventListener: GLEventListener) = this (title, width, height, eventListener, false, new GLCapabilities)

	def this(title: String, eventListener: GLEventListener) = this (title, 640, 640, eventListener)

	def this(title: String, eventListener: GLEventListener, fullscreen: Boolean) = this (title, 640, 640, eventListener, fullscreen, new GLCapabilities)


	def start {
		frame.setUndecorated(fullscreen)
		frame.addWindowListener(new WindowAdapter {
			override def windowClosing(e: WindowEvent) {
				stop
				exit(0)
			}
		})

		if (fullscreen) {
			usedDevice = GraphicsEnvironment.getLocalGraphicsEnvironment.getDefaultScreenDevice

			usedDevice.setFullScreenWindow(frame)
			usedDevice.setDisplayMode(findDisplayMode(
				usedDevice.getDisplayModes,
				width, height,
				usedDevice.getDisplayMode.getBitDepth,
				usedDevice.getDisplayMode.getRefreshRate
				))
		} else {
			val screenSize = Toolkit.getDefaultToolkit.getScreenSize
			frame.setSize(frame.getContentPane.getPreferredSize)
			frame.setLocation(
				(screenSize.width - frame.getWidth) / 2,
				(screenSize.height - frame.getHeight) / 2
				)
			frame.setVisible(true)
		}

		glCanvas.requestFocus
		animator.start
	}

	def stop {
		animator.stop
		if (fullscreen) {
			usedDevice.setFullScreenWindow(null)
			usedDevice = null
		}
		frame.dispose
	}

	private def findDisplayMode(displayModes: Array[DisplayMode], requestedWidth: Int, requestedHeight: Int, requestedDepth: Int, requestedRefreshRate: Int) = {
		var displayMode = findDisplayModeInternal(displayModes, requestedWidth, requestedHeight, requestedDepth, requestedRefreshRate)

		if (displayMode == null)
			displayMode = findDisplayModeInternal(displayModes, requestedWidth, requestedHeight, DONT_CARE, DONT_CARE)

		if (displayMode == null)
			displayMode = findDisplayModeInternal(displayModes, requestedWidth, DONT_CARE, DONT_CARE, DONT_CARE)

		if (displayMode == null)
			displayMode = findDisplayModeInternal(displayModes, DONT_CARE, DONT_CARE, DONT_CARE, DONT_CARE)

		displayMode
	}

	private def findDisplayModeInternal(displayModes: Array[DisplayMode], requestedWidth: Int, requestedHeight: Int, requestedDepth: Int, requestedRefreshRate: Int) = {
		displayModes.toList.find(dm =>
			(requestedWidth == DONT_CARE || requestedWidth == dm.getWidth) &&
							(requestedHeight == DONT_CARE || requestedHeight == dm.getHeight) &&
							(requestedDepth == DONT_CARE || requestedDepth == dm.getBitDepth) &&
							(requestedRefreshRate == DONT_CARE || requestedDepth == dm.getRefreshRate)
			).getOrElse(displayModes(0))
	}

}
