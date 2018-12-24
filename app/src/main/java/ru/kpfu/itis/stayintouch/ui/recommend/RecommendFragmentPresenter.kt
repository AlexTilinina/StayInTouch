package ru.kpfu.itis.stayintouch.ui.recommend

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.kpfu.itis.stayintouch.repository.PostRepository

@InjectViewState
class RecommendFragmentPresenter : MvpPresenter<RecommendFragmentView>() {

    fun init() {
        //TODO сервисы
        loadRecommendations()
    }

    fun loadRecommendations() {
        PostRepository
            .getAllPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewState::setLoading)
            .doAfterTerminate(viewState::setNotLoading)
            .subscribe(viewState::setNews, viewState::handleError)
    }
}