package com.play.anticorrosiveapp.bean;

import java.io.Serializable;
import java.util.List;

public class BaseMsgBean<T> implements Serializable {
    int errcode;
    String errmsg;
    int errdialog;
    T data;

    public BaseMsgBean(int errcode, String errmsg, int errdialog, T data) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.errdialog = errdialog;
        this.data = data;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getErrdialog() {
        return errdialog;
    }

    public void setErrdialog(int errdialog) {
        this.errdialog = errdialog;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
