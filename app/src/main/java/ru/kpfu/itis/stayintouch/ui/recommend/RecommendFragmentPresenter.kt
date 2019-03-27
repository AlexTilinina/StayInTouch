package ru.kpfu.itis.stayintouch.ui.recommend

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.kpfu.itis.stayintouch.repository.PostRepository
import ru.kpfu.itis.stayintouch.utils.COUNT_OF_ELEMENTS

@InjectViewState
class RecommendFragmentPresenter : MvpPresenter<RecommendFragmentView>() {

    fun loadRecommendations() {
        //TODO рекомендации
        PostRepository
            .getAllPosts(1, COUNT_OF_ELEMENTS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewState::setLoading)
            .doAfterTerminate(viewState::setNotLoading)
            .subscribe(viewState::setNews, viewState::handleError)
    }

    fun loadNextElements(start: Int) {
        PostRepository
            .getAllPosts(start, start + COUNT_OF_ELEMENTS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewState::setLoading)
            .doAfterTerminate(viewState::setNotLoading)
            .subscribe(viewState::loadMoreItems, viewState::handleError)
    }

    fun isSearch(tags: List<String>){
        if (tags.isNotEmpty()) {
            loadSearchData(tags)
        }
        else loadRecommendations()
    }

    fun loadSearchData(tags: List<String>) {
        PostRepository
            .findPostByTag(tags)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewState::setLoading)
            .doAfterTerminate(viewState::setNotLoading)
            .subscribe(viewState::setNews, viewState::handleError)
    }
}