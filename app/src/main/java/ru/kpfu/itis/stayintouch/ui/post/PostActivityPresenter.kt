package ru.kpfu.itis.stayintouch.ui.post

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.kpfu.itis.stayintouch.repository.CommentRepository
import ru.kpfu.itis.stayintouch.utils.COUNT_OF_ELEMENTS

@InjectViewState
class PostActivityPresenter : MvpPresenter<PostActivityView>()  {

    private var postId : Int = 0

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.getPostId()
        loadComments()
    }

    fun loadComments(){
        CommentRepository
            .getCommentsByPostId(postId, 0)
            .doOnSubscribe(viewState::setLoading)
            .doAfterTerminate(viewState::setNotLoading)
            .subscribe(viewState::showComments, viewState::handleError)
    }

    fun loadNextElements(page: Int) {
        CommentRepository
            .getCommentsByPostId(postId, COUNT_OF_ELEMENTS * page)
            .doOnSubscribe(viewState::setLoading)
            .doAfterTerminate(viewState::setNotLoading)
            .subscribe(viewState::loadMoreItems, viewState::handleError)
    }

    fun setPostId(postId: Int) {
        this.postId = postId
    }
}