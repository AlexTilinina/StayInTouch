package ru.kpfu.itis.stayintouch.ui.profile

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import io.reactivex.disposables.Disposable
import ru.kpfu.itis.stayintouch.model.User

@StateStrategyType(AddToEndSingleStrategy::class)
interface ProfileFragmentView: MvpView {

    fun loadUser(user: User)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun handleError(error: Throwable)

    fun setLoading(disposable: Disposable)

    fun setNotLoading()

    fun makeEditableInvisible()
}