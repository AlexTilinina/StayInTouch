package ru.kpfu.itis.stayintouch.service.mock

import io.reactivex.Single
import ru.kpfu.itis.stayintouch.model.AuthResponse

class AuthServiceMock {

    fun login(email: String, password: String): Single<AuthResponse> {
        val authResponse = AuthResponse("200", "1")
        return Single.just(authResponse)
    }

    fun loginFail(email: String, password: String): Single<AuthResponse> {
        val authResponse = AuthResponse("1", "1")
        return Single.just(authResponse)
    }
}