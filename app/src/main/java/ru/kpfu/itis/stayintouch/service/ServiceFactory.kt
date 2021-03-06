package ru.kpfu.itis.stayintouch.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ServiceFactory {

    private val retrofit = buildRetrofit()
    private val authService = retrofit.create(AuthService::class.java)
    private val signUpService = retrofit.create(SignUpService::class.java)
    private val postService = retrofit.create(PostService::class.java)
    private val tagService = retrofit.create(TagService::class.java)
    private val userService = retrofit.create(UserService::class.java)
    private val commentService = retrofit.create(CommentService::class.java)

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