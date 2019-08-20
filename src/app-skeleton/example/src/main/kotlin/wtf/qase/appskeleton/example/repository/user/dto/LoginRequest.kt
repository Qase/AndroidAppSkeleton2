package wtf.qase.appskeleton.example.repository.user.dto

import com.google.gson.annotations.Expose

data class LoginRequest(

    @Expose
    val username: String,

    @Expose
    val password: String
)
