package ru.kpfu.itis.stayintouch.ui.post

import android.content.Context
import android.view.Gravity
import android.widget.PopupWindow
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.model.Post
import java.util.*
import android.provider.CalendarContract.Events
import android.provider.CalendarContract
import android.content.Intent
import ru.kpfu.itis.stayintouch.R.id.*


class CalendarPopup {

    fun popup(context: Context, parent: ViewGroup, post: Post){
        val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customView = inflater.inflate(R.layout.popup_calendar, null)

        val mPopupWindow = PopupWindow(
            customView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val descriptionTextView = customView.findViewById<TextView>(tv_description)
        val dateTextView = customView.findViewById<TextView>(tv_date)
        val closeButton = customView.findViewById(R.id.btn_cancel) as Button
        val okButton = customView.findViewById<Button>(R.id.btn_ok)

        val date = post.date
        val dateString = "${date?.get(Calendar.DAY_OF_MONTH)}.${date?.get(Calendar.MONTH)?.plus(1)}.${date?.get(
            Calendar.YEAR)}"
        dateTextView.text = dateString
        descriptionTextView.text = post.text

        closeButton.setOnClickListener{
            mPopupWindow.dismiss()
        }

        okButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_INSERT)
            intent.type = "vnd.android.cursor.item/event"
            intent.putExtra(Events.TITLE, "Stay in touch event")
            intent.putExtra(Events.DESCRIPTION, post.text)

            intent.putExtra(
                CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                date?.timeInMillis
            )

            intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
            context.startActivity(intent);
        }

        mPopupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0)
    }

}