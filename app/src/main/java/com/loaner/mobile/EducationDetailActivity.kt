package com.loaner.mobile

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.RadioButton
import android.widget.Toast
import com.loaner.mobile.store.Prefs
import kotlinx.android.synthetic.main.activity_education_detail.*


class EducationDetailActivity : AppCompatActivity() {

    lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_education_detail)

        prefs = Prefs(this)

        buttonBack.setOnClickListener {
            finish()
        }

        btn_next?.setOnClickListener {

            val idEd = education_radio.checkedRadioButtonId
            val idStat = status_radio.checkedRadioButtonId

            when {
                idEd == -1 -> {
                    Toast.makeText(this, "Please select Education..", Toast.LENGTH_SHORT).show()
                }
                idStat == -1 -> {
                    Toast.makeText(this, "Please select Marital Status..", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val progressDialog = ProgressDialog(this)
                    progressDialog.setMessage("Loading...")
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.setCancelable(false)
                    progressDialog.show()

                    Handler(Looper.getMainLooper()).postDelayed({
                        progressDialog.dismiss()
                        val intent = Intent(this, BankDetailActivity::class.java)
                        startActivity(intent)
                        finish()
                    }, 1000)

                }
            }

        }

    }
}