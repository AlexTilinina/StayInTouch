package ru.kpfu.itis.stayintouch.service.mock

import io.reactivex.Single
import retrofit2.http.Query
import ru.kpfu.itis.stayintouch.model.User

class UserServiceMock {

    fun getUserById(@Query("id") id: String) : Single<User> {
        return Single.just(User(id, "Name", "Username", null))
    }
}