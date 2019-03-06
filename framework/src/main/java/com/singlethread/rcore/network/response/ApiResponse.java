package com.singlethread.rcore.network.response;

/**
 * Created by litianyuan on 2017/8/30.
 */

public class ApiResponse<T> {

    private boolean success;
    private int resultCode;
    private String msg;
    private long startTime;
    private long timeConsum;
    T data;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getTimeConsum() {
        return timeConsum;
    }

    public void setTimeConsum(long timeConsum) {
        this.timeConsum = timeConsum;
    }
}
