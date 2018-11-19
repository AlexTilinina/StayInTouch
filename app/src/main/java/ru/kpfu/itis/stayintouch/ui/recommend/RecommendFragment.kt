package ru.kpfu.itis.stayintouch.ui.recommend

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_recommend.*
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.model.Post
import ru.kpfu.itis.stayintouch.model.Tag
import ru.kpfu.itis.stayintouch.ui.adapter.PostAdapter
import ru.kpfu.itis.stayintouch.ui.adapter.RecommendAdapter

class RecommendFragment : MvpAppCompatFragment(), RecommendFragmentView {

    @InjectPresenter
    lateinit var presenter: RecommendFragmentPresenter

    companion object {

        fun newInstance(): Fragment {
            return RecommendFragment()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun changeLoadingState(isLoading: Boolean) {
        //TODO отображение прогресс бара в зависимости от наличия данных
    }

    override fun showDetails(position: Int) {
        //TODO переход на фрагмент просмора определенного поста
    }

    override fun setNews(news: List<Post>) {
        recycler_view.adapter = PostAdapter(news.toMutableList())
        recycler_view.layoutManager = LinearLayoutManager(activity)
    }

    override fun setTags(tags: List<Tag>) {
        recycler_view.adapter = RecommendAdapter(tags)
        recycler_view.layoutManager = LinearLayoutManager(activity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        presenter.init()
        this.activity?.toolbar?.title = resources.getString(R.string.nav_recommend)
        return inflater.inflate(R.layout.fragment_recommend, container, false)
    }

}
