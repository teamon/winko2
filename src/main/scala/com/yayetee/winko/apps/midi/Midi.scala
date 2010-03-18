package com.yayetee.winko.apps.demo

import com.yayetee.winko._
import apps.Loops
import javax.sound.midi._
import com.yayetee.tuio.{Speed, Pos}
import collection.mutable.{HashMap, ListBuffer}
import com.yayetee.RectMode

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

class TempoGfx(val parent: Tempo) extends GfxNode {
	def display(view: View) {
		view.translate(parent.x, parent.y)
		view.fill(200, 200, 200)
		view.arc(0, 0, 70, 20, 2 * Math.Pi)
		view.fill(50, 50, 50)
		view.arc(0, 0, 70, 20, parent.angle)
	}
}

case class TempoWave(val tempo: Tempo, val note: Note) extends GfxNode {
	def display(view: View) {
		val dist = tempo.position.distanceTo(note.position)
		// TODO: translate(position)

		Mash.logger.debug("angle = " + tempo.position.angleTo(note.position))

		view.translate(tempo.position.x, tempo.position.y)
		view.rotate(-tempo.position.angleTo(note.position))
		view.fill(255, 0, 0)
		view.rect(dist/2, 0, dist-200, 50)
		
	}
}

case class TempoTick

class Tempo(pos: Pos, sp: Speed) extends Fader(pos, sp) with Loops {
	val tempo = loop(300) {
		//Midi << Note.on(35, 100)
		notes.foreach {case (note, gfx) => note << TempoTick()}
	}

	val notes = new HashMap[Note, GfxNode]

	addGfxNode(new TempoGfx(this))

	onUpdate {
		tempo.timeout = value * 9 + 100

		val activeNotes = new ListBuffer[Note]()

		closeNotes(Midi.TEMPO_RANGE).foreach(note => {
			if (!notes.contains(note)) {
				val wave = TempoWave(this, note)
				addGfxNode(wave)
				notes += (note -> wave)
			}

			activeNotes += note
		})

		Mash.logger.debug(activeNotes)

		notes.foreach {
			case (note, gfx) => {
				if (!activeNotes.contains(note)) {
					removeGfxNode(gfx)
					notes.remove(note)
				}
			}
		}
	}

	onRemove {
		tempo.stopIt
	}

	def closeNotes(distance: Double) = Mash.emblems.filter {
		case (sid, emb) => {
			Mash.logger.debug(this.position.distanceTo(emb.position))
			emb != this && emb.isInstanceOf[Note] && this.position.distanceTo(emb.position) < distance
		}
	}.map {case (sid, emb) => emb.asInstanceOf[Note]}.toList
}

class NoteGfx(val parent: Note) extends GfxNode {
	def display(view: View) {
		view.translate(parent.x, parent.y)
		view.fill(200, 200, 200)
		view.arc(0, 0, 70, 20, 2 * Math.Pi)
		view.fill(255, 0, 0)
		view.arc(0, 0, 70, 20, 2*Math.Pi / parent.division)
	}
}

// 1, 2, 4, 8, 16

class Note(pos: Pos, sp: Speed, val note: Int) extends Fader(pos, sp) {
	addGfxNode(new NoteGfx(this))

	onUpdate {
		closeTempos(Midi.TEMPO_RANGE+100).foreach(_.runOnUpdateHooks)
	}

	var ticks = 0

	def division = Math.pow(2, (value.toInt/25).toInt)

	def <<(tick: TempoTick) {
		ticks += 1
		if (ticks >= division) {
			Midi << Note.on(note, 100)
			ticks = 0
		}
	}

	def closeTempos(distance: Double) = Mash.emblems.filter {
		case (sid, emb) => emb != this && emb.isInstanceOf[Tempo] && this.position.distanceTo(emb.position) < distance
	}.map {case (sid, emb) => emb.asInstanceOf[Tempo]}.toList
}

object Midi extends Application {
	val TEMPO_RANGE = 400
	var device: Option[MidiDevice] = None
	var receiver: Option[Receiver] = None

	override def name = "MIDI"

	override def emblem(symbolID: Int, pos: Pos, speed: Speed) = symbolID match {
		case 0 => new Tempo(pos, speed)
		case 4 => new Note(pos, speed, 48)
		case 7 => new Note(pos, speed, 35)
		case _ => 
	}

	def <<(msg: MidiMessage) = receiver foreach (_.send(msg, -1))

	def findDevice = {
		val devices = MidiSystem.getMidiDeviceInfo.toList
		val emu = devices.filter(_.getName == "E-MU 0404 | USB")

		if (emu.size > 1) Some(emu(1))
		else devices.find(_.getName == "Java Sound Synthesizer")
	}

	override def start {
		device = findDevice.map(MidiSystem.getMidiDevice(_))

		device.map(d => {
			Mash.logger.debug("MidiDevice: " + d.getDeviceInfo.getName)
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
		receiver foreach (_.close)
		device foreach (_.close)
	}
}
