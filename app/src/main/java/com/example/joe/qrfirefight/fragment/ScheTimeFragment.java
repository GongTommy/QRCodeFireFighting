package com.example.joe.qrfirefight.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.joe.qrfirefight.R;
import com.example.joe.qrfirefight.activity.ScheTimeActivity;
import com.example.joe.qrfirefight.adapter.CustomArrayAdapter;
import com.example.joe.qrfirefight.adapter.SelectorAdapter;
import com.example.joe.qrfirefight.model.ScheTimeEntity;
import com.example.joe.qrfirefight.model.ScheTimeSubmitEntity;
import com.example.joe.qrfirefight.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18145288 on 2019/6/20
 */

public class ScheTimeFragment extends Fragment {
    private final String TAG = "ScheTimeFragment";
    private RelativeLayout rlRefresh;
    private RecyclerView rlSelect;
    private SelectorAdapter selectorAdapter;
    private ViewPager parentVp;
    private AutoCompleteTextView atvContent;
    private List<String> autoDatas;
    private CustomArrayAdapter<String> atvAdapter;
    private RelativeLayout rlLogout;


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
        rlRefresh = view.findViewById(R.id.rlRefresh);
        rlSelect = view.findViewById(R.id.rlSelect);
        atvContent = view.findViewById(R.id.atvContent);
        rlLogout = view.findViewById(R.id.rlLogout);

        initData();
    }

    public void clearAtv() {
        if (atvContent != null) {
            atvContent.setText("");
        }
    }

    private void initData() {
        rlRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScheTimeActivity scheTimeActivity = (ScheTimeActivity) getActivity();
                scheTimeActivity.refreshScheTimeDatas();
            }
        });


        atvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                hideSoftKeyboard();
                parentVp.setCurrentItem(parentVp.getCurrentItem() + 1);
                TextView textView = (TextView) view;
                if (textView != null && textView.getText() != null){
                    String billNo = textView.getText().toString();
                    Log.i(TAG, "billNo:" + billNo);
                    ScheTimeActivity scheTimeActivity = (ScheTimeActivity) getActivity();
                    scheTimeActivity.getScheTimeDataDetail(billNo);
                }
            }
        });
        atvContent.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER){
                    TextView tv = (TextView) view;
                    if (tv != null && tv.getText() != null){
                        String text = tv.getText().toString();
                        if (text != null && autoDatas != null){
                            if (autoDatas.contains(text)){
                                Log.i(TAG, "billNo:" + text);
                                ScheTimeActivity scheTimeActivity = (ScheTimeActivity) getActivity();
                                scheTimeActivity.getScheTimeDataDetail(text);

                                hideSoftKeyboard();
                            }
                        }
                    }
                    return true;
                }
                return false;
            }
        });
        rlLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setMessage("是否注销")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ScheTimeActivity scheTimeActivity = (ScheTimeActivity) getActivity();
                                if (scheTimeActivity != null){
                                    scheTimeActivity.showAlertDialog();
                                }
                            }
                        })
                        .setNegativeButton("取消", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
        });
    }

    public void hideSoftKeyboard() {
        if (getActivity() != null && atvContent != null){
            //隐藏软键盘
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(atvContent.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void refreshData(List<ScheTimeEntity> list, Boolean isRefresh) {
        if (list == null || list.size() == 0) {
            return;
        }
        if (isRefresh != null && isRefresh == true){
            Utils.getInstance().showShortToast("刷新数据成功");
        }
        selectorAdapter = new SelectorAdapter(list, getActivity());
        rlSelect.setAdapter(selectorAdapter);
        rlSelect.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        autoDatas = new ArrayList<>();
        for (ScheTimeEntity entity : list) {
            if (entity == null) {
                continue;
            }
            autoDatas.add(entity.getBillno());
        }
        atvAdapter = new CustomArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, autoDatas);
        atvContent.setAdapter(atvAdapter);
    }

    public void reset(){
        if (atvContent != null){
            atvContent.setText("");
        }
    }
}
