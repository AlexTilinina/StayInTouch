package ru.kpfu.itis.stayintouch.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import ru.kpfu.itis.stayintouch.R

class AnswersFragment : Fragment() {

    companion object {

        fun newInstance(): Fragment {
            return AnswersFragment()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        this.activity?.toolbar?.title = resources.getString(R.string.nav_answers)
        return inflater.inflate(R.layout.fragment_answers, container, false)
    }

}
