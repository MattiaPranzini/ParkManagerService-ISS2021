import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import alice.tuprolog.Term
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.delay
import alice.tuprolog.Struct
import java.util.Timer
import kotlin.random.Random

class outSonar(name: String) : ActorBasic(name) {

	val DTFREE = 4
	var timer = 0

	private val mainScope = CoroutineScope(Dispatchers.Default)

	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi
	override suspend fun actorBody(msg: ApplMessage) {
		if (msg.msgType() == "event" && msg.msgId() == "arrivoauto") {
			start()
		} else if (msg.msgType() == "event" && msg.msgId() == "autospostata") {
			stop()
		}

	}

	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi
	suspend fun start() {
		println("START TIMER")
		
		startTimer()
	}

	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi
	suspend fun stop() {
		mainScope.cancel()
		val uscitaLiberata = MsgUtil.buildEvent(name, "uscitalibera", "uscitalibera")
		emit(uscitaLiberata)
		println("USCITA LIBERATA")
	}

	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi
	fun startTimer() {
		mainScope.launch {
			timer = 0
			while (timer < DTFREE) {
				timer++
				delay(1000)
				println("TIMER a " +timer)
			}

			val timeout = MsgUtil.buildEvent(name, "timeout", "timeout")
			emit(timeout)
			//val timeout1 = MsgUtil.buildDispatch(name, "managerservice", "timeout1", "timeout1")
			//forward(name, "timeout1", "managerservice")
			println("EMESSO ALLARME")
		}
	}

}