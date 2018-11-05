package ru.kpfu.itis.stayintouch.repository

import io.reactivex.Single
import ru.kpfu.itis.stayintouch.model.AuthResponse
import ru.kpfu.itis.stayintouch.service.ServiceFactory

object AuthRepository {

    fun login(email: String, password: String) : Single<AuthResponse> {
        return ServiceFactory.provideAuthServiceMock()
            .login(email, password)
    }

    fun loginFail(email: String, password: String) : Single<AuthResponse> {
        return ServiceFactory.provideAuthServiceMock()
            .loginFail(email, password)
    }
}