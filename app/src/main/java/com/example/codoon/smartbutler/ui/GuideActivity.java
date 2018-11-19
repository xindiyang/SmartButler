package com.example.codoon.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.codoon.smartbutler.MainActivity;
import com.example.codoon.smartbutler.R;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：Sunday
 * 项目名：SmartButler
 * 包名：com.example.codoon.smartbutler.ui
 * 文件名：GuideActivity
 * 创建时间：2018/11/6 下午6:41
 * 描述：引导页
 */
public class GuideActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    //容器
    private List<View> viewList = new ArrayList<>();
    private View view1, view2, view3;
    private ImageView point1, point2, point3, iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
    }

    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        view1 = View.inflate(this, R.layout.pager_item_one, null);
        view2 = View.inflate(this, R.layout.pager_item_two, null);
        view3 = View.inflate(this, R.layout.pager_item_three, null);
        view3.findViewById(R.id.btn_start).setOnClickListener(this);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewPager.setAdapter(new GuideAdapter());
        point1 = findViewById(R.id.point1);
        point2 = findViewById(R.id.point2);
        point3 = findViewById(R.id.point3);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        //设置默认图片
        setPointImage(true, false, false);
        //监听viewPager滑动
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //pager切换的回调
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setPointImage(true, false, false);
                        iv_back.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setPointImage(false, true, false);
                        iv_back.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setPointImage(false, false, true);
                        iv_back.setVisibility(View.GONE);
                        break;
                    default:
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    //设置小圆点的选中效果
    private void setPointImage(boolean isCheck1, boolean isCheck2, boolean isCheck3) {
        if (isCheck1) {
            point1.setBackgroundResource(R.drawable.point_on);
        } else {
            point1.setBackgroundResource(R.drawable.point_off);
        }

        if (isCheck2) {
            point2.setBackgroundResource(R.drawable.point_on);
        } else {
            point2.setBackgroundResource(R.drawable.point_off);
        }

        if (isCheck3) {
            point3.setBackgroundResource(R.drawable.point_on);
        } else {
            point3.setBackgroundResource(R.drawable.point_off);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startActivity(new Intent(this, MainActivity.class));
            case R.id.iv_back:
                startActivity(new Intent(this, MainActivity.class));

        }
    }

    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(viewList.get(position));
        }
    }

}
