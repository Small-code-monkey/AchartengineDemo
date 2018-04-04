package com.achartenginedemo.Base;

import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;

import java.util.List;

/**
 * Date：2018/4/4
 * Function: 曲线演示
 */
public abstract class CurveDemonstration {

    //处理XY轴数据
    protected XYMultipleSeriesDataset buildDataset(List<String> titles, List<double[]> xValues, List<double[]> yValues) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        addXYSeries(dataset, titles, xValues, yValues, 0);
        return dataset;
    }

    //形成曲线
    public void addXYSeries(XYMultipleSeriesDataset dataset, List<String> titles,
                            List<double[]> xValues, List<double[]> yValues, int scale) {
        int length = titles.size();
        for (int i = 0; i < length; i++) {
            XYSeries series = new XYSeries(titles.get(i), scale);
            double[] xV = xValues.get(i);
            double[] yV = yValues.get(i);
            int seriesLength = xV.length;
            for (int k = 0; k < seriesLength; k++) {
                series.add(xV[k], yV[k]);
            }
            dataset.addSeries(series);
        }
    }
}
