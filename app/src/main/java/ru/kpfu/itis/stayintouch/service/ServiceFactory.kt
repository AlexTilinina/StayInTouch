package ru.kpfu.itis.stayintouch.service

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.kpfu.itis.stayintouch.service.mock.*

object ServiceFactory {

    private val authService = buildRetrofit().create(AuthService::class.java)
    private val signUpService = buildRetrofit().create(SignUpService::class.java)
    private val postService = buildRetrofit().create(PostService::class.java)
    private val tagService = buildRetrofit().create(TagService::class.java)
    private val userService = buildRetrofit().create(UserService::class.java)

    fun provideAuthService(): AuthService {
        return authService
    }

    fun provideSignUpService(): SignUpService {
        return signUpService
    }

    fun providePostService(): PostService {
        return postService
    }

    fun provideTagService() : TagService {
        return tagService
    }

    fun provideUserService() : UserService {
        return userService
    }

    fun provideAuthServiceMock(): AuthServiceMock {
        return AuthServiceMock()
    }

    fun provideSignUpServiceMock(): SignUpServiceMock {
        return SignUpServiceMock()
    }

    fun providePostServiceMock(): PostServiceMock {
        return PostServiceMock()
    }

    fun provideTagServiceMock() : TagServiceMock {
        return TagServiceMock()
    }

    fun provideUserServiceMock() : UserServiceMock {
        return UserServiceMock()
    }

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://tagworld.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}