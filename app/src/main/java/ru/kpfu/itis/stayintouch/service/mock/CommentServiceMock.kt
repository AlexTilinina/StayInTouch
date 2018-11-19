package ru.kpfu.itis.stayintouch.service.mock

import io.reactivex.Single
import ru.kpfu.itis.stayintouch.model.Comment
import ru.kpfu.itis.stayintouch.model.User
import java.util.*

class CommentServiceMock {

    fun createComment(postId: Int, comment: Comment) : Single<Comment> {
        comment.id = "3"
        return Single.just(comment)
    }

    fun getCommentsByPostId(postId: Int, offset: Int) : Single<List<Comment>> {
        val comments = ArrayList<Comment>()
        val user = User("4", "Name", "Surname")
        val comment = Comment("3", user, "$offset: test 124 342423432", GregorianCalendar(), postId)
        for (i in 0..20)
            comments.add(comment)
        return Single.just(comments)
    }
}