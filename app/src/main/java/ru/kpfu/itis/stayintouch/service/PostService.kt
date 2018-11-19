package ru.kpfu.itis.stayintouch.service

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.kpfu.itis.stayintouch.model.CreatePostResponse
import ru.kpfu.itis.stayintouch.model.Post

interface PostService {

    @GET("news")
    fun getPostsByTagIds(@Query("tag") tags: List<Int>, @Query("offset") offset: Int) : Single<List<Post>>

    @POST("news/create")
    fun createPost(post: Post) : Single<CreatePostResponse>
}