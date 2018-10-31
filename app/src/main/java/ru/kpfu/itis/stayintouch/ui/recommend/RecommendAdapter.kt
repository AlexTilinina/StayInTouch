package ru.kpfu.itis.stayintouch.ui.recommend

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_tag.view.*
import ru.kpfu.itis.stayintouch.R.layout.item_tag
import ru.kpfu.itis.stayintouch.model.Tag

class RecommendAdapter(private val tags: List<Tag>) : RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(item_tag, parent, false)
        return RecommendViewHolder(view)
    }

    override fun getItemCount(): Int = tags.size

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        holder.itemView.tv_tag.text = tags[position].tag
    }

    class RecommendViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}