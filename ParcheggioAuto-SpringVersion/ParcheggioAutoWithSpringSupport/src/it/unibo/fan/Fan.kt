/* Generated by AN DISI Unibo */ 
package it.unibo.fan

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Fan ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		 var ventilatoreacceso = false  
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						 ventilatoreacceso=false  
						updateResourceRep( "ventilatoreacceso"  
						)
						forward("ventilatore", "ventilatore(false)" ,"basicrobot" ) 
					}
					 transition(edgeName="t00",targetState="s1",cond=whenEvent("tempalarmon"))
				}	 
				state("s1") { //this:State
					action { //it:State
						 ventilatoreacceso=true  
						updateResourceRep( "ventilatoreacceso"  
						)
						forward("ventilatore", "ventilatore(true)" ,"basicrobot" ) 
					}
					 transition(edgeName="t01",targetState="s0",cond=whenEvent("tempalarmoff"))
				}	 
			}
		}
}
