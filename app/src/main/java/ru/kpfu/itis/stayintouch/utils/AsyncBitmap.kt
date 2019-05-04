package ru.kpfu.itis.stayintouch.utils


import android.graphics.Bitmap
import android.os.AsyncTask
import android.widget.ImageView
import java.lang.ref.WeakReference
import android.media.MediaMetadataRetriever
import android.view.View

class AsyncBitmap(imageView: ImageView,
                  imageView2: ImageView,
                  private val width: Int,
                  private val height: Int,
                  private val videoPath: String?
) : AsyncTask<Int, Void, Bitmap>() {

    private var imageViewReference: WeakReference<ImageView> = WeakReference(imageView)
    private var imageView2Reference: WeakReference<ImageView> = WeakReference(imageView2)

    // Decode image in background.
    override fun doInBackground(vararg params: Int?): Bitmap? {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(videoPath, HashMap())
        val bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST)
        mediaMetadataRetriever.release()
        return bitmap
    }

    // Once complete, see if ImageView is still around and set bitmap.
    override fun onPostExecute(bitmap: Bitmap) {
        imageViewReference.get()?.setImageBitmap(
            Bitmap.createScaledBitmap(bitmap, width, height, false)
        )
        imageView2Reference.get()?.visibility = View.VISIBLE
    }

}