package com.example.htmlparsingwithjsoup.extensions

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.htmlparsingwithjsoup.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ViewModel.handleLaunch(
    execution: suspend CoroutineScope.() -> Unit,
    error: suspend CoroutineScope.(Throwable) -> Unit,
    finallyBlock: (suspend CoroutineScope.() -> Unit)? = null
) {
    this.viewModelScope.launch {
        launch {
            try {
                execution()
            } catch (e: Throwable) {
                error(e)
            } finally {
                finallyBlock?.invoke(this)
            }
        }
    }
}

/*
 * Creates a dialog to block ui for async operations
 */
fun Activity.createProgressDialog(isCancelable: Boolean = false): Dialog = Dialog(this).apply {
    setCancelable(isCancelable)
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    setContentView(R.layout.view_progress)
}
