package com.app.lizhilives.api

import android.text.TextUtils
import com.app.lizhilives.utils.runRxLambda
import org.json.JSONObject

object NetHelper{
    // TODO: 2020/4/01
    fun getStartSleep(success: (String, String, String) -> Unit) {
        runRxLambda(RetrofitFactory.getInstance().postPig(), {
            val json = String(it.bytes())
            if (!TextUtils.isEmpty(json))
            {
                val obj = JSONObject(json)
                success(obj.getString("status"), obj.getString("message"), obj.getString("data"))
            }

        }, {
            success("0", "网络请求失败", "0")
        })

    }
}