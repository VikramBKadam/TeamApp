package com.example.teamapp.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.teamapp.R
import com.example.teamapp.databinding.ActivityQrCodeBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder


class QrCodeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var invitationUrl: String
    private lateinit var binding: ActivityQrCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar.toolBar)
        binding.toolbar.idTextTitle.text = "My QR Code"
        binding.toolbar.idTextBack.setOnClickListener(this)
        invitationUrl = intent.getStringExtra(KEY_INVITATION_URL).toString()
        generateQrCode()
    }

    private fun generateQrCode() {
        try {
            val mWriter =
                MultiFormatWriter() //BitMatrix class to encode entered text and set Width & Height
            val mMatrix: BitMatrix = mWriter.encode(invitationUrl, BarcodeFormat.QR_CODE, 400, 400)
            val mEncoder = BarcodeEncoder()
            val mBitmap = mEncoder.createBitmap(mMatrix) //creating bitmap of code
            binding.idQrcode.setImageBitmap(mBitmap) //Setting generated QR code to imageView
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }


    companion object {
        private const val KEY_INVITATION_URL = "teamID"
        fun startActivity(activity: Activity, invitationUrl: String) {
            val intent = Intent(activity, QrCodeActivity::class.java)
            intent.putExtra(KEY_INVITATION_URL, invitationUrl)
            activity.startActivity(intent)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_text_back -> {
                finish()
            }
        }
    }

}