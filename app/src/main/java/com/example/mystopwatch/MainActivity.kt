package com.example.mystopwatch

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mystopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // mark if the timer is running
    private var isRunning: Boolean = false
    private var mewomewo = "mewomewo"
    private var milliSeconds: Int = 0
    private var seconds: Int = 0
    private var minutes: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil
                        .setContentView(this, R.layout.activity_main)

        // Timer
        val intervalTime: Long = 10 // interval time between 2 ticks
        val maxSeconds: Long = 86400 // max countdown time
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

            if(!isRunning) {
                binding.triggerButton.text = getString(R.string.stop_text)
                isRunning = true
                timer.start()
            } else {
                binding.triggerButton.text = getString(R.string.start_text)
                isRunning = false
                timer.cancel()
            }
        }

        binding.resetButton.setOnClickListener {

            binding.timerText.text = "00:00.00"
            minutes = 0
            seconds = 0
            milliSeconds = 0
        }
    }

    fun formatTime(minutes: Int, seconds: Int, milliSeconds: Int): String {

        val strMillis: String = if(milliSeconds/10 < 10) {
            "0${milliSeconds/10}"
        } else {
            (milliSeconds/10).toString()
        }

        val strSeconds: String = if(seconds < 10) {
            "0$seconds"
        } else {
            seconds.toString()
        }

        val strMinutes: String = if(minutes < 10) {
            "0$minutes"
        } else {
            minutes.toString()
        }

        return "$strMinutes:$strSeconds.$strMillis"
    }
}
