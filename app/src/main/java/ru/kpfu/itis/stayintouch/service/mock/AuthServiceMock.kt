package ru.kpfu.itis.stayintouch.service.mock

import io.reactivex.Single

class AuthServiceMock {

    fun login(email: String, password: String): Single<String> {
        return Single.just("token")
    }
}