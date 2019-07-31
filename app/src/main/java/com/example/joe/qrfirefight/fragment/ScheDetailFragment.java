package com.example.joe.qrfirefight.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.joe.qrfirefight.R;
import com.example.joe.qrfirefight.activity.ScheTimeActivity;
import com.example.joe.qrfirefight.adapter.DetailAdapter;
import com.example.joe.qrfirefight.adapter.SelectorAdapter;
import com.example.joe.qrfirefight.model.ScheTimeDetailEntity;

import java.util.List;

/**
 * Created by 18145288 on 2019/6/26.
 */

public class ScheDetailFragment extends Fragment implements View.OnClickListener {
    private Button btnPre, btnNext;
    private ViewPager parentVp;
    private RecyclerView rlDetail;
    private DetailAdapter selectorAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sche_detail_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnPre = view.findViewById(R.id.btnPre);
        btnNext = view.findViewById(R.id.btnNext);
        rlDetail = view.findViewById(R.id.rlDetail);
        initData();
    }

    private void initData() {
        ScheTimeActivity scheTimeActivity = (ScheTimeActivity) getActivity();
        if (scheTimeActivity != null){
            parentVp = scheTimeActivity.getViewPager();
        }
        btnPre.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext:
                parentVp.setCurrentItem(parentVp.getCurrentItem() + 1);
                ScheTimeActivity scheTimeActivity = (ScheTimeActivity) getActivity();
                if (scheTimeActivity != null){
                    scheTimeActivity.refreshSubmitPage();
                }
                break;
            case R.id.btnPre:
                parentVp.setCurrentItem(parentVp.getCurrentItem() - 1);
                break;
        }
    }

    public void refreshDatasDetail(List<ScheTimeDetailEntity> list){
        selectorAdapter = new DetailAdapter(list, getActivity());
        rlDetail.setAdapter(selectorAdapter);
        rlDetail.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }
}
