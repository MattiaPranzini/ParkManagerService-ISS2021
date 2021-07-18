
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

class GUIController  {
	
@FXML lateinit var temperatural: Label
	init{
		temperatural.text = "ciao"
		
	}
	fun startstop() {
		println("PROVA")
	}
}