package ru.kpfu.itis.stayintouch.service

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import ru.kpfu.itis.stayintouch.model.Tag

interface TagService {

    @GET("api/tags/")
    fun getTags(@Header("Authorization") token: String) : Single<List<Tag>>

    @GET("api/tags/{id}/sub")
    fun subscribeToTag(@Header("Authorization") token: String, @Path("id") id: Int) : Single<Tag>

    @GET("api/tags/{id}/unsub")
    fun unsubscribeFromTag(@Header("Authorization") token: String, @Path("id") id: Int) : Single<Tag>
}