package com.loaner.mobile

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_congrats.*

class CongratsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_congrats)

        borrowButton.setOnClickListener {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Loading...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.setCancelable(false)
            progressDialog.show()

            Handler(Looper.getMainLooper()).postDelayed({
                progressDialog.dismiss()

                val intent = Intent(this,LoanDetailsActivity::class.java)
                startActivity(intent)
                finish()

            }, 2000)
        }
    }
}