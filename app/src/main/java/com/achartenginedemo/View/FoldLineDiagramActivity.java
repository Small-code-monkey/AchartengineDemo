package com.achartenginedemo.View;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.achartenginedemo.Base.BaseActivity;
import com.achartenginedemo.R;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：2018/4/4
 * Function: 折线图
 */

public class FoldLineDiagramActivity extends BaseActivity {
    private static final String TAG = "FoldLineDiagramActivity";
    private Context context = FoldLineDiagramActivity.this;

    private List<String> title = new ArrayList<>();//名称
    private List<double[]> xValue = new ArrayList<>();// x轴值
    private List<double[]> yValue = new ArrayList<>();//y轴值
    private int[] colors = new int[0];//颜色
    private FrameLayout contentLayout;
    private View lineChartView;// 曲线图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fold_line_diagram);

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

        lineChartView = ChartFactory.getLineChartView(context, dataset, renderer);
        lineChartView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));//设置图纸的背景色
        contentLayout.removeAllViews();
        contentLayout.addView(lineChartView);//把曲线图添加进去
        lineChartView.invalidate();//视图更新
        lineChartView.setOnClickListener(lineChartViewClick);// 设置坐标点的点击事件
        lineChartView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }
}
