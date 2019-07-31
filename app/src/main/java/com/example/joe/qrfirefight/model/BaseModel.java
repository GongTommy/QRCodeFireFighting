package com.example.joe.qrfirefight.model;

import java.util.List;

/**
 * Created by 18145288 on 27/5/2019.
 */

public class BaseModel<T> {
    /**
     * Code : 1
     * Msg : 发送成功
     * Data : []
     */

    private List<T> Data;
    private int Code;
    private String Msg;

    public List<T> getData() {
        return Data;
    }

    public void setData(List<T> Data) {
        this.Data = Data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }
}
