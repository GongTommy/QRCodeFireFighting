package com.example.joe.qrfirefight.base;

import com.example.joe.qrfirefight.view.IMainView;

/**
 * Created by 18145288 on 27/5/2019.
 */

public abstract class BasePresent<ViewType extends IBaseView> {
    private ViewType mView;
    public void attach(ViewType view) {
        mView = view;
    }

    public void detach() {
        mView = null;
    }

    public boolean isAttach() {
        if (mView != null){
            return true;
        }
        return false;
    }

    public ViewType getView(){
        return mView;
    }
}
