package com.example.joe.qrfirefight.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.joe.qrfirefight.R;
import com.example.joe.qrfirefight.adapter.FirePagerAdapter;
import com.example.joe.qrfirefight.adapter.HistoryItemAdapter;
import com.example.joe.qrfirefight.base.BaseMvpActivity;
import com.example.joe.qrfirefight.database.DBManager;
import com.example.joe.qrfirefight.model.HistoryModel;
import com.example.joe.qrfirefight.model.BaseModel;
import com.example.joe.qrfirefight.present.MainPresent;
import com.example.joe.qrfirefight.utils.Utils;
import com.example.joe.qrfirefight.view.IMainView;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends BaseMvpActivity<IMainView, MainPresent> implements IMainView{
    private ViewPager vp_content;
    private TabLayout tlIndex;
    private FirePagerAdapter firePagerAdapter;
    private List<View> list_views;
    private List<String> list_titles;
    private EditText etQrCode;
    private RecyclerView rvHistory;
    private List<HistoryModel> models;
    private Button btnSubmitLocal;
    private CheckBox cbState;
    private DBManager dbManager;
    private final String WORKER_NUM = "worker_num";
    private TextView tvNum;
    private Button btnSync;
    private ProgressBar pbLoading;
    private final String TAG = "MainActivity";
    private AlertDialog alertDialog;
    private AlertDialog delDialog;
    private EditText etWorkNum;
    private HistoryItemAdapter historyItemAdapter;
    private Button btnLogout;
    private final int LOGIN_DIALOG = 1;
    private final int DEL_ITEM_DIALOG = 2;
    private int currDelPosition;
    private EditText etDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        initView();
        initData();
        showDialog();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainPresent createPresent() {
        return new MainPresent();
    }

    private void showDialog() {
        View contentView = View.inflate(MainActivity.this, R.layout.login_dialog_view, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("登录");
        builder.setView(contentView);
        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);

        initDialogListener(alertDialog, contentView);
    }

    private void showDelDialog(){
        View contentView = View.inflate(MainActivity.this, R.layout.login_dialog_view, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("删除条目");
        builder.setView(contentView);
        delDialog = builder.create();
        delDialog.show();

        etDel = contentView.findViewById(R.id.et_work_number);
        Button btnPos = contentView.findViewById(R.id.btnPos);
        btnPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vertifyQrCode(etDel, delDialog, DEL_ITEM_DIALOG);
            }
        });
        etDel.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                Log.i(TAG, "code:" + i + " Event:" + keyEvent.getKeyCode() + "hashCode:" + view.hashCode());
                if (KeyEvent.KEYCODE_ENTER == i) {
                    vertifyQrCode(etDel, delDialog, DEL_ITEM_DIALOG);
                    return true;
                }
                return false;
            }
        });
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (alertDialog != null && !alertDialog.isShowing()){
//            alertDialog.show();
//        if (etWorkNum != null){
//            etWorkNum.setText("");
//        }
//        }
//    }

    private void initDialogListener(final AlertDialog alertDialog, View contentView) {
        etWorkNum = contentView.findViewById(R.id.et_work_number);
        Button btnPos = contentView.findViewById(R.id.btnPos);
        Button btnNev = contentView.findViewById(R.id.btnNev);
        btnPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               vertifyQrCode(etWorkNum, alertDialog, LOGIN_DIALOG);
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
                    vertifyQrCode(etWorkNum, alertDialog, LOGIN_DIALOG) ;
                    return true;
                }
                return false;
            }
        });
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                Log.i(TAG, "dismiss--->>>");
                etQrCode.setOnKeyListener(new View.OnKeyListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        Log.i(TAG, "code:" + i + " Event:" + keyEvent.getKeyCode() + "hashCode:" + view.hashCode());
                        String text = etQrCode.getText() != null ? etQrCode.getText().toString() : "";
                        Log.i(TAG, "扫描数据:" + text);
                        if (KeyEvent.KEYCODE_ENTER == i && !text.equals("")){
                            btnSubmitLocal.setBackground(getResources().getDrawable(R.drawable.submit_data_change_bg));
                            return true;//使按键事件失效
                        }
                        return false;
                    }
                });
            }
        });
    }

    /**
     * 验证输入的工号是否合格
     * @param etWorkNum
     * @param alertDialog
     */
    public void vertifyQrCode(EditText etWorkNum, AlertDialog alertDialog, int code){
        String text = etWorkNum.getText() != null ? etWorkNum.getText().toString() : "";
        String pattern = "\\d{8}";
        boolean flag = Pattern.matches(pattern, text);
        if (!flag) {
            Utils.getInstance().showLongToast("你输入的工号不正确，请重新输入");
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etWorkNum.getWindowToken(), 0); //强制隐藏键盘
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            alertDialog.dismiss();
            etWorkNum.setText("");
            Utils.getInstance().saveStrSp(WORKER_NUM, text);
            if (code == LOGIN_DIALOG){
                Utils.getInstance().showShortToast("登录成功");
            }else if (code == DEL_ITEM_DIALOG) {
                //删除条目
                if (models == null || models.size() == 0){
                    Utils.getInstance().showShortToast("删除失败");
                    return;
                }
                if (models.get(currDelPosition) != null){
                    dbManager.delete(models.get(currDelPosition).getEquipmentid());
                }
                models.remove(currDelPosition);
                historyItemAdapter.notifyDataSetChanged();
                tvNum.setText("条数：" + String.valueOf(models.size()));
                Utils.getInstance().showShortToast("删除成功");
            }
        }
    }

    private void initView() {
        vp_content = findViewById(R.id.vp_content);
        tlIndex = findViewById(R.id.tl_index);
        View view1 = View.inflate(MainActivity.this, R.layout.view_submit, null);
        View view2 = View.inflate(MainActivity.this, R.layout.view_history, null);
        btnLogout = view1.findViewById(R.id.btnLogout);
        rvHistory = view2.findViewById(R.id.rvHistory);
        etQrCode = view1.findViewById(R.id.et_qr_code);
        etQrCode.clearFocus();
        cbState = view1.findViewById(R.id.cbState);
        list_views = new ArrayList<View>();
        list_views.add(view1);
        list_views.add(view2);
        list_titles = new ArrayList<String>();
        list_titles.add("扫码");
        list_titles.add("数据同步");
        btnSubmitLocal = view1.findViewById(R.id.btnSubmitLocal);
        tvNum = view2.findViewById(R.id.tvNum);
        btnSync = view2.findViewById(R.id.btnSync);
        pbLoading = view2.findViewById(R.id.pbLoading);
    }

    private void initData() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setMessage("是否注销")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.show();
                            }
                        })
                        .setNegativeButton("取消", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
        });
        btnSync.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (KeyEvent.KEYCODE_ENTER == i) {
                    return true;
                }
                return false;
            }
        });
        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断网络状态
                if (!isNetworkConnected(MainActivity.this)){
                    Utils.getInstance().showLongToast("当前网络不可用，请检查网络！！！");
                    return;
                }
                pbLoading.setVisibility(View.VISIBLE);
                List<HistoryModel> models = dbManager.queryAllData();
                Log.i(TAG, "models:" + new Gson().toJson(models));
                mPresent.submitHisMsg(models);
            }
        });
        dbManager = new DBManager(MainActivity.this);
        firePagerAdapter = new FirePagerAdapter(list_views, list_titles);
        vp_content.setAdapter(firePagerAdapter);
        tlIndex.setupWithViewPager(vp_content);
        btnSubmitLocal.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                String equipStr = etQrCode.getText() != null ? etQrCode.getText().toString() : "";
                if (!"".equals(equipStr)){
                    //提交到数据库
                    HistoryModel historyModel = new HistoryModel();
                    historyModel.setCreatedate(getCurrentTime());
                    historyModel.setCreateuserid(Utils.getInstance().getStrSp(WORKER_NUM));
                    historyModel.setEquipmentid(equipStr);
                    historyModel.setQualifiedstate(cbState.isChecked() == true ? 1 : 0);
                    HistoryModel oldModel  = dbManager.query(historyModel.getEquipmentid());
                    if (oldModel != null){
                        int state = oldModel.getQualifiedstate();
                        if (state == historyModel.getQualifiedstate()){
                            Utils.getInstance().showLongToast("该设备已存在，请不要重复添加");
                        }else {
                            Utils.getInstance().showLongToast("添加成功");
                            dbManager.update(historyModel);
                        }
                    } else{
                        Utils.getInstance().showLongToast("添加成功");
                        dbManager.insert(historyModel);
                    }

                }

                btnSubmitLocal.setBackground(getResources().getDrawable(R.drawable.submit_data_bg));
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                etQrCode.setText("");
                cbState.setChecked(true);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            }
        });
        tlIndex.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i(TAG, "position:" + tab.getPosition());
                int position = tab.getPosition();
                if (position == 1){
                    //历史记录数据
                    models = dbManager.queryAllData();
                    if (models != null){
                        historyItemAdapter = new HistoryItemAdapter(models);
                        rvHistory.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                        rvHistory.setAdapter(historyItemAdapter);
                        tvNum.setText("条数：" + String.valueOf(models.size()));

                        historyItemAdapter.setOnItemDelClickListener(new HistoryItemAdapter.OnItemDelClickListener() {
                            @Override
                            public void onItemDelClickListener(int position) {
                                if (models != null && models.size() > position){
                                    currDelPosition = position;
                                    showDelDialog();
                                }else {
                                    tvNum.setText("条数：" + String.valueOf(0));
                                }

                            }
                        });
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    /**
     * 判断当前网络连通性
     * @param context
     * @return
     */
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取当前时间
     */
    public String getCurrentTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    @Override
    public void uploadEquipMsgSuccess(BaseModel uploadHistoryModel) {
        pbLoading.setVisibility(View.INVISIBLE);
        if (uploadHistoryModel == null){
            Utils.getInstance().showLongToast("上传异常");
            return;
        }
        int code = uploadHistoryModel.getCode();
        if (code == 1){
            dbManager.deleteAllData();
            //清空列表数据
            models.clear();
            rvHistory.getAdapter().notifyDataSetChanged();
            tvNum.setText("条数：0");
            Utils.getInstance().showLongToast("上传成功");
        }else {
            Utils.getInstance().showLongToast("上传失败");
        }
    }

    @Override
    public void uploadEquipMsgFailed(Throwable throwable) {
        pbLoading.setVisibility(View.INVISIBLE);
        Log.i(TAG, "upload failed msg:" + new Gson().toJson(throwable));
        Utils.getInstance().showLongToast("上传失败");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

