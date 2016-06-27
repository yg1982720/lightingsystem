package com.zklink.lightingsystem.db;

/**
 * Created by Administrator on 2016/6/1.
 */
public class GroupInfo {
    public int _id;
    public int group_id;
    public String group_name;
    public String group_timetable;
    public String group_scene;
    public int group_lampinstallnum;
    public int group_iconid;


    public GroupInfo(){}
    public GroupInfo(int _id,int group_id,String group_name,String group_timetable,String group_scene,int group_lampinstallnum,int group_iconid){
        this._id = _id;
        this.group_id = group_id;
        this.group_name = group_name;
        this.group_timetable = group_timetable;
        this.group_scene = group_scene;
        this.group_lampinstallnum = group_lampinstallnum;
        this.group_iconid=group_iconid;
    }
}
