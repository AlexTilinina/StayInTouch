package ru.kpfu.itis.stayintouch.ui.createpost

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_create_post.*
import retrofit2.HttpException
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.model.Post
import ru.kpfu.itis.stayintouch.model.PostCreate
import ru.kpfu.itis.stayintouch.ui.MainActivity
import ru.kpfu.itis.stayintouch.utils.CODE_500
import java.util.*

class CreatePostActivity : MvpAppCompatActivity(), CreatePostActivityView {

    @InjectPresenter
    lateinit var presenter: CreatePostActivityPresenter

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
        initOnClickListeners()
    }

    override fun handleError(error: Throwable) {
        if (error is HttpException) {
            if (error.code() == CODE_500) {
                error.printStackTrace()
                Toast.makeText(this, getString(R.string.error_server_not_responding), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun setLoading(disposable: Disposable) {
        progress_bar.visibility = View.VISIBLE
    }

    override fun returnToMainActivity(post: Post) {
        MainActivity.create(this)
    }

    private fun initOnClickListeners() {
        btn_attach.setOnClickListener {
            //TODO attachments
        }
        btn_create_post.setOnClickListener {
            createPost()
        }
    }

    private fun createPost() {
        val text = et_text.text.toString()
        val tagsText = et_tag.text.toString()
        if (tagsText.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_empty_tags), Toast.LENGTH_LONG).show()
        } else {
            val splitText = tagsText.split(" ")
            val tags = ArrayList<String>()
            for (tag in splitText){
                if (!tag.startsWith('#'))
                    tags.add("#$tag")
                else tags.add(tag)
            }
            if (text.isNotEmpty())
                presenter.createPost(PostCreate(text, tags))
            else Toast.makeText(this, getString(R.string.error_empty_text), Toast.LENGTH_LONG).show()
        }
    }
}
