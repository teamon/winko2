package com.yayetee.winko.engine

import collection.mutable.ListBuffer
import com.yayetee.winko.MashCursor


/**
 * Hooks trait
 *
 * Mix it in any class to get hooks for free
 *
 * Example:
 *  class Foo extends Hooks {
 *    onUpdate {
 *      println("UPDATED!")
 *    }
 *  }
 *
 *  val f = new Foo
 *  f.runOnUpdateHooks
 *
 * @author teamon
 */

trait Hooks {
	val onUpdateHooks = new ListBuffer[() => Unit]
	val onRemoveHooks = new ListBuffer[() => Unit]
	val onCursorDownHooks = new ListBuffer[(MashCursor) => Unit]

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
	 * Add onCursorDown hook called on new cursor click within object bounds
	 *
	 * @author teamon
	 */
	def onCursorDown(f: (MashCursor) => Unit) = onCursorDownHooks.append(f)

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
	def runOnRemoveHooks = onUpdateHooks.foreach(f => f())

		/**
	 * Run onCursorDown hooks
	 *
	 * @author teamon
	 */
	def runOnCursorDownHooks(cursor: MashCursor) = onCursorDownHooks.foreach(f => f(cursor))

}
