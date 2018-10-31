package ru.kpfu.itis.stayintouch.ui.answers

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.kpfu.itis.stayintouch.model.Comment
import ru.kpfu.itis.stayintouch.model.User

@InjectViewState
class AnswersFragmentPresenter : MvpPresenter<AnswersFragmentView>() {

    fun init() {
        //TODO сервисы
        notifyDataLoaded(addTestData())
    }

    fun notifyDataLoaded(comments: List<Comment>) {
        viewState.changeLoadingState(false)
        viewState.setComments(comments)
    }

    fun addTestData() : List<Comment> {
        val user = User("4", "Name", "Surname")
        val comment = Comment("2", user, "test text 1234")
        val comments = ArrayList<Comment>()
        for (i in 0..9)
            comments.add(comment)
        return comments
    }
}