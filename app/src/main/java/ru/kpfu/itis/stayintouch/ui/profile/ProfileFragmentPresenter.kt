package ru.kpfu.itis.stayintouch.ui.profile

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.kpfu.itis.stayintouch.model.UserPatch
import ru.kpfu.itis.stayintouch.repository.UserRepository

@InjectViewState
class ProfileFragmentPresenter : MvpPresenter<ProfileFragmentView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadUser()
    }

    private fun loadUser() {
        UserRepository
            .getCurrentUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewState::setLoading)
            .doAfterTerminate(viewState::setNotLoading)
            .subscribe(viewState::loadUser, viewState::handleError)
    }

    fun editProfile(name: String, surname: String, email: String) {
        UserRepository
            .editProfile(UserPatch(name, surname, email))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewState::setLoading)
            .doAfterTerminate(viewState::setNotLoading)
            .doAfterTerminate(viewState::makeEditableInvisible)
            .subscribe(viewState::loadUser, viewState::handleError)
    }
}