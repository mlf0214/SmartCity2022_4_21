package com.example.myapplication.ui.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.bean.FyListBean;
import com.example.myapplication.bean.NewsListBean;
import com.example.myapplication.bean.User;
import com.example.myapplication.network.OkhttpUtil;
import com.example.myapplication.network.Okhttplim;
import com.example.myapplication.ui.base.BaseFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Response;

public class Fragment4 extends BaseFragment {


    private static final String TAG = "Fragment4";
    private RelativeLayout mToolbar;
    private ImageView mLeftIcon;
    private TextView mTitle;
    private ImageView mRightIcon;
    private BarChart mBarchart;
    private LineChart mLinchart;
    private PieChart mPie;
    private HorizontalBarChart mHorbarchart;
    private List<String> xchartstring;
    private List<Integer> ychartdata;
    private ProgressDialog progressDialog;
    private List<BarEntry> barEntries=new ArrayList<>();
    private List<String> xharstring=new ArrayList<>();

    @Override
    protected void initView() {
        mToolbar =   getView().findViewById(R.id.toolbar);
        mLeftIcon =  getView().findViewById(R.id.left_icon);
        mTitle =     getView().findViewById(R.id.title);
        mRightIcon = getView().findViewById(R.id.right_icon);
        mBarchart =  getView().findViewById(R.id.barchart);
        mLinchart =  getView().findViewById(R.id.linchart);
        mPie =       getView().findViewById(R.id.pie);
        mHorbarchart=getView().findViewById(R.id.horbarchart);
        mTitle.setText("数据分析");
        mLeftIcon.setImageResource(R.drawable.ic_baseline_chevron_left_24);
        progressDialog=new ProgressDialog(mActivity);
        progressDialog.setTitle("请稍等");
        progressDialog.setMessage("正在加载网络...");
        progressDialog.show();
    }

    @Override
    protected void initData() {
        selectNewsData();
        selectLinChartData();
//        selectPieData();
        selectHorChartData();






    }

    private void selectHorChartData() {
        OkhttpUtil okhttpUtil=new OkhttpUtil();
        okhttpUtil.setGet(User.URI+"/prod-api/api/house/housing/list?houseType="+"楼盘","");
        okhttpUtil.setOkhttplim(new Okhttplim() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

            @Override
            public void getString(String s) {
                FyListBean fyListBean=new Gson().fromJson(s,FyListBean.class);
                mActivity.runOnUiThread(new Runnable() {
                    @Override


                    public void run() {
                        List<FyListBean.RowsDTO> rows = fyListBean.getRows();
                        List<Integer> prices=new ArrayList<>();

                        for (int i = 0; i <4; i++) {
                            String price = rows.get(i).getPrice();
                            price = price.trim();
                             price = price.substring(0, price.length() - 2);
                             if (price.contains("元")){
                                 price=price.replace("元","");
                             }
                            Integer price1 = Integer.valueOf(price);
                            int zj = price1 * rows.get(i).getAreaSize();
                            prices.add(zj);
                           
                            xharstring.add(rows.get(i).getSourceName().substring(0,3)+"...");
                        }
                        Collections.reverse(prices);
                        for (int i = 0; i < prices.size(); i++) {
                            barEntries.add(new BarEntry(i,prices.get(i)));
                        }

                        Log.d(TAG, String.valueOf(prices));
                        Log.d(TAG, String.valueOf(barEntries));
                        setHarBarChatt(mHorbarchart,xharstring,barEntries);
                        mHorbarchart.invalidate();
                    }
                });
            }
        });
    }

    private void setHarBarChatt(HorizontalBarChart horbarchart, List<String> xharstring, List<BarEntry> barEntries) {
        horbarchart.setBackgroundColor(Color.WHITE);
        horbarchart.setDescription(null);
        horbarchart.setEnabled(false);
        XAxis xAxis = horbarchart.getXAxis();
        YAxis axisLeft = horbarchart.getAxisLeft();
        YAxis axisRight = horbarchart.getAxisRight();
        initXy(xAxis,axisLeft,axisRight,xharstring);
        BarData horData = getHorData(barEntries);
        mHorbarchart.setData(horData);
    }

    private BarData getHorData(List<BarEntry> barEntries) {
        BarDataSet barDataSet=new BarDataSet(barEntries,"总价");
        barDataSet.setDrawValues(true);
        initDataSet(barDataSet,Color.GREEN);
        BarData barData=new BarData(barDataSet);
        barData.setDrawValues(true);
        return barData;
    }

    private void initXy(XAxis xAxis, YAxis axisLeft, YAxis axisRight, List<String> xharstring) {
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
//        xAxis.setAxisMaximum(4);
        xAxis.setLabelCount(xharstring.size());
//        xAxis.setAxisMinimum(0);
        xAxis.setGranularity(1f);
//        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xharstring.get((int) (Math.abs(value)%xharstring.size()));
            }
        });
        axisLeft.setDrawGridLines(false);
        axisRight.setDrawGridLines(false);
        axisRight.setDrawLabels(false);
    }

//    private void selectPieData() {
//        //.饼状图：请分析外卖订餐每个分类内销量最高的商家，
//        // 该商家的销量占该分类全部商家销量总和的百分比。（图内显示相应分类名称及比例值）
//        OkhttpUtil okhttpUtil=new OkhttpUtil();
//        okhttpUtil.setGet(User.URI+"/prod-api/press/press/"+newSid,"");
//        okhttpUtil.setOkhttplim(new Okhttplim() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//            }
//
//            @Override
//            public void getString(String s) {
//                NewsXqBean newsXqBean=new Gson().fromJson(s,NewsXqBean.class);
//                mActivity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                    }
//                });
//            }
//        });
//    }

    private void selectLinChartData() {
        String[] cps=new String[]{"甘A","甘B","甘C"};
        int[] numbers=new int[]{10,20,30};
        List<Entry> list=new ArrayList<>();
        for (int i = 0; i <cps.length; i++) {
            list.add(new Entry(i,numbers[i]));
        }


        setLinchart(mLinchart,cps,list);
    }

    private void setLinchart(LineChart mLinchart, String[] cps, List<Entry> numbers) {
        mLinchart.setBackgroundColor(Color.WHITE);
        mLinchart.setDescription(null);
        mLinchart.setEnabled(false);
        XAxis xAxis = mLinchart.getXAxis();
        YAxis axisLeft = mLinchart.getAxisLeft();
        YAxis axisRight = mLinchart.getAxisRight();

        initLineXY(xAxis,axisLeft,axisRight,cps);

        LineData linData = getLinData(numbers);
        mLinchart.setData(linData);

    }

    private LineData getLinData(List<Entry> numbers) {
        LineDataSet lineDataSet=new LineDataSet(numbers,"牌照所在地统计数");
        LineData lineData=new LineData(lineDataSet);
        initDataSet(lineDataSet,Color.RED);
        return lineData;
    }

    private void initLineXY(XAxis xAxis, YAxis axisLeft, YAxis axisRight, String[] cps) {
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(cps.length-1);
        xAxis.setAxisMinimum(0);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(cps.length);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override

            public String getFormattedValue(float value) {
                    return cps[(int) (Math.abs(value)%cps.length)];
            }

        });
        axisLeft.setDrawGridLines(false);
        axisRight.setDrawGridLines(false);
        axisRight.setDrawLabels(false);


    }

    private void selectNewsData() {
        OkhttpUtil okhttpUtil=new OkhttpUtil();
        okhttpUtil.setGet(User.URI+"/prod-api/press/press/list","");
        okhttpUtil.setOkhttplim(new Okhttplim() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

            @Override
            public void getString(String s) {
                NewsListBean newsListBean=new Gson().fromJson(s,NewsListBean.class);
                List<NewsListBean.RowsDTO> rows = newsListBean.getRows();
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        xchartstring=new ArrayList<>();
                        ychartdata=new ArrayList<>();
                        for (int i = 0; i < rows.size(); i++) {
                            int id = rows.get(i).getId();
                            if (id>=50&&id<54){
                                xchartstring.add(rows.get(i).getTitle().substring(0,3)+"..");
                                ychartdata.add(rows.get(i).getCommentNum());
                            }
                        }
                        setBarchart(mBarchart,xchartstring,ychartdata);

                        mBarchart.invalidate();

                    }
                });

                progressDialog.dismiss();
            }
        });
    }

    private void setBarchart(BarChart barchart, List<String> xchartstring, List<Integer> ychartdata) {
        barchart.setDrawBarShadow(false);
        barchart.setBackgroundColor(Color.WHITE);
        barchart.setDescription(null);
        barchart.setEnabled(false);
        XAxis xAxis = barchart.getXAxis();
        YAxis axisLeft = barchart.getAxisLeft();
        YAxis axisRight = barchart.getAxisRight();
        initXy(xAxis,axisLeft,axisRight);
        BarData charData = getCharData(xchartstring, ychartdata);
        barchart.setData(charData);
        progressDialog.setMessage("正在整合数据");




    }
    private boolean[] falgs=new boolean[]{false,true};
    private BarData getCharData(List<String> xchartstring, List<Integer> ychartdata) {
        List<BarEntry> barEntries=new ArrayList<>();
        List<BarEntry> barEntries1=new ArrayList<>();


        for (int i = 0; i < ychartdata.size(); i++) {
            boolean flag=falgs[new Random().nextInt(2)];
            Integer integer = ychartdata.get(i);
            int i1;
            if (flag==true){
                 i1 = (integer / 2) + (integer / 10);

            }else {
                 i1 = (integer / 2) - (integer / 10);
            }
            int i2=integer-i1;
            barEntries.add(new BarEntry(i,i1));
            barEntries1.add(new BarEntry(i,i2));
        }
        BarDataSet barDataSet=new BarDataSet(barEntries,"男");
        BarDataSet barDataSet1=new BarDataSet(barEntries1,"女");
        initDataSet(barDataSet,Color.CYAN);
        initDataSet(barDataSet1,Color.RED);
        BarData barData=new BarData(barDataSet);
        barData.addDataSet(barDataSet1);
        barData.setBarWidth(0.1f);
        barData.groupBars(0,0.6f,0.1f);
        return barData;
    }

    private void initDataSet(DataSet dataSet, int color) {
        dataSet.setColor(color);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int)value);
            }
        });
    }

    private void initXy(XAxis xAxis, YAxis axisLeft, YAxis axisRight) {
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(xchartstring.size());
        xAxis.setAxisMinimum(0);
        xAxis.setLabelCount(xchartstring.size());
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xchartstring.get((int) (Math.abs(value)%xchartstring.size()));
            }
        });
        axisLeft.setDrawGridLines(false);
        axisRight.setDrawGridLines(false);
        axisRight.setDrawLabels(false);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fargament4;
    }
}
