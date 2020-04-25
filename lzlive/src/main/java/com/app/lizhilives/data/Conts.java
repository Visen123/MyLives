package com.app.lizhilives.data;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.app.lizhilives.utils.AsyncHttpClientUtils;

/**
 * @author Administrator
 * @date 2019/6/19 11:38
 */
public class Conts {
    public static final String[] TITLES={"推荐","散文","感悟","励志","爱情"};
    public static final String PAGEINDEX = "PAGEINDEX";
    public static final String ID = "ID";
    public static final String TYPE = "TYPE";
    public static final String DOWLOAD_URL = "励志生活下载地址www.baidu.com";
    public static final String MWURL = "http://www.duwenz.com/txtjson/classgroup/cgmw_all_editortj_";
    public static final String SWURL = "http://www.duwenz.com/txtjson/classgroup/cgmw_sanwen_editortj_";
    public static final String GWURL = "http://www.duwenz.com/txtjson/classgroup/cgmw_ganwu_editortj_";
    public static final String LZURL = "http://www.duwenz.com/txtjson/classgroup/cgmw_lizhi_editortj_";
    public static final String AQURL = "http://www.duwenz.com/txtjson/classgroup/cgmw_aiqing_editortj_";
    public static final String DETAIL_URL = "http://www.duwenz.com/txtjson/wzview/1/";
    public static final String WEB_WenXS="http://www.duwenz.com/wenxs/";
    public static final String[] URL={MWURL,SWURL,GWURL,LZURL,AQURL};

    public static  void getDetail_Data( String id, AsyncHttpResponseHandler a){
        Log.e("接口=","="+DETAIL_URL+id+".txt");
        AsyncHttpClientUtils.getInstance().get(DETAIL_URL+id+".txt",a);


    }
}
