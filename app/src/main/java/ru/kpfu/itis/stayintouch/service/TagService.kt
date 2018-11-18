package ru.kpfu.itis.stayintouch.service

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kpfu.itis.stayintouch.model.Tag

interface TagService {

    @GET("tags")
    fun getTagsByText(@Query("tag") text: String) : Single<Tag>
}