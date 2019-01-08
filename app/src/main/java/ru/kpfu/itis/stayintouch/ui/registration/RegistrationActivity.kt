package ru.kpfu.itis.stayintouch.ui.registration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_registration.*
import retrofit2.HttpException
import ru.kpfu.itis.stayintouch.*
import ru.kpfu.itis.stayintouch.R.string
import ru.kpfu.itis.stayintouch.model.AuthResponse
import ru.kpfu.itis.stayintouch.ui.auth.AuthActivity
import ru.kpfu.itis.stayintouch.utils.*

class RegistrationActivity : MvpAppCompatActivity(), RegistrationActivityView {

    @InjectPresenter
    lateinit var presenter: RegistrationActivityPresenter

    companion object {

        fun create(context: Context) {
            val intent = Intent(context, RegistrationActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        initClickListeners()
    }

    override fun handleError(error: Throwable) {
        if (error is HttpException) {
            if (error.code() == CODE_400) {
                if (error.response().errorBody()?.string()?.contains(SIGN_UP_EMAIL_EXISTS_ERROR) == true)
                    Toast.makeText(this, getString(R.string.error_email_exists),
                        Toast.LENGTH_LONG).show()
                if (error.response().errorBody()?.string()?.contains("username") == true) {
                    registration()
                }
            }
            if (error.code() == CODE_500) {
                Toast.makeText(this, getString(R.string.error_server_not_responding), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
        }
        setNotLoading()
    }

    override fun auth(result: AuthResponse) {
        Toast.makeText(this, getString(R.string.reg_success), Toast.LENGTH_LONG).show()
        AuthActivity.create(this, false)
    }

    override fun setNotLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun setLoading(disposable: Disposable) {
        progress_bar.visibility = View.VISIBLE
    }

    private fun initClickListeners() {
        btn_sign_up.setOnClickListener {
            registration()
        }
    }

    private fun registration() {
        val name = et_name.text.toString()
        val surname = et_surname.text.toString()
        val email = et_email.text.toString()
        val password = et_password.text.toString()
        val password2 = et_password2.text.toString()

        SoftKeyboardHelper.hideKeyboard(this, currentFocus)
        if (TextUtils.isEmpty(name)) {
            it_name.error = getString(string.error_empty_name)
            return
        } else it_name.error = null
        if (TextUtils.isEmpty(surname)) {
            it_surname.error = getString(string.error_empty_surname)
            return
        } else it_surname.error = null
        if (TextUtils.isEmpty(email)) {
            it_email.error = getString(string.error_empty_email)
            return
        } else it_email.error = null
        if (!email.matches(EMAIL_REGEX.toRegex())) {
            it_email.error = getString(string.error_wrong_email)
            return
        } else it_email.error = null
        if (TextUtils.isEmpty(password)) {
            it_password.error = getString(string.error_empty_password)
            return
        } else it_password.error = null
        if (!password.matches(PASSWORD_REGEX.toRegex())) {
            it_password.error = getString(string.error_wrong_password)
            return
        } else it_password.error = null
        if (!TextUtils.equals(password, password2)) {
            it_password2.error = getString(string.error_different_passwords)
            return
        } else it_password2.error = null
        presenter.registration(name, surname, email, password, password2)
    }
}
