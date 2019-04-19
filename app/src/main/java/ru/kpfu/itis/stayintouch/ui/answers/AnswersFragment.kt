package ru.kpfu.itis.stayintouch.ui.answers

import android.os.Build
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
import retrofit2.HttpException
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.model.Comment
import ru.kpfu.itis.stayintouch.model.User
import ru.kpfu.itis.stayintouch.ui.adapter.CommentAdapter
import ru.kpfu.itis.stayintouch.utils.CODE_500

class AnswersFragment : MvpAppCompatFragment(), AnswersFragmentView {

    @InjectPresenter
    lateinit var presenter: AnswersFragmentPresenter
    var myComments: MutableList<Comment> = ArrayList()
    var answers: MutableList<Comment> = ArrayList()
    lateinit var adapter : CommentAdapter

    companion object {

        fun newInstance(): Fragment {
            return AnswersFragment()
        }
    }

    override fun setAdapter() {
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(activity)
    }

    override fun showMyComments(comments : List<Comment>) {
        myComments = comments.toMutableList()
        adapter.changeDataSet(myComments)
    }

    override fun showAnswers(comments : List<Comment>) {
        answers = comments.toMutableList()
    }

    override fun setLoading(disposable: Disposable) {
        progress_bar.visibility = View.VISIBLE
    }

    override fun setNotLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun handleError(error: Throwable) {
        if (error is HttpException) {
            if (error.code() == CODE_500) {
                Toast.makeText(context, getString(R.string.error_server_not_responding), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
            error.printStackTrace()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter.init()
        this.activity?.toolbar?.title = resources.getString(R.string.nav_answers)
        val view = inflater.inflate(R.layout.fragment_answers, container, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.btns_segmented.setTintColor(resources.getColor(R.color.colorPrimaryLight, null))
        }
        adapter = CommentAdapter(myComments, context)
        view.btns_segmented.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                view.btn_my_comments.id -> {
                    adapter.changeDataSet(myComments)
                }
                view.btn_answers.id -> {
                    adapter.changeDataSet(answers)
                }
            }
        }
        return view
    }
}
