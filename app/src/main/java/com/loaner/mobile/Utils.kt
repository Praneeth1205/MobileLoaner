package com.loaner.mobile

import android.widget.Toast

object Utils {
    fun showLongToast(message: String) {
        Toast.makeText(MobileloanerApp.getContext(), message, Toast.LENGTH_LONG).show()
    }

    fun showShortToast(message: String) {
        Toast.makeText(MobileloanerApp.getContext(), message, Toast.LENGTH_SHORT).show()
    }
}