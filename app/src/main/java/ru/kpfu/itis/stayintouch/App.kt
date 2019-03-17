package ru.kpfu.itis.stayintouch

import android.app.Application
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File

class App: Application() {

    companion object {
        lateinit var picassoWithCache: Picasso
    }

    override fun onCreate() {
        super.onCreate()
        picassoWithCache = picassoWithCache()
    }

    private fun picassoWithCache(): Picasso {
        val httpCacheDirectory = File(cacheDir, "picasso-cache")
        val cache = Cache(httpCacheDirectory, 100 * 1024 * 1024)
        val okHttpClientBuilder = OkHttpClient.Builder().cache(cache)

        return Picasso.Builder(this)
            .downloader(
                OkHttp3Downloader(okHttpClientBuilder.build())
            )
            .build()
    }
}