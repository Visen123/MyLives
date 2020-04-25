package com.app.lizhilives.api;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * Created by Administrator on 2018/7/9 0009.
 */

@SuppressLint("RestrictedApi")
public class RefreshHeaders extends LinearLayout implements RefreshHeader {
    private LottieAnimationView view;
    private TextView tv;

    public RefreshHeaders(Context context) {
        this(context, null, 0);
    }

    public RefreshHeaders(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshHeaders(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);

        tv = new TextView(context);
        LayoutParams params1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params1.bottomMargin = -20;
        params1.rightMargin = 6;
        tv.setText("松开刷新");
        tv.setTextSize(11);
        tv.setVisibility(View.INVISIBLE);
        this.addView(tv, params1);

        view = new LottieAnimationView(context);
        view.setAnimation("antui_refresh-w96-h96-bala-w96-h96.json");
        view.loop(true);
        LayoutParams params = new LayoutParams(100, 100);
        params.rightMargin = 20;
        params.bottomMargin = 10;
        this.addView(view, params);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onPulling(float percent, int offset, int height, int extendHeight) {
        view.setMinAndMaxProgress(0f, 1f);
        if (percent < 0) {
            percent = 0;
            tv.setVisibility(View.INVISIBLE);
        } else if (percent > 1) {
            percent = 1;
            tv.setVisibility(View.VISIBLE);
        }
        float flag = (0 + (percent/10*2));
        view.setProgress(flag);
    }

    @Override
    public void onReleasing(float percent, int offset, int height, int extendHeight) {

    }


    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        view.cancelAnimation();
        return 0;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState) {
            case PullDownToRefresh:
                view.setProgress(0);
                tv.setVisibility(View.INVISIBLE);
                break;
            case Refreshing:
                tv.setVisibility(View.INVISIBLE);
                view.setMinAndMaxProgress(0.2f, 1f);
                view.playAnimation();
                break;
            case ReleaseToRefresh:

                break;
        }
    }

    public void finishRefresh(final SmartRefreshLayout refresh){
        view.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (((float)animation.getAnimatedValue()) >= 0.9){
                    refresh.finishRefresh();
                    view.cancelAnimation();
                }
            }
        });
    }
}
