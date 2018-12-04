package ru.kpfu.itis.stayintouch.ui

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.repository.AuthRepository
import ru.kpfu.itis.stayintouch.utils.SHARED_PREFERENCES_LOGGED
import ru.kpfu.itis.stayintouch.utils.SHARED_PREFS
import ru.kpfu.itis.stayintouch.utils.TOKEN


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val preferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        if (preferences?.getBoolean(SHARED_PREFERENCES_LOGGED, false) == true) {
            AuthRepository.refreshToken(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({ result ->
                    setStringPreference(TOKEN, result.token)
                    AuthRepository.setToken(this)
                    MainActivity.create(this)
                }, { error ->
                    AuthActivity.create(this, false)
                    error.printStackTrace()
                })
        } else {
            AuthActivity.create(this, false)
        }
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
