package ru.kpfu.itis.stayintouch.ui.registration

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.kpfu.itis.stayintouch.model.AuthResponse

interface RegistrationActivityView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun handleError(error: Throwable)

    fun auth(result: AuthResponse)
}