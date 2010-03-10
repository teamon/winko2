package com.yayetee.winko.engine

import collection.mutable.{HashMap, ListBuffer}
import com.yayetee.tuio.Logger

/**
 * Basic callbacks trait
 *
 * @author teamon
 */

trait Hooks {
	val onUpdateHooks = new ListBuffer[() => Unit]
	val onRemoveHooks = new ListBuffer[() => Unit]

	def onUpdate(f: => Unit){
		onUpdateHooks.append(f _)
	}

	def onRemove(f: => Unit){
		onRemoveHooks.append(f _)
	}

	def runOnUpdateHooks = onUpdateHooks.foreach(f => f())
	def runOnRemoveHooks = onUpdateHooks.foreach(f => f())

}
