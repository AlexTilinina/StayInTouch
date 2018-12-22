package ru.kpfu.itis.stayintouch.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_registration.*
import retrofit2.HttpException
import ru.kpfu.itis.stayintouch.*
import ru.kpfu.itis.stayintouch.R.string
import ru.kpfu.itis.stayintouch.repository.RegistrationRepository
import ru.kpfu.itis.stayintouch.utils.*

class RegistrationActivity : MvpAppCompatActivity() {

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

    fun initClickListeners() {
        btn_sign_up.setOnClickListener {
            val name = et_name.text.toString()
            val surname = et_surname.text.toString()
            val email = et_email.text.toString()
            val password = et_password.text.toString()
            val password2 = et_password2.text.toString()

            SoftKeyboardHelper.hideKeyboard(this, currentFocus)
            if (TextUtils.isEmpty(name)) {
                it_name.error = getString(string.error_empty_name)
            }
            if (TextUtils.isEmpty(surname)) {
                it_surname.error = getString(string.error_empty_surname)
            }
            if (TextUtils.isEmpty(email)) {
                it_email.error = getString(string.error_empty_email)
                return@setOnClickListener
            }
            if (!email.matches(EMAIL_REGEX.toRegex())) {
                it_email.error = getString(string.error_wrong_email)
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                it_password.error = getString(string.error_empty_password)
                return@setOnClickListener
            }
            if (!password.matches(PASSWORD_REGEX.toRegex())) {
                it_password.error = getString(string.error_wrong_password)
                return@setOnClickListener
            }
            if (!TextUtils.equals(password, password2)) {
                it_password2.error = getString(string.error_different_passwords)
                return@setOnClickListener
            }
            RegistrationRepository.registration(name, surname, email, password, password2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    AuthActivity.create(this, false)
                }, { error ->
                    if (error is HttpException) {
                        if (error.code() == CODE_400) {
                            if (error.response().errorBody()?.string()?.contains(SIGN_UP_EMAIL_EXISTS_ERROR) == true)
                                Toast.makeText(this, getString(R.string.error_email_exists),
                                    Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                    }
                })
        }
    }
}
