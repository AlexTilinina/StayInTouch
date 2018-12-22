package ru.kpfu.itis.stayintouch.ui.answers

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.kpfu.itis.stayintouch.repository.UserRepository

@InjectViewState
class AnswersFragmentPresenter : MvpPresenter<AnswersFragmentView>() {

    fun init() {
        super.onFirstViewAttach()
        loadMyComments()
    }

    fun loadMyComments(){
        UserRepository
            .getCurrentUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewState::setLoading)
            .doAfterTerminate(viewState::setNotLoading)
            .subscribe(viewState::showMyComments, viewState::handleError)
    }
}