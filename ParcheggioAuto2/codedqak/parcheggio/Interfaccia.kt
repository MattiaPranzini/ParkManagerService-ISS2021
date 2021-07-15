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

class Interfaccia(ctx : QakContext?, title: String = "Button", x: Int = 30, y: Int = 30) : Observable(), ActionListener {

    private val frame = JFrame(title)
    private val stoprobot = JButton("Stop robot")
	private val tempLabel = JLabel("Temperatura")
	private val tempArea = JTextField("temperatura")
	private val tempAlarmLabel = JLabel("Allarme Temp")
	private val tempAlarmArea = JTextField("C")
	private val fanLabel = JLabel("Ventilatore")
	private val fanBox = JCheckBox("")
	private val jpane = JPanel()
	private val alarmLabel = JLabel("Outdoor Alarm")
	private val alarmArea = JTextField("C")
	private	val ss = StartStop("startstop")
	private val robotLabel = JLabel("Stato robot")
	private val robotArea = JTextField("IDLE")
	private val parcheggi = Array<JTextField?>(6){null}
	
        
	
	

    init {
		var i=0
		while(i<6){
			var p = i+1
			parcheggi[i] = JTextField("P$p")
			parcheggi[i]!!.setBackground(Color.GREEN)
			i ++
		}
		println("CTX ## $ctx")
		ss.setInterfaccia(this, ctx)
		alarmArea.setBackground(Color.GREEN)
        stoprobot.font = Font(stoprobot.font.name, stoprobot.font.style, 26)
        stoprobot.addActionListener(this)
		
        jpane.add(stoprobot)
		jpane.add(tempLabel)
		jpane.add(tempArea)
		jpane.add(tempAlarmLabel)
		jpane.add(tempAlarmArea)
		jpane.add(fanLabel)
		jpane.add(fanBox)
		jpane.add(alarmLabel)
		jpane.add(alarmArea)
		jpane.add(robotLabel)
		jpane.add(robotArea)
		for (p in parcheggi){
			jpane.add(p)
		}

				frame.add(jpane)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(300, 200)
        frame.setLocation(x, y)
        frame.isVisible = true
    }
	
	fun update(){
		println("UPDATE")
		
	}
	fun temperatura(temp : String){
		tempArea.setText(temp)
	}
	
	fun stato(stato : String){
		robotArea.setText(stato)
	}
	
	fun park(id : Int, lib : Boolean){
		if(lib){
			parcheggi[id]!!.setBackground(Color.GREEN)
		} else{
			parcheggi[id]!!.setBackground(Color.RED)
		}
	}
	
	fun ventilatore(vent : String){
		if(vent.equals("true")){
			tempAlarmArea.setBackground(Color.RED)
			fanBox.setSelected(true)
		}else{
			tempAlarmArea.setBackground(Color.GREEN)
			fanBox.setSelected(false)
		}
	}
	
	fun alarm(al : Boolean){
		if(al){
			alarmArea.setBackground(Color.RED)
		}else{
			alarmArea.setBackground(Color.GREEN)
		}
	}
	
    override fun actionPerformed(e: ActionEvent?) {
		if(stoprobot.getText().equals("Stop robot")){
			stoprobot.setText("Start robot")
			//robotArea.setText("STOP")
		} else{
			stoprobot.setText("Stop robot")
		}
        setChanged()
        notifyObservers()
    }

}