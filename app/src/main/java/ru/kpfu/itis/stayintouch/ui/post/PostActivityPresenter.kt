package ru.kpfu.itis.stayintouch.ui.post

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.kpfu.itis.stayintouch.model.Comment
import ru.kpfu.itis.stayintouch.repository.CommentRepository
import ru.kpfu.itis.stayintouch.repository.PostRepository
import ru.kpfu.itis.stayintouch.repository.UserRepository
import ru.kpfu.itis.stayintouch.utils.COUNT_OF_ELEMENTS

@InjectViewState
class PostActivityPresenter : MvpPresenter<PostActivityView>()  {

    private var postId : Int = 0

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.getPostId()
        getUser()
    }

    fun initPost(){
        PostRepository
            .getPostById(postId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewState::setLoading)
            .doAfterTerminate(viewState::setNotLoading)
            .subscribe(viewState::initPost, viewState::handleError)
    }

    fun getUser() {
        UserRepository
            .getCurrentUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewState::setLoading)
            .subscribe { t1, t2 ->
                if (t1 != null) {
                    viewState.initUser(t1)
                    initPost()
                }
                if (t2 != null) {
                    viewState.handleError(t2)
                }

            }
    }

    fun createComment(postId: Int, comment: Comment) {
        CommentRepository
            .createComment(postId, comment)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewState::setLoading)
            .doAfterTerminate(viewState::setNotLoading)
            .subscribe(viewState::addItem, viewState::handleError)
    }

    fun setPostId(postId: Int) {
        this.postId = postId
    }
}