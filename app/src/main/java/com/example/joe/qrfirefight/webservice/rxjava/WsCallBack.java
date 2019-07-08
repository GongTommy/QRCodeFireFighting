package com.example.joe.qrfirefight.webservice.rxjava;



import android.util.Log;

import com.example.joe.qrfirefight.model.UploadHisMsgModel;
import com.google.gson.Gson;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by 18145288 on 27/5/2019.
 */

public abstract class WsCallBack implements Observer<UploadHisMsgModel> {
    private String TAG = "WsCallBack";
    public abstract void onSuccess(UploadHisMsgModel uploadHisMsgModel);
    public abstract void onFailed(Throwable ResponseBody);


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(UploadHisMsgModel uploadHisMsgModel) {
        onSuccess(uploadHisMsgModel);
    }

    @Override
    public void onError(Throwable e) {
        onFailed(e);
    }

    @Override
    public void onComplete() {

    }
}
