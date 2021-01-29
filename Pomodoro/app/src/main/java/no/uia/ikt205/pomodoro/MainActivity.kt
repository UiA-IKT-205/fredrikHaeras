package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    lateinit var timer: CountDownTimer
    lateinit var startButton: Button
    lateinit var add30: Button
    lateinit var add60: Button
    lateinit var add90: Button
    lateinit var add120: Button
    lateinit var countdownDisplay: TextView

    var timeToCountDownInMs = 0L
    val timeTicks = 1000L
    val buttonMinuteTimeIncrement = 30L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById<Button>(R.id.startCountdownButton)
        startButton.setOnClickListener() {
            startCountDown(it)
            startButton.isEnabled = false
        }

        add30 = findViewById<Button>(R.id.add30)
        add30.setOnClickListener() {
            Toast.makeText(this@MainActivity, "number 30 is pressed", Toast.LENGTH_SHORT).show()
            changeCountDownTime(newCountDownTimeinMs = 30*60000)
        }

        add60 = findViewById<Button>(R.id.add60)
        add60.setOnClickListener() {
            Toast.makeText(this@MainActivity, "number 60 is pressed", Toast.LENGTH_SHORT).show()
            changeCountDownTime(newCountDownTimeinMs = 60*60000)
        }

        add90 = findViewById<Button>(R.id.add90)
        add90.setOnClickListener() {
            Toast.makeText(this@MainActivity, "number 90 is pressed", Toast.LENGTH_SHORT).show()
            changeCountDownTime(newCountDownTimeinMs = 90*60000)
        }

        add120 = findViewById<Button>(R.id.add120)
        add120.setOnClickListener() {
            Toast.makeText(this@MainActivity, "number 120 is pressed", Toast.LENGTH_SHORT).show()
            changeCountDownTime(newCountDownTimeinMs = 120*60000)
        }

        countdownDisplay = findViewById<TextView>(R.id.countDownView)

        /*val setTimeDurationButtons = listOf<Button>(findViewById<Button>(R.id.add30),findViewById<Button>(R.id.setTimeDurationTo60MinutesBt),findViewById<Button>(R.id.setTimeDurationTo90MinutesBt),findViewById<Button>(R.id.setTimeDurationTo120MinutesBt))

        setTimeDurationButtons.forEachIndexed { index, button ->
            button.setOnClickListener(){
                if(startButton.isEnabled){
                    val newCountdownTime = minutesToMilliSeconds((index+1) * buttonMinuteTimeIncrement)
                    setCountDownTime(newCountdownTime)
                }
            }
        }*/

    }

    fun changeCountDownTime(newCountDownTimeinMs: Long){
        timeToCountDownInMs = newCountDownTimeinMs
        updateCountDownDisplay(timeToCountDownInMs)
    }

    fun startCountDown(v: View){

        timer = object : CountDownTimer(timeToCountDownInMs, timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                startButton.isEnabled = true
            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
            }
        }

        timer.start()
    }

    fun updateCountDownDisplay(timeInMs:Long){
        countdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

}