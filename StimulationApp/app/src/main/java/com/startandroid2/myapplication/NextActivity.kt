package com.startandroid2.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class NextActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var mDatabase: DatabaseReference? = null
    private var balanceTextView: TextView? = null
    private var earnButton: Button? = null
    private var spendButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference

        balanceTextView = findViewById<TextView>(R.id.balanceTextView)
        earnButton = findViewById<Button>(R.id.earnButton)
        spendButton = findViewById<Button>(R.id.spendButton)

        balanceTextView?.apply {
            earnButton?.setOnClickListener(View.OnClickListener { earnVirtualCurrency() })
            spendButton?.setOnClickListener(View.OnClickListener { spendVirtualCurrency() })

            updateBalance()
        }
    }

    private fun updateBalance() {
        val currentUser = mAuth?.currentUser
        if (currentUser != null) {
            val userRef = mDatabase?.child("users")?.child(currentUser.uid)

            userRef?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val balance = snapshot.child("balance").getValue(Long::class.java)
                        if (balance != null) {
                            balanceTextView?.text = "Balance: $balance"
                        } else {
                            Log.e("NextActivity", "Balance is null")
                        }
                    } else {
                        Log.e("NextActivity", "Snapshot does not exist")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("NextActivity", "Database error: ${error.message}")
                    // Handle error
                }
            })
        } else {
            Log.e("NextActivity", "Current user is null")
        }
    }

    private fun spendVirtualCurrency() {
        val currentUser = mAuth?.currentUser
        if (currentUser != null) {
            val userRef = mDatabase?.child("users")?.child(currentUser.uid)

            // TODO: Implement logic to spend virtual currency
            // For example, deduct 5 virtual currency from the user's balance
            userRef?.child("balance")?.setValue(ServerValue.increment(-5))
        } else {
            Log.e("NextActivity", "Current user is null in spendVirtualCurrency")
        }
    }

    private fun earnVirtualCurrency() {
        val currentUser = mAuth?.currentUser
        if (currentUser != null) {
            val userRef = mDatabase?.child("users")?.child(currentUser.uid)

            // TODO: Implement logic to earn virtual currency
            // For example, add 10 virtual currency to the user's balance
            userRef?.child("balance")?.setValue(ServerValue.increment(10))
        } else {
            Log.e("NextActivity", "Current user is null in earnVirtualCurrency")
        }
    }
}
