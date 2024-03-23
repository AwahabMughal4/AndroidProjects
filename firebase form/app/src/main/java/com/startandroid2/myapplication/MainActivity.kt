package com.startandroid2.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var buttonSubmit: Button
    private lateinit var database: FirebaseDatabase
    private lateinit var usersReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        editTextEmail = findViewById(R.id.editTextEmail)
        buttonSubmit = findViewById(R.id.buttonSubmit)

        database = FirebaseDatabase.getInstance()
        usersReference = database.getReference("users")

        buttonSubmit.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val email = editTextEmail.text.toString().trim()

            if (isValidName(name) && isValidEmail(email)) {
                checkDuplicateUser(name, email)
            }
        }
    }

    private fun isValidName(name: String): Boolean {
        if (name.isEmpty()) {
            showToast("Please enter a username.")
            return false
        }
        return true
    }

    private fun isValidEmail(email: String): Boolean {
        if (email.isEmpty()) {
            showToast("Please enter an email address.")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Please enter a valid email address.")
            return false
        }
        return true
    }

    private fun checkDuplicateUser(name: String, email: String) {
        usersReference.orderByChild("name").equalTo(name).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    showToast("Username already exists. Please choose another one.")
                } else {
                    checkDuplicateEmail(name, email)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("Error checking duplicate username: ${error.message}")
            }
        })
    }

    private fun checkDuplicateEmail(name: String, email: String) {
        usersReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    showToast("Email address already exists. Please use another one.")
                } else {
                    saveUserToDatabase(name, email)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("Error checking duplicate email: ${error.message}")
            }
        })
    }

    private fun saveUserToDatabase(name: String, email: String) {
        val userId = usersReference.push().key
        val user = User(name, email)

        if (userId != null) {
            usersReference.child(userId).setValue(user)
            showToast("User data saved successfully.")
            editTextName.text.clear()
            editTextEmail.text.clear()

            // Start the next activity
            val intent = Intent(this, NextActivity::class.java)
            startActivity(intent)
        } else {
            showToast("Error saving user data.")
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

// User data class
data class User(val name: String, val email: String)
