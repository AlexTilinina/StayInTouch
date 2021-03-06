package ru.kpfu.itis.stayintouch.ui.adapter

import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_post.view.*
import ru.kpfu.itis.stayintouch.R
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
        holder.itemView.iv_attachment_image.visibility = View.GONE
        holder.itemView.fl_attachment_video.visibility = View.GONE
        holder.itemView.fl_attachment_file.visibility = View.GONE
        if (post.attachments.isNotEmpty()) {
            val attachment = post.attachments[0]
            when (attachment.label) {
                ATTACH_LABEL_IMAGE -> {
                    holder.itemView.iv_attachment_image.visibility = View.VISIBLE
                    ImageLoadHelper.loadImage(
                        attachment.url,
                        holder.itemView.iv_attachment_image,
                        ATTACH_IMAGE_SIZE_MEDIUM
                    )
                }
                ATTACH_LABEL_VIDEO -> {
                    holder.itemView.fl_attachment_video.visibility = View.VISIBLE
                    holder.itemView.iv_attachment_video.setImageDrawable(
                        ContextCompat.getDrawable(parent.context, R.drawable.ic_video))
                    AsyncBitmap(holder.itemView.iv_attachment_video,
                        holder.itemView.iv_play,
                        ATTACH_VIDEO_WIDTH_MEDIUM,
                        ATTACH_IMAGE_SIZE_MEDIUM,
                        attachment.url).execute()
                    holder.itemView.iv_play.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.setDataAndType(Uri.parse(attachment.url), "video/mp4")
                        parent.context.startActivity(intent)
                    }
                }
                ATTACH_LABEL_FILE -> {
                    holder.itemView.fl_attachment_file.visibility = View.VISIBLE
                    holder.itemView.tv_attachment_file.text = if (attachment.name != null) attachment.name
                    else parent.context.resources.getString(R.string.download_file)
                    holder.itemView.iv_attachment_file.setImageDrawable(
                        parent.context.resources.getDrawable(R.drawable.ic_file, null))
                    holder.itemView.fl_attachment_file.setOnClickListener {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(attachment.url))
                        parent.context.startActivity(browserIntent)
                    }
                }
                ATTACH_LABEL_LINK -> {
                    holder.itemView.fl_attachment_file.visibility = View.VISIBLE
                    holder.itemView.tv_attachment_file.text = attachment.url
                    holder.itemView.iv_attachment_file.setImageDrawable(
                        parent.context.resources.getDrawable(R.drawable.ic_link, null))
                    holder.itemView.fl_attachment_file.setOnClickListener {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(attachment.url))
                        parent.context.startActivity(browserIntent)
                    }
                }
            }
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