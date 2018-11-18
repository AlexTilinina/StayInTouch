package ru.kpfu.itis.stayintouch.service

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kpfu.itis.stayintouch.model.User

interface UserService {

    @GET("users/user")
    fun getUserById(@Query("id") id: String) : Single<User>
}