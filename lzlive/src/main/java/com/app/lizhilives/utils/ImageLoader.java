package com.app.lizhilives.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.app.lizhilives.R;

/**
 * Created by Administrator on 2016/11/12.
 */

public class ImageLoader {
    /**
     * 网络加载图片，含头像默认图片
     *
     * @param mContext
     * @param url
     * @param view
     */
    public static void LoaderNetHead(Context mContext, String url, ImageView view) {
        Glide.with(mContext).load(url).transform(new GlideCircleTransform(mContext)).placeholder(R.mipmap.error).error(R.mipmap.error)
                .into(view);
    }

    /**
     * 本地加载图片成圆形
     *
     * @param mContext
     * @param b
     * @param view
     */
    public static void LoaderCircle(Context mContext, int b, ImageView view) {
        Glide.with(mContext).load(b).transform(new GlideCircleTransform(mContext))
                .into(view);
    }

    /**
     * 网络加载图片，含头像默认图片
     *
     * @param mContext
     * @param b
     * @param view
     */
    public static void LoaderRound(Context mContext, String b, ImageView view) {
        Glide.with(mContext).load(b).transform(new GlideRoundTransform(mContext)).placeholder(R.mipmap.error).error(R.mipmap.error)
                .into(view);
    }

    /**
     * 网络加载图片，含默认图片
     *
     * @param mContext
     * @param url
     * @param view
     */
    public static void LoaderNet(Context mContext, String url, ImageView view, int width, int height) {
        Glide.with(mContext).load(url).override(width, height).placeholder(R.mipmap.error).error(R.mipmap.error).into(view);
    }

    /**
     * 网络加载图片，含默认图片
     *
     * @param mContext
     * @param url
     * @param view
     */
    public static void LoaderNet2(Context mContext, String url, ImageView view, int width, int height) {
        Glide.with(mContext).load(url).override(width, height).placeholder(R.mipmap.error).error(R.mipmap.error).into(view);
    }

    /**
     * 网络加载图片，含默认图片
     * .placeholder(R.mipmap.ico_loading)
     *
     * @param url
     * @param view
     */
    public static void LoaderNet(Context mContext, String url, ImageView view) {
        Glide.with(mContext).load(url).placeholder(R.mipmap.error).error(R.mipmap.error).crossFade().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(view);
    }

    /**
     * 网络加载图片，含默认图片
     *
     * @param mContext
     * @param url
     * @param view
     */
    public static void LoaderNet2(Context mContext, String url, ImageView view) {
        Glide.with(mContext).load(url).placeholder(R.mipmap.error).error(R.mipmap.error).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(view);
    }
}
