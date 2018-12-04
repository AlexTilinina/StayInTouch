package ru.kpfu.itis.stayintouch.service

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.kpfu.itis.stayintouch.model.Comment

interface CommentService {

    @POST("api/news/{postId}/comment")
    fun createComment(@Path("postId") postId: Int, @Query("comment") comment: Comment) : Single<Comment>

    @GET("api/news/{postId}/comments")
    fun getCommentsByPostId(@Path("postId") postId: Int, @Query("offset") offset: Int) : Single<List<Comment>>
}