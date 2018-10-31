package ru.kpfu.itis.stayintouch.ui.news

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_post.view.*
import ru.kpfu.itis.stayintouch.R.layout.item_post
import ru.kpfu.itis.stayintouch.model.Post

class NewsAdapter(private val news: List<Post>, private val fragment: NewsFragment) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(item_post, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int = news.size


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.itemView.iv_author_image //TODO подгрузка картинки
        val username = "${news[position].author?.name} ${news[position].author?.surname}"
        holder.itemView.tv_author_name.text = username
        holder.itemView.tv_text.text = news[position].text
        if (news[position].date != null) {
            //TODO вроде дату надо как-то хитро выводить
            holder.itemView.tv_date.text = news[position].date.toString()
        } else {
            holder.itemView.tv_date.visibility = View.GONE
            holder.itemView.btn_event.visibility = View.GONE
        }
        var tags = ""
        for (tag in news[position].tags) {
            tags += "#${tag.tag} "
        }
        holder.itemView.tv_tags.text = tags
    }


}