package com.app.lizhilives.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.app.lizhilives.R;
import com.app.lizhilives.utils.MyProgressDialog;
import com.app.lizhilives.utils.ToastUtil;


public class MyDialog extends DialogFragment implements OnClickListener {

    String text = "励志生活下载地址";
    String url = "http://www.baidu.com";
    private UMWeb mUMWeb;
    private ImageView qrImgImageView;
    private View view;
    private Activity context;
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            MyProgressDialog.dialogShow(context);
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            MyProgressDialog.dialogHide();
            if (context != null && !context.isFinishing()) {
                ToastUtil.toastShow(context, platform + " 分享成功啦");
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            MyProgressDialog.dialogHide();
            if (context != null && !context.isFinishing())
                ToastUtil.toastShow(context, platform + " 分享失敗啦");
            Log.e("分享失败=","="+t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            MyProgressDialog.dialogHide();
            if (context != null && !context.isFinishing())
                ToastUtil.toastShow(context, platform + " 分享取消了");
        }
    };

    public static MyDialog newInstance(Activity context) {
        Bundle args = new Bundle();
        MyDialog fragment = new MyDialog();
        fragment.context = context;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        view = inflater.inflate(R.layout.invite_friend, container, false);
        initUI();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mUMWeb = shareText();
            }
        }).start();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data);
    }


    private UMWeb shareText() {
        UMImage image = new UMImage(context, R.drawable.umeng_socialize_qq);
        UMWeb web = new UMWeb(this.url);
        String string = context
                .getString(R.string.app_name);
        web.setTitle(string);//标题
        web.setThumb(image);  //缩略图
        web.setDescription(this.text);//描述
        return web;
    }

    private void initUI() {
        view.findViewById(R.id.dialog_liner_weixin).setOnClickListener(this);
        view.findViewById(R.id.dialog_liner_dx).setOnClickListener(this);
        view.findViewById(R.id.dialog_liner_pengyouquan).setOnClickListener(this);
        view.findViewById(R.id.dialog_liner_qq).setOnClickListener(this);
        qrImgImageView = (ImageView) view.findViewById(R.id.inviteTwoCode);
        qrImgImageView.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_liner_weixin:
                share(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.dialog_liner_pengyouquan:
                share(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case R.id.dialog_liner_qq:
                share(SHARE_MEDIA.QQ);
                break;
            case R.id.dialog_liner_dx:
                share(SHARE_MEDIA.QZONE);
                break;
            default:
                break;
        }
    }

    private void share(SHARE_MEDIA type) {
        if (mUMWeb == null) {
            ToastUtil.toastShow(context, "配寘生成中，請稍後...");
            return;
        }
        new ShareAction(context)
                .setPlatform(type)
                .withMedia(mUMWeb)
                .setCallback(umShareListener)
                .share();
        return;
    }





}
