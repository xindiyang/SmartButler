package com.example.codoon.smartbutler.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.codoon.smartbutler.R;
import com.example.codoon.smartbutler.utils.L;
import com.example.codoon.smartbutler.utils.StaticClass;
import com.kymjs.okhttp3.OkHttpStack;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.http.RequestQueue;

import okhttp3.OkHttpClient;

/**
 * 创建者：Sunday
 * 项目名：SmartButler
 * 包名：com.example.codoon.smartbutler.ui
 * 文件名：CourierACtivity
 * 创建时间：2018/11/19 下午4:21
 * 描述：快递查询
 */
public class CourierACtivity extends BaseActivity implements View.OnClickListener {
    private EditText et_name, et_number;
    private Button courier_btn;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);
        initView();
    }

    private void initView() {
        et_name = findViewById(R.id.et_name);
        et_number = findViewById(R.id.et_number);
        courier_btn = findViewById(R.id.query_btn);
        recyclerView = findViewById(R.id.recyclerView);
        courier_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.query_btn:
                /**
                 * 1.获取输入框的内容
                 * 2.判断是否为空
                 * 3.拿到数据请求json
                 * 4.解析json
                 * 5.recyclerView适配器
                 * 6.实体类item
                 * 7.设置数据显示效果
                 */
                //1.获取输入框的内容
                String name = et_name.getText().toString().trim();
                String number = et_number.getText().toString().trim();
                String url = "http://v.juhe.cn/exp/index?key="
                        + StaticClass.COURIER_KEY + "&com=" + name + "&no=" + number;
                //2.判断是否为空
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)) {
                    //3.拿到数据请求json
                    RxVolley.setRequestQueue(RequestQueue.newRequestQueue(RxVolley.CACHE_FOLDER,
                            new OkHttpStack(new OkHttpClient())));
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            Toast.makeText(CourierACtivity.this, t, Toast.LENGTH_SHORT).show();
                            L.i("json" + t);
                            super.onSuccess(t);
                        }
                    });

                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
        }

    }
}
