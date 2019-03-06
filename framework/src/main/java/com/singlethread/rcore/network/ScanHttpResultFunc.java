package com.singlethread.rcore.network;


import com.singlethread.rcore.network.exception.ApiException;
import com.singlethread.rcore.network.response.ApiResponse;

import rx.functions.Func1;

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 */
public class ScanHttpResultFunc<T> implements Func1<ApiResponse<T>, T> {

    @Override
    public T call(ApiResponse<T> httpResult) {

        int resultCode = httpResult.getResultCode();

        if (resultCode == 100) {
            return httpResult.getData();
        }

        throw new ApiException(resultCode, httpResult.getMsg());
    }

}