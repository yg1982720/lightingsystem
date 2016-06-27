package com.zklink.lightingsystem.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2016/6/1.
 */
public class DatabaseHelper  extends SQLiteOpenHelper{

    public static final String DB_NAME = "lamp.db";
    public static  final String CREATE_LAMP="create table if not exists lampinfo("
            +"_id integer primary key autoincrement,"
            +"lamp_id integer,"
            +"lamp_name varchar,"
            +"lamp_devicenum integer,"
            +"lamp_scalepoint integer,"
            +"lamp_group integer,"
            +"lamp_workmode integer,"
            +"lamp_usestatus integer)";
    public static  final String CREATE_GROUP="create table if not exists groupinfo("
            +"_id integer primary key autoincrement,"
            +"group_id integer,"
            +"group_name varchar,"
            +"group_timetable integer,"
            +"group_scene integer,"
            +"group_lampinstallnum integer,"
            +"group_iconid integer)";
    public static final String DB_TABLE_NAME_LAMP = "lampinfo";
    public static final String DB_TABLE_NAME_GROUP = "groupinfo";

    private static final int DB_VERSION=1;
    public DatabaseHelper(Context context) {
        //Context context, String name, CursorFactory factory, int version
        //factory输入null,使用默认值
        super(context, DB_NAME, null, DB_VERSION);
    }
    //数据第一次创建的时候会调用onCreate
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        db.execSQL(CREATE_LAMP);
        db.execSQL(CREATE_GROUP);
        //db.execSQL("CREATE TABLE IF NOT EXISTS tb_lampinfo" +
        //        "(_id INTEGER PRIMARY KEY AUTOINCREMENT, lamp_name VARCHAR, devicenum INTEGER, scalepoint INTEGER, group INTEGER,workmode INTEGER,usestatus INTEGER)");
        //db.execSQL("CREATE GROUPTABLE IF NOT EXISTS info" +
        //        "(group_id VARCHAR PRIMARY KEY AUTOINCREMENT, group_name VARCHAR, grouptimetable VARCHAR, groupscene VARCHAR,group_lampinstallnum INTEGER)");

    }
    //数据库第一次创建时onCreate方法会被调用，我们可以执行创建表的语句，当系统发现版本变化之后，会调用onUpgrade方法，我们可以执行修改表结构等语句
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //在表info中增加一列other
        db.execSQL("ALTER TABLE info ADD COLUMN other STRING");
    }


}
