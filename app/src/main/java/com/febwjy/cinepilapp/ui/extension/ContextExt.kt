package com.febwjy.cinepilapp.ui.extension

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.febwjy.cinepilapp.R

/**
 * Created by Febby Wijaya on 20/05/22.
 */
fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}