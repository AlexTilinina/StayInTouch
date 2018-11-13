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
import android.preference.PreferenceManager
import android.widget.Toast
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
            if (password.length < 6) {
                it_password.error = getString(string.error_short_password)
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
                    when (result.code){
                        CODE_200 -> {setLoggedInState(true)}
                        CODE_1 -> {
                            setLoadingState(false)
                            Toast.makeText(this, CODE_1_TEXT, Toast.LENGTH_LONG).show()
                        }
                        CODE_2 -> {
                            setLoadingState(false)
                            Toast.makeText(this, CODE_2_TEXT, Toast.LENGTH_LONG).show()
                        }
                    }
                }, { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                    error.printStackTrace()
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
        setBooleanPreference(applicationContext, SHARED_PREFERENCES_LOGGED, isLogged)
        if (isLogged) {
            MainActivity.create(this)
        }
    }

    private fun setBooleanPreference(context: Context, key: String, value: Boolean): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        if (preferences != null) {
            val editor = preferences.edit()
            editor.putBoolean(key, value)
            return editor.commit()
        }
        return false
    }
}
