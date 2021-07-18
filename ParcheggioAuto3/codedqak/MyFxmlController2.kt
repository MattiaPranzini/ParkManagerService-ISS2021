
import javafx.event.Event
import javafx.fxml.FXML
import javafx.scene.control.Label

class MyFxmlController2 {
@FXML 	var label1: Label ?= null

	@FXML
	fun buttonClicked(e: Event?) {
		label1!!.setText("CIAO")
		println("Button clicked")
	}

	fun update() {
		println("Update")
		label1!!.setText("UPDATE")
		println("Update 2")
	}
}