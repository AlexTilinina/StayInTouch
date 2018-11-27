package ru.kpfu.itis.stayintouch.ui.post

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewState::setLoading)
            .doAfterTerminate(viewState::setNotLoading)
            .subscribe(viewState::showComments, viewState::handleError)
    }

    fun loadNextElements(page: Int) {
        CommentRepository
            .getCommentsByPostId(postId, COUNT_OF_ELEMENTS * page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewState::setLoading)
            .doAfterTerminate(viewState::setNotLoading)
            .subscribe(viewState::loadMoreItems, viewState::handleError)
    }

    fun setPostId(postId: Int) {
        this.postId = postId
    }
}