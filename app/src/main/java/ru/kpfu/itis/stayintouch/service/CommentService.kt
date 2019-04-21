package ru.kpfu.itis.stayintouch.service

import io.reactivex.Single
import retrofit2.http.*
import ru.kpfu.itis.stayintouch.model.Comment

interface CommentService {

    @POST("api/news/{postId}/add-comment")
    fun createComment(@Header("Authorization") token: String, @Path("postId") postId: Int, @Body comment: Comment) : Single<Comment>

    @POST("/api/comments/{id}/add-answer")
    fun createAnswer(@Header("Authorization") token: String, @Path("id") commentId: String, @Body text: Comment) : Single<Comment>

    @GET("api/news/{postId}/comments")
    fun getCommentsByPostId(@Path("postId") postId: Int, @Query("offset") offset: Int) : Single<List<Comment>>

    @GET("api/comments/my")
    fun getMyComments(@Header("Authorization") token: String) : Single<List<Comment>>

    @GET("/api/answers")
    fun getAnswers(@Header("Authorization") token: String) : Single<List<Comment>>

    @GET("/api/comments/{id}")
    fun getCommentById(@Header("Authorization") token: String, @Path("id") commentId: String) : Single<Comment>
}