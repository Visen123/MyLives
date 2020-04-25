package com.app.lizhilives.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.app.lizhilives.R

abstract class BaseDialog(mContext: Context) : Dialog(mContext, R.style.dialog2) {
    val mContext = mContext
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val v = layoutInflater.inflate(getLayoutRes(), null, false)
        setContentView(v)
        val lp = window.attributes
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
        window.attributes = lp
        init(v)
    }

    abstract fun getLayoutRes(): Int

    abstract fun init(v: View)
}