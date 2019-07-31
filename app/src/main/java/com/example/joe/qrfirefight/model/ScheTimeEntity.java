package com.example.joe.qrfirefight.model;

public class ScheTimeEntity {

    /**
     * billno : CN023956  排期单号
     * billdate : 2019-05-07T00:00:00 日期
     * clientno : 10DC0581
     */

    private String billno;
    private String billdate;
    private String clientno;

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getBilldate() {
        return billdate;
    }

    public void setBilldate(String billdate) {
        this.billdate = billdate;
    }

    public String getClientno() {
        return clientno;
    }

    public void setClientno(String clientno) {
        this.clientno = clientno;
    }
}
