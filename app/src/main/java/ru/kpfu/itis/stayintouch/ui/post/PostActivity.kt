package ru.kpfu.itis.stayintouch.ui.post

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_post.*
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.model.Comment
import ru.kpfu.itis.stayintouch.model.Post
import ru.kpfu.itis.stayintouch.repository.CommentRepository
import ru.kpfu.itis.stayintouch.repository.UserRepository
import ru.kpfu.itis.stayintouch.ui.adapter.CommentAdapter
import ru.kpfu.itis.stayintouch.utils.COUNT_OF_ELEMENTS
import ru.kpfu.itis.stayintouch.utils.POST
import java.util.*

class PostActivity : MvpAppCompatActivity(), PostActivityView {

    @InjectPresenter
    lateinit var presenter: PostActivityPresenter

    lateinit var post: Post
    var isLoading = false
    var adapter = CommentAdapter(ArrayList(), fragmentManager)

    companion object {

        fun create(context: Context, post: Post) {
            val intent = Intent(context, PostActivity::class.java)
            intent.putExtra(POST, post)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        post = intent.extras.get(POST) as Post
        initPost()
        initOnClickListeners()
        initComments()
    }

    override fun showComments(comments: List<Comment>) {
        adapter.changeDataSet(comments)
    }

    override fun changeLoadingState(isLoading: Boolean) {
        this.isLoading = isLoading
        if (isLoading) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }
    }

    override fun loadMoreItems(items: List<Comment>) {
        adapter.addAll(items)
    }

    override fun handleError(error: Throwable) {
        Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
    }

    override fun setLoading(disposable: Disposable) {
        changeLoadingState(true)
    }

    override fun setNotLoading() {
        changeLoadingState(false)
    }

    override fun getPostId() {
        post.id?.let { presenter.setPostId(it) }
    }

    private fun initPost() {
        iv_author_image //TODO картиночка
        val name = "${post.author?.first_name} ${post.author?.last_name}"
        tv_author_name.text = name
        tv_text.text = post.text
        if (post.date != null) {
            val date = post.date
            val dateText = "${date?.get(Calendar.DAY_OF_MONTH)}.${date?.get(Calendar.MONTH)?.plus(1)}.${date?.get(
                Calendar.YEAR)}"
            tv_post_date.text = dateText
        }
        if (post.dateEvent != null) {
            val date = post.dateEvent
            val dateText = "${date?.get(Calendar.DAY_OF_MONTH)}.${date?.get(Calendar.MONTH)?.plus(1)}.${date?.get(
                Calendar.YEAR)}"
            tv_date.text = dateText
        } else {
            tv_date.visibility = View.GONE
            btn_event.visibility = View.GONE
        }
        var tags = ""
        for (tag in post.tags) {
            tags += "#${tag.tag} "
        }
        tv_tags.text = tags
    }

    private fun addToCalendar(item: Post) {
        val intent = Intent(Intent.ACTION_INSERT)
        intent.type = "vnd.android.cursor.item/event"
        intent.putExtra(CalendarContract.Events.TITLE, "Stay in touch event")
        intent.putExtra(CalendarContract.Events.DESCRIPTION, item.text)

        intent.putExtra(
            CalendarContract.EXTRA_EVENT_BEGIN_TIME,
            item.dateEvent?.timeInMillis
        )

        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
        applicationContext.startActivity(intent);
    }

    private fun initOnClickListeners() {
        tv_date.setOnClickListener {
            addToCalendar(post)
        }
        btn_event.setOnClickListener {
            addToCalendar(post)
        }
        btn_send.setOnClickListener {
            val comment = Comment("", UserRepository.getCurrentUser(this).blockingGet(), et_comment.text.toString(), GregorianCalendar(), post.id)
            et_comment.text.clear()
            post.id?.let { it1 -> CommentRepository.createComment(it1, comment) }
            //TODO scroll к последнему комменту
        }
    }

    private fun initComments() {
        val manager = LinearLayoutManager(this)
        rv_comments.adapter = adapter
        rv_comments.layoutManager = manager
        rv_comments.setHasFixedSize(true)
        rv_comments.addOnScrollListener(object: RecyclerView.OnScrollListener() {

            var currentPage = 0

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = manager.childCount
                val totalItemCount = manager.itemCount
                val firstVisibleItemPosition = manager.findFirstVisibleItemPosition()
                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= COUNT_OF_ELEMENTS) {
                        isLoading = true
                        presenter.loadNextElements(++currentPage)
                    }
                }
            }
        })
    }
}