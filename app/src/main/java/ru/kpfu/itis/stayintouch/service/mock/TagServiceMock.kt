package ru.kpfu.itis.stayintouch.service.mock

import io.reactivex.Single
import retrofit2.http.Query
import ru.kpfu.itis.stayintouch.model.Tag

class TagServiceMock {

    fun getTagsByText(@Query("tag") text: String) : Single<Tag> {
        return Single.just(Tag("2", text))
    }
}