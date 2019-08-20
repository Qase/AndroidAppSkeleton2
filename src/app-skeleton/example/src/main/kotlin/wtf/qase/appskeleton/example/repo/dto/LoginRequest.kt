package wtf.qase.appskeleton.example.repo.dto

import com.google.gson.annotations.Expose

data class LoginRequest(

    @Expose
    val username: String,

    @Expose
    val password: String
)
