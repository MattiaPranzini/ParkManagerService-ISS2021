package parcheggio

import it.unibo.kactor.ActorBasic
import java.util.Observer
import it.unibo.kactor.ApplMessage
import java.util.Observable
import kotlinx.coroutines.runBlocking
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.QakContext

class StartStop(name: String) : ActorBasic(name), Observer {

    private var present = true;
    private var int : ParkServiceStatusGUI ?= null 

    init {
       println("\t##STARTSTOP")
		
    }
	
	fun setInterfaccia(interf : ParkServiceStatusGUI, ctx : QakContext?){
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
			if (present) {
				present = false;
				println("PRESENT $present")
				//val stop = MsgUtil.buildEvent(name, "stop", "stop")
				//emit(stop)
				emit("stop", "stop(0)")
			} else {
				present = true;
				//val start = MsgUtil.buildEvent(name, "start", "start")
				//emit(start)
				emit("start", "start(0)")
				println("PRESENT $present")
			}
        }
    }

}
