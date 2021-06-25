package com.loaner.mobile.payment

interface PaymentStatusListener {
    fun onTransactionCompleted(transactionDetails: TransactionDetails?)
    fun onTransactionSuccess()
    fun onTransactionSubmitted()
    fun onTransactionFailed()
    fun onTransactionCancelled()
    fun onAppNotFound()
}