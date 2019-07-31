package com.example.joe.qrfirefight.view;

import com.example.joe.qrfirefight.base.IBaseView;
import com.example.joe.qrfirefight.model.ScheTimeDetailEntity;
import com.example.joe.qrfirefight.model.ScheTimeEntity;

import java.util.List;

public interface IScheTimeView extends IBaseView {
    /**
     * 获取排期列表数据成功
     * @param list
     */
    void getScheTimeDatasSuccess(List<ScheTimeEntity> list);
    void getScheTimeDatasFailed(Throwable throwable);

    /**
     * 获取单个排期单的详细信息
     * @param list
     */
    void getScheTimeDetailDataSuccess(List<ScheTimeDetailEntity> list);
    void getScheTimeDetailDataFailed(Throwable throwable);

    /**
     * 提交排期单数据成功
     */
    void submitScheTimeDatasSuccess();
    void submitScheTimeDatasFailed();
}
