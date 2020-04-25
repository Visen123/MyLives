package com.app.lizhilives.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.app.lizhilives.utils.ShadowUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.app.lizhilives.R;
import com.app.lizhilives.model.MeiWenModel;
import com.app.lizhilives.utils.ImageLoader;

import java.util.List;


/**
 * Created by Administrator on 2016/11/16.
 */

public class MWitemAdapter extends BaseQuickAdapter<MeiWenModel.ListBean, BaseViewHolder> {

    public MWitemAdapter(@Nullable List<MeiWenModel.ListBean> data) {
        super(R.layout.item_category_list, data);
    }


    @Override
    protected void convert(BaseViewHolder holder, MeiWenModel.ListBean listbean) {
        holder.setText(R.id.tv_cy_title, listbean.getTitle());
        holder.setText(R.id.tv_cy_time,listbean.getSht() );
        ImageView img = holder.getView(R.id.item_img);
        ImageLoader.LoaderNetHead(mContext, listbean.getHeadurl(), img);
        View v = holder.getView(R.id.relative_root);
        ShadowUtils.setItemShadow(mContext, v,15);
    }

}
