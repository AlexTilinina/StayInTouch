package ru.kpfu.itis.stayintouch.ui.splash

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.kpfu.itis.stayintouch.model.Token

interface SplashActivityView : MvpView {

    fun tokenChanged(token: Token)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun handleError(error: Throwable)
}