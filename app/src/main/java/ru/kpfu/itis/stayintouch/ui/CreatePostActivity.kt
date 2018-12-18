package ru.kpfu.itis.stayintouch.ui

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_create_post.*
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.model.PostCreate
import ru.kpfu.itis.stayintouch.model.Tag
import ru.kpfu.itis.stayintouch.repository.PostRepository
import ru.kpfu.itis.stayintouch.repository.TagRepository
import java.util.*

class CreatePostActivity : AppCompatActivity() {

    companion object {

        fun create(context: Context){
            val intent = Intent(context, CreatePostActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        /*toolbar.title = "Create post"
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }*/
        initOnClickListeners()
    }

    private fun initOnClickListeners() {
        btn_attach.setOnClickListener {
            //TODO attachments
        }
        btn_create_post.setOnClickListener {
            val text = et_text.text.toString()
            val tagsText = et_tag.text.toString().split(" ")
            val tags = ArrayList<Tag>()
            for (tag in tagsText){
                tags.add(TagRepository.getTagsByText(tag).blockingGet())
            }
            val post = PostCreate(text = text)
            PostRepository.createPost(post)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    progress_bar.visibility = View.VISIBLE
                }
                .doAfterSuccess {
                    MainActivity.create(this)
                }
                .subscribe()
        }
    }
}
