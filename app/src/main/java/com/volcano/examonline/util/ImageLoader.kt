package com.volcano.examonline.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.util.Base64
import java.io.*
import java.nio.charset.Charset

object ImageLoader {

    fun bitmap2File(bitmap: Bitmap, file: File): File {
        val bos = BufferedOutputStream(FileOutputStream(file))
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        bos.flush()
        bos.close()
        return file
    }

    fun uri2Bitmap(context: Context, uri: Uri): Bitmap {
        return BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
    }

    fun getBitMapFromUri(context: Context, uri: Uri) = context.contentResolver.openFileDescriptor(uri, "r")?.use {
            BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
        }

}