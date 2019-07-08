package com.example.joe.qrfirefight.model;

/**
 * Created by Joe on 2019-05-23.
 */

public class HistoryModel {
    /**
     * equipmentid : 100000
     * qualifiedstate : 1
     * createuserid : 18141097
     * createdate : 2019-05-22 13:20:00
     */

    private String equipmentid;
    private int qualifiedstate;
    private String createuserid;
    private String createdate;

    public String getEquipmentid() {
        return equipmentid;
    }

    public void setEquipmentid(String equipmentid) {
        this.equipmentid = equipmentid;
    }

    public int getQualifiedstate() {
        return qualifiedstate;
    }

    public void setQualifiedstate(int qualifiedstate) {
        this.qualifiedstate = qualifiedstate;
    }

    public String getCreateuserid() {
        return createuserid;
    }

    public void setCreateuserid(String createuserid) {
        this.createuserid = createuserid;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }
}
