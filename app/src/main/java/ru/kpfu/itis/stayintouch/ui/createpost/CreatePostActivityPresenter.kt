package ru.kpfu.itis.stayintouch.ui.createpost

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.kpfu.itis.stayintouch.model.PostCreate
import ru.kpfu.itis.stayintouch.repository.PostRepository

@InjectViewState
class CreatePostActivityPresenter : MvpPresenter<CreatePostActivityView>() {

    fun createPost(post: PostCreate) {
        PostRepository.createPost(post)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewState::setLoading)
            .subscribe(viewState::returnToMainActivity, viewState::handleError)
    }
}