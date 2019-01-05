package ru.kpfu.itis.stayintouch.utils

import android.content.Context

class PreferencesHelper {

    companion object {

        fun setBooleanPreference(context: Context, key: String, value: Boolean): Boolean {
            val preferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            if (preferences != null) {
                val editor = preferences.edit()
                editor.putBoolean(key, value)
                return editor.commit()
            }
            return false
        }

        fun setStringPreference(context: Context, key: String, value: String?): Boolean {
            val preferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            if (preferences != null) {
                val editor = preferences.edit()
                editor.putString(key, value)
                return editor.commit()
            }
            return false
        }

        fun getStringPreference(context: Context, key: String): String {
            val preferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            return preferences.getString(key, "")
        }
    }
}