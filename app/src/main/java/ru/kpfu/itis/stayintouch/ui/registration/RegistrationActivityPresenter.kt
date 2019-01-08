package ru.kpfu.itis.stayintouch.ui.registration

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.kpfu.itis.stayintouch.repository.RegistrationRepository

@InjectViewState
class RegistrationActivityPresenter : MvpPresenter<RegistrationActivityView>() {

    fun registration(name: String, surname: String, email: String, password: String, password2: String) {
        RegistrationRepository.registration(name, surname, email, password, password2)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe(viewState::setLoading)
            .doAfterTerminate(viewState::setNotLoading)
            .subscribe(viewState::auth, viewState::handleError)
    }
}