package com.example.joe.qrfirefight.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Joe on 2019-05-24.
 */

public class Utils {
    private final String SP_NAME = "local_sp";
    private Context mContext;
    private Utils() {
    }

    public static Utils getInstance(){
        return SingleInstance.instace;
    }

    private static class SingleInstance {
        private static final Utils instace = new Utils();
    }

    public void init(Context mContext){
        this.mContext = mContext;
    }

    public void saveStrSp(String key, String value){
        if (mContext == null){
            return;
        }
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void saveIntSp(String key, int value){
        if (mContext == null){
            return;
        }
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public String getStrSp(String key){
        if (mContext == null){
            return "";
        }
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public int getIntSp(String key){
        if (mContext == null){
            return 0;
        }
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
        return sp.getInt("key", 0);
    }

    public void showShortToast(String showStr){
        if (mContext == null){
            return;
        }
        Toast toast = Toast.makeText(mContext, showStr, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, -120);
        toast.show();
    }

    public void showLongToast(String showStr){
        if (mContext == null){
            return;
        }
        Toast toast = Toast.makeText(mContext, showStr, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, -120);
        toast.show();
    }
}
