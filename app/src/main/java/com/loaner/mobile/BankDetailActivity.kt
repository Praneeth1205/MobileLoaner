package com.loaner.mobile

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.loaner.mobile.store.Prefs
import kotlinx.android.synthetic.main.activity_bank_detail.*

class BankDetailActivity : AppCompatActivity() {

    lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_detail)

        prefs = Prefs(this)

        backBtn.setOnClickListener {
            finish()
        }

        submit_btn?.setOnClickListener {
            when{
                account_no.text.toString() == ""->Toast.makeText(this,"Please enter Account No.",Toast.LENGTH_SHORT).show()
                bank_name.text.toString() == ""->Toast.makeText(this,"Please enter  Bank Name",Toast.LENGTH_SHORT).show()
                branch_name.text.toString() == ""->Toast.makeText(this,"Please enter Branch Name",Toast.LENGTH_SHORT).show()
                ifsc_code.text.toString() == ""->Toast.makeText(this,"Please enter IFSC Code",Toast.LENGTH_SHORT).show()

                else->{
                    prefs.allDetailsFilled = true
                    val progressDialog = ProgressDialog(this)
                    progressDialog.setMessage("Loading...")
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.setCancelable(false)
                    progressDialog.show()

                    Handler(Looper.getMainLooper()).postDelayed({
                        progressDialog.dismiss()
                        val intent = Intent(this,CongratsActivity::class.java)
                        startActivity(intent)
                        finish()
                    },1000)

                }
            }
        }
    }
}