package ru.kpfu.itis.stayintouch.service

import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Query
import ru.kpfu.itis.stayintouch.model.AuthResponse

interface AuthService {

    @POST("login")
    fun login(
        @Query("e-mail") email: String,
        @Query("password") password: String
    ): Single<AuthResponse>
}