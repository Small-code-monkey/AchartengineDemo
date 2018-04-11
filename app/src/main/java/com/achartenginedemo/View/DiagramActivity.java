package com.achartenginedemo.View;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;

import com.achartenginedemo.Base.BaseActivity;
import com.achartenginedemo.R;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：2018/4/4
 * Function: 曲线图
 */

public class DiagramActivity extends BaseActivity {
    private static final String TAG = "FoldLineDiagramActivity";
    private Context context = DiagramActivity.this;

    private List<String> title = new ArrayList<>();//名称
    private List<double[]> xValue = new ArrayList<>();// x轴值
    private List<double[]> yValue = new ArrayList<>();//y轴值
    private int[] colors = new int[0];//颜色
    private FrameLayout contentLayout;
    private View lineChartView;// 曲线图
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fold_line_diagram);

        position = getIntent().getIntExtra("i", 0);
        title = (List<String>) getIntent().getSerializableExtra("title");
        xValue = (List<double[]>) getIntent().getSerializableExtra("xValue");
        yValue = (List<double[]>) getIntent().getSerializableExtra("yValue");

        initView();
    }

    private void initView() {
        contentLayout = findViewById(R.id.contentLayout);

        colors = new int[]{Color.BLUE, Color.RED};

        PointStyle[] styles = new PointStyle[]{PointStyle.CIRCLE, PointStyle.DIAMOND, PointStyle.TRIANGLE, PointStyle.SQUARE, PointStyle.CIRCLE};
        XYMultipleSeriesRenderer renderer = builder(colors, styles);

        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);//设置每个点都是实心
        }

        setChartSettings(renderer, "顶部标题", "X轴", "Y轴", Color.BLACK);
        XYMultipleSeriesDataset dataset = buiildDataset(title, xValue, yValue);

        /* 曲线类型在ChartFactory类中 */
        switch (position) {
            case 0:
                lineChartView = ChartFactory.getLineChartView(context, dataset, renderer);
                break;
            case 1:
                lineChartView = ChartFactory.getCubeLineChartView(context, dataset, renderer, 3);
                break;
            case 2:
                lineChartView = ChartFactory.getScatterChartView(context, dataset, renderer);
                break;
            case 3:
                //报错：at org.achartengine.GraphicalView.onDraw(GraphicalView.java:168)
                lineChartView = ChartFactory.getBubbleChartView(context, dataset, renderer);
                break;
            case 4:
                lineChartView = ChartFactory.getTimeChartView(context, dataset, renderer, BarChart.TYPE);
                break;
            case 5:
                /* BarChart.Type: DEFAULT:柱状并列 STACKED:柱状堆叠*/
                lineChartView = ChartFactory.getBarChartView(context, dataset, renderer, BarChart.Type.DEFAULT);
                break;
            case 6:
                lineChartView = ChartFactory.getRangeBarChartView(context, dataset, renderer, BarChart.Type.DEFAULT);
                break;
            case 7:
                /* 组合图 每条曲线标明曲线类型 (优先实现柱状图,否则柱状图文字被遮挡)*/
                String[] strings = {BarChart.TYPE, LineChart.TYPE};
                lineChartView = ChartFactory.getCombinedXYChartView(context, dataset, renderer, strings);
                break;
        }

        lineChartView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));//设置图纸的背景色
        contentLayout.removeAllViews();
        contentLayout.addView(lineChartView);//把曲线图添加进去
        lineChartView.invalidate();//视图更新
        lineChartView.setOnClickListener(lineChartViewClick);//设置坐标点的点击事件
        lineChartView.setOnTouchListener(lineChartViewTouch);//设置坐标点的触摸事件
    }
}
