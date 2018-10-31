package ru.kpfu.itis.stayintouch.ui.news

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.kpfu.itis.stayintouch.model.Post

@StateStrategyType(AddToEndSingleStrategy::class)
interface NewsFragmentView : MvpView {

    fun changeLoadingState(isLoading: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun showDetails(position: Int)

    fun setNews(news: List<Post>)
}