package ru.kpfu.itis.stayintouch.repository

import io.reactivex.Single
import retrofit2.http.Query
import ru.kpfu.itis.stayintouch.model.CreatePostResponse
import ru.kpfu.itis.stayintouch.model.Post
import ru.kpfu.itis.stayintouch.service.ServiceFactory

object PostRepository {

    fun getPostsByTagIds(tags: List<Int>, offset: Int) : Single<List<Post>> {
        return ServiceFactory.providePostServiceMock().getPostsByTagIds(tags, offset)
    }

    fun createPost(post: Post) : Single<CreatePostResponse> {
        return ServiceFactory.providePostServiceMock().createPost(post)
    }
}