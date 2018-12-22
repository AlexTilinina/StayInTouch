package ru.kpfu.itis.stayintouch.ui.recommend

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.kpfu.itis.stayintouch.model.Post
import ru.kpfu.itis.stayintouch.model.Tag
import ru.kpfu.itis.stayintouch.model.User

@InjectViewState
class RecommendFragmentPresenter : MvpPresenter<RecommendFragmentView>() {

    fun init() {
        //TODO сервисы
        notifyDataLoaded(addTestData())
    }

    fun notifyDataLoaded(news: List<Post>) {
        viewState.setNews(news)
    }

    fun addTestData() : List<Post> {
        val user = User(4, "Name", "Surname")
        val tag1 = Tag(4, "Test")
        val tag2 = Tag(3, "Checking")
        val tagList = ArrayList<Tag>()
        tagList.add(tag1)
        tagList.add(tag2)
        val testPost = Post(null, user, "Test data for post text", null)
        val postList = ArrayList<Post>()
        for (i in 0..9)
            postList.add(testPost)
        return postList
    }
}