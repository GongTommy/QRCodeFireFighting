package com.example.joe.qrfirefight.view;

import com.example.joe.qrfirefight.base.IBaseView;
import com.example.joe.qrfirefight.model.UploadHisMsgModel;

/**
 * Created by 18145288 on 27/5/2019.
 */

public interface IMainView extends IBaseView {
    void uploadEquipMsgSuccess(UploadHisMsgModel uploadHistoryModel);
    void uploadEquipMsgFailed(Throwable throwable);
}
