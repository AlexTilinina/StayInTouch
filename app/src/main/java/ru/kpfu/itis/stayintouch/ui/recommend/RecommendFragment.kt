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
import ru.kpfu.itis.stayintouch.utils.COUNT_OF_ELEMENTS
import ru.kpfu.itis.stayintouch.utils.SEARCH

class RecommendFragment : MvpAppCompatFragment(), RecommendFragmentView {

    @InjectPresenter
    lateinit var presenter: RecommendFragmentPresenter

    var isLoading = true
    val adapter = PostAdapter(ArrayList())

    companion object {

        fun newInstance(): Fragment {
            return RecommendFragment()
        }

        fun newInstance(tags: String) : Fragment {
            val fragment = RecommendFragment()
            val bundle = Bundle()
            bundle.putString(SEARCH, tags)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        this.activity?.toolbar?.title = resources.getString(R.string.nav_recommend)
        initSearch()
        return inflater.inflate(R.layout.fragment_recommend, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecycler()
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
        isLoading = true
        progress_bar.visibility = View.VISIBLE
    }

    override fun setNotLoading() {
        isLoading = false
        progress_bar.visibility = View.GONE
    }

    override fun setNews(news: List<Post>) {
        if (news.isEmpty()) {
            tv_empty.visibility = View.VISIBLE
        }
        else tv_empty.visibility = View.GONE
        adapter.changeDataSet(news.toMutableList())
    }

    override fun loadMoreItems(items: List<Post>) {
        adapter.addAll(items)
    }

    override fun setTags(tags: List<Tag>) {
        recycler_view.adapter = RecommendAdapter(tags)
    }

    private fun initSearch() {
        val tags = arguments?.getString(SEARCH)
        val tagsList = ArrayList<String>()
        if (!tags.isNullOrEmpty()) {
            val tagsText = tags.split(" ")
            for (tag in tagsText) {
                if (!tag.isEmpty()) {
                    if (!tag.startsWith('#'))
                        tagsList.add(tag)
                    else tagsList.add(tag.substring(1))
                }
            }
        }
        presenter.isSearch(tagsList)
    }

    private fun initRecycler() {
        val manager = LinearLayoutManager(activity)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = manager
        recycler_view.setHasFixedSize(true)
        recycler_view.addOnScrollListener(object: RecyclerView.OnScrollListener() {

            var currentPage = COUNT_OF_ELEMENTS + 1

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val r_manager = recyclerView?.layoutManager as LinearLayoutManager
                val visibleItemCount = r_manager.childCount
                val totalItemCount = r_manager.itemCount
                val firstVisibleItemPosition = r_manager.findFirstVisibleItemPosition()
                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= COUNT_OF_ELEMENTS) {
                        isLoading = true
                        presenter.loadNextElements(currentPage)
                        currentPage += COUNT_OF_ELEMENTS
                    }
                }
            }
        })
    }
}
