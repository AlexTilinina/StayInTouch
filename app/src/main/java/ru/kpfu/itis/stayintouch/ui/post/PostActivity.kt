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
import ru.kpfu.itis.stayintouch.ui.adapter.TagAdapter
import java.util.*
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.widget.MediaController
import retrofit2.HttpException
import ru.kpfu.itis.stayintouch.utils.*

class PostActivity : MvpAppCompatActivity(), PostActivityView {

    @InjectPresenter
    lateinit var presenter: PostActivityPresenter

    lateinit var post: Post
    lateinit var user: User
    var postId = 0
    var isLoading = false
    var adapter = CommentAdapter(ArrayList(), fragmentManager = fragmentManager)
    var tagsShown = false

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
        initTags()
        post.comments?.let { adapter.changeDataSet(it) }
    }

    override fun initUser(user: User) {
        this.user = user
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
        if (error is HttpException) {
            if (error.code() == CODE_500) {
                Toast.makeText(this, getString(R.string.error_server_not_responding), Toast.LENGTH_SHORT).show()
            }
        }
        else {
            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
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

    override fun getPostId() {
        presenter.setPostId(postId)
    }

    override fun addItem(comment: Comment) {
        adapter.add(comment)
    }

    private fun initPost() {
        ImageLoadHelper.loadImage(
            post.author?.profile?.photo_url,
            iv_author_image,
            PROFILE_IMAGE_SIZE_SMALL,
            R.mipmap.ic_launcher
        )
        val name = "${post.author?.first_name} ${post.author?.last_name}"
        tv_author_name.text = name
        tv_text.text = post.text
        if (post.created != null) {
            val date = DateHelper.getDateCreated(post.created)
            tv_post_date.text = DateHelper.parseDate(date)
        }
        if (post.dateEvent != null) {
            val date = post.dateEvent
            tv_date.text = date?.let { DateHelper.parseDate(it) }
        } else {
            tv_date.visibility = View.GONE
            btn_event.visibility = View.GONE
        }
        if (post.attachments.isNotEmpty()) {
            val attachment = post.attachments[0]
            when (attachment.label) {
                ATTACH_LABEL_IMAGE -> {
                    iv_attachment_image.visibility = View.VISIBLE
                    ImageLoadHelper.loadImage(
                        attachment.url,
                        iv_attachment_image,
                        ATTACH_IMAGE_SIZE_MEDIUM
                    )
                }
                ATTACH_LABEL_VIDEO -> {
                    fl_attachment_video.visibility = View.VISIBLE
                    iv_attachment_video.setImageDrawable(
                        ContextCompat.getDrawable(this, R.drawable.ic_video))
                    AsyncBitmap(iv_attachment_video,
                        iv_play,
                        ATTACH_VIDEO_WIDTH_MEDIUM,
                        ATTACH_IMAGE_SIZE_MEDIUM,
                        attachment.url).execute()
                    iv_play.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.setDataAndType(Uri.parse(attachment.url), "video/mp4")
                        startActivity(intent)
                    }
                }
            }
        }
        var tags = ""
        for (tag in post.tags) {
            tags += "${tag.name} "
        }
        tv_tags.text = tags
        ImageLoadHelper.loadImage(
            user.profile?.photo_url,
            iv_user_photo,
            PROFILE_IMAGE_SIZE_SMALL
        )
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
        applicationContext.startActivity(intent)
    }

    private fun initOnClickListeners() {
        tv_date.setOnClickListener {
            addToCalendar(post)
        }
        btn_event.setOnClickListener {
            addToCalendar(post)
        }
        btn_send.setOnClickListener {
            createComment()
        }
        btn_show_tags.setOnClickListener {
            if (!tagsShown) {
                btn_show_tags.setImageResource(R.drawable.ic_drop_down_up)
                rv_tags.visibility = View.VISIBLE
                tv_tags.visibility = View.INVISIBLE
                rv_tags.alpha = 0f
                rv_tags.animate()
                    .alpha(1f)
                    .setListener(null)
            } else {
                btn_show_tags.setImageResource(R.drawable.ic_drop_down)
                rv_tags.animate()
                    .alpha(0f)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            rv_tags.visibility = View.GONE
                            tv_tags.visibility = View.VISIBLE
                        }
                    })
            }
            tagsShown = !tagsShown
        }
    }

    private fun initTags() {
        val tags = post.tags
        for (tag in tags) {
            tag.subscr = !user.profile?.tags?.contains(tag)!!
        }
        rv_tags.adapter = TagAdapter(tags.toMutableList())
        rv_tags.layoutManager = LinearLayoutManager(this)
    }

    private fun createComment() {
        val comment = Comment("", user, et_comment.text.toString(), GregorianCalendar(), post.id)
        et_comment.text.clear()
        post.id?.let { it1 -> presenter.createComment(it1, comment) }
    }

    private fun initComments() {
        val manager = LinearLayoutManager(this)
        rv_comments.adapter = adapter
        rv_comments.layoutManager = manager
        rv_comments.setHasFixedSize(true)
        rv_comments.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            var currentPage = 0

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = manager.childCount
                val totalItemCount = manager.itemCount
                val firstVisibleItemPosition = manager.findFirstVisibleItemPosition()
                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= COUNT_OF_ELEMENTS
                    ) {
                        isLoading = true
                        //TODO сделать когда появится API
                        //presenter.loadNextElements(++currentPage)
                    }
                }
            }
        })
    }
}
