package com.singlethread.rcore.network;

import com.singlethread.rcore.network.exception.ApiException;
import com.singlethread.rcore.network.response.ApiResponse;

import rx.functions.Func1;

/**
 * Created with Android Studio
 * <p>
 * Created by litianyuan on 2017/9/6.
 */

public class HttpResultFuc<T> implements Func1<ApiResponse<T>,T> {
    @Override
    public T call(ApiResponse<T> httpResult) {
        if (!httpResult.isSuccess()) {
            throw new ApiException(httpResult.getResultCode(), httpResult.getMsg());
        }
        return httpResult.getData();
    }
}
