package ru.kpfu.itis.stayintouch.repository

import io.reactivex.Single
import ru.kpfu.itis.stayintouch.model.Post
import ru.kpfu.itis.stayintouch.model.PostCreate
import ru.kpfu.itis.stayintouch.service.ServiceFactory

object PostRepository {

    fun getNews(offset: Int) : Single<List<Post>> {
        return ServiceFactory.providePostService().getNews(AuthRepository.token, offset)
    }

    fun createPost(post: PostCreate) : Single<Post> {
        return ServiceFactory.providePostService().createPost(AuthRepository.token, post)
    }

    fun getPostById(id: Int) : Single<Post> {
        return ServiceFactory.providePostService().getPostById(AuthRepository.token, id)
    }
}