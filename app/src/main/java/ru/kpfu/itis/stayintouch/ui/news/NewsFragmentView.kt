package ru.kpfu.itis.stayintouch.ui.news

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import io.reactivex.disposables.Disposable
import ru.kpfu.itis.stayintouch.model.Post

@StateStrategyType(AddToEndSingleStrategy::class)
interface NewsFragmentView : MvpView {

    fun showPosts(posts: List<Post>)

    fun changeLoadingState(isLoading: Boolean)

    fun loadMoreItems(items: List<Post>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun handleError(error: Throwable)

    fun setLoading(disposable: Disposable)

    fun setNotLoading()

    fun checkIfEmpty()
}