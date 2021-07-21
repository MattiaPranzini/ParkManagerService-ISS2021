package it.unibo.webParcheggioAutoqak

import com.andreapivetta.kolor.Color
import connQak.ConnectionType
import connQak.connQakBase
import it.unibo.actor0.sysUtil
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.channels.Channel
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.beans.factory.annotation.Autowired

/*

 */

@Controller
class HIController {
    @Value("\${human.logo}")
    var appName: String?    = null
    /*
     * Update the page vie socket.io when the application-resource changes.
     * See also https://www.baeldung.com/spring-websockets-send-message-to-user
     */
    @Autowired
    var  simpMessagingTemplate : SimpMessagingTemplate? = null

    //lateinit var connSupport     : connQakBase
    lateinit var coapsupport     : CoapSupport
    lateinit var connToRobot     : connQakBase
    val channel      = Channel<String>()
    val coapObserver = WebPageCoapHandler(this, null)
    var moveresult="done"
    companion object{
        var logo = "just to test"
        //var answerMoveChannel  = Channel<String>()
        var robotanswerChannel  : Channel<String>? = null
        fun setAnswerChannel(answChannel  : Channel<String>){
            sysUtil.colorPrint("HIController | answChannelllllllllllll: ${answChannel}", Color.BLUE)
            robotanswerChannel = answChannel
        }

    }

    fun configure(addr : String){
        connQak.robothostAddr = addr

            connToRobot = connQakBase.create(ConnectionType.TCP)
            connToRobot.createConnection()

        coapsupport =
            CoapSupport("coap://${connQak.robothostAddr}:${connQak.robotPort}",
                "ctxbasicrobot/basicrobot")
        coapsupport.observeResource( coapObserver )

        sysUtil.colorPrint("HIController | INIT $simpMessagingTemplate", Color.GREEN)
    }



    @GetMapping("/")    //defines that the method handles GET requests.
    fun entry(viewmodel: Model): String {
         viewmodel.addAttribute("viewmodelarg", appName)
        sysUtil.colorPrint("HIController | entry model=$viewmodel", Color.GREEN)
        return  "login"
    }

    @PostMapping("/login")
    fun login(viewmodel : Model,
                        @RequestParam(name="email", required=false, defaultValue="h")email : String,
                        @RequestParam(name="password", required=false, defaultValue="h")pass : String) : String {
        if(email.equals("admin") && pass.equals("admin")){
            return "basicrobotqakGui"
        }


        return  "login"
    }

    @PostMapping("/configure")
    fun handleConfigure(viewmodel : Model,
      @RequestParam(name="move", required=false, defaultValue="h")addr : String) : String {
        //if( addr!="localhost"){
            configure(addr)
            viewmodel.addAttribute("viewmodelarg", "configured with basicrobot addr="+addr)
        /*}else{
            viewmodel.addAttribute("viewmodelarg", "localhost not allowed")
        }*/

        return  "basicrobotqakGui"
    }

    @PostMapping("/startstop")
    fun startStop(viewmodel : Model,
                        @RequestParam(name="stop", required=false, defaultValue="stop")valore : String) : String {
        //if( addr!="localhost"){
        println("PREMUTO $valore")
        val reqMsg = MsgUtil.buildEvent("parkmanagerservice", valore, valore)
        connToRobot.emit(reqMsg)
        /*configure(addr)
        viewmodel.addAttribute("viewmodelarg", "configured with basicrobot addr="+addr)*/
        /*}else{
            viewmodel.addAttribute("viewmodelarg", "localhost not allowed")
        }*/

        return  "basicrobotqakGui"
    }

}