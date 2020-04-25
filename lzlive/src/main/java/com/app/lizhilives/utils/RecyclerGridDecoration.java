package com.app.lizhilives.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;



/**
 * Created by huangcaiqing on 2018/10/12.
 */

public class RecyclerGridDecoration extends RecyclerView.ItemDecoration {
    private int margin;
    private int count;
    public static float dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public RecyclerGridDecoration(Context context) {
        margin = (int) dp2px(context, 15);
        count = 2;
    }

    public RecyclerGridDecoration(Context context, int count) {
        margin = (int) dp2px(context, 15);
        this.count = count;
    }

    public RecyclerGridDecoration(Context context, int margin, int count) {
        this.margin = (int) dp2px(context, margin);
        this.count = count;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildLayoutPosition(view) % count == 0) {
            outRect.set(margin, 0, margin / 2, 0);
        } else if (parent.getChildLayoutPosition(view) % count == count - 1) {
            outRect.set(margin / 2, 0, margin, 0);
        } else {
            outRect.set(margin / 2, 0, margin / 2, 0);
        }
    }
}