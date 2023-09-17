package com.febwjy.cinepilapp.ui.extension

import android.content.Context
import android.widget.Toast

/**
 * Created by Febby Wijaya on 16/09/23.
 */
fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}