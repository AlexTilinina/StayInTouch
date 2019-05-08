package ru.kpfu.itis.stayintouch.repository

import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.kpfu.itis.stayintouch.model.Message
import ru.kpfu.itis.stayintouch.model.Post
import ru.kpfu.itis.stayintouch.model.PostCreate
import ru.kpfu.itis.stayintouch.service.ServiceFactory

object PostRepository {

    fun getNews(start: Int, end: Int) : Single<List<Post>> {
        return ServiceFactory.providePostService().getNews(AuthRepository.token, start, end)
    }

    fun getAllPosts(start: Int, end: Int) : Single<List<Post>> {
        return ServiceFactory.providePostService().getAllPosts(AuthRepository.token, start, end)
    }

    fun createPost(post: PostCreate) : Single<Post> {
        return ServiceFactory.providePostService().createPost(AuthRepository.token, post)
    }

    fun getPostById(id: Int) : Single<Post> {
        return ServiceFactory.providePostService().getPostById(AuthRepository.token, id)
    }

    fun findPostByTag(tags: List<String>) : Single<List<Post>> {
        return ServiceFactory.providePostService().getPostsByTag(AuthRepository.token, tags)
    }

    fun addAttachment(file: MultipartBody.Part,
                      label: RequestBody,
                      attach_to: RequestBody) : Single<Message> {
        return ServiceFactory.providePostService().addAttachment(AuthRepository.token, file, label, attach_to)
    }

    fun addAttachmentLink(link: RequestBody,
                          label: RequestBody,
                          attach_to: RequestBody) : Single<Message> {
        return ServiceFactory.providePostService().addAttachmentLink(AuthRepository.token, link, label, attach_to)
    }
}