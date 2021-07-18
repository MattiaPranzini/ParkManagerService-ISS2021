
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.stage.Stage
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.event.EventHandler
import kotlinx.coroutines.runBlocking
import javafx.scene.control.TextField
import javafx.fxml.FXML
import javafx.scene.control.Label

class GUI : Application() {
 
    override fun start(primaryStage: Stage?) { 
 
        val fxmlLoader = FXMLLoader( GUI::class.java.getResource("test.fxml"))
		
        val parent : Parent = fxmlLoader.load()

		
 
        val c : GUIController = fxmlLoader.getController()
		
 
        val scene = Scene( parent )

 
        primaryStage!!.scene = scene 
        primaryStage.title = "GUI" 
        primaryStage.width = 667.0 
        primaryStage.height = 375.0 
        primaryStage.onShown = EventHandler { c.startstop() } 
        primaryStage.show()
		
    } 
 
    companion object { 
        @JvmStatic 
        fun main(args: Array<String>) { 
			launch(GUI::class.java)
			
        } 
    }
	
} 
