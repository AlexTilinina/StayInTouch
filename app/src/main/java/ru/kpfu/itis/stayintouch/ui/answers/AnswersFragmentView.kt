package ru.kpfu.itis.stayintouch.ui.answers

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.kpfu.itis.stayintouch.model.Comment

@StateStrategyType(AddToEndSingleStrategy::class)
interface AnswersFragmentView : MvpView {

    fun setComments(comments : List<Comment>)

    fun changeLoadingState(isLoading: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun showDetails(position: Int)
}