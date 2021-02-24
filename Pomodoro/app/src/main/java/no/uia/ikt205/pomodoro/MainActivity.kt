package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime
import java.time.chrono.MinguoChronology

class MainActivity : AppCompatActivity() {


    lateinit var timer:CountDownTimer
    lateinit var pause:CountDownTimer
    lateinit var startButton:Button
    lateinit var countdownDisplay:TextView
    lateinit var breakTimeDisplay:TextView
    lateinit var setWantedBreaks:EditText

    var timeToCountDownInMs = 1000L
    val timeTicks = 1000L
    var realTime:Long = 15L
    var repNumber = 0
    var flag = false
    var pauseTime:Long = 15L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       startButton = findViewById<Button>(R.id.startCountdownButton)
       startButton.setOnClickListener(){
           startCountDown(it)
       }

        val seekSetTime = findViewById<SeekBar>(R.id.seekBarSetTime)
        seekSetTime?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                countdownDisplay.text = "$progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(this@MainActivity, "Time set to: " + seekSetTime.progress + " minutes",
                Toast.LENGTH_SHORT).show()

                changeCountDownTime(seekSetTime.progress)

            }
            
        })

        val seekBreakTime = findViewById<SeekBar>(R.id.seekBarSetBreakTime)
        seekBreakTime?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                breakTimeDisplay.text = "$progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(applicationContext, "Start tracking", Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(this@MainActivity, "Brake time set to: " + seekBreakTime.progress + " minutes",
                        Toast.LENGTH_SHORT).show()

                changePause(seekBreakTime.progress)
            }

        })

        setWantedBreaks = findViewById<EditText>(R.id.setWantedBreaks)
        setWantedBreaks.setOnClickListener(){

            val text = setWantedBreaks.text
            repNumber = setWantedBreaks.text.toString().toInt()
            Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
        }

        countdownDisplay = findViewById<TextView>(R.id.countDownView)
        breakTimeDisplay = findViewById<TextView>(R.id.breakTime)


    fun changeCountDownTime(newCountDownTimeinMs: Long){
        timeToCountDownInMs = newCountDownTimeinMs
        updateCountDownDisplay(timeToCountDownInMs)
    }


    fun startCountDown(v: View){

        if (flag){
            timer.cancel()
            pause.cancel()
        }

        flag = true

        timer = object : CountDownTimer(realTime,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                pause.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
            }
        }

        pause = object : CountDownTimer(pauseTime, timeTicks){
            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Pause er ferdig", Toast.LENGTH_SHORT).show()
                if (repNumber > 1){
                    timer.start()
                    repNumber -= 1
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
            }
        }

        timer.start()
    }

    fun changeCountDownTime(Multiplier: Int){
        realTime = timeToCountDownInMs * Multiplier * 60
    }

    fun changePause(Multiplier: Int){
        pauseTime = timeTicks * Multiplier
    }

    fun updateCountDownDisplay(timeInMs:Long){
        countdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

}
