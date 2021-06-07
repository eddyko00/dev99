/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.chart;

import java.util.Date;
import java.util.List;

/**
 *
 * @author eddy
 */
public class ChartServiceStub {

    public boolean saveChartToFile(String name, String filepath, List<Date> xDate, List<Double> yNormalizeData,
            List<Date> buyDate, List<Double> buyD, List<Date> sellDate, List<Double> sellD) {
        return true;
    }

    public XYChart getlineChartBase(String name, List<Date> xData, List<Double> yNormalizeData) {

        // Create Chart
        XYChart chart = new XYChart();
        return chart;
    }

    public XYChart addBuyChart(XYChart chart, List<Date> xData, List<Double> yNormalizeData) {

        return chart;
    }

    public XYChart addSellChart(XYChart chart, List<Date> xData, List<Double> yNormalizeData) {

        return chart;
    }

    public XYChart getlineChart() {

        XYChart chart = new XYChart();
        return chart;
    }

    public int saveChart(XYChart chart, String filePathName) {
        return 0;
    }

    private static class XYChart {

        public XYChart() {
        }
    }

}
