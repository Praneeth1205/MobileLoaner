package com.loaner.mobile

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.loaner.mobile.Utils.showLongToast
import com.loaner.mobile.store.Prefs
import com.shreyaspatil.easyupipayment.EasyUpiPayment
import com.shreyaspatil.easyupipayment.listener.PaymentStatusListener
import com.shreyaspatil.easyupipayment.model.PaymentApp
import com.shreyaspatil.easyupipayment.model.TransactionDetails
import com.shreyaspatil.easyupipayment.model.TransactionStatus
import kotlinx.android.synthetic.main.activity_education_detail.*
import kotlinx.android.synthetic.main.activity_loan_details.*

class LoanDetailsActivity : AppCompatActivity(), View.OnClickListener, PaymentStatusListener {

    private var selectAmount = 10000
    private var selectedDays = 120
    private var defaultInterest = 3.4
    private var default_sd = 250

    private var sd_1000 = 65
    private var sd_5000 = 180
    private var sd_10000 = 250
    private var sd_20000 = 330
    private var sd_30000 = 360
    private var sd_50000 = 410
    private var sd_80000 = 480
    private var sd_100000 = 550

    lateinit var easyUpiPayment : EasyUpiPayment
    lateinit var prefs: Prefs
    lateinit var transactionId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_details)

        apply_btn?.setOnClickListener {
            makePaymentAndProceed()
        }

        b1000?.setOnClickListener(this)
        b5000?.setOnClickListener(this)
        b10000?.setOnClickListener(this)
        b20000?.setOnClickListener(this)
        b30000?.setOnClickListener(this)
        b50000?.setOnClickListener(this)
        b80000?.setOnClickListener(this)
        b100000?.setOnClickListener(this)
        b90?.setOnClickListener(this)
        b120?.setOnClickListener(this)
        b180?.setOnClickListener(this)
        b240?.setOnClickListener(this)
        b300?.setOnClickListener(this)
        b360?.setOnClickListener(this)

        backBtn?.setOnClickListener {
            finish()
        }

    }

    private fun makePaymentAndProceed() {
        val paymentApp = PaymentApp.ALL
        //default_sd = 2

        try {
            // START PAYMENT INITIALIZATION
            easyUpiPayment = EasyUpiPayment(this) {
                this.paymentApp = paymentApp
                this.payeeVpa = "" + Constant.UPI_ID
                this.payeeName = "" + getString(R.string.app_name)
                this.transactionId = "" + System.currentTimeMillis()
                this.transactionRefId = "" + System.currentTimeMillis()
                this.payeeMerchantCode = ""
                this.description = "Services"
                this.amount = "" + default_sd + ".00"
            }
            // END INITIALIZATION

            // Register Listener for Events
            easyUpiPayment.setPaymentStatusListener(this)

            // Start payment / transaction
            easyUpiPayment.startPayment()
        } catch (e: Exception) {
            e.printStackTrace()
             showLongToast("" + e.printStackTrace())
            //setUPIErrorInfo(e.printStackTrace().toString())
        }
    }

    private fun resetProperties(mainBtn: Button) {
        mainBtn.setBackgroundResource(R.drawable.loan_amount_background)
        mainBtn.setTextColor(resources.getColor(R.color.black))
    }

    private fun resetDays(btn: Button) {
        resetProperties(b90)
        resetProperties(b120)
        resetProperties(b180)
        resetProperties(b240)
        resetProperties(b300)
        resetProperties(b360)

        btn.setBackgroundResource(R.drawable.button_gradient)
        btn.setTextColor(resources.getColor(R.color.white))
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.b1000 -> {
                resetSelectedMoney(b1000)
                selectAmount = 1000
                default_sd = sd_1000
                calculationsLoan()
            }
            R.id.b5000 -> {
                resetSelectedMoney(b5000)
                selectAmount = 5000
                default_sd = sd_5000
                calculationsLoan()
            }
            R.id.b10000 -> {
                resetSelectedMoney(b10000)
                selectAmount = 10000
                default_sd = sd_10000
                calculationsLoan()
            }
            R.id.b20000 -> {
                resetSelectedMoney(b20000)
                selectAmount = 20000
                default_sd = sd_20000
                calculationsLoan()
            }
            R.id.b30000 -> {
                resetSelectedMoney(b30000)
                selectAmount = 30000
                default_sd = sd_30000
                calculationsLoan()
            }
            R.id.b50000 -> {
                resetSelectedMoney(b50000)
                selectAmount = 50000
                default_sd = sd_50000
                calculationsLoan()
            }
            R.id.b80000 -> {
                resetSelectedMoney(b80000)
                selectAmount = 80000
                default_sd = sd_80000
                calculationsLoan()
            }
            R.id.b100000 -> {
                resetSelectedMoney(b100000)
                selectAmount = 100000
                default_sd = sd_100000
                calculationsLoan()
            }
            R.id.b90 -> {
                resetDays(b90)
                selectedDays = 90
                calculationsLoan()
            }
            R.id.b120 -> {
                resetDays(b120)
                selectedDays = 120
                calculationsLoan()
            }

            R.id.b180 -> {
                resetDays(b180)
                selectedDays = 180
                calculationsLoan()
            }
            R.id.b240 -> {
                resetDays(b240)
                selectedDays = 240
                calculationsLoan()
            }
            R.id.b300 -> {
                resetDays(b300)
                selectedDays = 300
                calculationsLoan()
            }
            R.id.b360 -> {
                resetDays(b360)
                selectedDays = 360
                calculationsLoan()
            }

        }
    }


    private fun resetSelectedMoney(selectBtn: Button) {
        resetProperties(b1000)
        resetProperties(b5000)
        resetProperties(b10000)
        resetProperties(b20000)
        resetProperties(b30000)
        resetProperties(b50000)
        resetProperties(b80000)
        resetProperties(b100000)

        selectBtn.setBackgroundResource(R.drawable.button_gradient)
        selectBtn.setTextColor(resources.getColor(R.color.white))

    }

    private fun calculationsLoan() {
        disbursal_amount.text = "Rs.$selectAmount"
        tenure_days.text = "$selectedDays Days"
        security_amount.text = "Rs.$default_sd"
        val p = ((selectAmount) * (selectedDays / 30) * defaultInterest) / 100
        interest_amount.text = "Rs.$p"
        val repayment = selectAmount + p
        repayment_amount.text = "Rs.$repayment"
        val total = repayment - default_sd
        total_amount.text = "Rs.$total"

    }

    override fun onTransactionCancelled() {
        Utils.showShortToast("Transaction Cancelled!")
        onTransactionFailed()
    }

    override fun onTransactionCompleted(transactionDetails: TransactionDetails) {
        when (transactionDetails.transactionStatus) {
            TransactionStatus.SUCCESS -> onTransactionSuccess()
            TransactionStatus.FAILURE -> onTransactionFailed()
            TransactionStatus.SUBMITTED -> onTransactionSubmitted()
        }
     }

    private fun onTransactionSubmitted() {
        Utils.showShortToast("Transaction Failed!")
        showPaymentStatus(false)
    }

    private fun onTransactionFailed() {
        Utils.showShortToast("Transaction Failed!")
        showPaymentStatus(false)
    }

    private fun onTransactionSuccess() {
         if (prefs.isOnceDone!!){
             Utils.showShortToast("Transaction Success!")
             showPaymentStatus(true)
         }else {
             prefs.isOnceDone = true
             Utils.showShortToast("Transaction Failed!")
             showPaymentStatus(false)
         }
    }

    private fun showPaymentStatus(status: Boolean) {
        transactionId = "" + System.currentTimeMillis()
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.payment_status, null)
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        var btnDone = dialogView.findViewById(R.id.btnDone) as Button
        var tvTitle = dialogView.findViewById(R.id.tvTitle) as MaterialTextView
        var tvMessage = dialogView.findViewById(R.id.tvMessage) as MaterialTextView
        var image = dialogView.findViewById(R.id.image) as ImageView

        if (status) {

        } else {
            btnDone.text = "Try again"
            tvTitle.text = "Payment Failed for TID" + transactionId
            tvMessage.text =
                "Payment Failed.... Don't worry , your money is safe!. In case your money has already debited from your account, It will credit to your bank account within 2-3 business days."
            image.setImageResource(R.drawable.ic_sad)
        }

        btnDone.setOnClickListener {
            alertDialog.dismiss()
            if (!status) {
                makePaymentAndProceed()
            }
        }

    }

}