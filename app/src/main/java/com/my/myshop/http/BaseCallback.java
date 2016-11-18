package com.my.myshop.http;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by MY on 2016/9/14.
 */
public abstract class BaseCallback<T> {
    public Type type;
    public BaseCallback()
    {
        type = getSuperclassTypeParameter(getClass());
    }
    //把T转为type
    static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public abstract void onRequestBefore(Request request);
    public abstract void onResponse(Response response);
    public abstract void onFailure(Request request, Exception e);
    public abstract void onSuccess(Response response, T t);
    public abstract void onError(Response response, int code, Exception e);
}
