package com.bangkit.mocca

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContract
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkit.mocca.databinding.ActivityMainBinding
import com.bangkit.mocca.ui.transaction.manual.ManualTransactionActivity
import com.bangkit.mocca.ui.transaction.ocr.OCRTransactionActivity
import com.canhub.cropper.CropImage

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val navHostController = findNavController(R.id.nav_host_fragment_container)
        binding.bottomNavigationView.setupWithNavController(navHostController)
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false

        binding.fabAdd.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_sheet_layout)

        val btnClose: ImageView = dialog.findViewById(R.id.btn_close)
        val btnTransactionManual: LinearLayout = dialog.findViewById(R.id.manual_transaction)
        val btnTransactionOCR: LinearLayout = dialog.findViewById(R.id.ocr_transaction)

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        btnTransactionManual.setOnClickListener {
            val intent = Intent(this, ManualTransactionActivity::class.java)
            startActivity(intent)
        }

        btnTransactionOCR.setOnClickListener {
            val intent = Intent(this, OCRTransactionActivity::class.java)
            startActivity(intent)
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.BottomDialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }
}