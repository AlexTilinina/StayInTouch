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
import ru.kpfu.itis.stayintouch.model.User
import ru.kpfu.itis.stayintouch.ui.adapter.CommentAdapter
import ru.kpfu.itis.stayintouch.utils.COUNT_OF_ELEMENTS
import ru.kpfu.itis.stayintouch.utils.POST_ID
import java.util.*

class PostActivity : MvpAppCompatActivity(), PostActivityView {

    @InjectPresenter
    lateinit var presenter: PostActivityPresenter

    lateinit var post: Post
    var postId = 0
    var isLoading = false
    var adapter = CommentAdapter(ArrayList(), fragmentManager = fragmentManager)

    companion object {

        fun create(context: Context, postId: Int) {
            val intent = Intent(context, PostActivity::class.java)
            intent.putExtra(POST_ID, postId)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        postId = intent.extras.getInt(POST_ID)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initOnClickListeners()
        initComments()
    }

    override fun initPost(post: Post) {
        this.post = post
        initPost()
        post.comments?.let { adapter.changeDataSet(it) }
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
        error.printStackTrace()
    }

    override fun setLoading(disposable: Disposable) {
        changeLoadingState(true)
    }

    override fun setNotLoading() {
        changeLoadingState(false)
    }

    override fun getPostId() {
        presenter.setPostId(postId)
    }

    override fun addItem(comment: Comment) {
        adapter.add(comment)
    }

    private fun initPost() {
        iv_author_image //TODO картиночка
        val name = "${post.author?.first_name} ${post.author?.last_name}"
        tv_author_name.text = name
        tv_text.text = post.text
        if (post.created != null) {
            val date = post.getDateCreated()
            val hour =  if (date.get(Calendar.HOUR_OF_DAY) + 3 > 23) {
                date.get(Calendar.HOUR_OF_DAY) + 3 - 24
            } else {
                date.get(Calendar.HOUR_OF_DAY) + 3
            }
            val hourString = if (hour < 10) {
                "0$hour"
            } else {
                "$hour"
            }
            val minute = if (date.get(Calendar.MINUTE) < 10) {
                "0${date.get(Calendar.MINUTE)}"
            } else {
                "${date.get(Calendar.MINUTE)}"
            }
            val dateText = "$hourString:$minute ${date.get(Calendar.DAY_OF_MONTH)}.${date.get(Calendar.MONTH).plus(1)}.${date.get(
                Calendar.YEAR)}"
            tv_post_date.text = dateText
        }
        if (post.dateEvent != null) {
            val date = post.dateEvent
            val hour =  if (date?.get(Calendar.HOUR_OF_DAY)?.plus(3) ?: 0 > 23) {
                (date?.get(Calendar.HOUR_OF_DAY) ?: 0) + 3 - 24
            } else {
                (date?.get(Calendar.HOUR_OF_DAY) ?: 0) + 3
            }
            val hourString = if (hour < 10) {
                "0$hour"
            } else {
                "$hour"
            }
            val minute = if (date?.get(Calendar.MINUTE) ?: 0 < 10) {
                "0${date?.get(Calendar.MINUTE)}"
            } else {
                "${date?.get(Calendar.MINUTE)}"
            }
            val dateText = "$hourString:$minute ${date?.get(Calendar.DAY_OF_MONTH)}.${date?.get(Calendar.MONTH)?.plus(1)}.${date?.get(
                Calendar.YEAR)}"
            tv_date.text = dateText
        } else {
            tv_date.visibility = View.GONE
            btn_event.visibility = View.GONE
        }
        var tags = ""
        //TODO выпадающий список с тегами
        for (tag in post.tags) {
            tags += "#${tag.name} "
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
            presenter.getUserToCreateComment()
        }
    }

    override fun createComment(user: User) {
        val comment = Comment("", user, et_comment.text.toString(), GregorianCalendar(), post.id)
        et_comment.text.clear()
        post.id?.let { it1 -> presenter.createComment(it1, comment) }
        //TODO scroll к последнему комменту
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
                        //TODO сделать когда появится API
                        //presenter.loadNextElements(++currentPage)
                    }
                }
            }
        })
    }
}
