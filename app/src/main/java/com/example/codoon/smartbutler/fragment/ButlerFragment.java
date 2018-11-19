package com.example.codoon.smartbutler.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codoon.smartbutler.R;

/**
 * 创建者：Sunday
 * 项目名：SmartButler
 * 包名：com.example.codoon.smartbutler.fragment
 * 文件名：ButlerFragment
 * 创建时间：2018/10/8 上午11:35
 * 描述：TODO
 */

public class ButlerFragment extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.butler_frgament,null);
        return view;
    }
}
