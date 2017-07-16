package com.taichuan.http.bean;

/**
 * Created by gui on 2017/7/15.
 */
public class ResultData<T> {
    private boolean State;
    private int Code;
    private String Msg;
    private T Data;

    public boolean isState() {
        return State;
    }

    public void setState(boolean state) {
        State = state;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "State=" + State +
                ", Code=" + Code +
                ", Msg='" + Msg + '\'' +
                ", Data=" + Data +
                '}';
    }
}
