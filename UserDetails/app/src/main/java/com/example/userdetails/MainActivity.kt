package com.example.userdetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var addButton: Button
    private lateinit var readButton: Button
    private lateinit var usersRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addButton = findViewById(R.id.button_add)
        usersRef = FirebaseDatabase.getInstance().getReference("users")

        addButton.setOnClickListener {
            val intent = Intent(this, InsertActivity::class.java)
            startActivity(intent)
        }

        readButton = findViewById(R.id.button_read)

        readButton.setOnClickListener{
            val intent = Intent(this, ReadActivity::class.java)
            startActivity(intent)
        }

    }
}