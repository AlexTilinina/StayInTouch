package ru.kpfu.itis.stayintouch.ui.auth

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.kpfu.itis.stayintouch.repository.AuthRepository

@InjectViewState
class AuthActivityPresenter : MvpPresenter<AuthActivityView>() {

    fun login(email: String, password: String) {
        AuthRepository.login(email, password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(viewState::login, viewState::handleError)
    }

    fun setToken(token: String) {
        AuthRepository.setCorrectToken(token)
    }
}