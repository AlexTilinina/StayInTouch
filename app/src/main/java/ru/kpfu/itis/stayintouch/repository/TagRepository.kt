package ru.kpfu.itis.stayintouch.repository

import io.reactivex.Single
import retrofit2.http.Query
import ru.kpfu.itis.stayintouch.model.Tag
import ru.kpfu.itis.stayintouch.service.ServiceFactory

object TagRepository {

    fun getTagsByText(text: String) : Single<Tag> {
        return ServiceFactory.provideTagServiceMock().getTagsByText(text)
    }

    fun subscribeToTag(id: Int) : Single<Tag> {
        return ServiceFactory.provideTagService().subscribeToTag(AuthRepository.token, id)
    }

    fun unsubscribeFromTag(id: Int) : Single<Tag> {
        return ServiceFactory.provideTagService().unsubscribeFromTag(AuthRepository.token, id)
    }
}