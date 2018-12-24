package ru.kpfu.itis.stayintouch.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_tag.view.*
import ru.kpfu.itis.stayintouch.R.layout.item_tag
import ru.kpfu.itis.stayintouch.model.Tag
import ru.kpfu.itis.stayintouch.repository.TagRepository

class TagAdapter(private val tags: MutableList<Tag>, private var subscr: Boolean) : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    class TagViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(item_tag, parent, false)
        return TagViewHolder(view)
    }

    override fun getItemCount(): Int = tags.size

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val tag = tags[position]
        holder.itemView.tv_tag.text = tag.name
        if (subscr) {
            holder.itemView.btn_subscribe.visibility = View.VISIBLE
            holder.itemView.btn_unsubscribe.visibility = View.GONE
        } else {
            holder.itemView.btn_subscribe.visibility = View.GONE
            holder.itemView.btn_unsubscribe.visibility = View.VISIBLE
        }
        holder.itemView.btn_subscribe.setOnClickListener {
            TagRepository
                .subscribeToTag(tag.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { remove(tag) }
                .subscribe()
        }
        holder.itemView.btn_unsubscribe.setOnClickListener {
            TagRepository
                .unsubscribeFromTag(tag.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { remove(tag) }
                .subscribe()
        }
    }

    fun add(value: Tag) {
        tags.add(value)
        notifyDataSetChanged()
    }

    fun remove(value: Tag) {
        tags.remove(value)
        notifyDataSetChanged()
    }

    fun changeDataSet(values: List<Tag>) {
        tags.clear()
        tags.addAll(values)
        notifyDataSetChanged()
    }

    fun addAll(values: List<Tag>) {
        for (value in values) {
            tags.add(value)
            notifyItemInserted(tags.size - 1)
        }
    }

    fun clear() {
        tags.clear()
        notifyDataSetChanged()
    }
}