package ru.kpfu.itis.stayintouch.ui.adapter

import android.content.Intent
import android.provider.CalendarContract
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_post.view.*
import ru.kpfu.itis.stayintouch.R.layout.item_post
import ru.kpfu.itis.stayintouch.model.Post
import ru.kpfu.itis.stayintouch.ui.post.PostActivity
import ru.kpfu.itis.stayintouch.utils.*

class PostAdapter(private val news: MutableList<Post>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private lateinit var parent: ViewGroup

    class PostViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(item_post, parent, false)
        this.parent = parent
        return PostViewHolder(view)
    }

    override fun getItemCount(): Int = news.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = news[position]
        ImageLoadHelper.loadImage(
            post.author?.profile?.photo_url,
            holder.itemView.iv_author_image,
            PROFILE_IMAGE_SIZE_SMALL
        )
        val username = "${post.author?.first_name} ${post.author?.last_name}"
        holder.itemView.tv_author_name.text = username
        holder.itemView.tv_text.text = post.text
        if (post.created != null) {
            val date = DateHelper.getDateCreated(post.created)
            holder.itemView.tv_post_date.text = DateHelper.parseDate(date)
        }
        if (post.dateEvent != null) {
            val date = post.dateEvent
            holder.itemView.tv_date.text = date?.let { DateHelper.parseDate(it) }
        } else {
            holder.itemView.tv_date.visibility = View.GONE
            holder.itemView.btn_event.visibility = View.GONE
        }
        var tags = ""
        for (tag in post.tags) {
            tags += "${tag.name} "
        }
        if (post.attachments.isNotEmpty()) {
            val attachment = post.attachments[0]
            holder.itemView.iv_attachment_image.visibility = View.VISIBLE
            if (attachment.label.equals(ATTACH_LABEL_IMAGE)) {
                ImageLoadHelper.loadImage(
                    attachment.url,
                    holder.itemView.iv_attachment_image,
                    ATTACH_IMAGE_SIZE_MEDIUM
                )
            }
        } else {
            holder.itemView.iv_attachment_image.visibility = View.GONE
        }
        holder.itemView.tv_tags.text = tags
        holder.itemView.btn_comments.setOnClickListener {
            post.id?.let { it1 -> PostActivity.create(parent.context, it1) }
        }
        holder.itemView.setOnClickListener {
            post.id?.let { it1 -> PostActivity.create(parent.context, it1) }
        }
        holder.itemView.tv_date.setOnClickListener {
            addToCalendar(post)
        }
        holder.itemView.btn_event.setOnClickListener {
            addToCalendar(post)
        }
    }

    fun add(value: Post) {
        news.add(value)
        notifyDataSetChanged()
    }

    fun changeDataSet(values: List<Post>) {
        news.clear()
        news.addAll(values)
        notifyDataSetChanged()
    }

    fun addAll(values: List<Post>) {
        for (value in values) {
            news.add(value)
            notifyItemInserted(news.size - 1)
        }
    }

    fun clear() {
        news.clear()
        notifyDataSetChanged()
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
        parent.context.startActivity(intent)
    }
}