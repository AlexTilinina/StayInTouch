package ru.kpfu.itis.stayintouch.ui.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
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
        val username = "${user.first_name} ${user.last_name}"
        tv_username.text = username
        tv_email.text = user.email
        if (user.profile?.tags_list != null) {
            if (user.profile?.tags_list?.isNotEmpty() == true) {
                var tags = ""
                for (tag: Tag in user.profile?.tags_list!!) {
                    tags += "#${tag.tag} "
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
        iv_profile_image.visibility = View.GONE
        tv_username.visibility = View.GONE
        tv_email_fix.visibility = View.GONE
        tv_email.visibility = View.GONE
        tv_tags_fix.visibility = View.GONE
        tv_tags_list.visibility = View.GONE
        btn_log_out.visibility = View.GONE
    }

    override fun setNotLoading() {
        progress_bar.visibility = View.GONE
        iv_profile_image.visibility = View.VISIBLE
        tv_username.visibility = View.VISIBLE
        tv_email_fix.visibility = View.VISIBLE
        tv_email.visibility = View.VISIBLE
        tv_tags_fix.visibility = View.VISIBLE
        tv_tags_list.visibility = View.VISIBLE
        btn_log_out.visibility = View.VISIBLE
    }

    @ProvidePresenter
    fun providePresenter(): ProfileFragmentPresenter {
        return ProfileFragmentPresenter(context)
    }

    private fun initClickListeners(){
        btn_log_out.setOnClickListener {
            context?.let { it1 -> AuthActivity.create(it1, false) }
        }
    }
}
