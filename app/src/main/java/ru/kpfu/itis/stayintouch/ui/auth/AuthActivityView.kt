package ru.kpfu.itis.stayintouch.ui.auth

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.kpfu.itis.stayintouch.model.AuthResponse

interface AuthActivityView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun handleError(error: Throwable)

    fun login(result: AuthResponse)
}