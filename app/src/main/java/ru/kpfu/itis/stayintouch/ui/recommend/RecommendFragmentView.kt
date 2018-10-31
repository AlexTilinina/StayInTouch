package ru.kpfu.itis.stayintouch.ui.recommend

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.kpfu.itis.stayintouch.model.Post
import ru.kpfu.itis.stayintouch.model.Tag

interface RecommendFragmentView : MvpView {

    fun changeLoadingState(isLoading: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun showDetails(position: Int)

    fun setNews(news: List<Post>)

    fun setTags(tags: List<Tag>)
}