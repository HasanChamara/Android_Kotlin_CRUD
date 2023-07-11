package com.example.userdetails

//class User(val userId: String = "", val name: String = "", val username: String = "", val password: String = "") {
//    // Optional: You can add additional properties or methods to the User class if needed.
//}

import java.io.Serializable

data class User(val userId: String = "", val name: String = "", val username: String = "", val password: String = "") : Serializable {
    // Optional: You can add additional properties or methods to the User class if needed.
}
