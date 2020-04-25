package com.app.lizhilives.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import com.app.lizhilives.R
import kotlinx.android.synthetic.main.dialog_invite_code.view.*

class InviteCodeDialog(mContext: Context, code: String) : Dialog(mContext, R.style.dialog) {
    private val mContext = mContext
    private val code = code
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val v = layoutInflater.inflate(R.layout.dialog_invite_code, null, false)
        setContentView(v)

        val lp = window.attributes
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
        window.attributes = lp

        v.mContent.text = "邀请好友扫码下载兼职咸鱼\n我的邀请码【${code}】"

        v.mClose.setOnClickListener {
            dismiss()
        }
    }
}