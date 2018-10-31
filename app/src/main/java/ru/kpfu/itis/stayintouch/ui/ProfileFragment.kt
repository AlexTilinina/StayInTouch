package ru.kpfu.itis.stayintouch.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import ru.kpfu.itis.stayintouch.R

class ProfileFragment : Fragment() {

    companion object {

        fun newInstance(): Fragment {
            return ProfileFragment()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            /* param1 = it.getString(ARG_PARAM1)
             param2 = it.getString(ARG_PARAM2)*/
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.activity?.toolbar?.title = resources.getString(R.string.nav_profile)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
}
