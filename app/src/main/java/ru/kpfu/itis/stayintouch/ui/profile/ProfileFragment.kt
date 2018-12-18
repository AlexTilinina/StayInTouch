package ru.kpfu.itis.stayintouch.ui.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.model.Tag
import ru.kpfu.itis.stayintouch.model.User
import ru.kpfu.itis.stayintouch.ui.AuthActivity

class ProfileFragment : MvpAppCompatFragment(), ProfileFragmentView {

    @InjectPresenter
    lateinit var presenter: ProfileFragmentPresenter

    companion object {

        fun newInstance(): Fragment {
            return ProfileFragment()
        }
    }

    override fun onStart() {
        super.onStart()
        initClickListeners()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.activity?.toolbar?.title = resources.getString(R.string.nav_profile)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun loadUser(user: User) {
        tv_name.text = user.first_name
        tv_surname.text = user.last_name
        tv_email.text = user.email
        if (user.profile?.tags != null) {
            if (user.profile?.tags?.isNotEmpty() == true) {
                var tags = ""
                for (tag: Tag in user.profile?.tags!!) {
                    tags += "${tag.tag} "
                }
                tv_tags_list.text = tags
            } else {
                tv_tags_list.text = getString(R.string.empty_news)
            }
        }

    }

    override fun handleError(error: Throwable) {
        Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
        error.printStackTrace()
    }

    override fun setLoading(disposable: Disposable) {
        progress_bar.visibility = View.VISIBLE
    }

    override fun setNotLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun makeEditableInvisible() {
        et_first_name.visibility = View.GONE
        et_last_name.visibility = View.GONE
        et_email.visibility = View.GONE
        btn_ok.visibility = View.GONE

        tv_name.visibility = View.VISIBLE
        tv_surname.visibility = View.VISIBLE
        tv_email.visibility = View.VISIBLE
        tv_tags_fix.visibility = View.VISIBLE
        tv_tags_list.visibility = View.VISIBLE
        btn_log_out.visibility = View.VISIBLE
    }

    fun editProfile() {
        et_first_name.visibility = View.VISIBLE
        et_last_name.visibility = View.VISIBLE
        et_email.visibility = View.VISIBLE
        btn_ok.visibility = View.VISIBLE

        et_first_name.setText(tv_name.text)
        et_last_name.setText(tv_surname.text)
        et_email.setText(tv_email.text)

        tv_name.visibility = View.GONE
        tv_surname.visibility = View.GONE
        tv_email.visibility = View.GONE
        tv_tags_fix.visibility = View.GONE
        tv_tags_list.visibility = View.GONE
        btn_log_out.visibility = View.GONE
    }

    private fun initClickListeners(){
        btn_log_out.setOnClickListener {
            context?.let { it1 -> AuthActivity.create(it1, false) }
        }
        btn_ok.setOnClickListener {
            var name = ""
            var surname = ""
            var email = ""
            if (et_first_name.text.isNotEmpty() && et_last_name.text.isNotEmpty()
                            && et_email.text.isNotEmpty()) {
                name = et_first_name.text.toString()
                surname = et_last_name.text.toString()
                email = et_email.text.toString()
                presenter.editProfile(name, surname, email)
            } else {
                Toast.makeText(this.context, R.string.error_empty, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
