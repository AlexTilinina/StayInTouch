package ru.kpfu.itis.stayintouch.ui

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_auth_other_way.*
import ru.kpfu.itis.stayintouch.R

class AuthOtherWayActivity : AppCompatActivity() {

    companion object {

        fun create(context: Context) {
            val intent = Intent(context, AuthOtherWayActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_other_way)
        initClickListeners()
    }

    private fun initClickListeners() {
        btn_sign_in_google.setOnClickListener {
            //TODO гугловский виджет
        }
        btn_sign_in_vk.setOnClickListener {
            //TODO вкшный виджет
        }
    }
}
