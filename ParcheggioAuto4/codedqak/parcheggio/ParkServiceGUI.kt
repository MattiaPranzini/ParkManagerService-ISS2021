package parcheggio
import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.Observable
import javax.swing.*
import java.awt.Graphics2D
import java.awt.Graphics
import java.awt.Color
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.QakContext
import javax.swing.event.DocumentListener
import java.awt.event.KeyAdapter

class ParkServiceGUI(ctx : QakContext?, title: String = "PARK SERVICE", x: Int = 120, y: Int = 120) : Observable(), ActionListener {

    private val frame = JFrame(title)
    private val carEnter = JButton("CAR ENTER")
	private val tempLabel = JLabel("Posti Liberi")
	private val postiArea = JTextField("6")
	private val tokenLabel = JLabel("TOKEN ID")
	private val tokenArea = JTextField("                          ")
	private val ritiroLabel = JLabel("INSERIRE TOKEN ID")
	private val ritiroArea = JTextField()
	private val ritiroAuto = JButton("RITIRO AUTO")
	private val messaggio = JLabel("")
	private val bu = BottoneUtente("cliente")
	private val jpane = JPanel()
    init {
		println("CTX ## $ctx")
		bu.setInterfaccia(this, ctx)
        carEnter.font = Font(carEnter.font.name, carEnter.font.style, 26)
		ritiroAuto.font = Font(carEnter.font.name, carEnter.font.style, 26)
		carEnter.setEnabled(false)
        carEnter.addActionListener(this)
		ritiroAuto.addActionListener(this)
		
		ritiroArea.setColumns(25);
		
        jpane.add(carEnter)
		jpane.add(tempLabel)
		jpane.add(postiArea)
		jpane.add(tokenLabel)
		jpane.add(tokenArea)
		jpane.add(ritiroLabel)
		jpane.add(ritiroArea)
		jpane.add(ritiroAuto)
		jpane.add(messaggio)

		frame.add(jpane)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(300, 200)
        frame.setLocation(x, y)
        frame.isVisible = true
    }
	
	fun update(){
		println("UPDATE")
		
	}
	fun postiLiberi(p : String){
		postiArea.setText(p)
		if(p.toInt()>0){
			carEnter.setEnabled(true)
		} else{
			carEnter.setEnabled(false)
		}
	}
	
	fun receipt(s : String){
		tokenArea.setText(s)
	}
	
	fun messaggio(s: String){
		messaggio.setText(s)
	}
	
    override fun actionPerformed(e: ActionEvent?) {
		if(e!!.getActionCommand().equals("CAR ENTER")){
			carEnter.setEnabled(false)
		}
    	if(e.getActionCommand().equals("RITIRO AUTO")){
			if(ritiroArea.getText().length>0){
				setChanged()
				notifyObservers(e.getActionCommand().plus(ritiroArea.getText()))
			}
			ritiroArea.setText("")
			
		}else {
			setChanged()
			notifyObservers(e.getActionCommand())
		}

    }

}