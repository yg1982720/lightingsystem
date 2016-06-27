package com.zklink.lightingsystem.db;

/**
 * Created by Administrator on 2016/6/1.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 *DBManager是建立在DBHelper之上，封装了常用的业务方法
 */
public class DBManager {

    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context){
        helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    /**
     * 向表info中增加一个成员信息
     *
     * @param lampInfo
     */
    public void addlamp(List<LampInfo> lampInfo) {
        db.beginTransaction();// 开始事务
        try {
            for (LampInfo info : lampInfo) {
                // 向表info中插入数据
                db.execSQL("INSERT INTO lampinfo VALUES(null,?,?,?,?,?,?,?)", new Object[] { info.lamp_id,info.lamp_name, info.lamp_devicenum, info.lamp_scalepoint,
                        info.lamp_group,info.lamp_workmode,info.lamp_usestatus });
            }
            db.setTransactionSuccessful();// 事务成功
        } finally {
            db.endTransaction();// 结束事务
        }
    }

    /**
     * @param _id
     * @param lamp_id
     * @param lamp_name
     * @param lamp_devicenum
     * @param lamp_scalepoint
     * @param lamp_group
     * @param lamp_workmode
     * @param lamp_usestatus
     */
    public void addlamp(int _id,String lamp_id, String lamp_name, int lamp_devicenum,int lamp_scalepoint,int lamp_group, int lamp_workmode, int lamp_usestatus) {
        ContentValues cv = new ContentValues();
        cv.put("_id", _id);
        cv.put("lamp_id", lamp_id);
        cv.put("lamp_name", lamp_name);
        cv.put("lamp_devicenum", lamp_devicenum);
        cv.put("lamp_scalepoint", lamp_scalepoint);
        cv.put("lamp_group", lamp_group);
        cv.put("lamp_workmode", lamp_workmode);
        cv.put("lamp_usestatus", lamp_usestatus);
        db.insert(DatabaseHelper.DB_TABLE_NAME_LAMP, null, cv);
    }

    /**
     * 向表info中增加一个成员信息
     *
     * @param groupInfo
     */
    public void addgroup(List<GroupInfo> groupInfo) {
        db.beginTransaction();// 开始事务
        try {
            for (GroupInfo info : groupInfo) {
                // tb_groupinfo
                db.execSQL("INSERT INTO groupinfo VALUES(null,?,?,?,?,?,?)", new Object[] {info.group_id, info.group_name, info.group_timetable, info.group_scene,
                        info.group_lampinstallnum,info.group_iconid});
            }
            db.setTransactionSuccessful();// 事务成功
        } finally {
            db.endTransaction();// 结束事务
        }
    }

    /**
     * @param group_id
     * @param group_name
     * @param group_timetable
     * @param group_scene
     * @param group_lampinstallnum
     */
    public void addgroup(int _id,int group_id, String group_name, String group_timetable,String group_scene,int group_lampinstallnum,int group_group_iconid) {
        ContentValues cv = new ContentValues();
        cv.put("_id", _id);
        cv.put("group_id", group_id);
        cv.put("group_name", group_name);
        cv.put("group_timetable", group_timetable);
        cv.put("group_scene", group_scene);
        cv.put("group_lampinstallnum", group_lampinstallnum);
        cv.put("group_group_iconid", group_group_iconid);
        db.insert(DatabaseHelper.DB_TABLE_NAME_GROUP, null, cv);
    }
    /**
     * 通过id来删除数据
     *
     * @param lamp_id
     */
    public void delLampData(String lamp_id) {
        // ExecSQL("DELETE FROM info WHERE name ="+"'"+name+"'");
        String[] args = { lamp_id };
        db.delete(DatabaseHelper.DB_TABLE_NAME_LAMP, "lamp_id=?", args);
    }


    /**
     * 通过id来删除数据
     *
     * @param group_id
     */
    public void delGroupData(final int group_id) {
        // ExecSQL("DELETE FROM info WHERE name ="+"'"+name+"'");
        String[] args = {Integer.toString(group_id)};
        db.delete(DatabaseHelper.DB_TABLE_NAME_GROUP, "group_id=?", args);
    }
    /**
     * 清空数据
     */
    public void clearLampData() {
        ExecSQL("DELETE FROM lampinfo");
    }

    /**
     * 清空数据
     */
    public void clearGroupData() {
        ExecSQL("DELETE FROM groupinfo");
    }
    /**
     * 通过名字查询信息,返回所有的数据
     *
     * @param lamp_group
     */
    public ArrayList<LampInfo> searchLampData(final int lamp_group) {
        String sql = "SELECT * FROM lampinfo WHERE lamp_group =" + "'" + lamp_group + "'";
        return ExecSQLForLampInfo(sql);
    }

    public Boolean searchRepeatLamp(final String lamp_id) {
        String sql = "SELECT * FROM lampinfo WHERE lamp_id =" + "'" + lamp_id + "'";
        ArrayList<LampInfo> infoList = new ArrayList<LampInfo>();
        infoList=ExecSQLForLampInfo(sql);

        if(infoList.size()==0)
        {
            return true;
        }
        else {
            return false;
        }
    }

    public ArrayList<LampInfo> searchAllLampData() {
        String sql = "SELECT * FROM lampinfo";
        return ExecSQLForLampInfo(sql);
    }

    public ArrayList<GroupInfo> searchGroupData(final int group_id) {
        String sql = "SELECT * FROM groupinfo WHERE group_id =" + "'" + group_id + "'";
        return ExecSQLForGroupInfo(sql);
    }

    public Boolean searchRepeatGroup(final int group_id) {
        String sql = "SELECT * FROM groupinfo WHERE group_id =" + "'" + group_id + "'";
        ArrayList<GroupInfo> infoList = new ArrayList<GroupInfo>();
        infoList=ExecSQLForGroupInfo(sql);

        if(infoList.size()==0)
        {
            return true;
        }
        else {
            return false;
        }
    }

    public ArrayList<GroupInfo> searchAllGroupData() {
        String sql = "SELECT * FROM groupinfo";
        return ExecSQLForGroupInfo(sql);
    }
    /**
     * 通过名字来修改值
     *
     * @param raw
     * @param rawValue
     * @param whereName
     */
    public void updateData(String raw, String rawValue, String whereName) {
        String sql = "UPDATE lampinfo SET " + raw + " =" + " " + "'" + rawValue + "'" + " WHERE lamp_id =" + "'" + whereName
                + "'";
        ExecSQL(sql);
    }

    /**
     * 执行SQL命令返回list
     *
     * @param sql
     * @return
     */
    private ArrayList<LampInfo> ExecSQLForLampInfo(String sql) {
        ArrayList<LampInfo> list = new ArrayList<LampInfo>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            LampInfo info = new LampInfo();
            info._id = c.getInt(c.getColumnIndex("_id"));
            info.lamp_id = c.getString(c.getColumnIndex("lamp_id"));
            info.lamp_name = c.getString(c.getColumnIndex("lamp_name"));
            info.lamp_devicenum = c.getInt(c.getColumnIndex("lamp_devicenum"));
            info.lamp_scalepoint = c.getInt(c.getColumnIndex("lamp_scalepoint"));
            info.lamp_group = c.getInt(c.getColumnIndex("lamp_group"));
            info.lamp_workmode = c.getInt(c.getColumnIndex("lamp_workmode"));
            info.lamp_usestatus = c.getInt(c.getColumnIndex("lamp_usestatus"));
            list.add(info);
        }
        return list;
    }

    private ArrayList<GroupInfo> ExecSQLForGroupInfo(String sql) {
        ArrayList<GroupInfo> list = new ArrayList<GroupInfo>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            GroupInfo info = new GroupInfo();
            info._id = c.getInt(c.getColumnIndex("_id"));
            info.group_id = c.getInt(c.getColumnIndex("group_id"));
            info.group_name = c.getString(c.getColumnIndex("group_name"));
            info.group_timetable = c.getString(c.getColumnIndex("group_timetable"));
            info.group_scene = c.getString(c.getColumnIndex("group_scene"));
            info.group_lampinstallnum = c.getInt(c.getColumnIndex("group_lampinstallnum"));
            info.group_iconid = c.getInt(c.getColumnIndex("group_iconid"));
            list.add(info);
        }
        return list;
    }
    /**
     * 执行一个SQL语句
     *
     * @param sql
     */
    private void ExecSQL(String sql) {
        try {
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行SQL，返回一个游标
     *
     * @param sql
     * @return
     */
    private Cursor ExecSQLForCursor(String sql) {
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public void closeDB() {
        db.close();
    }

}
