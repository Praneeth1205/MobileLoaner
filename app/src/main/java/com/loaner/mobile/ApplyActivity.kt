package com.loaner.mobile

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.loaner.mobile.store.Prefs
import kotlinx.android.synthetic.main.activity_apply.*
import java.util.*

class ApplyActivity : AppCompatActivity() {

    lateinit var prefs: Prefs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply)

        prefs = Prefs(this)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        dob?.setOnClickListener {
            val dpd = DatePickerDialog(this, { view, mYear, mMonth, mDay ->
                dob.text = "$mDay/$mMonth/$mYear"
            }, year, month, day)
            dpd.show()
        }

        backBtn.setOnClickListener {
            finish()
        }

       next_btn.setOnClickListener {
           val idRadio = gender_radio.checkedRadioButtonId
            when {
                first_name.text.toString() == "" -> Toast.makeText(this, "Please enter First Name", Toast.LENGTH_SHORT).show()
                last_name.text.toString() == "" -> Toast.makeText(this, "Please enter Last Name", Toast.LENGTH_SHORT).show()
                email.text.toString() == "" -> Toast.makeText(this,"Please enter email", Toast.LENGTH_SHORT).show()
                dob.text.toString() == ""-> Toast.makeText(this,"Please select date", Toast.LENGTH_SHORT).show()
                idRadio == -1->Toast.makeText(this,"Please select gender", Toast.LENGTH_SHORT).show()
                else -> {
                    val progressDialog = ProgressDialog(this)
                    progressDialog.setMessage("Loading...")
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.setCancelable(false)
                    progressDialog.show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        progressDialog.dismiss()
                        val intent = Intent(this, EducationDetailActivity::class.java)
                        startActivity(intent)
                        finish()
                    },1000)

                }
            }
        }

    }
}