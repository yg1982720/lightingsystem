package com.zklink.lightingsystem.db;

/**
 * Created by Administrator on 2016/6/29.
 */
public class ControllerInfo{
    public int _id;
    public String controller_id;
    public String controller_name;
    public String longitudeandlatitude;//经纬度
    public String controller_ipandport;//IP和端口
    public String controller_domainname;//域名和端口
    public ControllerInfo(){}
    public ControllerInfo(int _id,String controller_id,String controller_name,String longitudeandlatitude,String controller_ipandport,String controller_domainname){
        this._id = _id;
        this.controller_id = controller_id;
        this.controller_name = controller_name;
        this.longitudeandlatitude = longitudeandlatitude;
        this.controller_ipandport = controller_ipandport;
        this.controller_domainname = controller_domainname;
    }
}

