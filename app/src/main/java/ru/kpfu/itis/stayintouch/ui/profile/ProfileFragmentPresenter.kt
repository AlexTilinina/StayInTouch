package ru.kpfu.itis.stayintouch.ui.profile

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    fun changePhoto(file: MultipartBody.Part) {
        UserRepository
            .changePhoto(file)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewState::setLoading)
            .doAfterTerminate(viewState::setNotLoading)
            .subscribe(viewState::getMessage, viewState::handleError)
    }
}