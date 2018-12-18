package ru.kpfu.itis.stayintouch.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    private val commentService = buildRetrofit().create(CommentService::class.java)

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

    fun provideCommentService() : CommentService {
        return commentService
    }

    fun provideTagServiceMock() : TagServiceMock {
        return TagServiceMock()
    }

    fun provideCommentServiceMock() : CommentServiceMock {
        return CommentServiceMock()
    }

    private fun buildRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY //TODO убрать

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://finelifex-eduhelper-api.herokuapp.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}