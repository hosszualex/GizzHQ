package com.ah.gizzhq.domain.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log


fun defaultLog(message: String) {
    Log.d("=======>", message)
}

fun Uri.toBitmap(context: Context): Bitmap? {
    val bitmap: Bitmap? = if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images
            .Media.getBitmap(context.contentResolver, this)

    } else {
        val source = ImageDecoder
            .createSource(context.contentResolver, this)
        ImageDecoder.decodeBitmap(source)
    }

    return bitmap
}
