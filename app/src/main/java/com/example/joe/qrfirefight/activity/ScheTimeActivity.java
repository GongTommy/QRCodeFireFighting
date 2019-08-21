package com.example.joe.qrfirefight.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.example.joe.qrfirefight.R;
import com.example.joe.qrfirefight.adapter.SelPagerAdapter;
import com.example.joe.qrfirefight.base.BaseMvpActivity;
import com.example.joe.qrfirefight.fragment.ScheDetailFragment;
import com.example.joe.qrfirefight.fragment.ScheSubmitFragment;
import com.example.joe.qrfirefight.fragment.ScheTimeFragment;
import com.example.joe.qrfirefight.model.BaseModel;
import com.example.joe.qrfirefight.model.ScheTimeDetailEntity;
import com.example.joe.qrfirefight.model.ScheTimeEntity;
import com.example.joe.qrfirefight.model.ScheTimeSubmitEntity;
import com.example.joe.qrfirefight.present.ScheTimePresent;
import com.example.joe.qrfirefight.utils.Utils;
import com.example.joe.qrfirefight.view.IScheTimeView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by 18145288 on 19/6/2019.
 */

public class ScheTimeActivity extends BaseMvpActivity<IScheTimeView, ScheTimePresent>
        implements IScheTimeView{
    private String TAG = "ScheTimeActivity";
    private ViewPager vpContent;
    private List<Fragment> fragments;
    private ScheTimeFragment fragment1;
    private ScheDetailFragment fragment2;
    private ScheSubmitFragment fragment3;
    private ProgressBar pb;
    private AlertDialog alertDialog;
    private EditText etWorkNum;
    public  String WORKER_NUM = "worker_num";
    private ScheTimeSubmitEntity scheTimeSubmitEntity = new ScheTimeSubmitEntity();
    private List<ScheTimeEntity> scheTimeEntities;
    private Boolean isRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        showDialog();
    }

    private void showDialog() {
        View contentView = View.inflate(ScheTimeActivity.this, R.layout.login_dialog_view, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ScheTimeActivity.this);
        builder.setTitle("登录");
        builder.setView(contentView);
        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);

        initDialogListener(alertDialog, contentView);
    }

    public void showAlertDialog(){
        if (alertDialog != null){
            alertDialog.show();
        }
    }

    private void initDialogListener(final AlertDialog alertDialog, View contentView) {
        if (alertDialog == null || contentView == null){
            return;
        }
        etWorkNum = contentView.findViewById(R.id.et_work_number);
        Button btnPos = contentView.findViewById(R.id.btnPos);
        Button btnNev = contentView.findViewById(R.id.btnNev);
        btnPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vertifyQrCode(etWorkNum, alertDialog);
            }
        });
        btnNev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        etWorkNum.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                Log.i(TAG, "code:" + i + " Event:" + keyEvent.getKeyCode() + "hashCode:" + view.hashCode());
                if (KeyEvent.KEYCODE_ENTER == i) {
                    vertifyQrCode(etWorkNum, alertDialog);
                    return true;
                }
                return false;
            }
        });
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etWorkNum.getWindowToken(), 0); //强制隐藏键盘
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            }
        });
    }

    /**
     * 验证输入的工号是否合格
     * @param etWorkNum
     * @param alertDialog
     */
    public void vertifyQrCode(EditText etWorkNum, AlertDialog alertDialog){
        String text = etWorkNum.getText() != null ? etWorkNum.getText().toString() : "";
        String pattern = "\\d{8}";
        boolean flag = Pattern.matches(pattern, text);
        if (!flag) {
            Utils.getInstance().showShortToast("你输入的工号不正确，请重新输入");
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

            alertDialog.dismiss();
            etWorkNum.setText("");
            Utils.getInstance().saveStrSp(WORKER_NUM, text);
            Utils.getInstance().showShortToast("登录成功");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.schetime_activity;
    }

    @Override
    public ScheTimePresent createPresent() {
        return new ScheTimePresent();
    }

    private void initView() {
        vpContent = findViewById(R.id.vpContent);
    }

    private void initData(){
        fragments = new ArrayList<>();
        fragment1 = new ScheTimeFragment();
        fragment2 = new ScheDetailFragment();
        fragment3 = new ScheSubmitFragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        SelPagerAdapter selPagerAdapter = new SelPagerAdapter(getSupportFragmentManager(), fragments);
        vpContent.setAdapter(selPagerAdapter);
        vpContent.setCurrentItem(0);
        vpContent.setOffscreenPageLimit(3);
        vpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0 && fragment1 != null){
                    fragment1.reset();
                }

                if (position == 1){
                    if (fragment1 != null){
                        fragment1.hideSoftKeyboard();
                    }
                    if (fragment3 != null){
                        fragment3.hideSoftKeyboard();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pb = findViewById(R.id.pb);

        mPresent.getScheTimeDatas();
        pb.setVisibility(View.VISIBLE);
    }

    public ViewPager getViewPager(){
        return vpContent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String from = intent.getStringExtra("from");
        if (from != null && from.equals("search")){
            vpContent.setCurrentItem(1);
        }
    }

    @Override
    public void getScheTimeDatasSuccess(List<ScheTimeEntity> list) {
        scheTimeEntities = list;
        fragment1.refreshData(list, isRefresh);
        isRefresh = false;
        if (pb != null){
            pb.setVisibility(View.GONE);
        }
    }

    @Override
    public void getScheTimeDatasFailed(Throwable throwable) {
        if (pb != null){
            pb.setVisibility(View.GONE);
        }
        Utils.getInstance().showShortToast("获取数据失败");
    }

    @Override
    public void getScheTimeDetailDataSuccess(List<ScheTimeDetailEntity> list) {
        if (list == null || list.size() == 0) {
            Utils.getInstance().showShortToast("獲取詳細數據為空");
            return;
        }
        fragment2.refreshDatasDetail(list);
        vpContent.setCurrentItem(1);
        if (pb != null){
            pb.setVisibility(View.GONE);
        }
    }

    @Override
    public void getScheTimeDetailDataFailed(Throwable throwable) {
        Log.i(TAG, "getScheTimeDetailDataFailed");
        Utils.getInstance().showShortToast("獲取詳細數據失敗");
        if (pb != null){
            pb.setVisibility(View.GONE);
        }
    }

    @Override
    public void submitScheTimeDatasSuccess(){
        vpContent.setCurrentItem(0);
        Utils.getInstance().showShortToast("提交成功");
        if (pb != null){
            pb.setVisibility(View.GONE);
        }
        mPresent.getScheTimeDatas();
        fragment1.clearAtv();
    }

    @Override
    public void submitScheTimeDatasFailed() {
        if (pb != null){
            pb.setVisibility(View.GONE);
        }
        Utils.getInstance().showShortToast("提交失败");
    }

    public void checkQrCodeExist(String billNo, String localQrCodes){
        if (mPresent == null || pb == null){
            return;
        }
        mPresent.schedulDataCheck(billNo, localQrCodes);
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void schedulDataCheckSuccess(BaseModel baseModel) {
        if (pb != null){
            pb.setVisibility(View.INVISIBLE);
        }
        if (baseModel == null){
            Utils.getInstance().showLongToast("网络错误");
            return;
        }
        String msg = baseModel.getMsg();
        int code = baseModel.getCode();
        if (code == 1){
            fragment3.addDataToList();
        }else {
            Utils.getInstance().showLongToast(msg != null ? msg : "");
        }
    }

    @Override
    public void schedulDataCheckFailed(Throwable throwable) {
        if (pb != null){
            pb.setVisibility(View.INVISIBLE);
        }
        Utils.getInstance().showLongToast("网络错误");
    }

    public void refreshScheTimeDatas(){
        if (mPresent == null){
            return;
        }
        isRefresh = true;
        pb.setVisibility(View.VISIBLE);
        mPresent.getScheTimeDatas();
    }

    public void getScheTimeDataDetail(String billNo){
        if (billNo == null || billNo.equals("")){
            return;
        }
        if (mPresent == null){
            return;
        }
        if (pb != null) {
            pb.setVisibility(View.VISIBLE);
        }
        mPresent.getScheTimeDetailData(billNo);

        scheTimeSubmitEntity.setBillno(billNo);
        handleSubmitEntity(billNo);
        if (fragment2 != null){
            fragment2.setBillNo(billNo);
        }
    }

    private void handleSubmitEntity(String billNo){
        if (billNo == null || billNo.equals("")){
            return;
        }
        if (scheTimeEntities != null && scheTimeEntities.size() > 0){
            for(int i = 0; i < scheTimeEntities.size(); i++){
                ScheTimeEntity entity = scheTimeEntities.get(i);
                if (entity != null){
                    String no = entity.getBillno();
                    if (no != null && no.equals(billNo)){
                        String date = entity.getBilldate();
                        scheTimeSubmitEntity.setBilldate(date);
                    }
                }
            }
        }
    }

    public void refreshSubmitPage(){
        vpContent.setCurrentItem(2);
        fragment3.setCurrentBillNo(scheTimeSubmitEntity);
    }

    public void submitBillDatas(List<ScheTimeSubmitEntity> list){
        if (mPresent == null){
            return;
        }
        mPresent.submitScheTimeDatas(list);
        Log.i(TAG, "Submit Data:" + new Gson().toJson(list));
        pb.setVisibility(View.VISIBLE);
    }

    public void hideSoftKeyboard(View view) {
        //隐藏软键盘
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (alertDialog != null && !alertDialog.isShowing()){
//            alertDialog.show();
//        if (etWorkNum != null) {
//            etWorkNum.setText("");
//        }
//        }
//    }
}
