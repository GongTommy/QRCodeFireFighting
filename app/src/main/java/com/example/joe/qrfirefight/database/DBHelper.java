package com.example.joe.qrfirefight.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Joe on 2019-05-23.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    public static final String TABLE_NAME = "history_data";
    public static final String CREATE_DATE = "createdate";
    public static final String CREATE_USER_ID = "createuserid";
    public static final String EQUIP_MENT_ID = "equipmentid";
    public static final String QUALIFIED_STATE = "qualifiedstate";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // 当第一次创建数据库的时候，调用该方法
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table history_data(createdate varchar(30) primary key," +
                "createuserid varchar(20), equipmentid varchar(20), qualifiedstate varchar(1))";
        Log.i(TAG, "create Database------------->");
        db.execSQL(sql);
    }

    /**
     * 升级执行方法
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG, "onUpgrade Database------------->");
    }

    /**
     * 降级执行方法
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onDowngrade Database------------->");
    }
}
