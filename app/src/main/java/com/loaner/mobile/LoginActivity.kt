package com.loaner.mobile

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_verify.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signin_btn?.setOnClickListener {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Loading...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.setCancelable(false)
            progressDialog.show()

            Handler(Looper.getMainLooper()).postDelayed({
                progressDialog.dismiss()

                if (phone_number.text.toString() == "") {
                    makeText(
                        this, "Please enter your number",
                        LENGTH_SHORT
                    ).show()
                } else if (!checkbox_tc.isChecked) {
                    makeText(
                        this, "Please accept our terms&conditions",
                        LENGTH_SHORT
                    ).show()
                } else {
                    val otpPin = generateOTP()
                    val intent = Intent(this, VerifyActivity::class.java)
                    intent.putExtra("otp",otpPin)
                    startActivity(intent)
                }

            }, 2000)

        }

        backBtn.setOnClickListener {
            finish()
        }

        terms_conditions?.setOnClickListener {
            startActivity(Intent(this,TermsConditionsActivity::class.java))
        }

        privacy_policy?.setOnClickListener {
            startActivity(Intent(this,PrivacypolicyActivity::class.java))
        }

    }
    private fun generateOTP(): String {
        val randomPin = (Math.random() * 9000).toInt() + 1000
        return randomPin.toString()
    }

}