package com.yayetee.winko.apps.demo

import com.yayetee.winko._
import javax.sound.midi._
import com.yayetee.tuio.Pos

abstract class Loop(var timeout: Int) extends Thread {
	var keep = true

	def stopIt {keep = false}

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

trait Loops {
	def loop(timeout: Int, f: Unit) = {
		val l = new Loop(timeout) {def tick {f}}
		l.start
		l
	}
}

class Tempo(pos: Pos) extends MashEmblem(pos) with Loops {
	val tempo = loop(300, {
		Midi << Note.on(35, 100)
	})

	onUpdate {
		tempo.timeout = (position.angle * 900 / (2 * Math.Pi)).toInt + 100
	}

	onRemove {
		tempo.stopIt
	}

}

class Note(pos: Pos) extends MashEmblem(pos) {
	//	val loop = new Loop(500){
	//		def tick {
	//			Midi << Note.on(35, 100)
	//		}
	//	}
	//	loop.start



}

object Midi extends Application {
	var device: Option[MidiDevice] = None
	var receiver: Option[Receiver] = None

	override def name = "MIDI"

	def createEmblem(symbolID: Int, pos: Pos) = symbolID match {
		case _ => new Note(pos)
	}

	def createFinger(pos: Pos) = new MashFinger(pos)

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


