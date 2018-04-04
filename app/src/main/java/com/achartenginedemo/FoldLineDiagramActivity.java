package com.achartenginedemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Function: 折线图
 */

public class FoldLineDiagramActivity extends AppCompatActivity {
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

        initView();
    }

    private void initView() {
        contentLayout = findViewById(R.id.contentLayout);

        title = Arrays.asList("1", "2");

        //X轴负责线段长度
        for (int i = 0; i < title.size(); i++) {
            xValue.add(new double[]{1, 2, 3, 4, 5});
        }
        //Y轴负责每条线段的数据点
        yValue.add(new double[]{1, 2, 3, 4, 5});
        yValue.add(new double[]{5, 2, 3, 1, 4});

        colors = new int[]{Color.BLUE, Color.RED};

        PointStyle[] styles = new PointStyle[]{PointStyle.CIRCLE, PointStyle.DIAMOND, PointStyle.TRIANGLE, PointStyle.SQUARE, PointStyle.CIRCLE};
        XYMultipleSeriesRenderer renderer = builder(colors, styles);

        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);//设置每个点都是实心
        }

        setChartSettings(renderer, "", "X", "Y", Color.BLACK);

        //----------------------
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

    /**
     * 坐标点的点击事件
     */
    private View.OnClickListener lineChartViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GraphicalView graphicalView = (GraphicalView) v;
            SeriesSelection seriesAndPoint = graphicalView.getCurrentSeriesAndPoint();
            if (seriesAndPoint == null) {
                return;
            }
        }
    };

    /**
     * 图表外部设置
     *
     * @param renderer
     * @param title
     * @param xTitle
     * @param yTitle
     * @param axesColor
     */
    private void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle, String yTitle, int axesColor) {
        renderer.setXLabelsPadding(5);// 设置x轴与标签文字之间的距离
        renderer.setYLabelsPadding(5);

        renderer.setXLabelsAlign(Paint.Align.CENTER);// 设置刻度线与X轴之间的相对位置关系
        renderer.setPanEnabled(true, true);// 设置x轴可滑动，y轴可滑动
        renderer.setClickEnabled(true);// 设置轴上的点可点击
        renderer.setSelectableBuffer(20);// 设置点击范围
        renderer.setMarginsColor(ContextCompat.getColor(context, R.color.white));// 设置四周颜色

        renderer.setChartTitle(title);// 设置图表标题
        renderer.setXTitle(xTitle);// 设置x轴标题
        renderer.setYTitle(yTitle);// 设置y轴标题
        renderer.setXLabelsColor(Color.BLACK);// 设置x轴标签颜色
        renderer.setYLabelsColor(0, Color.BLACK);// 设置y轴标签颜色
        renderer.setAxesColor(axesColor);// 设置坐标轴颜色
        renderer.setLabelsColor(Color.BLACK);// 设置xy轴标题文本颜色
        renderer.setShowGrid(true);// 设置是否显示网格
        try {
            //当displayChartValues属性为true之后，在绘图时AChartEngine将会按照XYSeries对象的x轴的取值作为索引，
            //如果X轴的取值不是从1开始循环之后将会出现索引越界的错误。
            //此时在设置XYSeries的X轴的值的时候需要按照索引顺序指定，由小到大才不会报错
            //设置点上显示数值，只有点的下标从零开始渐增，才不会报下标越界错误
            renderer.setDisplayChartValues(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 渲染器
     *
     * @param renderer
     * @param colors
     * @param styles
     */
    private void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors, PointStyle[] styles) {
        renderer.setLabelsTextSize(getResources().getDimensionPixelSize(R.dimen.dimen_15dp));
        renderer.setLegendTextSize(getResources().getDimensionPixelSize(R.dimen.dimen_15dp));
        renderer.setAxisTitleTextSize(getResources().getDimensionPixelSize(R.dimen.dimen_15dp));
        //上  左  下 右   图表四周的范围
        renderer.setMargins(new int[]{dp2px(context, 10), dp2px(context, 30), dp2px(context, 50), dp2px(context, 10)});
        renderer.setLegendHeight(dp2px(context, 50));
        renderer.setYLabels(8);

        renderer.setPointSize(dp2px(context, 4));// 设置点的大小
        renderer.setBarSpacing(4.5);//设置柱状大小
        renderer.setZoomEnabled(false, true);// 设置x轴能缩放，y轴不能缩放

        int length = colors.length;
        //根据颜色数组长度判断
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);// 设置曲线的颜色
            r.setPointStyle(styles[i]);// 设置曲线样式
            r.setDisplayChartValues(true);// 将点的值显示出来

            if (i == 0) {
                r.setChartValuesSpacing(dp2px(context, 20));// 显示的点的值与图的距离
            } else {
                r.setChartValuesSpacing(dp2px(context, -32));// 显示的点的值与图的距离
            }

            r.setChartValuesTextSize(getResources().getDimensionPixelSize(R.dimen.dimen_12dp));// 点的值的文字大小
            r.setLineWidth(dp2px(context, 2));// 设置折线宽度
            renderer.addSeriesRenderer(r);
        }
    }

    /**
     * 构建
     *
     * @param colors 曲线颜色数组
     * @param styles 曲线样式
     * @return
     */
    private XYMultipleSeriesRenderer builder(int[] colors, PointStyle[] styles) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        setRenderer(renderer, colors, styles);
        return renderer;
    }

    /**
     * 设置滑动限制
     *
     * @param yValueMin
     * @param yValueMax
     */
    private void setLineChartPanAndZoomLimits(XYMultipleSeriesRenderer renderer, double yValueMin, double yValueMax, double lastMaxLimitX) {
        renderer.setPanLimits(new double[]{-0.5, lastMaxLimitX, yValueMin, yValueMax});// 设置滑动范围，前面两个参数是x轴范围，后面是y轴范围
        renderer.setZoomLimits(new double[]{-0.5, lastMaxLimitX, yValueMin, yValueMax});// 设置缩放限制
    }

    /**
     * 数据集合
     *
     * @param curvetitle
     * @param xValues
     * @param yValues
     * @return
     */
    private XYMultipleSeriesDataset buiildDataset(List<String> curvetitle, List<double[]> xValues, List<double[]> yValues) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        addXYSeries(dataset, curvetitle, xValues, yValues, 0);
        return dataset;
    }

    private void addXYSeries(XYMultipleSeriesDataset dataset, List<String> curvetitle, List<double[]> xValues, List<double[]> yValues, int scale) {
        int length = curvetitle.size();

        for (int i = 0; i < length; i++) {
            if (i < xValues.size() && i < yValues.size()) {
                Log.d(TAG, "line number =" + i);
                XYSeries series = new XYSeries(curvetitle.get(i), scale);

                double[] xV = xValues.get(i);
                double[] yV = yValues.get(i);
//                for (int j = 0; j < yV.length; j++) {
//                    yV[j] = Double.parseDouble(decimalFormat.format(yV[j]));//转成格式
//                }
                int seriesLength = xV.length;

                for (int k = 0; k < seriesLength; k++) {
                    if (k < yV.length) {
                        Log.d(TAG, "Index " + k + " x " + xV[k] + " y " + yV[k]);
                        series.add(k, xV[k], yV[k]);//系列
                    }
                }
                dataset.addSeries(series);
            }
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
