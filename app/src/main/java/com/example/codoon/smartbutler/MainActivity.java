package com.example.codoon.smartbutler;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.codoon.smartbutler.fragment.ButlerFragment;
import com.example.codoon.smartbutler.fragment.GirlFragment;
import com.example.codoon.smartbutler.fragment.UserFragment;
import com.example.codoon.smartbutler.fragment.WeChatFragment;
import com.example.codoon.smartbutler.ui.SettingActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //TabLayout
    private TabLayout tabLayout;
    //ViewPager
    private ViewPager viewPager;
    //Title
    private List<String> title;
    //Fragment
    private List<Fragment> fragments;
    //悬浮窗
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //去掉阴影
        getSupportActionBar().setElevation(0);
        initData();
        initView();
    }

    //初始化View
    private void initView() {
        floatingActionButton = findViewById(R.id.fab_setting);
        //默认隐藏
        floatingActionButton.setVisibility(View.GONE);
        floatingActionButton.setOnClickListener(this);
        tabLayout = findViewById(R.id.mTablayOut);
        viewPager = findViewById(R.id.mViewPager);
        //viewpager预加载
        viewPager.setOffscreenPageLimit(fragments.size());
        //viewpager的滑动监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("TAG", "position:" + position);
                if (position == 0) {
                    floatingActionButton.setVisibility(View.GONE);
                } else {
                    floatingActionButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //viewpager设置适配器
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            //返回item的个数
            @Override
            public int getCount() {
                return fragments.size();
            }

            //设置标题
            @Override
            public CharSequence getPageTitle(int position) {
                return title.get(position);
            }
        });
        //绑定
        tabLayout.setupWithViewPager(viewPager);
    }

    //初始化数据
    private void initData() {
        title = new ArrayList<>();
        title.add("服务管家");
        title.add("微信精选");
        title.add("图片社区");
        title.add("个人中心");
        fragments = new ArrayList<>();
        fragments.add(new ButlerFragment());
        fragments.add(new WeChatFragment());
        fragments.add(new GirlFragment());
        fragments.add(new UserFragment());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
        }
    }
}
