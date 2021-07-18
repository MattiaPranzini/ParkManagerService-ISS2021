package parcheggio

import it.unibo.kactor.ActorBasic
import java.util.Observer
import it.unibo.kactor.ApplMessage
import java.util.Observable
import kotlinx.coroutines.runBlocking
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.QakContext

class BottoneUtente(name: String) : ActorBasic(name), Observer {

    private var present = true;
    private var int : InterfacciaUtente ?= null 

    init {
       println("\t##BOTTONEUTENTE")
		
    }
	
	fun setInterfaccia(interf : InterfacciaUtente, ctx : QakContext?){
		context = ctx
		println(context)
		int = interf
		int!!.addObserver(this)
	}

    override suspend fun actorBody(msg: ApplMessage) {}

    @kotlinx.coroutines.ObsoleteCoroutinesApi
    @kotlinx.coroutines.ExperimentalCoroutinesApi
    override fun update(o: Observable?, arg: Any?) {
        runBlocking {
			if(arg!!.toString().startsWith("CAR ENTER")){
				println("CAR ENTER")
			}
			if(arg.toString().startsWith("RITIRO AUTO")){
				println("RITIRO AUTO")
			}
        }
    }

}
