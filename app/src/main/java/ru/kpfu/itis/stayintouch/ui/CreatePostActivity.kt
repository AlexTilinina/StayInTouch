package ru.kpfu.itis.stayintouch.ui

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create_post.*
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.model.Post
import ru.kpfu.itis.stayintouch.model.Tag
import ru.kpfu.itis.stayintouch.repository.PostRepository
import ru.kpfu.itis.stayintouch.repository.TagRepository
import ru.kpfu.itis.stayintouch.repository.UserRepository
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
        initOnClickListeners();
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
            val post = Post(null, UserRepository.getCurrentUser(this).blockingGet(), text, GregorianCalendar(), null, tags)
            PostRepository.createPost(post)
            //TODO добавление в список постов юзера
            MainActivity.create(this)
        }
    }
}
