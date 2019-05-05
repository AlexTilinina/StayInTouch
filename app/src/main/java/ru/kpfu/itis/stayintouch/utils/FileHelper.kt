package ru.kpfu.itis.stayintouch.utils

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore

class FileHelper {

    companion object {

        fun getPathFromURI(uri: Uri, context: Context, type: String): String {
            val wholeID = DocumentsContract.getDocumentId(uri)
            var externalContentUri: Uri? = null
            var sel: String? = null
            var selArgs: Array<String>? = null
            when (type) {
                ATTACH_LABEL_IMAGE -> {
                    externalContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    sel = MediaStore.Images.Media._ID + "=?"
                    val id = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                    selArgs = arrayOf(id)
                }
                ATTACH_LABEL_VIDEO -> {
                    externalContentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    sel = MediaStore.Images.Media._ID + "=?"
                    val id = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                    selArgs = arrayOf(id)
                }
                ATTACH_LABEL_FILE -> {
                    if (DocumentsContract.isDocumentUri(context, uri)) {
                        if (isExternalStorageDocument(uri)) {
                            val docId = DocumentsContract.getDocumentId(uri)
                            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                            if ("primary".equals(split[0], ignoreCase = true)) {
                                return (Environment.getExternalStorageDirectory().toString() + "/"
                                        + split[1])
                            }
                        } else {
                            if (isDownloadsDocument(uri)) {
                                val id = DocumentsContract.getDocumentId(uri)
                                val contentUri = ContentUris.withAppendedId(
                                    Uri.parse(id),
                                    id.toLong()
                                )
                                return getDataColumn(context, contentUri, null, null)
                            }
                            else {
                                if (isMediaDocument(uri)) {
                                    val docId = DocumentsContract.getDocumentId(uri)
                                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                                    var contentUri: Uri? = null
                                    when (split[0]) {
                                        "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                        "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                                        "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                                    }

                                    val selection = "_id=?"
                                    val selectionArgs = arrayOf(split[1])
                                    return getDataColumn(context, contentUri, selection, selectionArgs)
                                }
                            }
                        }
                    } else {
                        return if ("content".equals(uri.scheme, ignoreCase = true)) {
                            if (isGooglePhotosUri(uri)) uri.lastPathSegment else
                                getDataColumn(context, uri, null, null)
                        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                            uri.path
                        } else ""
                    }
                }
            }
            return getDataColumn(context, externalContentUri, sel, selArgs)
        }

        fun getPathFromURI(uri: Uri, context: Context): String {
            var filePath = ""
            val wholeID = DocumentsContract.getDocumentId(uri)

            val id = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
            val column = arrayOf(MediaStore.Images.Media.DATA)
            val sel = MediaStore.Images.Media._ID + "=?"

            val cursor = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, arrayOf(id), null
            )

            val columnIndex = cursor.getColumnIndex(column[0])
            if (cursor.moveToFirst())
                filePath = cursor.getString(columnIndex)
            cursor.close()
            return filePath
        }

        private fun getDataColumn(
            context: Context,
            uri: Uri?,
            selection: String?,
            selectionArgs: Array<String>?
        ): String {
            var path = ""
            val column = MediaStore.MediaColumns.DATA
            val projection = arrayOf(column)

            val cursor = context.contentResolver.query(
                uri, projection, selection, selectionArgs, null
            )
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndexOrThrow(column)
                    path = cursor.getString(index)
                }
                cursor.close()
            }
            return path
        }

        private fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri.authority
        }

        private fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri.authority
        }

        private fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri.authority
        }

        private fun isGooglePhotosUri(uri: Uri): Boolean {
            return "com.google.android.apps.photos.content" == uri.authority
        }

    }
}