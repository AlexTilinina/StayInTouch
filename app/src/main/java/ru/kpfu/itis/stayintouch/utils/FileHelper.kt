package ru.kpfu.itis.stayintouch.utils

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore

class FileHelper {

    companion object {

        fun getPathFromURI(uri: Uri, context: Context, type: String): String {
            var filePath = ""
            val wholeID = DocumentsContract.getDocumentId(uri)

            // Split at colon, use second item in the array
            val id = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]

            var column = emptyArray<String>()
            var externalContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            when (type) {
                ATTACH_LABEL_IMAGE -> {
                    column = arrayOf(MediaStore.Images.Media.DATA)
                }
                ATTACH_LABEL_VIDEO -> {
                    column = arrayOf(MediaStore.Video.Media.DATA)
                    externalContentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                }
            }

            // where id is equal to
            val sel = MediaStore.Images.Media._ID + "=?"

            val cursor = context.contentResolver.query(
                externalContentUri,
                column, sel, arrayOf(id), null
            )

            val columnIndex = cursor.getColumnIndex(column[0])

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex)
            }
            cursor.close()
            return filePath
        }

        fun getPathFromURI(uri: Uri, context: Context): String {
            var filePath = ""
            val wholeID = DocumentsContract.getDocumentId(uri)

            // Split at colon, use second item in the array
            val id = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]

            val column = arrayOf(MediaStore.Images.Media.DATA)

            // where id is equal to
            val sel = MediaStore.Images.Media._ID + "=?"

            val cursor = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, arrayOf(id), null
            )

            val columnIndex = cursor.getColumnIndex(column[0])

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex)
            }
            cursor.close()
            return filePath
        }
    }
}