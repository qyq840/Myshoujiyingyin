package com.atguigu.mymediaplayer.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.mymediaplayer.basefragment.BaseFragment;

/**
 * Created by 进击的程序猿 on 2017/1/16.
 */

public class NetVideoFragment extends BaseFragment {
    private TextView textView;

    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        return textView;

    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "本地音频数据初始化了。。");
        textView.setText("第二个fragment");
    }
}
