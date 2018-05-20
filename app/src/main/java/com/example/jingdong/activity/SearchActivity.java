package com.example.jingdong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jingdong.R;

public class SearchActivity extends AppCompatActivity {

    private ImageView img_searchback;
    /**
     * 请输入搜索内容
     */
    private EditText ed_keywords;
    /**
     * 搜索
     */
    private TextView tv_sou;
    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();

        tv_sou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得关键字
                keyword = ed_keywords.getText().toString().trim();
                if (!TextUtils.isEmpty(keyword)){
                    Intent intent = new Intent(SearchActivity.this, SearchListActivity.class);
                    intent.putExtra("keyword", keyword);
                    startActivity(intent);
                }else {
                    Toast.makeText(SearchActivity.this, "请输入搜索内容!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initView() {
        img_searchback = (ImageView) findViewById(R.id.img_searchback);
        ed_keywords = (EditText) findViewById(R.id.ed_keywords);
        tv_sou = (TextView) findViewById(R.id.tv_sou);
        //返回
        img_searchback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
