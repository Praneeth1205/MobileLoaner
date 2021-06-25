package com.loaner.mobile.payment

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.loaner.mobile.R
import java.util.HashMap

class PaymentsUIActivity : AppCompatActivity() {

    private var singleton: Singleton? = null


    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payments_ui)

        singleton = Singleton.getInstance()

        val intent = intent
        val payment: Payment = intent.getSerializableExtra("payment") as com.loaner.mobile.model.Payment

        val payUri = Uri.Builder()

        payUri.scheme("upi").authority("pay")
        payUri.appendQueryParameter("pa", payment.vpa)
        payUri.appendQueryParameter("pn", payment.name)
        payUri.appendQueryParameter("tid", payment.txnId)

        if (payment.payeeMerchantCode != null) {
            payUri.appendQueryParameter("mc", payment.payeeMerchantCode)
        }

        payUri.appendQueryParameter("tr", payment.txnRefId)
        payUri.appendQueryParameter("tn", payment.description)
        payUri.appendQueryParameter("am", payment.amount)
        payUri.appendQueryParameter("cu", payment.currency)

        val uri = payUri.build()

        val paymentIntent = Intent(Intent.ACTION_VIEW)
        paymentIntent.data = uri

        if (payment.defaultPackage != null) {
            paymentIntent.setPackage(payment.defaultPackage)
        }

        if (paymentIntent.resolveActivity(packageManager) != null) {
            val intentList = packageManager
                .queryIntentActivities(paymentIntent, 0)
            showApps(intentList, paymentIntent)
            //checkForIndividualApps()
        } else {
//            Toast.makeText(
//                this, "No UPI app found! Please Install to Proceed!",
//                Toast.LENGTH_SHORT
//            ).show()

            callbackOnAppNotFound()
//            if(noUPIcallback!=null){
//                noUPIcallback?.onNoUPIAppFoundCallback()
//                finish()
//            }
            checkForIndividualApps()
        }
    }

    private fun checkForIndividualApps() {
        val GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user"
        val GOOGLE_PAY_REQUEST_CODE = 123
        var tid = ""+System.currentTimeMillis()

        try {
            val uri = Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", getString(R.string.vpa))
                .appendQueryParameter("pn", getString(R.string.payee))
                .appendQueryParameter("tr", tid)
                .appendQueryParameter("tn", "Cashfield")
                .appendQueryParameter("am", ""+ Constant.userOrderAmount+".00")
                .appendQueryParameter("cu", "INR")
                .build()
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = uri
            intent.setPackage(GOOGLE_PAY_PACKAGE_NAME)
            startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE)
        }catch (exc : java.lang.Exception){

        }

    }

    private fun showApps(
        appsList: List<ResolveInfo>,
        intent: Intent
    ) {
        val onCancelListener =
            View.OnClickListener {
                callbackTransactionCancelled()
                finish()
            }
        val appsBottomSheet = BottomsheetFragment(appsList, intent, onCancelListener)
        appsBottomSheet.show(supportFragmentManager, "Pay Using")
    }

    @SuppressLint("DefaultLocale")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PAYMENT_REQUEST) {
            if (data != null) {
                val response = data.getStringExtra("response")
                if (response == null) {
                    callbackTransactionCancelled()
                    Log.d(TAG, "Response is null")
                } else {
                    val transactionDetails: TransactionDetails = getTransactionDetails(response)
                    callbackTransactionComplete(transactionDetails)
                    try {
                        when {
                            transactionDetails.status?.toLowerCase().equals("success") -> {
                                callbackTransactionSuccess()
                            }
                            transactionDetails.status?.toLowerCase().equals("submitted") -> {
                                callbackTransactionSubmitted()
                            }
                            else -> {
                                callbackTransactionFailed()
                            }
                        }
                    } catch (e: Exception) {
                        callbackTransactionCancelled()
                        callbackTransactionFailed()
                    }
                }
            } else {
                Log.e(TAG, "Intent Data is null. User cancelled")
                callbackTransactionCancelled()
            }
            finish()
        }
    }

    private fun getQueryString(url: String): Map<String, String> {
        val params = url.split("&").toTypedArray()
        val map: MutableMap<String, String> =
            HashMap()
        for (param in params) {
            val name = param.split("=").toTypedArray()[0]
            val value = param.split("=").toTypedArray()[1]
            map[name] = value
        }
        return map
    }

    private fun getTransactionDetails(response: String): TransactionDetails {
        val map = getQueryString(response)
        val transactionId = map["txnId"]
        val responseCode = map["responseCode"]
        val approvalRefNo = map["ApprovalRefNo"]
        val status = map["Status"]
        val transactionRefId = map["txnRef"]
        return TransactionDetails(
            transactionId,
            responseCode,
            approvalRefNo,
            status,
            transactionRefId
        )
    }

    private fun isListenerRegistered(): Boolean {
        return Singleton.getInstance()!!.isListenerRegistered
    }

    private fun callbackOnAppNotFound() {
        Log.e(TAG, "No UPI app found on device.")
        if (isListenerRegistered()) {
            singleton!!.getListener().onAppNotFound()
        }
        //finish()
    }

    private fun callbackTransactionSuccess() {
        if (isListenerRegistered()) {
            singleton!!.getListener().onTransactionSuccess()
        }
    }

    private fun callbackTransactionSubmitted() {
        if (isListenerRegistered()) {
            singleton!!.getListener().onTransactionSubmitted()
        }
    }

    private fun callbackTransactionFailed() {
        if (isListenerRegistered()) {
            singleton!!.getListener().onTransactionFailed()
        }
    }

    private fun callbackTransactionCancelled() {
        if (isListenerRegistered()) {
            singleton!!.getListener().onTransactionCancelled()
        }
    }

    private fun callbackTransactionComplete(transactionDetails: TransactionDetails) {
        if (isListenerRegistered()) {
            singleton!!.getListener().onTransactionCompleted(transactionDetails)
        }
    }

    companion object {
        const val TAG = "PaymentsUIActivity"
        const val PAYMENT_REQUEST: Int = 100
        const val EXTRA_KEY_PAYMENT = "payment"
    }
}