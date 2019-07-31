package com.example.joe.qrfirefight.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.joe.qrfirefight.R;
import com.example.joe.qrfirefight.activity.ScheTimeActivity;
import com.example.joe.qrfirefight.adapter.ScheHisAdapter;
import com.example.joe.qrfirefight.model.ScheTimeDetailEntity;
import com.example.joe.qrfirefight.model.ScheTimeEntity;
import com.example.joe.qrfirefight.model.ScheTimeSubmitEntity;
import com.example.joe.qrfirefight.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by 18145288 on 2019/6/26.
 */

public class ScheSubmitFragment extends Fragment implements View.OnClickListener{
    private Button btnPre, btnNext;
    private Button btnSubmit;
    private ViewPager parentVp;
    private RecyclerView rvScheTime;
    private List<ScheTimeSubmitEntity> scheTimeDatas;
    private TextView tvNum;
    private EditText etScheCode;
    private int addTime;//扫码一次会出现两次回车键，禁止此情况
    private ScheTimeActivity scheTimeActivity;
    private ScheTimeSubmitEntity scheTimeDetailEntity;
    public  String WORKER_NUM = "worker_num";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sche_submit_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        rvScheTime = view.findViewById(R.id.rvScheTime);
        btnPre = view.findViewById(R.id.btnPre);
        tvNum = view.findViewById(R.id.tvTimeNum);
        btnNext = view.findViewById(R.id.btnNext);
        etScheCode = view.findViewById(R.id.etScheCode);
        scheTimeActivity = (ScheTimeActivity) getActivity();
        if (scheTimeActivity != null) {
            parentVp = scheTimeActivity.getViewPager();
        }
        initData();
    }

    public void initData(){
        btnNext.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnNext.setEnabled(false);
        scheTimeDatas = new ArrayList<>();
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
        etScheCode.requestFocus();
        etScheCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (KeyEvent.KEYCODE_ENTER == i) {
                    if (addTime == 1) {
                        addTime = 0;
                        return true;
                    }
                    addTime++;
                    ScheTimeSubmitEntity entity = new ScheTimeSubmitEntity();
                    if (scheTimeDetailEntity != null){
                        entity.setBillno(scheTimeDetailEntity.getBillno());
                        entity.setBilldate(scheTimeDetailEntity.getBilldate());
                    }
                    if (etScheCode != null && etScheCode.getText() != null){
                        entity.setPackbarcode(etScheCode.getText().toString());
                    }
                    String date = dateToStrLong(new Date());
                    String userID = Utils.getInstance().getStrSp(WORKER_NUM);
                    entity.setCreateuserid(userID);
                    entity.setCreateusername(userID);
                    entity.setUploaddate(date);
                    scheTimeDatas.add(entity);
                    scheHisAdapter.notifyDataSetChanged();
                    etScheCode.setText("");
                    tvNum.setText(String.valueOf(scheTimeDatas.size()));
                    return true;
                }
                return false;
            }
        });
    }

    public static String dateToStrLong(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
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
            case R.id.btnSubmit:
                scheTimeActivity.submitBillDatas(scheTimeDatas);
                //数据上传成功之后，清空数据库，清空列表，条数重置为0
                scheTimeDatas.clear();
                rvScheTime.getAdapter().notifyDataSetChanged();
                tvNum.setText("0");
                break;
        }
    }

    public void setCurrentBillNo(ScheTimeSubmitEntity scheTimeDetailEntity) {
        this.scheTimeDetailEntity = scheTimeDetailEntity;
    }

    public void hideSoftKeyboard() {
        if (getActivity() != null && etScheCode != null){
            //隐藏软键盘
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(etScheCode.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
