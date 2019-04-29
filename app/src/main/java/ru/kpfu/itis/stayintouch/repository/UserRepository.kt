package ru.kpfu.itis.stayintouch.repository

import io.reactivex.Single
import okhttp3.MultipartBody
import ru.kpfu.itis.stayintouch.model.Message
import ru.kpfu.itis.stayintouch.model.User
import ru.kpfu.itis.stayintouch.model.UserPatch
import ru.kpfu.itis.stayintouch.service.ServiceFactory

object UserRepository {

    fun getCurrentUser(): Single<User> {
        return ServiceFactory.provideUserService().getCurrentUser(AuthRepository.token)
    }

    fun editProfile(userPatch: UserPatch): Single<User> {
        return ServiceFactory.provideUserService().editProfile(AuthRepository.token, userPatch)
    }

    fun changePhoto(file: MultipartBody.Part) : Single<Message> {
        return ServiceFactory.provideUserService().changePhoto(AuthRepository.token, file)
    }
}