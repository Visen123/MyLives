package com.app.lizhilives.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;

import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.base.CrazyShadowDirection;


/**
 * Created by Administrator on 2018/7/12 0012.
 */

public class ShadowUtils {
    public static void setItemShadow(Context mContext, View v, int padding){
        new CrazyShadow.Builder()
                .setContext(mContext)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(dp2px(mContext, 5))
                .setCorner(dp2px(mContext, 4))
                .setBackground(Color.parseColor("#ffffff"))
                .setImpl(CrazyShadow.IMPL_DRAW)
                .setBaseShadowColor(Color.parseColor("#80cccccc"))
                .action(v);
        int v1 = (int)dp2px(mContext, padding);
        v.setPadding(v1,v1,v1,v1);
    }

    public static void setViewShadow(Context mContext, View v){
        new CrazyShadow.Builder()
                .setContext(mContext)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(dp2px(mContext, 5))
                .setCorner(dp2px(mContext, 5))
                .setBackground(Color.parseColor("#ffffff"))
                .setImpl(CrazyShadow.IMPL_DRAW)
                .setBaseShadowColor(Color.parseColor("#80cccccc"))
                .action(v);
    }

    public static void setViewShadow(Context mContext, View v, int radius){
        new CrazyShadow.Builder()
                .setContext(mContext)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(dp2px(mContext, 5))
                .setCorner(dp2px(mContext, radius))
                .setBackground(Color.parseColor("#ffffff"))
                .setImpl(CrazyShadow.IMPL_DRAW)
                .setBaseShadowColor(Color.parseColor("#80cccccc"))
                .action(v);
    }

    public static void setViewShadow2(Context mContext, View v){
        new CrazyShadow.Builder()
                .setContext(mContext)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(dp2px(mContext, 5))
                .setCorner(dp2px(mContext, 3))
                .setBackground(Color.parseColor("#FBE3B7"))
                .setImpl(CrazyShadow.IMPL_DRAW)
                .setBaseShadowColor(Color.parseColor("#80000000"))
                .action(v);
    }

    public static void setViewShadow3(Context mContext, View v){
        new CrazyShadow.Builder()
                .setContext(mContext)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(dp2px(mContext, 3))
                .setCorner(dp2px(mContext, 1))
                .setBackground(Color.parseColor("#ffffff"))
                .setImpl(CrazyShadow.IMPL_DRAW)
                .setBaseShadowColor(Color.parseColor("#dddddd"))
                .action(v);
    }

    public static void setViewShadow(Context mContext, View v, String color){
        new CrazyShadow.Builder()
                .setContext(mContext)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(dp2px(mContext, 5))
                .setCorner(dp2px(mContext, 5))
                .setBackground(Color.parseColor(color))
                .setImpl(CrazyShadow.IMPL_DRAW)
                .setBaseShadowColor(Color.parseColor(color))
                .action(v);
    }

    public static void setItemShadow(Context mContext, View v, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom){
        new CrazyShadow.Builder()
                .setContext(mContext)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(dp2px(mContext, 5))
                .setCorner(dp2px(mContext, 4))
                .setBackground(Color.parseColor("#ffffff"))
                .setImpl(CrazyShadow.IMPL_DRAW)
                .setBaseShadowColor(Color.parseColor("#cccccc"))
                .action(v);
        int v1 = (int)dp2px(mContext, paddingLeft);
        int v2 = (int)dp2px(mContext, paddingTop);
        int v3 = (int)dp2px(mContext, paddingRight);
        int v4 = (int)dp2px(mContext, paddingBottom);
        v.setPadding(v1,v2,v3,v4);
    }

    public static float dp2px(Context context, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
