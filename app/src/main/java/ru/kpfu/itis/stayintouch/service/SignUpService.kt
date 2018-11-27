package ru.kpfu.itis.stayintouch.service

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import ru.kpfu.itis.stayintouch.model.AuthResponse
import ru.kpfu.itis.stayintouch.model.RegistrationRequest

interface SignUpService {

    @POST("auth/registration/")
    fun registration(@Body registrationRequest: RegistrationRequest): Single<AuthResponse>
}