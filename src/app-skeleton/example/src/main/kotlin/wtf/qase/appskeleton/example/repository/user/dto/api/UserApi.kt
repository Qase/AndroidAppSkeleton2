package wtf.qase.appskeleton.example.repository.user.dto.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import wtf.qase.appskeleton.example.repository.user.dto.LoginRequest
import wtf.qase.appskeleton.example.repository.user.dto.LoginResponse

interface UserApi {

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Single<Response<LoginResponse>>

    @POST("logout")
    fun logout(): Single<Response<Unit>>

    @GET("questions")
    fun getQuestions(): Single<Response<Unit>>
}
