package ru.kpfu.itis.stayintouch.service

import io.reactivex.Single
import retrofit2.http.*
import ru.kpfu.itis.stayintouch.model.Post
import ru.kpfu.itis.stayintouch.model.PostCreate
import ru.kpfu.itis.stayintouch.model.Tag

interface PostService {

    @GET("api/feed")
    fun getNews(@Header("Authorization") token: String, @Query("offset") offset: Int) : Single<List<Post>>

    @GET("api/news/")
    fun getAllPosts(@Header("Authorization") token: String) : Single<List<Post>>

    @POST("api/news/")
    fun createPost(@Header("Authorization") token: String, @Body post: PostCreate) : Single<Post>

    @GET("api/news/{id}")
    fun getPostById(@Header("Authorization") token: String, @Path("id") id: Int) : Single<Post>

    @GET("api/find")
    fun getPostsByTag(@Header("Authorization") token: String,
                      @Query("tag") tags: List<String>) : Single<List<Post>>
}