package ru.kpfu.itis.stayintouch.ui.news

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.kpfu.itis.stayintouch.service.ServiceFactory
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
        ServiceFactory.providePostServiceMock()
            .getPostsByTagIds(testTagsList(), 0)
            .doOnSubscribe(viewState::setLoading)
            .doAfterTerminate(viewState::setNotLoading)
            .doAfterTerminate(viewState::checkIfEmpty)
            .subscribe(viewState::showPosts, viewState::handleError)
    }

    fun loadNextElements(page: Int) {
        ServiceFactory.providePostServiceMock()
            .getPostsByTagIds(testTagsList(), COUNT_OF_ELEMENTS * page)
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