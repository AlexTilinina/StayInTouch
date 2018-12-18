package ru.kpfu.itis.stayintouch.ui.news

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.kpfu.itis.stayintouch.repository.PostRepository
import ru.kpfu.itis.stayintouch.utils.COUNT_OF_ELEMENTS
import java.util.ArrayList

@InjectViewState
class NewsFragmentPresenter : MvpPresenter<NewsFragmentView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadPosts()
    }

    @SuppressLint("CheckResult")
    fun loadPosts(){
        PostRepository
            .getNews(0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewState::setLoading)
            .doAfterTerminate(viewState::setNotLoading)
            .doAfterTerminate(viewState::checkIfEmpty)
            .subscribe(viewState::showPosts, viewState::handleError)
    }

    @SuppressLint("CheckResult")
    fun loadNextElements(page: Int) {
        PostRepository
            .getNews(COUNT_OF_ELEMENTS * page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewState::setLoading)
            .doAfterTerminate(viewState::setNotLoading)
            .subscribe(viewState::loadMoreItems, viewState::handleError)
    }
}