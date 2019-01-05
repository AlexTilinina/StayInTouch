package ru.kpfu.itis.stayintouch.ui.splash

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.kpfu.itis.stayintouch.repository.AuthRepository

@InjectViewState
class SplashActivityPresenter : MvpPresenter<SplashActivityView>() {

    fun refreshToken(token: String) {
        AuthRepository.refreshToken(token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe (viewState::tokenChanged, viewState::handleError)
    }

    fun setToken(token: String) {
        AuthRepository.setCorrectToken(token)
    }
}