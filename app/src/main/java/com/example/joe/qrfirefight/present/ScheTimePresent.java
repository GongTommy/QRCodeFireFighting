package com.example.joe.qrfirefight.present;

import android.util.Log;
import com.example.joe.qrfirefight.base.BasePresent;
import com.example.joe.qrfirefight.model.BaseModel;
import com.example.joe.qrfirefight.model.ScheTimeDetailEntity;
import com.example.joe.qrfirefight.model.ScheTimeEntity;
import com.example.joe.qrfirefight.model.ScheTimeSubmitEntity;
import com.example.joe.qrfirefight.view.IScheTimeView;
import com.example.joe.qrfirefight.webservice.NetManager;
import com.example.joe.qrfirefight.webservice.rxjava.WsCallBack;
import com.google.gson.Gson;
import java.util.List;


public class ScheTimePresent extends BasePresent<IScheTimeView> {
    private String TAG = "ScheTimePresent";

    public void getScheTimeDatas(){
        NetManager.getInstance().getScheTimeDatas(new WsCallBack() {
            @Override
            public void onSuccess(BaseModel baseModel) {
                Log.i("WsTransformer", "success:" + new Gson().toJson(baseModel));
                List<ScheTimeEntity> list = baseModel.getData();
                Log.i("WsTransformer", "list:" + new Gson().toJson(list));
                getView().getScheTimeDatasSuccess(list);
            }

            @Override
            public void onFailed(Throwable responseBody) {
                Log.i("WsTransformer", "failed:" + new Gson().toJson(responseBody));
                getView().getScheTimeDatasFailed(responseBody);
            }
        });
    }

    /**
     * 获取单个排期单的详细数据
     * @param billNo
     */
    public void getScheTimeDetailData(String billNo){
        NetManager.getInstance().getScheTimeDetailData(billNo, new WsCallBack() {
            @Override
            public void onSuccess(BaseModel baseModel) {
                List<ScheTimeDetailEntity> list = baseModel.getData();
                getView().getScheTimeDetailDataSuccess(list);
            }

            @Override
            public void onFailed(Throwable responseBody) {
                getView().getScheTimeDetailDataFailed(responseBody);
            }
        });
    }

    /**
     * 提交所有二维码数据
     * @param list
     */
    public void submitScheTimeDatas(List<ScheTimeSubmitEntity> list){
        NetManager.getInstance().submitScheTimeDatas(list, new WsCallBack() {
            @Override
            public void onSuccess(BaseModel baseModel) {
                Log.i(TAG, "onSuccess");
                getView().submitScheTimeDatasSuccess();
            }

            @Override
            public void onFailed(Throwable ResponseBody) {
                Log.i(TAG, "onFailed:" + new Gson().toJson(ResponseBody));
                getView().submitScheTimeDatasFailed();
            }
        });
    }

    public void schedulDataCheck(String billNo, String localQrCodes) {
        NetManager.getInstance().schedulDataCheck(billNo, localQrCodes, new WsCallBack() {
            @Override
            public void onSuccess(BaseModel baseModel) {
                Log.i(TAG, "check success");
                getView().schedulDataCheckSuccess(baseModel);
            }

            @Override
            public void onFailed(Throwable responseBody) {
                Log.i(TAG, "check failed");
                getView().schedulDataCheckFailed(responseBody);
            }
        });
    }
}
