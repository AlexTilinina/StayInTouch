package ru.kpfu.itis.stayintouch.service

import io.reactivex.Single
import retrofit2.http.*
import ru.kpfu.itis.stayintouch.model.User
import ru.kpfu.itis.stayintouch.model.UserPatch

interface UserService {

    @GET("api/users/{id}/")
    fun getUserById(@Header("Authorization") token: String, @Path("id") id: String) : Single<User>

    @GET("auth/get-current-user/")
    fun getCurrentUser(@Header("Authorization") token: String): Single<User>

    @PATCH("auth/get-current-user/")
    fun editProfile(@Header("Authorization") token: String, @Body userPath: UserPatch): Single<User>
}