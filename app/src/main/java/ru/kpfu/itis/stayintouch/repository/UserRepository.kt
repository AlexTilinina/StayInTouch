package ru.kpfu.itis.stayintouch.repository

import android.content.Context
import io.reactivex.Single
import ru.kpfu.itis.stayintouch.model.User
import ru.kpfu.itis.stayintouch.service.ServiceFactory

object UserRepository {

    fun getCurrentUser(context: Context) : Single<User> {
        return ServiceFactory.provideUserService().getCurrentUser(AuthRepository.getToken(context))
    }
}