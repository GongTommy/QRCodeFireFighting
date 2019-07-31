package com.example.joe.qrfirefight.webservice.rxjava;



import com.example.joe.qrfirefight.model.BaseModel;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by 18145288 on 27/5/2019.
 */

public abstract class WsCallBack implements Observer<BaseModel> {
    public abstract void onSuccess(BaseModel baseModel);
    public abstract void onFailed(Throwable ResponseBody);


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseModel baseModel) {
        onSuccess(baseModel);
    }

    @Override
    public void onError(Throwable e) {
        onFailed(e);
    }

    @Override
    public void onComplete() {

    }
}
