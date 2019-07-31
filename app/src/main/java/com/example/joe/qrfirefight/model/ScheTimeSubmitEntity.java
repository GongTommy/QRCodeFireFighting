package com.example.joe.qrfirefight.model;

public class ScheTimeSubmitEntity {


    /**
     * billno : billno
     * billdate : 2019-05-07T00:00:00
     * packbarcode : 18145288
     *
     * id, uploaddate, createdate, createuserid, createusername
     */

    private String billno;
    private String billdate;
    private String packbarcode;
    private String uploaddate;
    private String createdate;
    private String createuserid;
    private String createusername;

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

    public String getPackbarcode() {
        return packbarcode;
    }

    public void setPackbarcode(String packbarcode) {
        this.packbarcode = packbarcode;
    }

    public String getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(String uploaddate) {
        this.uploaddate = uploaddate;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getCreateuserid() {
        return createuserid;
    }

    public void setCreateuserid(String createuserid) {
        this.createuserid = createuserid;
    }

    public String getCreateusername() {
        return createusername;
    }

    public void setCreateusername(String createusername) {
        this.createusername = createusername;
    }
}
