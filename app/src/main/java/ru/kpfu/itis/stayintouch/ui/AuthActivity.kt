package ru.kpfu.itis.stayintouch.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_auth.*
import ru.kpfu.itis.stayintouch.R.string
import android.widget.Toast
import retrofit2.HttpException
import ru.kpfu.itis.stayintouch.*
import ru.kpfu.itis.stayintouch.repository.AuthRepository
import ru.kpfu.itis.stayintouch.utils.*

class AuthActivity : MvpAppCompatActivity() {

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
            AuthRepository.login(email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({ result ->
                    setStringPreference(TOKEN, result.token)
                    AuthRepository.setToken(this)
                    setLoggedInState(true)
                }, { error ->
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
                })
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
        setBooleanPreference(SHARED_PREFERENCES_LOGGED, isLogged)
        if (isLogged) {
            MainActivity.create(this)
        }
    }

    private fun setBooleanPreference(key: String, value: Boolean): Boolean {
        val preferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        if (preferences != null) {
            val editor = preferences.edit()
            editor.putBoolean(key, value)
            return editor.commit()
        }
        return false
    }

    private fun setStringPreference(key: String, value: String?): Boolean {
        val preferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        if (preferences != null) {
            val editor = preferences.edit()
            editor.putString(key, value)
            return editor.commit()
        }
        return false
    }
}
