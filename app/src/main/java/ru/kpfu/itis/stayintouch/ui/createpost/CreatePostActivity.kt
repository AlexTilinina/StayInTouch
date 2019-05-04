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
import kotlinx.android.synthetic.main.activity_create_post.progress_bar
import kotlinx.android.synthetic.main.activity_create_post.toolbar
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.model.Post
import ru.kpfu.itis.stayintouch.model.PostCreate
import ru.kpfu.itis.stayintouch.ui.MainActivity
import java.io.File
import java.util.*
import android.support.v7.widget.PopupMenu
import android.util.Log
import ru.kpfu.itis.stayintouch.model.AttachmentCreate
import ru.kpfu.itis.stayintouch.model.Message
import ru.kpfu.itis.stayintouch.utils.*


class CreatePostActivity : MvpAppCompatActivity(), CreatePostActivityView {

    @InjectPresenter
    lateinit var presenter: CreatePostActivityPresenter
    var attachment: AttachmentCreate? = null

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
                Toast.makeText(this, getString(R.string.error_server_not_responding), Toast.LENGTH_SHORT).show()
            }
        }
        else {
            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            error.printStackTrace()
        }
        progress_bar.visibility = View.GONE
    }

    override fun setLoading(disposable: Disposable) {
        progress_bar.visibility = View.VISIBLE
    }

    override fun returnToMainActivity(post: Post) {
        MainActivity.create(this)
    }

    override fun returnToMainActivity(message: Message) {
        MainActivity.create(this)
    }

    override fun addAttachment(post: Post) {
        if (attachment == null) {
            Toast.makeText(this, "Что-то пошло не так", Toast.LENGTH_LONG).show()
        } else {
            attachment?.attach_to = RequestBody.create(MediaType.parse("multipart/form-data"), post.id.toString())
            attachment?.let { presenter.addAttachment(it) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null && data.data != null) {
            iv_attach.visibility = View.VISIBLE
            tv_attach.visibility = View.VISIBLE
            btn_delete.visibility = View.VISIBLE
            var label_name = ""
            when (requestCode) {
                PICK_IMAGE_REQUEST -> {
                    label_name = ATTACH_LABEL_IMAGE
                    iv_attach.setImageDrawable(resources.getDrawable(R.drawable.ic_image, null))
                }
                PICK_VIDEO_REQUEST -> {
                    label_name = ATTACH_LABEL_VIDEO
                    iv_attach.setImageDrawable(resources.getDrawable(R.drawable.ic_video, null))
                }
            }
            val path = FileHelper.getPathFromURI(data.data, this, label_name)
            val fileToLoad = File(path)
            val file = RequestBody.create(MediaType.parse("multipart/form-data"), fileToLoad)
            val multipartFile = MultipartBody.Part.createFormData("file", fileToLoad.name, file)

            val label = RequestBody.create(MediaType.parse("multipart/form-data"), label_name)
            attachment = AttachmentCreate(multipartFile, label)

            tv_attach.text = fileToLoad.name

            /*when (requestCode) {
                PICK_IMAGE_REQUEST -> {
                    val path = this.let { FileHelper.getPathFromURI(data.data, it) }
                    val picture = File(path)
                    val file = RequestBody.create(MediaType.parse("multipart/form-data"), picture)
                    val multipartFile = MultipartBody.Part.createFormData("file", picture.name, file)
                    val label = RequestBody.create(MediaType.parse("multipart/form-data"), ATTACH_LABEL_IMAGE)
                    attachment = AttachmentCreate(multipartFile, label)
                    iv_attach.setImageDrawable(resources.getDrawable(R.drawable.ic_image, null))
                    tv_attach.text = picture.name
                }
                PICK_VIDEO_REQUEST -> {

                }
            }*/

        }
    }

    private fun initOnClickListeners() {
        btn_attach.setOnClickListener {
            showPopupAttachmentsMenu(it)
        }
        btn_create_post.setOnClickListener {
            createPost()
        }
        btn_delete.setOnClickListener {
            attachment = null
            iv_attach.visibility = View.GONE
            tv_attach.visibility = View.GONE
            btn_delete.visibility = View.GONE
        }
    }

    private fun showPopupAttachmentsMenu(v: View) {
        val menu = PopupMenu(this, v)
        makeMenuIconsVisible(menu)
        menu.inflate(R.menu.create_post_attachments_menu)

        menu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.attach_image -> {
                    selectPictureFromDevice()
                }
                R.id.attach_video -> {
                    selectVideoFromDevice()
                }
                R.id.attach_file -> {

                }
                R.id.attach_link -> {

                }
            }
            true
        }
        menu.show()
    }

    private fun makeMenuIconsVisible(menu: PopupMenu) {
        try {
            val fields = menu.javaClass.declaredFields
            for (field in fields) {
                if ("mPopup" == field.name) {
                    field.isAccessible = true
                    val menuPopupHelper = field.get(menu)
                    val classPopupHelper = Class.forName(menuPopupHelper.javaClass.name)
                    val setForceIcons = classPopupHelper.getMethod("setForceShowIcon",
                        Boolean::class.javaPrimitiveType)
                    setForceIcons.invoke(menuPopupHelper, true)
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun selectPictureFromDevice() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    private fun selectVideoFromDevice() {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select a Video"), PICK_VIDEO_REQUEST)
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
                if (tag.isNotEmpty()) {
                    if (!tag.startsWith('#'))
                        tags.add("#$tag")
                    else tags.add(tag)
                }
            }
            if (text.isNotEmpty()) {
                val post = PostCreate(text, tags)
                if (attachment != null) {
                    presenter.createPostWithAttachments(post)
                } else {
                    presenter.createPost(post)
                }
            }
            else Toast.makeText(this, getString(R.string.error_empty_text), Toast.LENGTH_LONG).show()
        }
    }
}
