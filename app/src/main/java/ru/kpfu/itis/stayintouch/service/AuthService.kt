package ru.kpfu.itis.stayintouch.service

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import ru.kpfu.itis.stayintouch.model.AuthResponse
import ru.kpfu.itis.stayintouch.model.LoginRequest

interface AuthService {

    @POST("auth/login/")
    fun login(@Body loginRequest: LoginRequest): Single<AuthResponse>

    @POST("auth/refresh-token/")
    fun refreshToken(@Query("token") token: String): Single<String>
}