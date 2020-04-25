package com.app.lizhilives.mvp;

import com.app.lizhilives.api.BaseObserver;
import com.app.lizhilives.model.MeiWenModel;

/**
 * 负责逻辑的处理（处理view和model的联系）
 */
public class WenZhanPresenter extends BasePresenter {
    private WenZhanView baseView;
    private WenZhanModel model;
    public WenZhanPresenter(IBaseView baseView) {
        this.baseView= (WenZhanView) baseView;
        attachView(this.baseView);
        model=new WenZhanModel(baseView.getContext());
    }


    public void loadListData(int page){
        baseView.showLoading();
        model.getInfro(page, new BaseObserver<MeiWenModel>(baseView.getContext(),null) {
            @Override
            protected void onHandleSuccess(MeiWenModel meiWenModel) {
                baseView.hideLoading();
                baseView.getSuccess(meiWenModel);
                baseView.showToast("数据请求成功");

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                baseView.hideLoading();
                baseView.showToast("数据请求失败");
            }
        });

    }

}
