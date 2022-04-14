package com.example.myapplication.ui.fragment;

import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.bean.BannerBean;
import com.example.myapplication.bean.Mesevent;
import com.example.myapplication.bean.NewType;
import com.example.myapplication.bean.NewsListBean;
import com.example.myapplication.bean.ServerBean;
import com.example.myapplication.bean.User;
import com.example.myapplication.network.OkhttpUtil;
import com.example.myapplication.network.Okhttplim;
import com.example.myapplication.ui.activity.KDYActivity;
import com.example.myapplication.ui.activity.MainActivity;
import com.example.myapplication.ui.activity.NewsXqActivity;
import com.example.myapplication.ui.activity.ZHBSACtivity;
import com.example.myapplication.ui.base.BaseFragment;
import com.example.myapplication.util.MyBaseAdapter;
import com.example.myapplication.util.MyGridView;
import com.example.myapplication.util.MyListView;
import com.example.myapplication.util.ViewHolde;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class Fragment1 extends BaseFragment {
    private ImageView mLeftIcon;
    private TextView mTitle;
    private ImageView mRightIcon;
    private Banner mBanner;
    private MyGridView mMgv;
    private RadioGroup mRg;
    private RadioButton mRb1;
    private RadioButton mRb2;
    private RadioButton mRb3;
    private RadioButton mRb4;
    private RadioButton mRb5;
    private RadioButton mRb6;
    private MyListView mMlv;
    private String[] servername = {"首页", "数据分析", "智慧党建", "个人中心"};
    private int[] icons = {R.drawable.ic_home_24, R.drawable.ic_baseline_data_24, R.drawable.ic_baseline_mood_24
            , R.drawable.ic_baseline_user_circle_24};

    @Override
    protected void initView() {

        mLeftIcon = getView().findViewById(R.id.left_icon);
        mTitle = getView().findViewById(R.id.title);
        mRightIcon = getView().findViewById(R.id.right_icon);
        mBanner = getView().findViewById(R.id.banner);
        mMgv = getView().findViewById(R.id.mgv);
        mRg = getView().findViewById(R.id.rg);
        mRb1 = getView().findViewById(R.id.rb1);
        mRb2 = getView().findViewById(R.id.rb2);
        mRb3 = getView().findViewById(R.id.rb3);
        mRb4 = getView().findViewById(R.id.rb4);
        mRb5 = getView().findViewById(R.id.rb5);
        mRb6 = getView().findViewById(R.id.rb6);
        mMlv = getView().findViewById(R.id.mlv);
          mTitle.setText("主页");
    }

    @Override
    protected void initData() {
        setBanner();
        setServer();
        setNewType();
    }

    private void setNewType() {
        OkhttpUtil okhttpUtil = new OkhttpUtil();
        okhttpUtil.setGet(User.URI + "/prod-api/press/category/list", "");
        okhttpUtil.setOkhttplim(new Okhttplim() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

            @Override
            public void getString(String s) {
                NewType newType = new Gson().fromJson(s, NewType.class);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<NewType.DataDTO> rows = newType.getData();
                        mRb1.setText(rows.get(0).getName());
                        mRb2.setText(rows.get(1).getName());
                        mRb3.setText(rows.get(2).getName());
                        mRb4.setText(rows.get(3).getName());
                        mRb5.setText(rows.get(4).getName());
                        mRb6.setText(rows.get(5).getName());
                        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                RadioButton mRb = mRg.findViewById(i);
                                int i1 = mRg.indexOfChild(mRb);
                                int id = rows.get(i1).getId();
                                setNewList(id);
                            }
                        });
                        mRb1.setChecked(true);
                    }
                });
            }
        });
    }

    private void setNewList(int id) {
        OkhttpUtil okhttpUtil = new OkhttpUtil();
        okhttpUtil.setGet(User.URI + "/prod-api/press/press/list?type=" + id, "");
        okhttpUtil.setOkhttplim(new Okhttplim() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

            @Override
            public void getString(String s) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        NewsListBean newsListBean = new Gson().fromJson(s, NewsListBean.class);
                        List<NewsListBean.RowsDTO> rows = newsListBean.getRows();
                        mMlv.setAdapter(new MyBaseAdapter<NewsListBean.RowsDTO>(rows, R.layout.newslist, rows.size()) {
                            private ImageView mIv;
                            private TextView mTv4;
                            private TextView mTv3;
                            private TextView mTv2;
                            private TextView mTv1;
                            @Override
                            protected void conerview(ViewHolde viewHolde, NewsListBean.RowsDTO rowsDTO, int i) {
                                mTv1 = viewHolde.getView(R.id.tv1);
                                mTv2 = viewHolde.getView(R.id.tv2);
                                mTv3 = viewHolde.getView(R.id.tv3);
                                mTv4 = viewHolde.getView(R.id.tv4);
                                mIv = viewHolde.getView(R.id.iv);
                                Glide.with(mActivity).load(User.URI+rowsDTO.getCover())
                                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                                        .into(mIv);
                                mTv1.setText(rowsDTO.getTitle());
                                mTv2.setText("\t\t"+Html.fromHtml(rowsDTO.getContent()));
                                mTv3.setText("评论总数："+rowsDTO.getCommentNum());
                                mTv4.setText("发布时间："+rowsDTO.getPublishDate());



                            }
                        });
                        mMlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Mesevent mesevent=new Mesevent();
                                mesevent.setNewSID(String.valueOf(rows.get(i).getId()));
                                EventBus.getDefault().postSticky(mesevent);
                                goActivity(NewsXqActivity.class);
                            }
                        });
                    }
                });
            }
        });
    }

    private void setServer() {
        OkhttpUtil okhttpUtil = new OkhttpUtil();
        okhttpUtil.setGet(User.URI + "/prod-api/api/service/list", "");
        okhttpUtil.setOkhttplim(new Okhttplim() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

            @Override
            public void getString(String s) {
                ServerBean serverBean = new Gson().fromJson(s, ServerBean.class);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<ServerBean.RowsDTO> rows = serverBean.getRows();
                        ServerBean.RowsDTO zhbs = new ServerBean.RowsDTO();
                        ServerBean.RowsDTO dy = new ServerBean.RowsDTO();
                        ServerBean.RowsDTO mzyy = new ServerBean.RowsDTO();
                        List<ServerBean.RowsDTO> rowsDTOS = new ArrayList<>();
                        //智慧巴士，电影，门诊预约
                        for (int i = 0; i < rows.size(); i++) {
                            if (rows.get(i).getServiceName().contains("智慧巴士")) {
                                zhbs = rows.get(i);
                            }
                            if (rows.get(i).getServiceName().contains("看电影")) {
                                dy = rows.get(i);
                            }
                            if (rows.get(i).getServiceName().contains("门诊预约")) {
                                mzyy = rows.get(i);
                            }
                        }
                        for (int i = 0; i < servername.length; i++) {
                            ServerBean.RowsDTO rowsDTO = new ServerBean.RowsDTO();
                            rowsDTO.setId(icons[i]);
                            rowsDTO.setServiceName(servername[i]);
                            rowsDTOS.add(rowsDTO);
                        }
                        rowsDTOS.add(zhbs);
                        rowsDTOS.add(dy);
                        rowsDTOS.add(mzyy);
                        ServerBean.RowsDTO e = new ServerBean.RowsDTO();
                        e.setId(R.drawable.ic_baseline_add_24);
                        e.setServiceName("更多服务");
                        rowsDTOS.add(e);
                        mMgv.setAdapter(new MyBaseAdapter<ServerBean.RowsDTO>(rowsDTOS, R.layout.home_server, 8) {
                            //服务内容应包含主页、智慧巴士、个人中心、数据分析、电影、门诊预约、智慧党建
                            private TextView mTv;
                            private ImageView mIcon;

                            @Override
                            protected void conerview(ViewHolde viewHolde, ServerBean.RowsDTO rowsDTO, int i) {
                                mIcon = viewHolde.getView(R.id.icon);
                                mTv = viewHolde.getView(R.id.tv);
                                mIcon.setMinimumWidth(x / 6);
                                mIcon.setMinimumHeight(x / 6);
                                if ((i >= 0 && i <= 3) || i == rowsDTOS.size() - 1) {
                                    Glide.with(mActivity).load(rowsDTO.getId())
                                            .into(mIcon);
                                    mTv.setText(rowsDTO.getServiceName());
                                } else {
                                    Glide.with(mActivity).load(User.URI + rowsDTO.getImgUrl())
                                            .into(mIcon);
                                    mTv.setText(rowsDTO.getServiceName());
                                }
                            }
                        });
                        mMgv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            MainActivity mainActivity = (MainActivity) mActivity;

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                switch (i) {
                                    case 0:
                                        Toast.makeText(mActivity, "在主页", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        mainActivity.setTab(3);
                                        break;
                                    case 2:
                                        mainActivity.setTab(2);
                                        break;
                                    case 3:
                                        mainActivity.setTab(4);
                                        break;
                                    case 4:
                                        goActivity(ZHBSACtivity.class);
                                        break;
                                    case 5:
                                        goActivity(KDYActivity.class);
                                        break;
                                    case 7:
                                        mainActivity.setTab(1);
                                        break;
                                }
                            }
                        });

                    }
                });
            }
        });

    }

    private void setBanner() {
        OkhttpUtil okhttpUtil = new OkhttpUtil();
        okhttpUtil.setGet(User.URI + "/prod-api/api/rotation/list?pageNum=1&pageSize=8&type=2", "");
        okhttpUtil.setOkhttplim(new Okhttplim() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

            @Override
            public void getString(String s) {
                BannerBean bannerBean = new Gson().fromJson(s, BannerBean.class);
                List<BannerBean.RowsDTO> rows = bannerBean.getRows();
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBanner.setAdapter(new BannerImageAdapter<BannerBean.RowsDTO>(rows) {
                            @Override
                            public void onBindView(BannerImageHolder bannerImageHolder, BannerBean.RowsDTO rowsDTO, int i, int i1) {
                                Glide.with(mActivity).load(User.URI + rowsDTO.getAdvImg())
                                        .into(bannerImageHolder.imageView);
                            }
                        }).start();
                        mBanner.setOnBannerListener(new OnBannerListener() {
                            @Override
                            public void OnBannerClick(Object o, int i) {
                                int targetId = rows.get(i).getTargetId();
                                Mesevent mesevent = new Mesevent();
                                mesevent.setNewSID(String.valueOf(targetId));
                                EventBus.getDefault().postSticky(mesevent);
                                goActivity(NewsXqActivity.class);
                            }
                        });
                    }
                });
            }
        });


    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fargament1;
    }
}
