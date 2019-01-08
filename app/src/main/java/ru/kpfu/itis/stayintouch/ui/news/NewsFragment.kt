package ru.kpfu.itis.stayintouch.ui.news

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
import kotlinx.android.synthetic.main.fragment_news.*
import retrofit2.HttpException
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.model.Post
import ru.kpfu.itis.stayintouch.ui.adapter.PostAdapter
import ru.kpfu.itis.stayintouch.utils.CODE_500

class NewsFragment : MvpAppCompatFragment(), NewsFragmentView {

    @InjectPresenter
    lateinit var presenter: NewsFragmentPresenter

    var isLoading = true
    val adapter = PostAdapter(ArrayList())

    companion object {

        fun newInstance(): Fragment {
            return NewsFragment()
        }
    }

    override fun changeLoadingState(isLoading: Boolean) {
        this.isLoading = isLoading
        swipe_refresh_layout.isRefreshing = isLoading
    }

    override fun showPosts(posts: List<Post>) {
        adapter.changeDataSet(posts)
    }

    override fun loadMoreItems(items: List<Post>) {
        adapter.addAll(items)
    }

    override fun checkIfEmpty() {
        if (recycler_view.adapter.itemCount > 0){
            tv_empty.visibility = View.GONE
        } else {
            tv_empty.visibility = View.VISIBLE
        }
    }

    override fun handleError(error: Throwable) {
        if (error is HttpException) {
            if (error.code() == CODE_500) {
                Toast.makeText(context, getString(R.string.error_server_not_responding), Toast.LENGTH_SHORT).show()
            }
        }
        else {
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            error.printStackTrace()
        }
        setNotLoading()
    }

    override fun setLoading(disposable: Disposable) {
        changeLoadingState(true)
    }

    override fun setNotLoading() {
        changeLoadingState(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.activity?.toolbar?.title = resources.getString(R.string.nav_news)
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecycler()
        initSwipeRefreshLayout()
    }

    private fun initSwipeRefreshLayout() {
        swipe_refresh_layout.setOnRefreshListener {
            presenter.loadPosts()
        }
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
