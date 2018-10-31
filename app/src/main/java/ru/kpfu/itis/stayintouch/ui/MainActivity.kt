package ru.kpfu.itis.stayintouch.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.kpfu.itis.stayintouch.R.id.*
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.ui.news.NewsFragment


class MainActivity : MvpAppCompatActivity() {

    companion object {
        const val NEWS_FRAGMENT_TAG = "NewsFragment"
        const val RECOMMENDATION_FRAGMENT_TAG = "RecommendFragment"
        const val ANSWERS_FRAGMNET_TAG = "AnswersFragment"
        const val PROFILE_FRAGMENT_TAG = "ProfileFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Fabric.with(this, Answers())
        Fabric.with(this, Crashlytics())
        initListeners()
        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.container,
                    NewsFragment.newInstance(),
                    NEWS_FRAGMENT_TAG
                )
                .addToBackStack(NEWS_FRAGMENT_TAG)
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val currentFrag = supportFragmentManager.findFragmentByTag(NEWS_FRAGMENT_TAG)
        return if (currentFrag != null && currentFrag.isVisible) {
            menuInflater.inflate(R.menu.toolbar_create_post_menu, menu)
            true
        } else {
            super.onCreateOptionsMenu(menu)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            action_create_post -> {
                //TODO окно создания поста
            }
        }
        return true
    }

    private fun initListeners() {
       /* btn_sign_in.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }*/
        bottom_navigation.setOnNavigationItemSelectedListener {
            invalidateOptionsMenu()
            when (it.itemId) {
                nav_news -> {
                    //todo новости
                    supportFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.container,
                            NewsFragment.newInstance(),
                            NEWS_FRAGMENT_TAG
                        )
                        .addToBackStack(NEWS_FRAGMENT_TAG)
                        .commit()
                }
                nav_recommend -> {
                    //todo рекомендашки
                    supportFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.container,
                            RecommendFragment.newInstance(),
                            RECOMMENDATION_FRAGMENT_TAG
                        )
                        .addToBackStack(RECOMMENDATION_FRAGMENT_TAG)
                        .commit()
                }
                nav_answers -> {
                    //todo ответики и поиск
                    supportFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.container,
                            AnswersFragment.newInstance(),
                            ANSWERS_FRAGMNET_TAG
                        )
                        .addToBackStack(ANSWERS_FRAGMNET_TAG)
                        .commit()
                }
                nav_profile -> {
                    //todo профиль
                    supportFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.container,
                            ProfileFragment.newInstance(),
                            PROFILE_FRAGMENT_TAG
                        )
                        .addToBackStack(PROFILE_FRAGMENT_TAG)
                        .commit()
                }
            }
            true
        }
    }
}
