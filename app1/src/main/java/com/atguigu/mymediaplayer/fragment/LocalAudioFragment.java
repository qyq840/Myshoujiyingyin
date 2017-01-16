package com.atguigu.mymediaplayer.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atguigu.mymediaplayer.R;
import com.atguigu.mymediaplayer.ShowImageAndGifActivity;
import com.atguigu.mymediaplayer.adapter.NetAudioFragmentAdapter;
import com.atguigu.mymediaplayer.basefragment.BaseFragment;
import com.atguigu.mymediaplayer.bean.ListBeanItem;
import com.atguigu.mymediaplayer.utils.CacheUtils;
import com.atguigu.mymediaplayer.utils.Constant;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by 进击的程序猿 on 2017/1/16.
 */

public class LocalAudioFragment extends BaseFragment {
    private static final String TAG = LocalAudioFragment.class.getSimpleName();
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.progressbar)
    ProgressBar progressbar;
    @Bind(R.id.tv_no_media)
    TextView tvNomedia;
    @Bind(R.id.refresh)
    MaterialRefreshLayout refreshLayout;

    private NetAudioFragmentAdapter myAdapter;


    @Override
    public View initView() {

        View view = View.inflate(mContext, R.layout.fragment_local_audio, null);
        //把view注入到xUtils3框中
        ButterKnife.bind(this, view);
        refreshLayout.setMaterialRefreshListener(new MyMaterialRefreshListener());
        return view;

    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "本地音频数据初始化了。。");

        String saveJson = CacheUtils.getString(mContext, Constant.NET_URL);
        Log.e("TAG", saveJson);
        if (!TextUtils.isEmpty(saveJson)) {
            processData(saveJson);
        }

        getDataFromNet();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                ListBeanItem.ListBean listEntity = datas.get(position);
                if (listEntity != null) {
                    //3.传递视频列表
                    Intent intent = new Intent(mContext, ShowImageAndGifActivity.class);
                    if (listEntity.getType().equals("gif")) {
                        String url = listEntity.getGif().getImages().get(0);
                        intent.putExtra("url", url);
                        mContext.startActivity(intent);
                    } else if (listEntity.getType().equals("image")) {
                        String url = listEntity.getImage().getBig().get(0);
                        intent.putExtra("url", url);
                        mContext.startActivity(intent);
                    }
                }
            }
        });

    }

    class MyMaterialRefreshListener extends MaterialRefreshListener {

        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            //            Toast.makeText(mContext, "下拉刷新", Toast.LENGTH_SHORT).show();
            getDataFromNet();
        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            super.onRefreshLoadMore(materialRefreshLayout);
            //Toast.makeText(mContext, "加载更多", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataFromNet() {
        RequestParams reques = new RequestParams(Constant.NET_URL);
        x.http().get(reques, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Log.e("TA", "xUtils3联网请求成功==" + result);
                Log.e("TAG", "线程名称==" + Thread.currentThread().getName());
                CacheUtils.putString(mContext, Constant.NET_URL, result);
                LogUtil.e("onSuccess==" + result);

                processData(result);
                refreshLayout.finishRefresh();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "xUtils3请求失败了==" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    List<ListBeanItem.ListBean> datas;

    private void processData(String json) {
        datas = paraseJson(json);

        //LogUtil.e(netAudioBean.getList().get(0).getText()+"-----------");

        Log.e("TAG", "datas的数据是" + datas);
        if (datas != null && datas.size() > 0) {
            //有视频
            tvNomedia.setVisibility(View.GONE);
            //设置适配器
            myAdapter = new NetAudioFragmentAdapter(mContext, datas);
            listview.setAdapter(myAdapter);
        } else {
            //没有视频
            tvNomedia.setVisibility(View.VISIBLE);
        }

        progressbar.setVisibility(View.GONE);
    }

    /**
     * +     * 使用系统的接口解析json数据
     * +     * @param json
     * +     * @return
     * +
     */
    private List<ListBeanItem.ListBean> paraseJson(String json) {
        ListBeanItem netAudioBean = new Gson().fromJson(json, ListBeanItem.class);
        return netAudioBean.getList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}



