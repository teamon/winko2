package com.yayetee.winko.apps.demo

import com.yayetee.winko._
import collection.mutable.ListBuffer
import javax.sound.midi._

abstract class Loop(var timeout: Int) extends Thread {
	var keep = true

	def stopIt { keep = false }

	override def run {
		while (keep) {
			tick
			Thread.sleep(timeout)
		}
	}

	def tick
}


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

class Note(xp: Float, yp: Float, a: Float, sym: Int) extends MashSymbol(xp, yp, a) {
	val loop = new Loop(500){
		def tick {
			Midi << Note.on(35, 100)
		}
	}
	loop.start

	
	onUpdate {
		loop.timeout = (angle * 900 / (2*Math.Pi)).toInt + 100
	}

	onRemove {
		loop.stopIt
	}

}

object Midi extends Application {
	var device: Option[MidiDevice] = None
	var receiver: Option[Receiver] = None

	override def name = "MIDI"

	def createSymbol(symbolID: Int, xpos: Float, ypos: Float, a: Float) = symbolID match {
		case _ => new Note(xpos, ypos, a, symbolID)
	}

	def createCursor(xpos: Float, ypos: Float) = new MashCursor(xpos, ypos)

	def <<(msg: MidiMessage) {
		receiver match {
			case Some(r) =>
				r.send(msg, -1)
			case None =>
		}
	}

	override def start {
		device = MidiSystem.getMidiDeviceInfo.toList.find(_.getName == "Java Sound Synthesizer").map(MidiSystem.getMidiDevice(_))


		device.map(d => {
			d.open

			val rec = d.getReceiver
			receiver = if (rec != null) Some(rec) else None
		})

	
		val msg = new ShortMessage
		msg.setMessage(ShortMessage.PROGRAM_CHANGE, 9, 1, 0)
		this << msg
	}

	override def stop {
		receiver.map(_.close)
		device.map(_.close)
	}
}


