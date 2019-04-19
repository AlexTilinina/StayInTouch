package ru.kpfu.itis.stayintouch.ui.adapter

import android.app.FragmentManager
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_comment.view.*
import ru.kpfu.itis.stayintouch.model.Comment
import ru.kpfu.itis.stayintouch.R.layout.item_comment
import ru.kpfu.itis.stayintouch.ui.post.AnswerCommentDialog
import ru.kpfu.itis.stayintouch.ui.post.PostActivity
import ru.kpfu.itis.stayintouch.utils.ANSWER_COMMENT_DIALOG_TAG
import ru.kpfu.itis.stayintouch.utils.DateHelper
import ru.kpfu.itis.stayintouch.utils.ImageLoadHelper
import ru.kpfu.itis.stayintouch.utils.PROFILE_IMAGE_SIZE_MEDIUM

class CommentAdapter (
    private val comments: MutableList<Comment>,
    private val context: Context? = null,
    private val fragmentManager: FragmentManager? = null
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    lateinit var answerAdapter : CommentAdapter

    class CommentViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        ImageLoadHelper.loadImage(
            comment.author?.profile?.photo_url,
            holder.itemView.iv_author_image,
            PROFILE_IMAGE_SIZE_MEDIUM
        )
        val username = "${comment.author?.first_name} ${comment.author?.last_name}"
        holder.itemView.tv_author_name.text = username
        holder.itemView.tv_text.text = comment.text
        val date = comment.date
        if (date != null) {
            holder.itemView.tv_date.text = DateHelper.parseDate(date)
        } else {
            holder.itemView.tv_date.visibility = View.GONE
        }
        if (fragmentManager != null) {
            holder.itemView.tv_answer.setOnClickListener {
                comment.news_commented?.let { it1 ->
                    val fragment = AnswerCommentDialog.newInstance(comment.id, it1)
                    fragment.commentAdapter = this
                    fragment.show(fragmentManager, ANSWER_COMMENT_DIALOG_TAG)
                }
            }

            answerAdapter = CommentAdapter(ArrayList())
            holder.itemView.recycler_view.adapter = answerAdapter
            holder.itemView.recycler_view.layoutManager = LinearLayoutManager(context)

            if (comment.answers.isNotEmpty()) {
                answerAdapter = CommentAdapter(comment.answers.toMutableList(), context)
            }
        } else {
            holder.itemView.tv_answer.visibility = View.GONE
            holder.itemView.setOnClickListener {
                context?.let { it1 ->
                    comment.news_commented?.let { it2 ->
                        PostActivity.create(it1, it2)
                    }
                }
            }
        }

    }

    fun add(value: Comment) {
        comments.add(value)
        notifyDataSetChanged()
    }

    fun changeDataSet(values: List<Comment>) {
        comments.clear()
        comments.addAll(values)
        notifyDataSetChanged()
    }

    fun addAll(values: List<Comment>) {
        for (value in values) {
            comments.add(value)
            notifyItemInserted(comments.size - 1)
        }
    }

    fun clear() {
        comments.clear()
        notifyDataSetChanged()
    }
}