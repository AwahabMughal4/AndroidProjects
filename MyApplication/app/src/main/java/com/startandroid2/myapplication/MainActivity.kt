package com.startandroid2.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var sendButtonData: Button
    lateinit var sendTextData: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        sendButtonData = findViewById(R.id.button_send_id)
        sendTextData = findViewById(R.id.text_send_id)

        sendButtonData.setOnClickListener {
            // Get the text from the EditText
            val str = sendTextData.text.toString()

            // Create an intent to start the SecondActivity
            val intent = Intent(this, SecondActivity::class.java)

            // Pass the text data to the SecondActivity
            intent.putExtra("message_key", str)

            // Start the SecondActivity
            startActivity(intent)
        }
    }
}
