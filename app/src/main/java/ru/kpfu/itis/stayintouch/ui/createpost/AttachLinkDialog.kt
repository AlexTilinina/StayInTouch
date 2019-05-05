package ru.kpfu.itis.stayintouch.ui.createpost

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.widget.EditText
import android.widget.Toast
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.utils.LINK_REGEX

class AttachLinkDialog: DialogFragment() {

    lateinit var createPostActivityView: CreatePostActivityView

    companion object {

        fun newInstance(): AttachLinkDialog {
            return AttachLinkDialog()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)
        val view = activity.layoutInflater.inflate(R.layout.dialog_attach_link, null)
        builder.setView(view)
            .setPositiveButton(R.string.send, null)
            .setNegativeButton(R.string.cancel) { _, _ ->
                this@AttachLinkDialog.dialog.cancel()
            }
        val dialog = builder.create()
        dialog.setOnShowListener {
            val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val editText = view.findViewById<EditText>(R.id.et_link)
            button.setOnClickListener {
                val text = editText.text.toString()
                if (text.isEmpty()) {
                    Toast.makeText(context, resources.getString(R.string.enter_link), Toast.LENGTH_LONG).show()
                } else {
                    if (!text.matches(LINK_REGEX.toRegex())) {
                        Toast.makeText(context, resources.getString(R.string.error_link_not_correct), Toast.LENGTH_LONG).show()
                    }
                }
                if (text.isNotEmpty() && text.matches(LINK_REGEX.toRegex())) {
                    createPostActivityView.addLink(editText.text.toString())
                    dialog.dismiss()
                }
            }
        }
        return dialog
    }
}