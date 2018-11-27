package ru.kpfu.itis.stayintouch.repository

import android.content.Context
import io.reactivex.Single
import ru.kpfu.itis.stayintouch.model.AuthResponse
import ru.kpfu.itis.stayintouch.model.LoginRequest
import ru.kpfu.itis.stayintouch.service.ServiceFactory
import ru.kpfu.itis.stayintouch.utils.SHARED_PREFS
import ru.kpfu.itis.stayintouch.utils.TOKEN

object AuthRepository {

    fun login(email: String, password: String) : Single<AuthResponse> {
        return ServiceFactory.provideAuthService()
            .login(LoginRequest(email, password))
    }

    fun getToken(context: Context) : String {
        val preferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        return "JWT ${preferences.getString(TOKEN, "")}"
    }
}