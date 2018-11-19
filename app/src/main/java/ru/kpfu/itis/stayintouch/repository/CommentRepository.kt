package ru.kpfu.itis.stayintouch.repository

import io.reactivex.Single
import retrofit2.http.Path
import ru.kpfu.itis.stayintouch.model.Comment
import ru.kpfu.itis.stayintouch.service.ServiceFactory

object CommentRepository {

    fun createComment(postId: Int, comment: Comment) : Single<Comment> {
        return ServiceFactory.provideCommentServiceMock().createComment(postId, comment)
    }

    fun getCommentsByPostId(postId: Int, offset: Int) : Single<List<Comment>> {
        return ServiceFactory.provideCommentServiceMock().getCommentsByPostId(postId, offset)
    }
}