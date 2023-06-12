package com.bangkit.mocca.ui.transaction.ocr

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bangkit.mocca.databinding.ActivityOcrtransactionBinding
import com.bangkit.mocca.utils.createCustomTempFile
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageActivity
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import java.io.File

class OCRTransactionActivity : CropImageActivity() {

    private lateinit var binding: ActivityOcrtransactionBinding
    private lateinit var currentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOcrtransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btnCrop.setOnClickListener { cropImage() }

        binding.btnScanImage.setOnClickListener {
            uploadImage()
        }

        setCropImageView(binding.cropImageView)
    }

    private fun uploadImage() {

    }

    override fun onPickImageResult(resultUri: Uri?) {
        super.onPickImageResult(resultUri)

        if (resultUri != null) {
            binding.cropImageView.setImageUriAsync(resultUri)
        }
    }

    override fun getResultIntent(uri: Uri?, error: Exception?, sampleSize: Int): Intent {
        val result = super.getResultIntent(uri, error, sampleSize)
        return result.putExtra("EXTRA_KEY", "extra_data")
    }

    override fun setResult(uri: Uri?, error: Exception?, sampleSize: Int) {
        val result = CropImage.ActivityResult(
            originalUri = binding.cropImageView.imageUri,
            uriContent = uri,
            error = error,
            cropPoints = binding.cropImageView.cropPoints,
            cropRect = binding.cropImageView.cropRect,
            rotation = binding.cropImageView.rotatedDegrees,
            wholeImageRect = binding.cropImageView.wholeImageRect,
            sampleSize = sampleSize
        )

        binding.cropImageView.setImageUriAsync(result.uriContent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

//    private val launcherIntentCamera = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) {
//        if (it.resultCode == RESULT_OK) {
//            val myFile = File(currentPhotoPath)
//            myFile.let { file ->
//                val exifInterface = ExifInterface(currentPhotoPath)
//                val rotation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
//                val rotationInDegrees = exifToDegrees(rotation)
//
//                val bitmap = BitmapFactory.decodeFile(file.path)
//                val rotatedBitmap = rotateBitmap(bitmap, rotationInDegrees)
//                binding.cropImageView.setImageBitmap(rotatedBitmap)
//            }
//        }
//    }

//    private fun rotateBitmap(bitmap: Bitmap, degrees: Float): Bitmap {
//        val matrix = Matrix()
//        matrix.postRotate(degrees)
//        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix,true)
//    }
//
//    private fun exifToDegrees(exifOrientation: Int): Float {
//        return when(exifOrientation) {
//            ExifInterface.ORIENTATION_ROTATE_90 -> 90F
//            ExifInterface.ORIENTATION_ROTATE_180 -> 180F
//            ExifInterface.ORIENTATION_ROTATE_270 -> 270F
//            else -> 0F
//        }
//    }

//    private fun startTakePhoto() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        intent.resolveActivity(packageManager)
//
//        createCustomTempFile(application).also {
//            val photoURI: Uri = FileProvider.getUriForFile(
//                this@OCRTransactionActivity,
//                "com.bangkit.mocca",
//                it
//            )
//            currentPhotoPath = it.absolutePath
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//            launcherIntentCamera.launch(intent)
//        }
//    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 0
    }
}