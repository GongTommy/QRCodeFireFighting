package com.example.joe.qrfirefight.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.joe.qrfirefight.R;
import com.example.joe.qrfirefight.activity.MainActivity;
import com.example.joe.qrfirefight.activity.ScheTimeActivity;
import com.example.joe.qrfirefight.adapter.HistoryItemAdapter;
import com.example.joe.qrfirefight.adapter.ScheHisAdapter;
import com.example.joe.qrfirefight.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;

/**
 * Created by 18145288 on 2019/6/26.
 */

public class ScheSubmitFragment extends Fragment implements View.OnClickListener{
    private Button btnPre, btnNext;
    private ViewPager parentVp;
    private RecyclerView rvScheTime;
    private List<String> scheTimeDatas;
    private TextView tvNum;
    private EditText etScheCode;
    private int addTime;//扫码一次会出现两次回车键，禁止此情况

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sche_submit_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvScheTime = view.findViewById(R.id.rvScheTime);
        btnPre = view.findViewById(R.id.btnPre);
        tvNum = view.findViewById(R.id.tvTimeNum);
        btnNext = view.findViewById(R.id.btnNext);
        etScheCode = view.findViewById(R.id.etScheCode);
        ScheTimeActivity scheTimeActivity = (ScheTimeActivity) getActivity();
        if (scheTimeActivity != null){
            parentVp = scheTimeActivity.getViewPager();
        }
        initData();
    }

    public void initData(){
        btnNext.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        btnNext.setEnabled(false);
        scheTimeDatas = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            scheTimeDatas.add(String.valueOf(i));
        }
        final ScheHisAdapter scheHisAdapter = new ScheHisAdapter(scheTimeDatas);
        scheHisAdapter.setOnItemDelClickListener(new ScheHisAdapter.OnItemDelClickListener() {
            @Override
            public void onItemDelClickListener(final int position) {
                if (scheTimeDatas != null && scheTimeDatas.size() > position){
                     new AlertDialog.Builder(getActivity())
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    scheTimeDatas.remove(position);
                                    scheHisAdapter.notifyDataSetChanged();
                                    Utils.getInstance().showShortToast("删除成功");
                                    tvNum.setText("总条数：" + String.valueOf(scheTimeDatas.size()));
                                }
                            })
                             .setMessage("确定删除吗？")
                             .show();

                }
            }
        });
        rvScheTime.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvScheTime.setAdapter(scheHisAdapter);
        tvNum.setText("总条数：" + String.valueOf(scheTimeDatas.size()));
        etScheCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                Log.i("ScheSubmit", "i:" + i + " " + KeyEvent.KEYCODE_ENTER);
                if (KeyEvent.KEYCODE_ENTER == i) {
                    if (addTime == 1){
                        addTime = 0;
                        return true;
                    }
                    addTime++;
                    scheTimeDatas.add(0, etScheCode.getText().toString());
                    scheHisAdapter.notifyDataSetChanged();
                    etScheCode.setText("");
                    tvNum.setText(String.valueOf(scheTimeDatas.size()));
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext:
                parentVp.setCurrentItem(parentVp.getCurrentItem() + 1);
                break;
            case R.id.btnPre:
                parentVp.setCurrentItem(parentVp.getCurrentItem() - 1);
                break;
        }
    }
}
