package com.startandroid2.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SecondActivity : AppCompatActivity() {
    lateinit var receiver_msg: TextView
    lateinit var second_page_b: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        receiver_msg = findViewById(R.id.received_value_id)
        val intent = intent
        val str = intent.getStringExtra("message_key")
        receiver_msg.text = str ?: "Default Text"

        second_page_b = findViewById(R.id.second_page_button)

        second_page_b.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
    }
}
