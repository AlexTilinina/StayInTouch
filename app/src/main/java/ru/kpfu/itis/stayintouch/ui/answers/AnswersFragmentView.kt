package ru.kpfu.itis.stayintouch.ui.answers

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import io.reactivex.disposables.Disposable
import ru.kpfu.itis.stayintouch.model.Comment
import ru.kpfu.itis.stayintouch.model.User

@StateStrategyType(AddToEndSingleStrategy::class)
interface AnswersFragmentView : MvpView {

    fun showMyComments(user: User)

    fun showAnswers()

    fun setLoading(disposable: Disposable)

    fun setNotLoading()

    @StateStrategyType(SkipStrategy::class)
    fun showDetails(position: Int)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun handleError(error: Throwable)
}