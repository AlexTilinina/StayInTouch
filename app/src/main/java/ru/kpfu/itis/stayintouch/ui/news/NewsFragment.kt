package ru.kpfu.itis.stayintouch.ui.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_news.*
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.model.Post

class NewsFragment : MvpAppCompatFragment(), NewsFragmentView {

    @InjectPresenter
    lateinit var presenter: NewsFragmentPresenter

    companion object {

        fun newInstance(): Fragment {
            return NewsFragment()
        }
    }

    override fun changeLoadingState(isLoading: Boolean) {
        //TODO отображение прогресс бара в зависимости от наличия данных
    }

    override fun showDetails(position: Int) {
        //TODO переход на фрагмент просмора определенного поста
    }

    override fun setNews(news: List<Post>) {
        recycler_view.adapter = NewsAdapter(news, this)
        recycler_view.layoutManager = LinearLayoutManager(activity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter.init()
        this.activity?.toolbar?.title = resources.getString(R.string.nav_news)
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

}
