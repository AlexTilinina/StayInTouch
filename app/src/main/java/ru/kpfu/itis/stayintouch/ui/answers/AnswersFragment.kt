package ru.kpfu.itis.stayintouch.ui.answers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_answers.*
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.model.Comment
import ru.kpfu.itis.stayintouch.ui.post.CommentAdapter

class AnswersFragment : MvpAppCompatFragment(), AnswersFragmentView {

    @InjectPresenter
    lateinit var presenter: AnswersFragmentPresenter

    companion object {

        fun newInstance(): Fragment {
            return AnswersFragment()
        }
    }

    override fun setComments(comments: List<Comment>) {
        recycler_view.adapter = CommentAdapter(comments)
        recycler_view.layoutManager = LinearLayoutManager(activity)
    }

    override fun changeLoadingState(isLoading: Boolean) {
        //TODO прогресс бар
    }

    override fun showDetails(position: Int) {
        //TODO открыть пост с комментами
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter.init()
        this.activity?.toolbar?.title = resources.getString(R.string.nav_answers)
        return inflater.inflate(R.layout.fragment_answers, container, false)
    }

}
