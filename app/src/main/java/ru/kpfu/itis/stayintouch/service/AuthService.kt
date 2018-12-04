package ru.kpfu.itis.stayintouch.service

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import ru.kpfu.itis.stayintouch.model.AuthResponse
import ru.kpfu.itis.stayintouch.model.LoginRequestEmail
import ru.kpfu.itis.stayintouch.model.LoginRequestUsername
import ru.kpfu.itis.stayintouch.model.Token

interface AuthService {

    @POST("auth/login/")
    fun login(@Body loginRequest: LoginRequestEmail): Single<AuthResponse>

    @POST("auth/login/")
    fun login(@Body loginRequest: LoginRequestUsername): Single<AuthResponse>

    @POST("auth/refresh-token/")
    fun refreshToken(@Body token: Token): Single<Token>
}