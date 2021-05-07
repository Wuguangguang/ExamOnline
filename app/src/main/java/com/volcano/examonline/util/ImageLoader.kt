package com.volcano.examonline.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.nio.charset.Charset

object ImageLoader {

    // 客户端 -> 服务器 上传
    fun bitmap2String(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        return baos.toString()
    }

    // 拍照文件 -> 客户端回显
    fun file2Bitmap(file: File): Bitmap {
        val fileInputStream = FileInputStream(file)
        val bytes =  ByteArray(fileInputStream.available())
        fileInputStream.read(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0 ,bytes.size)
    }

    // 服务器 -> 客户端 转换
    fun byteArray2Bitmap(str: String): Bitmap {
        val bytes = str.toByteArray(Charsets.UTF_8)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }


    fun uri2Bitmap(context: Context, uri: Uri): Bitmap {
        return BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
    }

    fun getBitMapFromUri(context: Context, uri: Uri) = context.contentResolver.openFileDescriptor(uri, "r")?.use {
            BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
        }

    fun rotateIfRequired(file: File,bitmap: Bitmap): Bitmap {
        val exif = ExifInterface(file.path)
        return when(exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitMap(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitMap(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitMap(bitmap, 270)
            else -> bitmap
        }
    }

    private fun rotateBitMap(bitmap: Bitmap, i: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(i.toFloat())
        val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        bitmap.recycle()
        return rotatedBitmap
    }
}