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
import com.example.joe.qrfirefight.adapter.SelectorAdapter;
import com.example.joe.qrfirefight.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18145288 on 2019/6/20.
 */

public class ScheTimeFragment extends Fragment implements View.OnClickListener{
    private final String TAG = "ScheTimeFragment";
    private Button btnPre, btnNext, btnRefresh;
    private RecyclerView rlSelect;
    private SelectorAdapter selectorAdapter;
    private ViewPager parentVp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.sel_pager_fragment, container, false);
        ScheTimeActivity scheTimeActivity = (ScheTimeActivity) getActivity();
        parentVp = scheTimeActivity.getViewPager();
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnNext = view.findViewById(R.id.btnNext);
        btnPre = view.findViewById(R.id.btnPre);
        btnRefresh = view.findViewById(R.id.btnRefresh);
        rlSelect = view.findViewById(R.id.rlSelect);

        initData();
    }

    private void initData() {
        btnPre.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        List<String> list = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            list.add("A");
        }
        selectorAdapter = new SelectorAdapter(list, getActivity());
        rlSelect.setAdapter(selectorAdapter);
        rlSelect.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //首页不启用“上一步”按钮
        btnPre.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRefresh:
                break;
            case R.id.btnNext:
                if (selectorAdapter != null && selectorAdapter.isSelected()){
                    parentVp.setCurrentItem(parentVp.getCurrentItem() + 1);
                }else {
                    Utils.getInstance().showShortToast("请先选择条目");
                }
                break;
            case R.id.btnPre:
                parentVp.setCurrentItem(parentVp.getCurrentItem() - 1);
                break;
        }
    }
}
