package com.app.lizhilives.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 全部展示,不需要滑动
 * 
 * @author Lee
 */
public class NoScrollGridView extends GridView {

	public NoScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int spec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, spec);
	}
}
