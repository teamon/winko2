package com.yayetee.tuio

/**
 * Factory
 *
 * Generic interface for Tuio objects factory
 *
 * @author teamon
 */


trait Factory[E, F] {
	def createEmblem(symbolID: Int, pos: Pos): E

	def createFinger(pos: Pos): F
}
