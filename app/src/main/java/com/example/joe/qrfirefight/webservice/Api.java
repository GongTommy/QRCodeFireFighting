package com.example.joe.qrfirefight.webservice;

import com.example.joe.qrfirefight.model.HistoryModel;
import com.example.joe.qrfirefight.model.UploadHisMsgModel;

import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by 18145288 on 27/5/2019.
 */

public interface Api {

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("actionapi/Index/CheckFireEquipment")
    Observable<UploadHisMsgModel> submitHisMsg(@Body List<HistoryModel> historyModels);

}
