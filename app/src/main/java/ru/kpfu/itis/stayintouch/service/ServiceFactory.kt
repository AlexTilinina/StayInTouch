package ru.kpfu.itis.stayintouch.service

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.kpfu.itis.stayintouch.service.mock.AuthServiceMock
import ru.kpfu.itis.stayintouch.service.mock.SignUpServiceMock

object ServiceFactory {

    private val authService: AuthService = buildRetrofit().create(AuthService::class.java)
    private val signUpService: SignUpService = buildRetrofit().create(SignUpService::class.java)

    fun provideAuthService(): AuthService {
        return authService
    }

    fun provideSignUpService(): SignUpService {
        return signUpService
    }

    fun provideAuthServiceMock(): AuthServiceMock {
        return AuthServiceMock()
    }

    fun provideSignUpServiceMock(): SignUpServiceMock {
        return SignUpServiceMock()
    }

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://tagworld.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}