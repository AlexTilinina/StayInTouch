package ru.kpfu.itis.stayintouch.service

import io.reactivex.Single
import retrofit2.http.Query
import ru.kpfu.itis.stayintouch.model.Post
import ru.kpfu.itis.stayintouch.model.Tag

interface PostService {

    fun getPostsByTagIds(@Query("tag") tags: List<Int>, @Query("offset") offset: Int) : Single<List<Post>>
}