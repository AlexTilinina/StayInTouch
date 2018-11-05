package ru.kpfu.itis.stayintouch.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ru.kpfu.itis.stayintouch.R
import android.preference.PreferenceManager
import ru.kpfu.itis.stayintouch.utils.SHARED_PREFERENCES_LOGGED


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this.applicationContext)
        if (preferences?.getBoolean(SHARED_PREFERENCES_LOGGED, false) == true) {
            MainActivity.create(this)
        } else {
            AuthActivity.create(this, false)
        }
    }
}
