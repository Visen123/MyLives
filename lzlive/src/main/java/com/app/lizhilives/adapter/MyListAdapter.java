package com.app.lizhilives.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.lizhilives.R;
import com.app.lizhilives.model.MeiWenModel;
import com.app.lizhilives.utils.ImageLoader;

import java.util.List;

public class MyListAdapter extends BaseAdapter {

    private Context mContext;
    private List<MeiWenModel.ListBean> mList;

    public MyListAdapter(Context mContext, List<MeiWenModel.ListBean> data) {

        this.mContext = mContext;
        mList = data;

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup viewGroup) {
        ViewHolder holder ;
        if (convertview == null) {
            holder = new ViewHolder();
            convertview = LayoutInflater.from(mContext).inflate(R.layout.item_mylistadpter, null);
            holder.mTvTitle = convertview.findViewById(R.id.tv_cy_title);
            holder.mTvTime = convertview.findViewById(R.id.tv_cy_time);
            holder.mImg = convertview.findViewById(R.id.item_img);
            convertview.setTag(holder);
        } else {
            holder = (ViewHolder) convertview.getTag();
        }
        if (mList.size() > 0) {
            MeiWenModel.ListBean listBean = mList.get(position);
            holder.mTvTitle.setText(listBean.getTitle());
            holder.mTvTime.setText(listBean.getSht());
            ImageLoader.LoaderNetHead(mContext, listBean.getHeadurl(), holder.mImg);
        }

        return convertview;
    }

    class ViewHolder {
        private TextView mTvTitle;
        private TextView mTvTime;
        private ImageView mImg;
    }
}
