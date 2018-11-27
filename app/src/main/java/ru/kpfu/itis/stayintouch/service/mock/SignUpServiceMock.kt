package ru.kpfu.itis.stayintouch.service.mock

import io.reactivex.Single

class SignUpServiceMock {

    fun registration(name: String, surname: String, email: String, password: String, password2: String): Single<String> {
        return Single.just("token")
    }
}