package ru.kpfu.itis.stayintouch.repository

import io.reactivex.Single
import ru.kpfu.itis.stayintouch.model.AuthResponse
import ru.kpfu.itis.stayintouch.model.RegistrationRequest
import ru.kpfu.itis.stayintouch.service.ServiceFactory

object RegistrationRepository {

    fun registration(name: String,
                     surname: String,
                     email: String,
                     password: String,
                     password2: String
    ): Single<AuthResponse> {
        return ServiceFactory.provideSignUpService()
            .registration(RegistrationRequest(email.split("@")[0], name, surname, email, password, password2))
    }
}