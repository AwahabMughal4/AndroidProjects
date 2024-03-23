package com.startandroid2.arrayadapter

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var myAdapter: ArrayAdapter<String>
    private lateinit var items: MutableList<String>
    private lateinit var ldata: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        items = mutableListOf("java", "python", "c#", "ruby", "javascript", "Go")
        myAdapter = ArrayAdapter(this, R.layout.singlerowdesign, R.id.label, items)

        ldata = findViewById(R.id.listdata)
        ldata.adapter = myAdapter

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            // Toggle the visibility of the ListView when the button is clicked
            ldata.visibility = if (ldata.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
    }
}
