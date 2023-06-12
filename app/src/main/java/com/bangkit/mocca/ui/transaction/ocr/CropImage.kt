package com.bangkit.mocca.ui.transaction.ocr

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.core.app.ActivityCompat
import com.canhub.cropper.CropImageActivity
import com.canhub.cropper.databinding.CropImageActivityBinding

internal class CropImage : CropImageActivity() {

    private lateinit var binding: CropImageActivityBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = CropImageActivityBinding.inflate(layoutInflater)
    }

    companion object {
        fun start(activity: Activity) {
            ActivityCompat.startActivity(
                activity,
                Intent(activity, CropImage::class.java),
                null
            )
        }
    }
}