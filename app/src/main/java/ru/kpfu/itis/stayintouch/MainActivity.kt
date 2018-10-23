package ru.kpfu.itis.stayintouch

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ru.kpfu.itis.stayintouch.R.id.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListeners()
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, NewsFragment.newInstance())
                .addToBackStack("NewsFragment")
                .commit()
        }
    }

    private fun initListeners() {
       /* btn_sign_in.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }*/
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                nav_news -> {
                    //todo новости
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, NewsFragment.newInstance())
                        .addToBackStack("NewsFragment")
                        .commit()
                }
                nav_recommend -> {
                    //todo рекомендашки
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, RecommendFragment.newInstance())
                        .addToBackStack("RecommendFragment")
                        .commit()
                }
                nav_answers -> {
                    //todo ответики и поиск
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, AnswersFragment.newInstance())
                        .addToBackStack("AnswersFragment")
                        .commit()
                }
                nav_profile -> {
                    //todo профиль
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, ProfileFragment.newInstance())
                        .addToBackStack("ProfileFragment")
                        .commit()
                }
            }
            true
        }
    }
}
