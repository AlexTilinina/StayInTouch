package ru.kpfu.itis.stayintouch.ui.post

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.model.Comment
import ru.kpfu.itis.stayintouch.model.User
import ru.kpfu.itis.stayintouch.repository.CommentRepository
import ru.kpfu.itis.stayintouch.repository.UserRepository
import java.util.*

class AnswerCommentDialog : DialogFragment() {

    companion object {

        fun newInstance(postId: Int): AnswerCommentDialog {
            val answerCommentDialog = AnswerCommentDialog()
            val bundle = Bundle()
            bundle.putInt("postId", postId)
            answerCommentDialog.arguments = bundle
            return answerCommentDialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)
        val view = activity.layoutInflater.inflate(R.layout.dialog_comment, null)
        val postId = arguments.getInt("postId")
        builder.setView(view)
            .setPositiveButton(R.string.send, null)
            .setNegativeButton(R.string.cancel) { _, _ ->
                this@AnswerCommentDialog.dialog.cancel()
            }
        val dialog = builder.create()
        val progressBar = view.findViewById<ProgressBar>(R.id.pb_comment)
        dialog.setOnShowListener {

            val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener {
                val editText = view.findViewById<EditText>(R.id.et_comment)
                if (editText.text.toString().isNotEmpty()) {
                    UserRepository
                        .getCurrentUser()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { setLoading(progressBar) }
                        .subscribe{t-> createComment(t, editText, postId, dialog)}
                }
            }
        }
        return dialog
    }

    fun createComment(user: User, editText: EditText, postId: Int, dialog: Dialog) {
        val comment = Comment("", user, editText.text.toString(), GregorianCalendar(), postId)
        CommentRepository
            .createComment(postId, comment)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{t -> dialog.dismiss() }
    }

    fun setLoading(progressBar: ProgressBar){
        progressBar.visibility = View.VISIBLE
    }
}