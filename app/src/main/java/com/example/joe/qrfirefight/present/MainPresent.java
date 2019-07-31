package com.example.joe.qrfirefight.present;

import android.util.Log;

import com.example.joe.qrfirefight.base.BasePresent;
import com.example.joe.qrfirefight.model.HistoryModel;
import com.example.joe.qrfirefight.model.BaseModel;
import com.example.joe.qrfirefight.view.IMainView;
import com.example.joe.qrfirefight.webservice.NetManager;
import com.example.joe.qrfirefight.webservice.rxjava.WsCallBack;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by 18145288 on 27/5/2019.
 */

public class MainPresent extends BasePresent<IMainView> {
    private final String TAG = "MainPresent";

    public void submitHisMsg(List<HistoryModel> historyModels){
        NetManager.getInstance().submitHisMsg(historyModels, new WsCallBack() {
            @Override
            public void onSuccess(BaseModel uploadHisMsgModel) {
                Log.i(TAG, "msg1:" + new Gson().toJson(uploadHisMsgModel));
                getView().uploadEquipMsgSuccess(uploadHisMsgModel);
            }

            @Override
            public void onFailed(Throwable uploadHisMsgModel) {
                Log.i(TAG, "msg2:" + new Gson().toJson(uploadHisMsgModel));
                getView().uploadEquipMsgFailed(uploadHisMsgModel);
            }
        });
    }

}
