package com.example.joe.qrfirefight.webservice;

import android.content.Context;

import com.example.joe.qrfirefight.model.HistoryModel;
import com.example.joe.qrfirefight.model.ScheTimeSubmitEntity;
import com.example.joe.qrfirefight.webservice.rxjava.WsCallBack;
import com.example.joe.qrfirefight.webservice.rxjava.WsTransformer;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 18145288 on 24/5/2019.
 */

public class NetManager {
    private final String TAG = "NetManager" ;
    private final String TEST_BASE_URL = "http://appdemo.karrie.com/";
    private static final int DEFAULT_NETWORK_TIMEOUT = 10000;
    private Context mContext;
    private Api api;
    private Retrofit.Builder retrofitBuilder;
    private static class InstanceHolder{
        private static final NetManager instance = new NetManager();
    }

    private NetManager(){
    }

    public static NetManager getInstance(){
        return InstanceHolder.instance;
    }

    public void init(Context mContext){
        this.mContext = mContext;
        retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = retrofitBuilder.baseUrl(TEST_BASE_URL)
                .client(getOkHttpClient())
                .build();
        api = retrofit.create(Api.class);
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.retryOnConnectionFailure(false)
//                .cache(null)
//                .cookieJar(cookieJar)
                .connectTimeout(DEFAULT_NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
//                .addInterceptor(new LogInterceptor());

        return builder.build();
    }

    public void submitHisMsg(List<HistoryModel> historyModels, WsCallBack wsCallBack){
        api.submitHisMsg(historyModels)
                .compose(WsTransformer.instance())
                .subscribe(wsCallBack);
    }

    /**
     * 获取排期单列表信息
     * @param wsCallBack
     */
    public void getScheTimeDatas(WsCallBack wsCallBack){
        api.getScheTimeDatas()
                .compose(WsTransformer.instance())
                .subscribe(wsCallBack);
    }

    /**
     * 获取单个排期单的详细信息
     */
    public void getScheTimeDetailData(String billNo, WsCallBack wsCallBack){
        api.getScheTimeDetailData(billNo)
                .compose(WsTransformer.instance())
                .subscribe(wsCallBack);
    }

    public void submitScheTimeDatas(List<ScheTimeSubmitEntity> list, WsCallBack wsCallBack){
        api.submitSchedulAutoData(list)
                .compose(WsTransformer.instance())
                .subscribe(wsCallBack);
    }

}
