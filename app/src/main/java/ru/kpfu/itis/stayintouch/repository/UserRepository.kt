package ru.kpfu.itis.stayintouch.repository

import android.content.Context
import android.preference.PreferenceManager
import ru.kpfu.itis.stayintouch.model.User
import ru.kpfu.itis.stayintouch.service.ServiceFactory
import ru.kpfu.itis.stayintouch.utils.USER_ID

object UserRepository {

    fun getUserId(context: Context) : String {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(USER_ID, "")
    }

    fun getCurrentUser(context: Context) : User {
        return getUserById(getUserId(context))
    }

    fun getUserById(id: String) : User {
        return ServiceFactory.provideUserServiceMock().getUserById(id).blockingGet()
    }
}