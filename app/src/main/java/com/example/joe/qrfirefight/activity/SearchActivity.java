package com.example.joe.qrfirefight.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import com.example.joe.qrfirefight.R;

public class SearchActivity extends Activity {
    private AutoCompleteTextView atvContent;
    private ImageView ivBack;
    private static final String[] avtData = new String[]{
            "刘德华", "刘亦菲", "黄家驹", "黄晓明", "刘备"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        initView();
        initData();
    }

    private void initView() {
        atvContent = findViewById(R.id.atvContent);
        ivBack = findViewById(R.id.ivBack);
    }

    private void initData(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, avtData);
        atvContent.setAdapter(adapter);
        atvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //隐藏软键盘
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(atvContent.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                Intent intent = new Intent(SearchActivity.this, ScheTimeActivity.class);
                intent.putExtra("from", "search");
                intent.putExtra("content", avtData[position]);
                startActivity(intent);
                finish();
            }
        });
    }
}
