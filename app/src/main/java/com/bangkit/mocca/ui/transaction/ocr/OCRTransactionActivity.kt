package com.bangkit.mocca.ui.transaction.ocr

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bangkit.mocca.databinding.ActivityOcrtransactionBinding
import com.bangkit.mocca.utils.reduceFileImage
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import com.bangkit.mocca.data.Result
import com.bangkit.mocca.utils.uriToFile

class OCRTransactionActivity : CropImageActivity() {

    private lateinit var binding: ActivityOcrtransactionBinding

    private val ocrViewModel by viewModels<OcrViewModel>()
    private var getFile: File? = null

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

        ocrViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.btnCrop.setOnClickListener { cropImage() }

        binding.btnScanImage.setOnClickListener {
            uploadImage()
        }

        setCropImageView(binding.cropImageView)
    }

    private fun uploadImage() {
        if (getFile != null) {
            val requestFile = reduceFileImage(getFile as File)
            val requestImageFile = requestFile.asRequestBody("image/jpeg".toMediaType())
            val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "scan", requestFile.name, requestImageFile
            )

            ocrViewModel.uploadImage(photo = imageMultiPart)
            ocrViewModel.scanImageResult.observe(this) { result ->
                when (result) {
                    is Result.Loading -> showLoading(true)

                    is Result.Success -> {
                        Toast.makeText(
                           this,
                           "Sukses upload gambar",
                           Toast.LENGTH_SHORT
                        ).show()

                        val product = result.data
                        val intent = Intent(this@OCRTransactionActivity, ListTransactionOcrActivity::class.java)
                        intent.putExtra(ListTransactionOcrActivity.EXTRA_PRODUCT_LIST, product)
                        startActivity(intent)
                    }

                    is Result.Error -> {
                        Toast.makeText(
                            this,
                            "Gagal upload gambar",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } else {
            Toast.makeText(
                this,
                "Tidak ada gambar yang di pilih/diambil",
                Toast.LENGTH_SHORT
            ).show()
        }
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

        val selectedImage: Uri = result.uriContent as Uri
        val file = uriToFile(selectedImage, this)
        getFile = file

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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 0
    }
}