package ru.kpfu.itis.stayintouch.service.mock

import io.reactivex.Single
import ru.kpfu.itis.stayintouch.model.AuthResponse

class SignUpServiceMock {

    fun registration(name: String, surname: String, photo: String?, email: String, password: String): Single<AuthResponse> {
        val authResponse = AuthResponse("200", "1")
        return Single.just(authResponse)
    }

    fun registrationFail(name: String, surname: String, photo: String?, email: String, password: String): Single<AuthResponse> {
        val authResponse = AuthResponse("3", "1")
        return Single.just(authResponse)
    }
}