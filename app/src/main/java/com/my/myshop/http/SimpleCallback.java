package com.my.myshop.http;

import android.content.Context;
import android.content.Intent;

import com.my.myshop.R;
import com.my.myshop.util.ToastUtil;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


/**
 * Created by <a href="http://www.cniao5.com">菜鸟窝</a>
 * 一个专业的Android开发在线教育平台
 */
public abstract class SimpleCallback<T> extends BaseCallback<T> {
    protected Context mContext;
    public SimpleCallback(Context context){
        mContext = context;
    }

    @Override
    public void onRequestBefore(Request request) {
    }

    @Override
    public void onFailure(Request request, Exception e) {
    }

    @Override
    public void onResponse(Response response) {
    }

//    @Override
//    public void onTokenError(Response response, int code) {
//        ToastUtil.show(mContext, mContext.getString(R.string.token_error));
//        Intent intent = new Intent();
//        intent.setClass(mContext, LoginActivity.class);
//        mContext.startActivity(intent);
//        CniaoApplication.getInstance().clearUser();
//    }
}
