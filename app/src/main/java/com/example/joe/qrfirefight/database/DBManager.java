package com.example.joe.qrfirefight.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.solver.widgets.Helper;

import com.example.joe.qrfirefight.model.HistoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 2019-05-23.
 */

public class DBManager {
    private DBHelper helper;
    public DBManager(Context mContext){
        helper = new DBHelper(mContext, "fire_fight", null, 1);//version要大于0
    }

    /**
     * 插入数据
     * @param historyModel
     */
    public void insert(HistoryModel historyModel){
        if (historyModel == null || helper == null){
            return;
        }
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("createdate", historyModel.getCreatedate());
        contentValues.put("createuserid", historyModel.getCreateuserid());
        contentValues.put("equipmentid", historyModel.getEquipmentid());
        contentValues.put("qualifiedstate", historyModel.getQualifiedstate());
        db.insert("history_data",null, contentValues);
        db.close();
    }

    public int update(HistoryModel historyModel){
        if (historyModel == null || helper == null){
            return 0;
        }
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("qualifiedstate", historyModel.getQualifiedstate());
        int flag = sqLiteDatabase.update(DBHelper.TABLE_NAME, contentValues,
                "equipmentid = ?", new String[]{historyModel.getEquipmentid()});
        return flag;
    }

    public HistoryModel query(String equiment_id){
        if (helper == null){
            return null;
        }
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_NAME, new String[]{DBHelper.EQUIP_MENT_ID, DBHelper.CREATE_DATE,
                DBHelper.CREATE_USER_ID, DBHelper.QUALIFIED_STATE},
                DBHelper.EQUIP_MENT_ID + "=?", new String[]{equiment_id}, null, null,null );
        if (cursor != null && cursor.getCount() > 0){
            if (cursor.moveToFirst()){
                HistoryModel historyModel = new HistoryModel();
                historyModel.setCreatedate(cursor.getString(cursor.getColumnIndex(DBHelper.CREATE_DATE)));
                historyModel.setCreateuserid(cursor.getString(cursor.getColumnIndex(DBHelper.CREATE_USER_ID)));
                historyModel.setQualifiedstate(cursor.getInt(cursor.getColumnIndex(DBHelper.QUALIFIED_STATE)));
                historyModel.setEquipmentid(cursor.getString(cursor.getColumnIndex(DBHelper.EQUIP_MENT_ID)));
                cursor.close();
                return historyModel;
//                karclient0428
            }
        }
        return null;
    }

    /**
     * 清空表数据，不删除表结构
     */
    public void deleteAllData() {
        if (helper == null){
            return;
        }
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "delete from " + DBHelper.TABLE_NAME;
        db.execSQL(sql);
        db.close();
    }

    /**
     * 删除单条数据
     */
    public int delete(String equipNum){
        if (helper == null || equipNum == null || equipNum.equals("")){
            return -1;
        }
        SQLiteDatabase db = helper.getWritableDatabase();
        int state = db.delete(DBHelper.TABLE_NAME, "equipmentid = ?", new String[]{equipNum});
        return state;
    }

    /**
     * 查询SQLite数据库。读出所有数据内容。
     * @return
     */
    public List<HistoryModel> queryAllData() {
        if (helper == null){
            return null;
        }
        List<HistoryModel> historyModels = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME, null);
        if (cursor != null && cursor.getCount() > 0) {
            historyModels = new ArrayList<>();
            while (cursor.moveToNext()) {
                HistoryModel historyModel = new HistoryModel();
                historyModel.setCreatedate(cursor.getString(cursor.getColumnIndex(DBHelper.CREATE_DATE)));
                historyModel.setCreateuserid(cursor.getString(cursor.getColumnIndex(DBHelper.CREATE_USER_ID)));
                historyModel.setQualifiedstate(cursor.getInt(cursor.getColumnIndex(DBHelper.QUALIFIED_STATE)));
                historyModel.setEquipmentid(cursor.getString(cursor.getColumnIndex(DBHelper.EQUIP_MENT_ID)));
                historyModels.add(historyModel);
            }
            cursor.close();
        }
        return historyModels;
    }
}
