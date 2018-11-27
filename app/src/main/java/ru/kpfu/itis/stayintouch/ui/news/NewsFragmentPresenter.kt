package ru.kpfu.itis.stayintouch.ui.news

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

    fun loadPosts(){
        //TODO сгрузить с профиля список тегов
        PostRepository
            .getPostsByTagIds(testTagsList(), 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewState::setLoading)
            .doAfterTerminate(viewState::setNotLoading)
            .doAfterTerminate(viewState::checkIfEmpty)
            .subscribe(viewState::showPosts, viewState::handleError)
    }

    fun loadNextElements(page: Int) {
        PostRepository
            .getPostsByTagIds(testTagsList(), COUNT_OF_ELEMENTS * page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewState::setLoading)
            .doAfterTerminate(viewState::setNotLoading)
            .subscribe(viewState::loadMoreItems, viewState::handleError)
    }

    fun testTagsList(): List<Int> {
        val tagList = ArrayList<Int>()
        tagList.add(1)
        tagList.add(2)
        tagList.add(4)
        return tagList
    }
}