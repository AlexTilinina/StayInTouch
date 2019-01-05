package ru.kpfu.itis.stayintouch.repository

import android.content.Context
import io.reactivex.Single
import ru.kpfu.itis.stayintouch.model.AuthResponse
import ru.kpfu.itis.stayintouch.model.LoginRequestEmail
import ru.kpfu.itis.stayintouch.model.LoginRequestUsername
import ru.kpfu.itis.stayintouch.model.Token
import ru.kpfu.itis.stayintouch.service.ServiceFactory
import ru.kpfu.itis.stayintouch.utils.SHARED_PREFS
import ru.kpfu.itis.stayintouch.utils.TOKEN

object AuthRepository {

    lateinit var token: String

    fun login(email: String, password: String) : Single<AuthResponse> {
        return if (email.contains("@")) {
            ServiceFactory.provideAuthService()
                .login(LoginRequestEmail(email, password))
        } else ServiceFactory.provideAuthService()
            .login(LoginRequestUsername(email, password))
    }

    fun setCorrectToken(token: String) {
        this.token = "JWT $token"
    }

    fun refreshToken(token: String): Single<Token> {
        return ServiceFactory.provideAuthService().refreshToken(Token(token))
    }
}