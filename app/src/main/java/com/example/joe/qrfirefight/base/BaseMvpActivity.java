package com.example.joe.qrfirefight.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 18145288 on 27/5/2019.
 */

public abstract class BaseMvpActivity<ViewType extends IBaseView, PresentType extends BasePresent>
        extends AppCompatActivity implements IBaseView {
    public PresentType mPresent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        mPresent = createPresent();
        mPresent.attach(this);
    }

    public abstract int getLayoutId();

    public abstract PresentType createPresent();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresent.detach();
        mPresent = null;
    }
}
