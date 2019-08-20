package wtf.qase.appskeleton.example.repository.user.dto

import com.google.gson.annotations.Expose

data class LoginResponse(

    @Expose
    val accessToken: String,

    @Expose
    val valid: Long
)
