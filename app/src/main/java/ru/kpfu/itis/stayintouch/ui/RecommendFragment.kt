package ru.kpfu.itis.stayintouch.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import ru.kpfu.itis.stayintouch.R

class RecommendFragment : Fragment() {

    companion object {

        fun newInstance(): Fragment {
            return RecommendFragment()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        this.activity?.toolbar?.title = resources.getString(R.string.nav_recommend)
        return inflater.inflate(R.layout.fragment_recommend, container, false)
    }

}
