package ru.kpfu.itis.stayintouch.ui.recommend

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import io.reactivex.disposables.Disposable
import ru.kpfu.itis.stayintouch.model.Post
import ru.kpfu.itis.stayintouch.model.Tag

interface RecommendFragmentView : MvpView {

    fun setLoading(disposable: Disposable)

    fun setNotLoading()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun handleError(error: Throwable)

    fun setNews(news: List<Post>)

    fun setTags(tags: List<Tag>)

    fun loadMoreItems(items: List<Post>)
}