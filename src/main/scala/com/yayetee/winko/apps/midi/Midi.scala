package com.yayetee.winko.apps.demo

import com.yayetee.winko._
import apps.Loops
import javax.sound.midi._
import com.yayetee.tuio.Pos
import com.yayetee.Rectangle

object Note {
	def on(data1: Int, data2: Int) = {
		val msg = new ShortMessage
		msg.setMessage(ShortMessage.NOTE_ON, 9, data1, data2)
		msg
	}

	def off(data1: Int, data2: Int) = {
		val msg = new ShortMessage
		msg.setMessage(ShortMessage.NOTE_OFF, 9, data1, data2)
		msg
	}
}



class Tempo extends MashEmblem with Loops {
	val tempo = loop(300) {
		Midi << Note.on(35, 100)
	}

	onUpdate {
		tempo.timeout = (position.angle * 900 / (2 * Math.Pi)).toInt + 100
	}

	onRemove {
		tempo.stopIt
	}

}

//class Button(val parent: Note) extends GfxNode {
//	def boundingRect = new Rectangle(parent.x, parent.y, 100 x 100)
//
//	onFingerDown(cur => Mash.logger.debug("DON`T TOUCH ME!"))
//
//	def display(v: View) {
//		v.fill(0xFF, 0, 0)
//		//		v.rotate(30)
//		v.rect(boundingRect)
//	}
//
//	override def contains(finger: MashFinger) = {
//		Mash.logger.debug("cursor: " + finger.x + ", " + finger.y)
//		Mash.logger.debug("parent: " + parent.x + ", " + parent.y)
//		boundingRect.contains(finger.x, finger.y)
//	}
//}

class Note(pos: Pos) extends MashEmblem(pos) {

}

object Midi extends Application {
	var device: Option[MidiDevice] = None
	var receiver: Option[Receiver] = None

	override def name = "MIDI"

	override def createEmblem(symbolID: Int, pos: Pos) = symbolID match {
		case 0 => new Tempo
		case _ => new Note(pos)
	}

	def <<(msg: MidiMessage) {
		receiver match {
			case Some(r) => r.send(msg, -1)
			case None =>
		}
	}

	def findDevice = {
		val devices = MidiSystem.getMidiDeviceInfo.toList
		val emu = devices.filter(_.getName == "E-MU 0404 | USB")

		if(emu.size > 1) Some(emu(1))
		else devices.find(_.getName == "Java Sound Synthesizer")
	}

	override def start {
		device = findDevice.map(MidiSystem.getMidiDevice(_))

		device.map(d => {
			d.open
			val rec = d.getReceiver
			receiver = if (rec != null) Some(rec) else None
		})

		// set drums!
		val msg = new ShortMessage
		msg.setMessage(ShortMessage.PROGRAM_CHANGE, 9, 1, 0)
		this << msg
	}

	override def stop {
		receiver.map(_.close)
		device.map(_.close)
	}
}


