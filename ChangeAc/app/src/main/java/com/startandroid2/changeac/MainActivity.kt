package com.startandroid2.changeac


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Retrieve the reference to the button
        val buttonNavigate: Button = findViewById(R.id.buttonNavigate)

        // Set a click listener for the button
        buttonNavigate.setOnClickListener {
            // Create an Intent to start SecondActivity
            val intent = Intent(this, SecondActivity::class.java)

            // Optionally, you can pass data to the next activity using putExtra
            // intent.putExtra("key", "value")

            // Start the SecondActivity
            startActivity(intent)
        }
    }
}
