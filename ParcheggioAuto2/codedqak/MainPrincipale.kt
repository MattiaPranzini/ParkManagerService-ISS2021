package it.unibo.ctxParcheggio

import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import Main2

suspend fun main() {
	coroutineScope{
		launch{
	    Main2().avvia()
		println("World 1")
			
	}
	coroutineScope{
		launch{
		 QakContext.createContexts(
           "localhost", this, "parcheggio.pl", "sysRules.pl")
		println("World 2")
	}
	}
	
	}

	println("Hello")
	println("Done")
}