package ru.kpfu.itis.stayintouch.ui.answers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_answers.*
import kotlinx.android.synthetic.main.fragment_answers.view.*
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.model.Comment
import ru.kpfu.itis.stayintouch.ui.adapter.CommentAdapter

class AnswersFragment : MvpAppCompatFragment(), AnswersFragmentView {

    @InjectPresenter
    lateinit var presenter: AnswersFragmentPresenter

    companion object {

        fun newInstance(): Fragment {
            return AnswersFragment()
        }
    }

    override fun setComments(comments: MutableList<Comment>) {
        recycler_view.adapter = CommentAdapter(comments)
        recycler_view.layoutManager = LinearLayoutManager(activity)
    }

    override fun setLoading(disposable: Disposable) {
        progress_bar.visibility = View.VISIBLE
    }

    override fun setNotLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun showDetails(position: Int) {
        //TODO открыть пост с комментами
    }

    override fun handleError(error: Throwable) {
        Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
        error.printStackTrace()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter.init()
        this.activity?.toolbar?.title = resources.getString(R.string.nav_answers)
        val view = inflater.inflate(R.layout.fragment_answers, container, false)
        view.btns_segmented.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                view.btn_my_comments.id -> {

                }
                view.btn_answers.id -> {
                    //TODO ответы
                }
            }
        }
        return view
    }
}
