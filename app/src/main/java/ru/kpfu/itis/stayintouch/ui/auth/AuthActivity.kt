package ru.kpfu.itis.stayintouch.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import kotlinx.android.synthetic.main.activity_auth.*
import ru.kpfu.itis.stayintouch.R.string
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import retrofit2.HttpException
import ru.kpfu.itis.stayintouch.*
import ru.kpfu.itis.stayintouch.model.AuthResponse
import ru.kpfu.itis.stayintouch.ui.MainActivity
import ru.kpfu.itis.stayintouch.ui.registration.RegistrationActivity
import ru.kpfu.itis.stayintouch.utils.*

class AuthActivity : MvpAppCompatActivity(), AuthActivityView {

    @InjectPresenter
    lateinit var presenter: AuthActivityPresenter

    companion object {

        fun create(context: Context, logged: Boolean){
            val intent = Intent(context, AuthActivity::class.java)
            intent.putExtra(SHARED_PREFERENCES_LOGGED, logged)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        initOnClickListeners()
        setLoggedInState(intent.extras.getBoolean(SHARED_PREFERENCES_LOGGED))
    }

    override fun handleError(error: Throwable) {
        if (error is HttpException) {
            if (error.code() == CODE_400) {
                if (error.response().errorBody()?.string()?.contains(LOGIN_DATA_ERROR) == true){
                    Toast.makeText(this, getString(R.string.error_login_data),
                        Toast.LENGTH_LONG).show()
                }
            }
            if (error.code() == CODE_500) {
                Toast.makeText(this, getString(R.string.error_server_not_responding),
                    Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
        }
        setLoadingState(false)
    }

    override fun login(result: AuthResponse) {
        PreferencesHelper.setStringPreference(this, TOKEN, result.token)
        presenter.setToken(PreferencesHelper.getStringPreference(this, TOKEN))
        setLoggedInState(true)
    }

    private fun initOnClickListeners() {
        btn_sign_up.setOnClickListener {
            RegistrationActivity.create(this)
        }
        btn_sign_in.setOnClickListener {
            val email = et_email.text.toString()
            val password = et_password.text.toString()
            SoftKeyboardHelper.hideKeyboard(this, currentFocus)
            if (TextUtils.isEmpty(email)) {
                it_email.error = getString(string.error_empty_email)
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                it_password.error = getString(string.error_empty_password)
                return@setOnClickListener
            }
            if (!email.matches(EMAIL_REGEX.toRegex())) {
                it_email.error = getString(string.error_wrong_email)
                return@setOnClickListener
            }
            setLoadingState(true)
            presenter.login(email, password)
        }
        btn_sign_in_google.setOnClickListener {
            //TODO гугловский виджет
        }
        btn_sign_in_vk.setOnClickListener {
            //TODO вкшный виджет
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        if (isLoading) {
            progress_bar.visibility = View.VISIBLE
            container.visibility = View.GONE
        } else {
            progress_bar.visibility = View.GONE
            container.visibility = View.VISIBLE
        }
    }

    private fun setLoggedInState(isLogged: Boolean) {
        PreferencesHelper.setBooleanPreference(this, SHARED_PREFERENCES_LOGGED, isLogged)
        if (isLogged) {
            MainActivity.create(this)
        }
    }
}
