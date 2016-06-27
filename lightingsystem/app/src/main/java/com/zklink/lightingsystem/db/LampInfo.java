package com.zklink.lightingsystem.db;

/**
 * Created by Administrator on 2016/6/1.
 */
public class LampInfo {
    public int _id;
    public String lamp_id;
    public String lamp_name;
    public int lamp_devicenum;
    public int lamp_scalepoint;
    public int lamp_group;
    public int lamp_workmode;
    public int lamp_usestatus;
    public LampInfo(){}
    public LampInfo(int _id,String lamp_id,String lamp_name,int lamp_devicenum,int lamp_scalepoint,int lamp_group,int lamp_workmode,int lamp_usestatus){
        this._id = _id;
        this.lamp_id = lamp_id;
        this.lamp_name = lamp_name;
        this.lamp_devicenum = lamp_devicenum;
        this.lamp_scalepoint = lamp_scalepoint;
        this.lamp_group = lamp_group;
        this.lamp_workmode = lamp_workmode;
        this.lamp_usestatus = lamp_usestatus;
    }
}
