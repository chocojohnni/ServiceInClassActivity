package edu.temple.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var timerText: TextView
    private var countDownTimer: CountDownTimer? = null

    private val DEFAULT_START = 100
    private var currentValue = DEFAULT_START

    private val PREFS_NAME = "CountdownPrefs"
    private val KEY_SAVED_VALUE = "saved_countdown_value"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerText = findViewById(R.id.timerTextView)

        findViewById<Button>(R.id.startButton).setOnClickListener {
            startLogic()
        }

        findViewById<Button>(R.id.stopButton).setOnClickListener {
            pauseAndSaveLogic()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_start -> { startLogic(); true }
            R.id.action_stop -> { pauseAndSaveLogic(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startLogic() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        currentValue = prefs.getInt(KEY_SAVED_VALUE, DEFAULT_START)

        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer((currentValue * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                currentValue = (millisUntilFinished / 1000).toInt()
                timerText.text = currentValue.toString()
            }

            override fun onFinish() {
                timerText.text = "Finished!"
                currentValue = DEFAULT_START
            }
        }.start()
    }

    private fun pauseAndSaveLogic() {
        countDownTimer?.cancel()

        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putInt(KEY_SAVED_VALUE, currentValue)
            apply()
        }
        Toast.makeText(this, "Saved at $currentValue", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()

        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        with(prefs.edit()) {
            remove(KEY_SAVED_VALUE)
            apply()
        }
        countDownTimer?.cancel()
    }
}