package com.example.mystopwatch

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mystopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var isRunning: Boolean = false

    private var milliSeconds: Int = 0
    private var seconds: Int = 0
    private var minutes: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lateinit var binding: ActivityMainBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val intervalTime: Long = 10
        val maxSeconds: Long = 86400
        val timer =
            object: CountDownTimer(maxSeconds * 1000, intervalTime) {
                override fun onTick(t0: Long) {
                    milliSeconds += 10
                    if(milliSeconds == 1000) {
                        milliSeconds = 0
                        seconds++
                    }
                    if(seconds == 60) {
                        seconds = 0
                        minutes++
                    }
                    binding.timerText.text = formatTime(minutes, seconds, milliSeconds)
                }

                override fun onFinish() { }
            }

        binding.triggerButton.setOnClickListener {

            Toast.makeText(this, "Triggered", Toast.LENGTH_SHORT).show()

            if(isRunning == false) {
                binding.triggerButton.text = getString(R.string.stop_text)
                isRunning = true
                timer.start()
            } else {
                binding.triggerButton.text = getString(R.string.start_text)
                isRunning = false
                timer.cancel()
            }
        }
    }

    private fun onTimerStart() {

    }

    private fun onTimerStop() {

    }

    fun formatTime(minutes: Int, seconds: Int, milliSeconds: Int): String {

        var strMillis: String
        if(milliSeconds/10 < 10) {
            strMillis = "0${milliSeconds/10}"
        } else {
            strMillis = milliSeconds.toString()
        }

        var strSeconds: String
        if(seconds < 10) {
            strSeconds = "0$seconds"
        } else {
            strSeconds = seconds.toString()
        }

        var strMinutes: String
        if(minutes < 10) {
            strMinutes = "0$minutes"
        } else {
            strMinutes = minutes.toString()
        }

        return "$strMinutes:$strSeconds.$strMillis"
    }
}
