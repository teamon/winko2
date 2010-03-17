package com.yayetee.winko.engine

import collection.mutable.ListBuffer
import com.yayetee.winko.{MashFinger}

/**
 * Hooks trait
 *
 * Mix it in any class to get hooks for free
 *
 * Example:
 *  class Foo extends Hooks  {
 *    onUpdate  {
 *      println("UPDATED!")
 * }
 * }
 *
 *  val f = new Foo
 *  f.runOnUpdateHooks
 *
 * @author teamon
 */

trait Hooks {
	val onUpdateHooks = new ListBuffer[() => Unit]
	val onRemoveHooks = new ListBuffer[() => Unit]
	val onFingerDownHooks = new ListBuffer[(MashFinger) => Unit]

	/**
	 * Add onUpdate hook called on object state update
	 *
	 * @author teamon
	 */
	def onUpdate(f: => Unit) = onUpdateHooks.append(f _)

	/**
	 * Add onRemove hook called on object removal
	 *
	 * @author teamon
	 */
	def onRemove(f: => Unit) = onRemoveHooks.append(f _)

	/**
	 * Add onFingerDown hook called on new finger click within object bounds
	 *
	 * @author teamon
	 */
	def onFingerDown(f: (MashFinger) => Unit) = onFingerDownHooks.append(f)

	/**
	 * Run onUpdate hooks
	 *
	 * @author teamon
	 */
	def runOnUpdateHooks = onUpdateHooks.foreach(f => f())

	/**
	 * Run onRemove hooks
	 *
	 * @author teamon
	 */
	def runOnRemoveHooks = onRemoveHooks.foreach(f => f())

	/**
	 * Run onFingerDown hooks
	 *
	 * @author teamon
	 */
	def runOnCursorDownHooks(finger: MashFinger) = onFingerDownHooks.foreach(f => f(finger))

}
