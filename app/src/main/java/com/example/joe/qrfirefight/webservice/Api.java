package com.example.joe.qrfirefight.webservice;

import com.example.joe.qrfirefight.model.HistoryModel;
import com.example.joe.qrfirefight.model.ScheTimeDetailEntity;
import com.example.joe.qrfirefight.model.ScheTimeEntity;
import com.example.joe.qrfirefight.model.BaseModel;
import com.example.joe.qrfirefight.model.ScheTimeSubmitEntity;

import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by 18145288 on 27/5/2019.
 * 接口类
 */

public interface Api {

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("actionapi/Index/CheckFireEquipment")
    Observable<BaseModel> submitHisMsg(@Body List<HistoryModel> historyModels);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @GET("actionapi/Index/SchedulAutoDataMst")
    Observable<BaseModel<ScheTimeEntity>> getScheTimeDatas();


    @Headers({"Content-Type: application/json","Accept: application/json"})
    @GET("actionapi/Index/SchedulAutoDataItem")
    Observable<BaseModel<ScheTimeDetailEntity>> getScheTimeDetailData(@Query("BillNo") String billNo);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("actionapi/Index/SchedulAutoData")
    Observable<BaseModel> submitSchedulAutoData(@Body List<ScheTimeSubmitEntity> list);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @GET("actionapi/Index/SchedulDataCheck")
    Observable<BaseModel> schedulDataCheck(@Query("BillNo") String billno, @Query("PackBarCodes") String packBarCodes);
}
