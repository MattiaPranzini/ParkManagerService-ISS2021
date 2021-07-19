import org.eclipse.californium.core.CoapClient
import org.eclipse.californium.core.CoapResponse
import org.eclipse.californium.core.coap.MediaTypeRegistry
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.ApplMessage
import java.util.Scanner
import org.eclipse.californium.core.CoapHandler
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.launch
import org.junit.Test
import org.junit.Assert.assertTrue
import kotlinx.coroutines.Job
import it.unibo.kactor.QakContext
import kotlinx.coroutines.cancelAndJoin
import java.net.Socket
import java.io.BufferedReader
import java.io.InputStreamReader

internal class Test2 {

	var job: Job? = null

	@Test
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi
	fun mainTest() {
		var s = java.io.File("log.txt").readLines()
		var i = 0
		while (i < s.size) {
			if (s.get(i).equals("HOME")) {
				i++
				while (s.get(i).length > 10) {
					assertTrue(!s.get(i).contains("false", false))
					i++
				}
				assertTrue(s.get(i).equals("(0, 0)"))
			} else if (s.get(i).equals("INDOOR")) {

				i++
				while (s.get(i).length > 10) {
					println(s.get(i))
					assertTrue(!s.get(i).contains("false", false))
					println("FINE")
					i++
				}
				println(s.get(i))
				assertTrue(s.get(i).equals("(5, 0)"))
			} else if (s.get(i).equals("OUTDOOR")) {
				i++
				while (s.get(i).length > 10) {
					assertTrue(!s.get(i).contains("false", false))
					i++
				}
				println(s.get(i))
				assertTrue(s.get(i).equals("(5, 4)"))
			} else if (s.get(i).startsWith("SLOT")) {
				val x = s.get(i)[5].toString()
				val y = s.get(i)[7].toString()
				i++
				while (s.get(i).length > 10) {
					assertTrue(!s.get(i).contains("false", false))
					i++
				}
				println(s.get(i))
				assertTrue(s.get(i).equals("($x, $y)"))
			}
			i++
		}

	}


}
    


