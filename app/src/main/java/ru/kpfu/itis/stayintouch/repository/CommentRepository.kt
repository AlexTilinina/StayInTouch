package ru.kpfu.itis.stayintouch.repository

import io.reactivex.Single
import ru.kpfu.itis.stayintouch.model.Comment
import ru.kpfu.itis.stayintouch.service.ServiceFactory

object CommentRepository {

    fun createComment(postId: Int, comment: Comment) : Single<Comment> {
        return ServiceFactory.provideCommentService().createComment(AuthRepository.token, postId, comment)
    }

    fun getMyComments() : Single<List<Comment>> {
        return ServiceFactory.provideCommentService().getMyComments(AuthRepository.token)
    }

    fun getCommentsByPostId(postId: Int, offset: Int) : Single<List<Comment>> {
        return ServiceFactory.provideCommentServiceMock().getCommentsByPostId(postId, offset)
    }
}