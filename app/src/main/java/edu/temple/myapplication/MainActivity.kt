package edu.temple.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast // Added for demonstration

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.startButton).setOnClickListener {
            startLogic()
        }

        findViewById<Button>(R.id.stopButton).setOnClickListener {
            stopLogic()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_start -> {
                startLogic()
                true
            }
            R.id.action_stop -> {
                stopLogic()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startLogic() {
        Toast.makeText(this, "Starting...", Toast.LENGTH_SHORT).show()
    }

    private fun stopLogic() {
        Toast.makeText(this, "Stopping...", Toast.LENGTH_SHORT).show()
    }
}