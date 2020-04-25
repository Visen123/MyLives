package com.app.lizhilives.mvp;

import com.app.lizhilives.model.MeiWenModel;

/**
 * 列表回调接口数据
 */
public interface WenZhanView extends IBaseView {

    /**
     * 获取数据列表成功，回调接口提供给View使用
     * @param listbean
     */
    void getSuccess(MeiWenModel listbean);
}
