package com.example.userdetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var updateButton: Button

    private lateinit var user: User
    private lateinit var usersRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        user = intent.getSerializableExtra("user") as User
        usersRef = FirebaseDatabase.getInstance().getReference("users")

        nameEditText = findViewById(R.id.edit_text_name)
        usernameEditText = findViewById(R.id.edit_text_username)
        passwordEditText = findViewById(R.id.edit_text_password)
        updateButton = findViewById(R.id.button_update)

        nameEditText.setText(user.name)
        usernameEditText.setText(user.username)
        passwordEditText.setText(user.password)

        updateButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (name.isNotBlank() && username.isNotBlank() && password.isNotBlank()) {
                val updatedUser = User(user.userId, name, username, password)

                usersRef.child(user.userId).setValue(updatedUser)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show()
//                            finish()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Failed to update user", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }

    }
}