package ru.kpfu.itis.stayintouch.ui.recommend

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_recommend.*
import retrofit2.HttpException
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.model.Post
import ru.kpfu.itis.stayintouch.model.Tag
import ru.kpfu.itis.stayintouch.ui.adapter.PostAdapter
import ru.kpfu.itis.stayintouch.ui.adapter.RecommendAdapter
import ru.kpfu.itis.stayintouch.utils.CODE_500

class RecommendFragment : MvpAppCompatFragment(), RecommendFragmentView {

    @InjectPresenter
    lateinit var presenter: RecommendFragmentPresenter

    var isLoading = true
    val adapter = PostAdapter(ArrayList())

    companion object {

        fun newInstance(): Fragment {
            return RecommendFragment()
        }
    }

    override fun handleError(error: Throwable) {
        if (error is HttpException) {
            if (error.code() == CODE_500) {
                error.printStackTrace()
                Toast.makeText(activity, getString(R.string.error_server_not_responding), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun setLoading(disposable: Disposable) {
        isLoading = true
        progress_bar.visibility = View.VISIBLE
    }

    override fun setNotLoading() {
        isLoading = false
        progress_bar.visibility = View.GONE
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        val manager = LinearLayoutManager(activity)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = manager
        recycler_view.setHasFixedSize(true)
        recycler_view.addOnScrollListener(object: RecyclerView.OnScrollListener() {

            var currentPage = 0

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = manager.childCount
                val totalItemCount = manager.itemCount
                val firstVisibleItemPosition = manager.findFirstVisibleItemPosition()
                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= 20) {
                        isLoading = true
                        //TODO добавить подгрузку новых постов, когда появится API
                        //presenter.loadNextElements(++currentPage)
                    }
                }
            }
        })
    }
}
