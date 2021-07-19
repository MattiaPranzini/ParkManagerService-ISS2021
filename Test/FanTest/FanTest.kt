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
 
 
class FanTest {
		
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
				myactor=QakContext.getActor("fan")
 				while(  myactor == null )		{
					println("+++++++++ waiting for system startup ...")
					delay(500)
					myactor=QakContext.getActor("fan")
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
	fun fanTest()  {
		println("+++++++++ fanTest ")
		//sysUtil.waitUser("PLEASE, put the robot at HOME", 1000 )
		
		
		runBlocking{
			val channelForObservers = Channel<String>()
			val tmpOn = MsgUtil.buildEvent("parcheggio", "tempalarmon", "tempalarmon")
			MsgUtil.sendMsg(tmpOn, myactor!!)
			delay(200)
			testingObserver!!.addObserver( channelForObservers,"ventilatore")
			var result = channelForObservers.receive()
			println("+++++++++ fanTest r RESULT=$result")
			assertEquals( result, "ventilatore(true)")
			
			val tmpOff = MsgUtil.buildEvent("parcheggio", "tempalarmoff", "tempalarmoff")
			MsgUtil.sendMsg(tmpOff, myactor!!)
			delay(200)
			testingObserver!!.addObserver( channelForObservers,"ventilatore")
			result = channelForObservers.receive()
			println("+++++++++ fanTest r RESULT=$result")
			assertEquals( result, "ventilatore(false)")
			
		}		  
	}
	
 }