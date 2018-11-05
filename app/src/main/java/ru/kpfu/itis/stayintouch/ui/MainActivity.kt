package ru.kpfu.itis.stayintouch.ui

import android.content.Context
import android.content.Intent
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
import ru.kpfu.itis.stayintouch.ui.answers.AnswersFragment
import ru.kpfu.itis.stayintouch.ui.news.NewsFragment
import ru.kpfu.itis.stayintouch.ui.recommend.RecommendFragment
import ru.kpfu.itis.stayintouch.utils.ANSWERS_FRAGMENT_TAG
import ru.kpfu.itis.stayintouch.utils.NEWS_FRAGMENT_TAG
import ru.kpfu.itis.stayintouch.utils.PROFILE_FRAGMENT_TAG
import ru.kpfu.itis.stayintouch.utils.RECOMMENDATION_FRAGMENT_TAG


class MainActivity : MvpAppCompatActivity() {

    companion object {
        fun create(context: Context){
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
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
        var currentFrag = supportFragmentManager.findFragmentByTag(NEWS_FRAGMENT_TAG)
        if (currentFrag != null && currentFrag.isVisible) {
            menuInflater.inflate(R.menu.toolbar_create_post_menu, menu)
        }
        currentFrag = supportFragmentManager.findFragmentByTag(RECOMMENDATION_FRAGMENT_TAG)
        if (currentFrag != null && currentFrag.isVisible) {
            menuInflater.inflate(R.menu.toolbar_search_menu, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            action_create_post -> {
                //TODO окно создания поста
            }
            action_search -> {
                //TODO теги
            }
        }
        return true
    }

    private fun initListeners() {
        bottom_navigation.setOnNavigationItemSelectedListener {
            invalidateOptionsMenu()
            when (it.itemId) {
                nav_news -> {
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
                    supportFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.container,
                            AnswersFragment.newInstance(),
                            ANSWERS_FRAGMENT_TAG
                        )
                        .addToBackStack(ANSWERS_FRAGMENT_TAG)
                        .commit()
                }
                nav_profile -> {
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
