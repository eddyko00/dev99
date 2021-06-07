/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.chart;

import java.awt.Color;
import java.io.IOException;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import org.knowm.xchart.*;
import org.knowm.xchart.style.*;
import org.knowm.xchart.style.lines.*;
import org.knowm.xchart.style.markers.*;

/**
 *
 * @author eddy
 */
public class ChartService {

    protected static Logger logger = Logger.getLogger("ChartService");

    public byte[] streamCompareChartToByte(String name, List<Date> xDate, List<Double> yNormalizeData,
            List<Date> buyDate, List<Double> buyD,
            List<Date> sellDate, List<Double> sellD,
            List<Date> compDate, List<Double> compD) {
        try {
//            System.out.println("> streamChartToFile ");
            if (yNormalizeData.size() == 0) {
                return null;
            }
            if (xDate.size() == 0) {
                return null;
            }
            XYChart chart = getlineChartBase(name, xDate, yNormalizeData);
            if (compDate.size() != 0) {
                addCompareChart(chart, compDate, compD);
            } else {
//                System.out.println("> saveChart exception sellDate");
            }

            if (buyDate.size() != 0) {
                addBuyChart(chart, buyDate, buyD);
            } else {
//                System.out.println("> saveChart exception buyDate");
            }
            if (sellDate.size() != 0) {
                addSellChart(chart, sellDate, sellD);
            } else {
//                System.out.println("> saveChart exception sellDate");
            }

            return BitmapEncoder.getBitmapBytes(chart, BitmapEncoder.BitmapFormat.JPG);
        } catch (Exception ex) {
            logger.info("> saveChart exception" + ex.getMessage());
        }
        return null;
    }

    public byte[] streamChartToByte(String name, List<Date> xDate, List<Double> yNormalizeData,
            List<Date> buyDate, List<Double> buyD, List<Date> sellDate, List<Double> sellD) {
        try {
//            System.out.println("> streamChartToFile ");
            if (yNormalizeData.size() == 0) {
                return null;
            }
            if (xDate.size() == 0) {
                return null;
            }
            XYChart chart = getlineChartBase(name, xDate, yNormalizeData);

            if (buyDate.size() != 0) {
                addBuyChart(chart, buyDate, buyD);
            } else {
//                System.out.println("> saveChart exception buyDate");
            }
            if (sellDate.size() != 0) {
                addSellChart(chart, sellDate, sellD);
            } else {
//                System.out.println("> saveChart exception sellDate");
            }
            return BitmapEncoder.getBitmapBytes(chart, BitmapEncoder.BitmapFormat.JPG);
        } catch (Exception ex) {
            logger.info("> saveChart exception" + ex.getMessage());
        }
        return null;
    }

    public boolean saveChartToFile(String name, String filepath, List<Date> xDate, List<Double> yNormalizeData,
            List<Date> buyDate, List<Double> buyD, List<Date> sellDate, List<Double> sellD) {
        try {
//            System.out.println("> saveChartToFile " + filepath);
            if (yNormalizeData.size() == 0) {
                return false;
            }
            if (xDate.size() == 0) {
                return false;
            }

            XYChart chart = getlineChartBase(name, xDate, yNormalizeData);

            if (buyDate.size() != 0) {
                addBuyChart(chart, buyDate, buyD);
            } else {
                logger.info("> saveChart exception buyDate");
            }
            if (sellDate.size() != 0) {
                addSellChart(chart, sellDate, sellD);
            } else {
                logger.info("> saveChart exception sellDate");
            }

            saveChart(chart, filepath);
            return true;
        } catch (Exception ex) {
            logger.info("> saveChartToFile exception" + ex.getMessage());
        }
        return false;
    }

    public XYChart getlineChartBase(String name, List<Date> xData, List<Double> yNormalizeData) {

        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(name).build();

        // Customize Chart
//        chart.getStyler().setLegendVisible(false);
        // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        XYSeries series = chart.addSeries("BH", xData, yNormalizeData);
        series.setMarker(SeriesMarkers.NONE);
        series.setLineStyle(SeriesLines.DASH_DASH);
        series.setLineColor(Color.BLACK);

        return chart;
    }

    public XYChart addBuyChart(XYChart chart, List<Date> xData, List<Double> yNormalizeData) {

        // Customize Chart
        XYSeries series = chart.addSeries("B", xData, yNormalizeData);
        series.setLineStyle(SeriesLines.NONE);
        series.setMarker(SeriesMarkers.TRIANGLE_UP);
        series.setMarkerColor(Color.GREEN);
        return chart;
    }

    public XYChart addSellChart(XYChart chart, List<Date> xData, List<Double> yNormalizeData) {
        XYSeries series = chart.addSeries("S", xData, yNormalizeData);
        series.setLineStyle(SeriesLines.NONE);
        series.setMarker(SeriesMarkers.TRIANGLE_DOWN);
        series.setMarkerColor(Color.RED);
        return chart;
    }

    public XYChart addCompareChart(XYChart chart, List<Date> xData, List<Double> yNormalizeData) {
        XYSeries series = chart.addSeries("C", xData, yNormalizeData);

        series.setLineStyle(SeriesLines.NONE);
        series.setMarker(SeriesMarkers.CROSS);
        series.setMarkerColor(Color.GRAY);
        return chart;
    }

    public XYChart getlineChart() {

        // Create Chart
        XYChart chart
                = new XYChartBuilder()
                        .width(800)
                        .height(600)
                        .title("LineChart05")
                        .xAxisTitle("X")
                        .yAxisTitle("Y")
                        .build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSW);
        chart.getStyler().setYAxisLogarithmic(true);
        chart.getStyler().setYAxisMin(0.0);
        chart.getStyler().setYAxisMax(1000.0);
        chart.getStyler().setXAxisMin(2.0);
        chart.getStyler().setXAxisMax(7.0);
        chart.getStyler().setToolTipsEnabled(true);

        // Series
        double[] xData = new double[]{0.0, 1.0, 2.0, 3.0, 4.0, 5, 6};
        double[] yData = new double[]{106, 44, 26, 10, 7.5, 3.4, .88};
        double[] yData2 = new double[]{102, 49, 23.6, 11.3, 5.4, 2.6, 1.25};

        XYSeries series = chart.addSeries("A", xData, yData);
        series.setLineStyle(SeriesLines.NONE);
        series.setMarker(SeriesMarkers.DIAMOND);
        series.setMarkerColor(Color.BLACK);

        XYSeries series2 = chart.addSeries("B", xData, yData2);
        series2.setMarker(SeriesMarkers.NONE);
        series2.setLineStyle(SeriesLines.DASH_DASH);
        series2.setLineColor(Color.BLACK);

        return chart;
    }

    public int saveChart(XYChart chart, String filePathName) {
        if ((filePathName == null) || (filePathName.length() == 0)) {
            filePathName = "T:/Netbean/debug/SampleChart";
        }
//        logger.info("> saveChart " + filePathName);

        try {
            BitmapEncoder.saveBitmap(chart, filePathName, BitmapEncoder.BitmapFormat.JPG);

//        BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapFormat.PNG);
//        BitmapEncoder.saveJPGWithQuality(chart, "./Sample_Chart_With_Quality.jpg", 0.95f);
//        BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapFormat.BMP);
//        BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapFormat.GIF);      
            return 1;
        } catch (IOException ex) {
            logger.info("> saveChart exception" + ex.getMessage());
        }

        return 0;
    }

}
