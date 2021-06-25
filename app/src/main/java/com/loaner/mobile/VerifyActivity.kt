package com.loaner.mobile

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.loaner.mobile.store.Prefs
import kotlinx.android.synthetic.main.activity_verify.*
import kotlin.random.Random
import kotlin.random.nextInt

class VerifyActivity : AppCompatActivity() {
    lateinit var prefs: Prefs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)

        prefs = Prefs(this)


        //getOtp()

        val number = Random.nextInt(1000..9999)
        otpTxt.text = number.toString()

        editMobile.setOnClickListener {
            finish()
        }
        back.setOnClickListener {
            finish()
        }

        otpBtn.setOnClickListener {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Loading...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.setCancelable(false)
            progressDialog.show()

            Handler(Looper.getMainLooper()).postDelayed({
                progressDialog.dismiss()

                linearOtp.visibility = View.VISIBLE
                otpBtn.visibility = View.GONE
                go_btn.visibility = View.VISIBLE
            }, 2000)

        }

        go_btn?.setOnClickListener {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Loading...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.setCancelable(false)
            progressDialog.show()

            Handler(Looper.getMainLooper()).postDelayed({
                progressDialog.dismiss()
                when {
                    pin_entry.text.toString() == "" -> {
                        Toast.makeText(this,"Enter OTP...",Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        prefs.isLoggined = true
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

            }, 2000)
        }
    }
}