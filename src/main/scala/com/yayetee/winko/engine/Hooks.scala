package com.yayetee.winko.engine

import collection.mutable.{HashMap, ListBuffer}

/**
 * Basic callbacks trait
 *
 * @author Tymon Tobolski
 */

trait Callbacks {
	val callbacks = new HashMap[String, ListBuffer[() => Unit]]

	def onUpdate(f: => Unit){
		callbacksList("onUpdate").append(f)
	}

	def onRemove(f: => Unit){
		callbacksList("onRemove").append(f)
	}

	def runCallbacks(key: String){
		callbacksList(key).foreach(_())
	}

	protected def callbacksList(key: String): ListBuffer[() => Unit] = {
		if(!callbacks.contains(key)){
			callbacks += (key -> new ListBuffer[() => Unit])
		}
		callbacks.get(key).get
	}
}
