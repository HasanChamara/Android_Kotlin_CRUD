package com.example.userdetails

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var userList: List<User> = emptyList()
    private lateinit var usersRef: DatabaseReference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        val context = holder.itemView.context
        holder.bind(user)

        holder.updateButton.setOnClickListener {
            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("user", user)
            context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            showDeleteConfirmationDialog(user, context)
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setUserList(users: List<User>) {
        userList = users
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.text_view_name)
        private val usernameTextView: TextView = itemView.findViewById(R.id.text_view_username)
//        private val passwordTextView: TextView = itemView.findViewById(R.id.text_view_password)
        val updateButton: Button = itemView.findViewById(R.id.button_update)
        val deleteButton: Button = itemView.findViewById(R.id.button_delete)

        fun bind(user: User) {
            nameTextView.text = user.name
            usernameTextView.text = user.username
//            passwordTextView.text = user.password
        }
    }

    private fun showDeleteConfirmationDialog(user: User, context: Context) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("Confirm Delete")
            .setMessage("Are you sure you want to delete this user?")
            .setPositiveButton("Yes") { _, _ ->
                usersRef = FirebaseDatabase.getInstance().getReference("users")
                usersRef.child(user.userId).removeValue()

                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "User Deleted Successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Failed to delete user", Toast.LENGTH_SHORT).show()
                        }
                    }

            }
            .setNegativeButton("No", null)
            .show()
    }

}