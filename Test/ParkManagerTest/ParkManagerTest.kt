package utils

import org.junit.Assert.*
import java.net.UnknownHostException
import org.junit.BeforeClass
import cli.System.IO.IOException
import org.junit.Test
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.channels.Channel
import it.unibo.kactor.QakContext
import org.junit.Before
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import org.junit.AfterClass
import it.unibo.kactor.sysUtil
import it.unibo.kactor.ApplMessage
import org.junit.After
 
 
class ParkManagerTest {
		
	companion object{
		var testingObserver   : CoapObserverForTesting ? = null
		var systemStarted         = false
		val channelSyncStart      = Channel<String>()
		//var testingObserver       : CoapObserverForTesting? = null
		var myactor               : ActorBasic? = null
		var counter               = 1
		@JvmStatic
        @BeforeClass
		//@Target([AnnotationTarget.FUNCTION]) annotation class BeforeClass
		//@Throws(InterruptedException::class, UnknownHostException::class, IOException::class)
		fun init() {
			GlobalScope.launch{
				it.unibo.ctxbasicrobot.main() //keep the control
			}
			GlobalScope.launch{
				myactor=QakContext.getActor("basicrobot")
 				while(  myactor == null )		{
					println("+++++++++ waiting for system startup ...")
					delay(500)
					myactor=QakContext.getActor("basicrobot")
				}				
				delay(2000)	//Give time to move lr
				channelSyncStart.send("starttesting")
			}		 
		}//init
		
		@JvmStatic
	    @AfterClass
		fun terminate() {
			println("terminate the testing")
		}
		
	}//companion object
	
	@Before
	fun checkSystemStarted()  {
		testingObserver = null
		println("\n=================================================================== ") 
	    println("+++++++++ BEFOREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE testingObserver=$testingObserver")
		if( ! systemStarted ) {
			runBlocking{
				channelSyncStart.receive()
				systemStarted = true
			    println("+++++++++ checkSystemStarted resumed ")
			}			
		} 
		if( testingObserver == null) testingObserver = CoapObserverForTesting("obstesting${counter++}")
  	}
	
	@After
	fun removeObs(){
		println("+++++++++ AFTERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR  ${testingObserver!!.name}")
		testingObserver!!.terminate()
		testingObserver = null
		runBlocking{
			delay(1000)
		}
 	}
    
	@Test
	fun manager(){
 		println("+++++++++ testmanager ")
		//Send a command and look at the result
		var result  = ""
		runBlocking{
 			val channelForObserver = Channel<String>()
			
			val accptin = MsgUtil.buildEvent("parcheggio", "acceptin", "acceptin")
			MsgUtil.sendMsg(accptin, myactor!!)
		
			
		    val cmd = MsgUtil.buildRequest("parcheggio", "carenter", "carenter", "parcheggio")
			MsgUtil.sendMsg(cmd, myactor!!)
			
			testingObserver!!.addObserver( channelForObserver,"stato(WORKING)")
			result = channelForObserver.receive()
			println("+++++++++ testmanager l RESULT=$result")
			assertEquals( result, "stato(WORKING)")
			delay(200)
			
			testingObserver!!.addObserver( channelForObserver,"matrice")
			result = channelForObserver.receive()
			println("+++++++++ testmanager l RESULT=$result")
			assertEquals( result, "matrice(1)")
			delay(200)
			
			testingObserver!!.addObserver( channelForObserver,"stato(STOP)")
			val stop = MsgUtil.buildEvent("parcheggio", "stop", "stop")
			MsgUtil.sendMsg(stop, myactor!!)
			result = channelForObserver.receive()
			println("+++++++++ testmanager l RESULT=$result")
			assertEquals( result, "stato(STOP)")
			delay(200)
			
			
			
			testingObserver!!.addObserver( channelForObserver,"stato(IDLE)")
			val start = MsgUtil.buildEvent("parcheggio", "start", "start")
			MsgUtil.sendMsg(start, myactor!!)
			result = channelForObserver.receive()
			println("+++++++++ testmanager l RESULT=$result")
			assertEquals( result, "stato(IDLE)")
			delay(200)
			
			
			
			/*
			testingObserver!!.addObserver( channelForObserver,"pos((0, 0))")
			result = channelForObserver.receive()
			println("+++++++++ testmanager r RESULT=$result")
			assertEquals( result, "pos((0, 0))")
			*/
			delay(5000)
			val acceptout = MsgUtil.buildRequest("parcheggio", "acceptout", "acceptout(H000)", "parcheggio")
			MsgUtil.sendMsg(acceptout, myactor!!)
//			result = channelForObserver.receive()
//			println("+++++++++  testmanager RESULT=$result ")	
//			assertEquals( result, "response(true)")
			
			testingObserver!!.addObserver( channelForObserver,"pos((5, 4))" )
			result = channelForObserver.receive()
			println("+++++++++  testmanager RESULT=$result ")	
			assertEquals( result, "pos((5, 4))")
			
			delay(3000)
			
			testingObserver!!.addObserver( channelForObserver,"alarm" )
			result = channelForObserver.receive()
			println("+++++++++  testmanager RESULT=$result ")	
			assertEquals( result, "alarm(outdoor)")
			
			testingObserver!!.addObserver( channelForObserver,"outdoorLibera")
			val uscitaLibera = MsgUtil.buildEvent("parcheggio", "uscitalibera", "uscitalibera")
			MsgUtil.sendMsg(uscitaLibera, myactor!!)
			result = channelForObserver.receive()
			println("+++++++++ testmanager l RESULT=$result")
			assertEquals( result, "outdoorLibera(true)")
			delay(200)
			
			
			 
	/*		result = channelForObserver.receive()
			println("+++++++++ testmanager r RESULT=$result")
			assertEquals( result, "ricevuta(H000)")
			
			testingObserver!!.addObserver( channelForObserver,"pos((1, 1))")
			result = channelForObserver.receive()
			println("+++++++++ testmanager r RESULT=$result")
			assertEquals( result, "pos((1, 1))")
			delay(400)
			testingObserver!!.addObserver( channelForObserver,"pos((0, 0))")
			result = channelForObserver.receive()
			println("+++++++++ testmanager r RESULT=$result")
			assertEquals( result, "pos((0, 0))")*/
		}	
	} 
 

	
 }