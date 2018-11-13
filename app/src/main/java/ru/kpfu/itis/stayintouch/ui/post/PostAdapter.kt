package ru.kpfu.itis.stayintouch.ui.post

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_post.view.*
import ru.kpfu.itis.stayintouch.R.layout.item_post
import ru.kpfu.itis.stayintouch.model.Post
import java.util.*



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
        holder.itemView.iv_author_image //TODO подгрузка картинки
        val username = "${news[position].author?.name} ${news[position].author?.surname}"
        holder.itemView.tv_author_name.text = username
        holder.itemView.tv_text.text = news[position].text
        if (news[position].date != null) {
            val date = news[position].date
            val dateText = "${date?.get(Calendar.DAY_OF_MONTH)}.${date?.get(Calendar.MONTH)?.plus(1)}.${date?.get(Calendar.YEAR)}"
            holder.itemView.tv_date.text = dateText
        } else {
            holder.itemView.tv_date.visibility = View.GONE
            holder.itemView.btn_event.visibility = View.GONE
        }
        var tags = ""
        for (tag in news[position].tags) {
            tags += "#${tag.tag} "
        }
        holder.itemView.tv_tags.text = tags
        holder.itemView.setOnClickListener {
            //TODO экран с постом
        }
        holder.itemView.tv_date.setOnClickListener {
            createPopupWindow(news[position])
        }
        holder.itemView.btn_event.setOnClickListener {
            createPopupWindow(news[position])
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

    private fun createPopupWindow(item: Post) {
        CalendarPopup().popup(parent.context, parent, item)
    }
}