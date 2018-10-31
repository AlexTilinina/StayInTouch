package ru.kpfu.itis.stayintouch.ui.news

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.kpfu.itis.stayintouch.model.Post
import ru.kpfu.itis.stayintouch.model.Tag
import ru.kpfu.itis.stayintouch.model.User

@InjectViewState
class NewsFragmentPresenter : MvpPresenter<NewsFragmentView>() {

    fun init() {
        //TODO сервисы
        notifyDataLoaded(addTestData())
    }

    fun notifyDataLoaded(news: List<Post>) {
        viewState.changeLoadingState(false)
        viewState.setNews(news)
    }

    fun addTestData() : List<Post> {
        val user = User("4", "Name", "Surname")
        val tag = Tag("4", "Azaza")
        val tagList = ArrayList<Tag>()
        tagList.add(tag)
        val testPost: Post = Post(user, "test", null, tagList)
        val postList = ArrayList<Post>()
        for (i in 0..9)
            postList.add(testPost)
        return postList
    }

}