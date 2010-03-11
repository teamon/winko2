package com.yayetee.winko.engine

import collection.mutable.ListBuffer


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

	/**
	 * Add onUpdate hook
	 *
	 * @author teamon
	 */
	def onUpdate(f: => Unit) {
		onUpdateHooks.append(f _)
	}

	/**
	 * Add onRemove hook
	 *
	 * @author teamon
	 */
	def onRemove(f: => Unit) {
		onRemoveHooks.append(f _)
	}

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

}
