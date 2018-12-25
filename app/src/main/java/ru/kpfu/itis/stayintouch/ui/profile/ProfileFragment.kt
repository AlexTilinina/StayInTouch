package ru.kpfu.itis.stayintouch.ui.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.squareup.picasso.Picasso
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import ru.kpfu.itis.stayintouch.R
import ru.kpfu.itis.stayintouch.model.Tag
import ru.kpfu.itis.stayintouch.model.User
import ru.kpfu.itis.stayintouch.ui.AuthActivity
import ru.kpfu.itis.stayintouch.ui.adapter.TagAdapter
import ru.kpfu.itis.stayintouch.utils.PROFILE_IMAGE_SIZE_MEDIUM
import android.content.Intent
import ru.kpfu.itis.stayintouch.utils.PICK_IMAGE_REQUEST
import android.app.Activity.RESULT_OK
import okhttp3.MediaType
import java.io.File
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import ru.kpfu.itis.stayintouch.model.Message
import ru.kpfu.itis.stayintouch.utils.CODE_500


class ProfileFragment : MvpAppCompatFragment(), ProfileFragmentView {

    @InjectPresenter
    lateinit var presenter: ProfileFragmentPresenter

    companion object {

        fun newInstance(): Fragment {
            return ProfileFragment()
        }
    }

    override fun onStart() {
        super.onStart()
        initClickListeners()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.activity?.toolbar?.title = resources.getString(R.string.nav_profile)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun loadUser(user: User) {
        tv_name.text = user.first_name
        tv_surname.text = user.last_name
        tv_email.text = user.email
        if (!user.profile?.photo_url.isNullOrEmpty()) {
            Picasso.get()
                .load(user.profile?.photo_url)
                .resize(PROFILE_IMAGE_SIZE_MEDIUM, PROFILE_IMAGE_SIZE_MEDIUM)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(iv_profile_image)
        }
        if (user.profile?.tags != null) {
            if (user.profile?.tags?.isNotEmpty() == true) {
                val manager = LinearLayoutManager(context)
                recycler_view.adapter = TagAdapter(user.profile?.tags as MutableList<Tag>, false)
                recycler_view.layoutManager = manager
                var tags = ""
                for (tag: Tag in user.profile?.tags!!) {
                    tags += "${tag.name} "
                }
                tv_tags_list.text = tags
            } else {
                tv_tags_list.text = getString(R.string.empty_news)
            }
        }

    }

    override fun handleError(error: Throwable) {
        if (error is HttpException) {
            if (error.code() == CODE_500) {
                error.printStackTrace()
                Toast.makeText(activity, getString(R.string.error_server_not_responding), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getMessage(message: Message) {
        Toast.makeText(activity, message.message, Toast.LENGTH_LONG).show()
    }

    override fun setLoading(disposable: Disposable) {
        progress_bar.visibility = View.VISIBLE
    }

    override fun setNotLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun makeEditableInvisible() {
        et_first_name.visibility = View.GONE
        et_last_name.visibility = View.GONE
        et_email.visibility = View.GONE
        btn_ok.visibility = View.GONE
        recycler_view.visibility = View.GONE

        tv_name.visibility = View.VISIBLE
        tv_surname.visibility = View.VISIBLE
        tv_email.visibility = View.VISIBLE
        tv_tags_list.visibility = View.VISIBLE
        btn_log_out.visibility = View.VISIBLE
    }

    fun editProfile() {
        et_first_name.visibility = View.VISIBLE
        et_last_name.visibility = View.VISIBLE
        et_email.visibility = View.VISIBLE
        btn_ok.visibility = View.VISIBLE
        recycler_view.visibility = View.VISIBLE

        et_first_name.setText(tv_name.text)
        et_last_name.setText(tv_surname.text)
        et_email.setText(tv_email.text)

        tv_name.visibility = View.GONE
        tv_surname.visibility = View.GONE
        tv_email.visibility = View.GONE
        tv_tags_list.visibility = View.GONE
        btn_log_out.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {

            val uri = data.data
            Picasso.get()
                .load(uri)
                .resize(PROFILE_IMAGE_SIZE_MEDIUM, PROFILE_IMAGE_SIZE_MEDIUM)
                .centerCrop()
                .noFade()
                .placeholder(R.drawable.ic_download)
                .into(iv_profile_image)
            val picture = File(uri.path)
            val file = RequestBody.create(MediaType.parse("image/*"), picture);
            val fileToUpload = MultipartBody.Part.createFormData("image", picture.name, file)
            //TODO ждать фикса
            presenter.changePhoto(fileToUpload)
        }
    }

    private fun initClickListeners() {
        btn_log_out.setOnClickListener {
            context?.let { it1 -> AuthActivity.create(it1, false) }
        }
        btn_ok.setOnClickListener {
            var name = ""
            var surname = ""
            var email = ""
            if (et_first_name.text.isNotEmpty() && et_last_name.text.isNotEmpty()
                && et_email.text.isNotEmpty()) {
                name = et_first_name.text.toString()
                surname = et_last_name.text.toString()
                email = et_email.text.toString()
                presenter.editProfile(name, surname, email)
            } else {
                Toast.makeText(this.context, R.string.error_empty_fields, Toast.LENGTH_SHORT).show()
            }
        }
        iv_profile_image.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
        }
    }
}
