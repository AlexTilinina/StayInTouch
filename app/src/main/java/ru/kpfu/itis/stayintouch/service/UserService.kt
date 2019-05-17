package ru.kpfu.itis.stayintouch.service

import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import ru.kpfu.itis.stayintouch.model.Message
import ru.kpfu.itis.stayintouch.model.User
import ru.kpfu.itis.stayintouch.model.UserPatch

interface UserService {

    @GET("auth/get-current-user/")
    fun getCurrentUser(@Header("Authorization") token: String): Single<User>

    @PATCH("auth/get-current-user/")
    fun editProfile(@Header("Authorization") token: String,
                    @Body userPath: UserPatch): Single<User>

    @Multipart
    @POST("auth/get-current-user/change-photo")
    fun changePhoto(@Header("Authorization") token: String,
                    @Part file: MultipartBody.Part) : Single<Message>
}