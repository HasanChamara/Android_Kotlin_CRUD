package com.example.userdetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var insertButton: Button

    private lateinit var database: FirebaseDatabase
    private lateinit var usersRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        database = FirebaseDatabase.getInstance()
        usersRef = database.getReference("users")

        nameEditText = findViewById(R.id.edit_text_name)
        usernameEditText = findViewById(R.id.edit_text_username)
        passwordEditText = findViewById(R.id.edit_text_password)
        insertButton = findViewById(R.id.button_insert)


        insertButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (name.isNotBlank() && username.isNotBlank() && password.isNotBlank()) {
                val userId = usersRef.push().key
                val user = User(userId!!, name, username, password)

                if (userId != null) {
                    usersRef.child(userId).setValue(user)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "User inserted successfully", Toast.LENGTH_SHORT).show()
//                                finish()
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "Failed to insert user", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            } else {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }

    }
}