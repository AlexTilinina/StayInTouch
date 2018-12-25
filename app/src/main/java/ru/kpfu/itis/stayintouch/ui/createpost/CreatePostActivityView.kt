package ru.kpfu.itis.stayintouch.ui.createpost

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import io.reactivex.disposables.Disposable
import ru.kpfu.itis.stayintouch.model.Post

interface CreatePostActivityView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun handleError(error: Throwable)

    fun setLoading(disposable: Disposable)

    fun returnToMainActivity(post: Post)
}