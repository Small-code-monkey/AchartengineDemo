package com.achartenginedemo.Base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.achartenginedemo.R;

import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;

/**
 * Date：2018/4/4
 * Function:
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private Context context = BaseActivity.this;

    /**
     * 坐标点的点击事件
     */
    protected View.OnClickListener lineChartViewClick = new View.OnClickListener() {
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
    protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle, String yTitle, int axesColor) {
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
    protected XYMultipleSeriesRenderer builder(int[] colors, PointStyle[] styles) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        setRenderer(renderer, colors, styles);
        return renderer;
    }

    /**
     * 数据集合
     *
     * @param curvetitle
     * @param xValues
     * @param yValues
     * @return
     */
    protected XYMultipleSeriesDataset buiildDataset(List<String> curvetitle, List<double[]> xValues, List<double[]> yValues) {
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
                int seriesLength = xV.length;

                for (int k = 0; k < seriesLength; k++) {
                    if (k < yV.length) {
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