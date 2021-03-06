package ru.kpfu.itis.stayintouch.ui.post

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import io.reactivex.disposables.Disposable
import ru.kpfu.itis.stayintouch.model.Comment
import ru.kpfu.itis.stayintouch.model.Post
import ru.kpfu.itis.stayintouch.model.User

interface PostActivityView : MvpView {

    fun changeLoadingState(isLoading: Boolean)

    fun loadMoreItems(items: List<Comment>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun handleError(error: Throwable)

    fun setLoading(disposable: Disposable)

    fun setNotLoading()

    fun getPostId()

    fun initPost(post: Post)

    fun initUser(user: User)

    fun addItem(comment: Comment)
}