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
 
 
class PickupTest{
		
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
	fun pickup()  {
		println("+++++++++ pickupTest ")
		//sysUtil.waitUser("PLEASE, put the robot at HOME", 1000 )
		
		
		runBlocking{
			val channelForObservers = Channel<String>()
			val accptin = MsgUtil.buildEvent("parcheggio", "acceptin", "acceptin")
			MsgUtil.sendMsg(accptin, myactor!!)
			val park = MsgUtil.buildRequest("parcheggio", "carenter", "carenter", "parcheggio")
			MsgUtil.sendMsg(park, myactor!!)
			delay(400)
			testingObserver!!.addObserver( channelForObservers,"pos((0, 0))")
			var result = channelForObservers.receive()
			println("+++++++++ pickupTest r RESULT=$result")
			assertEquals( result, "pos((0, 0))")
			
			
			testingObserver!!.addObserver( channelForObservers,"response" )
			
			
			val cmd = MsgUtil.buildRequest("parcheggio", "acceptout", "acceptout(H000)", "parcheggio")
			MsgUtil.sendMsg(cmd, myactor!!)
			result = channelForObservers.receive()
			println("+++++++++  pickupTest RESULT=$result ")	
			assertEquals( result, "response(true)")
			
			//delay(200)
			testingObserver!!.addObserver( channelForObservers,"posti(6)" )
			result = channelForObservers.receive()
			println("+++++++++  pickupTest RESULT=$result ")	
			assertEquals( result, "posti(6)")
			
			testingObserver!!.addObserver( channelForObservers,"pos((1, 1))" )
			result = channelForObservers.receive()
			println("+++++++++  pickupTest RESULT=$result ")	
			assertEquals( result, "pos((1, 1))")	
			
			testingObserver!!.addObserver( channelForObservers,"pos((5, 4))" )
			result = channelForObservers.receive()
			println("+++++++++  pickupTest RESULT=$result ")	
			assertEquals( result, "pos((5, 4))")	

			delay(400)
			testingObserver!!.addObserver( channelForObservers,"pos((0, 0))")
			result = channelForObservers.receive()
			println("+++++++++ pickupTest r RESULT=$result")
			assertEquals( result, "pos((0, 0))")
		}		  
	}
	
 }