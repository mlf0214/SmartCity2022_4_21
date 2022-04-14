package com.example.myapplication.ui.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.bean.ServerBean;
import com.example.myapplication.bean.User;
import com.example.myapplication.network.OkhttpUtil;
import com.example.myapplication.network.Okhttplim;
import com.example.myapplication.ui.activity.MainActivity;
import com.example.myapplication.ui.base.BaseFragment;
import com.example.myapplication.util.MyBaseAdapter;
import com.example.myapplication.util.MyGridView;
import com.example.myapplication.util.ViewHolde;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class Fragment2 extends BaseFragment {
    private ImageView mLeftIcon;
    private TextView mTitle;
    private ImageView mRightIcon;
    private RadioGroup mRg;
    private MyGridView mMgv;
    private RadioButton mRb1;

    @Override
    protected void initView() {

        mLeftIcon = getView().findViewById(R.id.left_icon);
        mTitle = getView().findViewById(R.id.title);
        mRightIcon = getView().findViewById(R.id.right_icon);
        mRg = getView().findViewById(R.id.rg);
        mMgv = getView().findViewById(R.id.mgv);
        mRb1 = getView().findViewById(R.id.rb1);
        mTitle.setText("全部服务");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = mRg.findViewById(i);
                if (rb.isChecked()){
                    String s = rb.getText().toString();
                    selectMgv(s);
                    Toast.makeText(mActivity, "点击了", Toast.LENGTH_SHORT).show();
                }
                if (rb.getText().toString().equals("全部服务")){
                    String s = "";
                    selectMgv(s);
                }

            }
        });
        mRb1.setChecked(true);


    }

    private void selectMgv(String s) {
        OkhttpUtil okhttpUtil = new OkhttpUtil();
        okhttpUtil.setGet(User.URI + "/prod-api/api/service/list?serviceType=" + s, "");
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
                        mMgv.setNumColumns(3);
                        mMgv.setAdapter(new MyBaseAdapter<ServerBean.RowsDTO>(rows, R.layout.home_server, rows.size()) {

                            private TextView mTv;
                            private ImageView mIcon;

                            @Override
                            protected void conerview(ViewHolde viewHolde, ServerBean.RowsDTO rowsDTO, int i) {
                                mIcon = viewHolde.getView(R.id.icon);
                                mTv = viewHolde.getView(R.id.tv);
                                mIcon.setMinimumWidth(x / 5);
                                mIcon.setMinimumHeight(x / 5);
                                Glide.with(mActivity).load(User.URI+rowsDTO.getImgUrl())
                                                                          .into(mIcon);
                                mTv.setText(rowsDTO.getServiceName());
                            }
                        });
                        mMgv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            MainActivity mainActivity= (MainActivity) mActivity;
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String serviceName = rows.get(i).getServiceName();
                                Toast.makeText(mActivity, serviceName, Toast.LENGTH_SHORT).show();
                                switch (serviceName){
                                    case "数据分析":
                                        mainActivity.setTab(3);
                                        break;
                                }
                            }
                        });

                    }
                });
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fargament2;
    }
}
