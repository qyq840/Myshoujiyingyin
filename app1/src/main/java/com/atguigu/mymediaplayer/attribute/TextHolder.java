package com.atguigu.mymediaplayer.attribute;

import android.view.View;
import android.widget.TextView;

import com.atguigu.mymediaplayer.R;
import com.atguigu.mymediaplayer.bean.ListBeanItem;

/**
 * Created by  on 2017/1/16.
 */

public class TextHolder extends BaseViewHolder {
    TextView tvContext;

    public TextHolder(View convertView) {
        super(convertView);
        //中间公共部分 -所有的都有
        tvContext = (TextView) convertView.findViewById(R.id.tv_context);


    }

    public void setData(ListBeanItem.ListBean mediaItem) {
        super.setData(mediaItem);
        //设置文本-所有的都有
        tvContext.setText(mediaItem.getText() + "_" + mediaItem.getType());
    }

}
