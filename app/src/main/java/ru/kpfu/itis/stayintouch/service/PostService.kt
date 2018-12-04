package ru.kpfu.itis.stayintouch.service

import io.reactivex.Single
import retrofit2.http.*
import ru.kpfu.itis.stayintouch.model.Post
import ru.kpfu.itis.stayintouch.model.PostCreate

interface PostService {

    @GET("api/news/")
    fun getNews(@Header("Authorization") token: String, @Query("offset") offset: Int) : Single<List<Post>>

    @POST("api/news/")
    fun createPost(@Header("Authorization") token: String, @Body post: PostCreate) : Single<Post>
}