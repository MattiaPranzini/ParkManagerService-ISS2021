import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.net.URL
import kotlinx.coroutines.runBlocking
import MyFxmlController2
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay

class Main2 : Application() {
	
	var x = ""
	var controller : MyFxmlController2 = MyFxmlController2()
	lateinit 	var loader : FXMLLoader
	lateinit var vbox : VBox
	
	companion object {
		@JvmStatic
		fun main(args: Array<String>?) {
			launch(Main2::class.java)
		}
		
		
		//lateinit var controller : MyFxmlController2
	}

	
	private val mainScope = CoroutineScope(Dispatchers.Default)
	override fun start(primaryStage: Stage?) {
		loader = FXMLLoader(Main2::class.java.getResource("hello-world.fxml"))
		//loader!!.setLocation(URL("file:///C:/Users/matti/Desktop/Universita/Ingegneria dei Sistemi Software M/NOSTRO/ESAME/HelloFX/src/hello-world.fxml"))
		x="XX"
		println("STAMPA")
		val vbox: VBox = loader.load()
		controller  = loader.getController()
		println(controller)
		//controller!!.update()
		val scene = Scene(vbox)
		primaryStage!!.setScene(scene)
		primaryStage!!.show()
	}

	

	suspend fun avvia() {
		mainScope.launch{
				main(Array<String>(5) { "it = $it" })
			
			
		}
		println("Avvia2")
		controller.update()
	
	}
	
	fun prova(){
		println(x)
		println(controller)
		//controller!!.update()
	}

}