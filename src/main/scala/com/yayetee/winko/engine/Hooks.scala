package com.yayetee.winko.engine

import collection.mutable.{HashMap, ListBuffer}
import com.yayetee.tuio.Logger

/**
 * Basic callbacks trait
 *
 * @author Tymon Tobolski
 */

trait Hooks {
	val hooks = new HashMap[String, ListBuffer[() => Unit]]

	def onUpdate(f: => Unit){
		hooksList("onUpdate").append(f _)
	}

	def onRemove(f: => Unit){
		hooksList("onRemove").append(f _)
	}

	def runHooks(key: String){
		hooksList(key).foreach(f => f())
	}

	protected def hooksList(key: String): ListBuffer[() => Unit] = {
		if(!hooks.contains(key)){
			hooks += (key -> new ListBuffer[() => Unit])
		}
		hooks.get(key).get
	}
}
