package com.yayetee.winko

import com.yayetee.tuio.{Speed, Pos}

class Fader(pos: Pos, sp: Speed) extends MashEmblem(pos, sp) {
	val e = 0.26
	val PI2 = 2 * Math.Pi
	val prevSpeed = Speed()
	var angle = 0.0
	var angleDiff = 0.0

	onUpdate {
		if (speed.rotation != 0) {
			if (speed.rotation * prevSpeed.rotation < 0) { // change direction
				if (state != 0) angleDiff = PI2 - position.angle
			} else {
				if (speed.rotation > 0 && state == 1) { // incresing
					angle = PI2
				} else if (speed.rotation < 0 && state == -1) { // decresing
					angle = 0
				} else {
					angle = position.angle + angleDiff
				}

				if (angle > PI2) angle -= PI2
			}
		}

		prevSpeed.update(speed)
	}

	def value = ((100 * angle) / PI2).toInt

	def state = if (angle + e > PI2) 1 else if (angle - e < 0) -1 else 0

}

//  def paint(p: PApplet) {
//    p.noStroke
//    p.fill(255, 255, 255, 100)
//    p.ellipse(0, 0, 60, 60)
//    p.fill(255, 255)
//    p.arc(0, 0, 60, 60, 0, realAngle.toFloat)
//    p.fill(GUI.BackgroundColor)
//    p.ellipse(0, 0, 40, 40)
//    p.fill(200)
//    p.textSize(15)
//    p.text(level.toString + "%", 30, 30)
//  }
//
