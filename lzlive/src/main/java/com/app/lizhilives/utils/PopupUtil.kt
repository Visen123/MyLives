package com.app.lizhilives.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import com.app.lizhilives.R
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
import kotlinx.android.synthetic.main.popup_share_menu.view.*
import org.jetbrains.anko.layoutInflater

object PopupUtil {
   private val URL="http://www.baidu.com"
    fun showShareMenu(mContext: Context, msg: String) {
        val v = mContext.layoutInflater.inflate(R.layout.popup_share_menu, null, false)
        val mPopup = PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        mPopup.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mPopup.animationStyle = R.style.feedback_popup_anim
        mPopup.isOutsideTouchable = true
        mPopup.isTouchable = true

        v.mShareToWx.setOnClickListener {
            share(mContext, URL,SHARE_MEDIA.WEIXIN)
            //mPopup.dismiss()
        }

        v.mShareToWxCircle.setOnClickListener {
            share(mContext, URL,SHARE_MEDIA.WEIXIN_CIRCLE)
           // mPopup.dismiss()
        }

        v.mShareToQq.setOnClickListener {
            share(mContext, URL,SHARE_MEDIA.QQ)
        }

        v.mC2C.setOnClickListener {
            InviteCodeDialog(mContext, "1").show()
        }

        v.mShareCopyUrl.setOnClickListener {
        }

        v.mShareCancel.setOnClickListener {
            mPopup.dismiss()
        }
        mPopup.setOnDismissListener {
            setActivityAlpha(mContext as Activity, 1f)
        }
        mPopup.showAtLocation((mContext as Activity).window.decorView, Gravity.BOTTOM, 0, 0)
        setActivityAlpha(mContext as Activity, 0.5f)
    }

    private fun share(mContext: Context, url: String,mshare_media:SHARE_MEDIA) {
        var image = UMImage(mContext, R.mipmap.ic_launcher)
        val umWeb = UMWeb(url)
        umWeb.setThumb(image)
        umWeb.title = "励志生活下载地址"
        umWeb.description = "快来下载吧~"
        ShareAction(mContext as Activity?)
                .setPlatform(mshare_media)
                .setCallback(object : UMShareListener {
                    override fun onStart(share_media: SHARE_MEDIA) {

                    }

                    override fun onResult(share_media: SHARE_MEDIA) {

                    }

                    override fun onError(share_media: SHARE_MEDIA, throwable: Throwable) {
                        Toast.makeText(mContext, "分享失败", Toast.LENGTH_SHORT).show()
                    }

                    override fun onCancel(share_media: SHARE_MEDIA) {
                        Toast.makeText(mContext, "取消分享", Toast.LENGTH_SHORT).show()
                    }
                })
                .withMedia(umWeb)
                .share()
    }

    fun setActivityAlpha(a: Activity, alpha: Float) {
        val lp = a.window.attributes
        lp.alpha = alpha
        a.window.attributes = lp
    }
}