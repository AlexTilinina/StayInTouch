package ru.kpfu.itis.stayintouch.service.mock

import io.reactivex.Single
import ru.kpfu.itis.stayintouch.model.CreatePostResponse
import ru.kpfu.itis.stayintouch.model.Post
import ru.kpfu.itis.stayintouch.model.Tag
import ru.kpfu.itis.stayintouch.model.User
import ru.kpfu.itis.stayintouch.utils.COUNT_OF_ELEMENTS
import java.util.*

class PostServiceMock {

    fun getPostsByTagIds(tags: List<Int>, offset: Int) : Single<List<Post>> {
        val user = User(4, "Name", "Surname")
        val tag = Tag("4", "Azaza")
        val tagList = ArrayList<Tag>()
        tagList.add(tag)
        val testPost = Post(2, user, "$offset test", GregorianCalendar(), null, tagList)
        val postList = ArrayList<Post>()
        for (i in 0..COUNT_OF_ELEMENTS)
            postList.add(testPost)
        return Single.just(postList)
    }

    fun createPost(post: Post) : Single<CreatePostResponse> {
        return Single.just(CreatePostResponse("1", post))
    }
}