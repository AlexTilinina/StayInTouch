package ru.kpfu.itis.stayintouch.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import com.arellomobile.mvp.MvpAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.kpfu.itis.stayintouch.R.id.*
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.ui.answers.AnswersFragment
import ru.kpfu.itis.stayintouch.ui.news.NewsFragment
import ru.kpfu.itis.stayintouch.ui.profile.ProfileFragment
import ru.kpfu.itis.stayintouch.ui.recommend.RecommendFragment
import ru.kpfu.itis.stayintouch.utils.*
import ru.kpfu.itis.stayintouch.ui.createpost.CreatePostActivity

class MainActivity : MvpAppCompatActivity() {

    companion object {

        fun create(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }

        fun create(context: Context, tag: String) {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(SEARCH, tag)
            context.startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Fabric.with(this, Answers())
        Fabric.with(this, Crashlytics())
        initListeners()
        setSupportActionBar(toolbar)
        checkPermissions()
        if (intent.extras != null) {
            val searchQuery = intent.extras.getString(SEARCH)
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.container,
                    RecommendFragment.newInstance(searchQuery),
                    RECOMMENDATION_FRAGMENT_TAG
                )
                .commit()
        } else {
            if (savedInstanceState == null) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.container,
                        NewsFragment.newInstance(),
                        NEWS_FRAGMENT_TAG
                    )
                    .commit()
            }
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
        currentFrag = supportFragmentManager.findFragmentByTag(PROFILE_FRAGMENT_TAG)
        if (currentFrag != null && currentFrag.isVisible) {
            menuInflater.inflate(R.menu.toolbar_edit_profile_menu, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            action_create_post -> {
                CreatePostActivity.create(this);
            }
            action_search -> {
                (item.actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(
                                R.id.container,
                                RecommendFragment.newInstance(query),
                                RECOMMENDATION_FRAGMENT_TAG
                            )
                            .commit()
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        //TODO фильтровать теги
                        return true
                    }
                })
            }
            action_edit_profile -> {
                (supportFragmentManager
                    .findFragmentByTag(PROFILE_FRAGMENT_TAG)
                        as ProfileFragment)
                    .editProfile()
            }
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
            != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR)
            requestPermissions(permissions, 0)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, 0)
        }
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
                        .commit()
                }
            }
            true
        }
    }
}
