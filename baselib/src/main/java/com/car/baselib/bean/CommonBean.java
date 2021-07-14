package com.car.baselib.bean;


public class CommonBean<T> extends BaseBean {

    public String code;
    public String msg;
    public T data;
    public boolean success;
    public int size;
    public int total;
}
