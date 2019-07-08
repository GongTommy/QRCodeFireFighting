package com.example.joe.qrfirefight.webservice.rxjava;

import android.util.Log;

import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 18145288 on 27/5/2019.
 */

public class WsTransformer implements ObservableTransformer {
    private final String TAG = "WsTransformer";
    private WsTransformer(){

    }
    private static class InstanceHolder{
        private static final WsTransformer instance = new WsTransformer();
    }
    public static WsTransformer instance(){
        return InstanceHolder.instance;
    }
    @Override
    public ObservableSource apply(Observable upstream) {
        return upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Function<Throwable, ObservableSource>() {
                    @Override
                    public ObservableSource apply(Throwable throwable) throws Exception {
                        Log.i(TAG, "网络错误：" + new Gson().toJson(throwable));
                        return Observable.error(throwable);
                    }
                });
    }
}
