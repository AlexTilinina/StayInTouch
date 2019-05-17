package ru.kpfu.itis.stayintouch.ui.splash

import android.content.Context
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import retrofit2.HttpException
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.model.Token
import ru.kpfu.itis.stayintouch.ui.auth.AuthActivity
import ru.kpfu.itis.stayintouch.ui.MainActivity
import ru.kpfu.itis.stayintouch.utils.PreferencesHelper
import ru.kpfu.itis.stayintouch.utils.SHARED_PREFERENCES_LOGGED
import ru.kpfu.itis.stayintouch.utils.SHARED_PREFS
import ru.kpfu.itis.stayintouch.utils.TOKEN

class SplashActivity : MvpAppCompatActivity(), SplashActivityView {

    @InjectPresenter
    lateinit var presenter: SplashActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val preferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        if (preferences?.getBoolean(SHARED_PREFERENCES_LOGGED, false) == true) {
            presenter.refreshToken(PreferencesHelper.getStringPreference(this, TOKEN))
        } else {
            AuthActivity.create(this, false)
        }
    }

    override fun tokenChanged(token: Token) {
        PreferencesHelper.setStringPreference(this, TOKEN, token.token)
        presenter.setToken(PreferencesHelper.getStringPreference(this, TOKEN))
        MainActivity.create(this)
    }

    override fun handleError(error: Throwable) {
        if (error is HttpException) {
            if (error.message().contains("timeout")){
                presenter.refreshToken(PreferencesHelper.getStringPreference(this, TOKEN))
            }
        } else {
            AuthActivity.create(this, false)
            error.printStackTrace()
        }
    }
}
