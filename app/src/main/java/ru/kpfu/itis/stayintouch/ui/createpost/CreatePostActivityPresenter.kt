package ru.kpfu.itis.stayintouch.ui.createpost

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.kpfu.itis.stayintouch.model.AttachmentCreate
import ru.kpfu.itis.stayintouch.model.PostCreate
import ru.kpfu.itis.stayintouch.repository.PostRepository

@InjectViewState
class CreatePostActivityPresenter : MvpPresenter<CreatePostActivityView>() {

    fun createPost(post: PostCreate) {
        PostRepository.createPost(post)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewState::setLoading)
            .subscribe(viewState::returnToMainActivity, viewState::handleError)
    }

    fun createPostWithAttachments(post: PostCreate) {
        PostRepository.createPost(post)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewState::setLoading)
            .subscribe(viewState::addAttachment, viewState::handleError)
    }

    fun addAttachment(attachment: AttachmentCreate) {
        if (attachment.file != null) {
            attachment.attach_to?.let { PostRepository
                .addAttachment(attachment.file, attachment.label, it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(viewState::returnToMainActivity, viewState::handleError)}
        } else {
            attachment.attach_to?.let {
                attachment.url?.let { it1 ->
                    PostRepository
                        .addAttachmentLink(it1, attachment.label, it)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(viewState::returnToMainActivity, viewState::handleError)
                }
            }
        }

    }
}