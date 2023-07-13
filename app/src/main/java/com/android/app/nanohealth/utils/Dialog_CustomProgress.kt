package com.android.app.nanohealth.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.android.app.nanohealth.R
import com.android.app.nanohealth.api.ApiManager

class Dialog_CustomProgress(a: Activity) : Dialog(a) {
    private var ll_progress_dialog: LinearLayout? = null
    private val activity: Activity
    private var progressBar2: ProgressBar? = null

    init {
        activity = a
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)
        setContentView(R.layout.custom_progress_dialog_style)
        initUI()
    }

    private fun initUI() {
        ll_progress_dialog = findViewById<View>(R.id.ll_progress_dialog) as LinearLayout
        progressBar2 = findViewById<View>(R.id.progressBar2) as ProgressBar
        progressBar2!!.getIndeterminateDrawable().setColorFilter(
            activity.getResources().getColor(R.color.app_color),
            PorterDuff.Mode.MULTIPLY
        )
    }
}