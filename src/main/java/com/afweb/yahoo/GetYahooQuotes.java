/**
 *
 * Script to download Yahoo historical quotes using the new cookie authenticated site.
 *
 * Usage: java -classpath $CLASSPATH GetJavaQuotes SYMBOL
 *
 * http://blog.bradlucas.com/posts/2017-06-02-new-yahoo-finance-quote-download-url/
 * Author: Brad Lucas brad@beaconhill.com
 * Latest: https://github.com/bradlucas/get-yahoo-quotes
 *
 * Copyright (c) 2017 Brad Lucas - All Rights Reserved
 *
 *
 * History
 *
 * 06-04-2017 : Created script
 *
 */
// https://ca.finance.yahoo.com/quote/TD.TO/history?p=TD.TO
// Click download data
// https://query1.finance.yahoo.com/v7/finance/download/TD.TO?period1=1552056389&period2=1583675189&interval=1d&events=history&crumb=qHB3dMXSmt6
package com.afweb.yahoo;

import com.afweb.model.stock.AFstockInfo;
import com.afweb.service.ServiceAFweb;
import com.afweb.stock.StockInfoUtils;
import com.afweb.util.CKey;
import com.afweb.util.StringTag;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.Collections;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.client.CookieStore;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.protocol.HttpClientContext;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.methods.HttpGet;
import org.apache.commons.lang3.StringEscapeUtils;
import java.util.logging.Logger;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;

public class GetYahooQuotes {

    public static Logger logger = Logger.getLogger("GetYahooQuotes");

    HttpClient client = HttpClientBuilder.create().build();
    HttpClientContext context = HttpClientContext.create();

    public GetYahooQuotes() {
        CookieStore cookieStore = new BasicCookieStore();
        client = HttpClientBuilder.create().build();
        context = HttpClientContext.create();
        context.setCookieStore(cookieStore);
    }

    public String getPage(String symbol) {
        ServiceAFweb.getServerObj().setCntInterRequest(ServiceAFweb.getServerObj().getCntInterRequest() + 1);

        String rtn = null;
        String url = String.format("https://finance.yahoo.com/quote/%s/?p=%s", symbol, symbol);
        HttpGet request = new HttpGet(url);
//        System.out.println(url);

//        request.addHeader("User-Agent", "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13");
        //http://blog.bradlucas.com/posts/2017-06-04-yahoo-finance-quote-download-java/
        request.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:74.0) Gecko/20100101 Firefox/74.0");
        try {
            ///Addding proxy //////////////////////
            if (CKey.PROXY == true) {
                HttpHost proxy = new HttpHost(ServiceAFweb.PROXYURL, 8080, "http");

                RequestConfig config = RequestConfig.custom()
                        .setProxy(proxy)
                        .build();
                request.setConfig(config);
            }
            ///Addding proxy /////////////////

            HttpResponse response = client.execute(request, context);
//            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rtn = result.toString();
            HttpClientUtils.closeQuietly(response);
            return rtn;
        } catch (Exception ex) {
            StockInfoUtils.logger.info("Exception:" + ex.getMessage());

        }
//        System.out.println("returning from getPage");
        ServiceAFweb.getServerObj().setCntInterException(ServiceAFweb.getServerObj().getCntInterException() + 1);

        return rtn;
    }

//    public String getPage(String symbol) {
//        ServiceAFweb.getServerObj().setCntInterRequest(ServiceAFweb.getServerObj().getCntInterRequest() + 1);
//
//        String rtn = null;
//        String url = String.format("https://finance.yahoo.com/quote/%s/?p=%s", symbol, symbol);
//        HttpGet request = new HttpGet(url);
////        logger.info("getPage " + url);
//
//        request.addHeader("User-Agent", "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13");
//        try {
//
//            ///Addding proxy //////////////////////
//            if (CKey.PROXY == true) {
//                HttpHost proxy = new HttpHost(ServiceAFweb.PROXYURL, 8080, "http");
//
//                RequestConfig config = RequestConfig.custom()
//                        .setProxy(proxy)
//                        .build();
//                request.setConfig(config);
//            }
//            ///Addding proxy /////////////////
//
//            HttpResponse response = client.execute(request, context);
////            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
//
//            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//            StringBuffer result = new StringBuffer();
//            String line = "";
//            while ((line = rd.readLine()) != null) {
//                result.append(line);
//            }
//            rtn = result.toString();
//            HttpClientUtils.closeQuietly(response);
//            return rtn;
//        } catch (Exception ex) {
//            logger.info("getPage Exception " + ex);
//        }
//        ServiceAFweb.getServerObj().setCntInterException(ServiceAFweb.getServerObj().getCntInterException() + 1);
//
//        return rtn;
//    }
    public List<String> splitPageData(String page) {
        // Return the page as a list of string using } to split the page
        return Arrays.asList(page.split("}"));
    }

    public String findCrumb(List<String> lines) {
        String crumb = "";
        String rtn = "";
        for (String l : lines) {
            if (l.indexOf("CrumbStore") > -1) {
                rtn = l;
                break;
            }
        }
        // ,"CrumbStore":{"crumb":"OKSUqghoLs8"        
        if (rtn != null && !rtn.isEmpty()) {
            String[] vals = rtn.split(":");                 // get third item
            crumb = vals[2].replace("\"", "");              // strip quotes
            crumb = StringEscapeUtils.unescapeJava(crumb);  // unescape escaped values (particularly, \u002f
        }
        return crumb;
    }

    public String getCrumb(String symbol) {
        return findCrumb(splitPageData(getPage(symbol)));
    }

//    public String findCrumb(List<String> lines) {
//        String crumb = "";
//        String rtn = "";
//        for (String l : lines) {
//            if (l.indexOf("CrumbStore") > -1) {
//                rtn = l;
//                break;
//            }
//        }
//        // ,"CrumbStore":{"crumb":"OKSUqghoLs8"        
//        if (rtn != null && !rtn.isEmpty()) {
//            String[] vals = rtn.split(":");                 // get third item
//            crumb = vals[2].replace("\"", "");              // strip quotes
//            crumb = StringEscapeUtils.unescapeJava(crumb);  // unescape escaped values (particularly, \u002f
//        }
//        return crumb;
//    }
//
//    public String getCrumb(String symbol) {
//        return findCrumb(splitPageData(getPage(symbol)));
//    }
    //https://ca.finance.yahoo.com/quote/TD.TO/history?p=TD.TO
    //https://query1.finance.yahoo.com/v7/finance/download/TD.TO?period1=1552056389&period2=1583675189&interval=1d&events=history&crumb=qHB3dMXSmt6
//    public void downloadData(String symbol, long startDate, long endDate, String crumb) {
//        String filename = String.format("%s.csv", symbol);
//        String url = String.format("https://query1.finance.yahoo.com/v7/finance/download/%s?period1=%s&period2=%s&interval=1d&events=history&crumb=%s", symbol, startDate, endDate, crumb);
//        HttpGet request = new HttpGet(url);
//        System.out.println(url);
//
//        request.addHeader("User-Agent", "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13");
//        try {
//            HttpResponse response = client.execute(request, context);
//            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
//            HttpEntity entity = response.getEntity();
//
//            String reasonPhrase = response.getStatusLine().getReasonPhrase();
//            int statusCode = response.getStatusLine().getStatusCode();
//            
//            System.out.println(String.format("statusCode: %d", statusCode));
//            System.out.println(String.format("reasonPhrase: %s", reasonPhrase));
//
//            if (entity != null) {
//                BufferedInputStream bis = new BufferedInputStream(entity.getContent());
//                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(filename)));
//                int inByte;
//                while((inByte = bis.read()) != -1) 
//                    bos.write(inByte);
//                bis.close();
//                bos.close();
//            }
//            HttpClientUtils.closeQuietly(response);
//
//        } catch (Exception ex) {
//            System.out.println("Exception");
//            System.out.println(ex);
//        }
//    }    
//    
    //https://ca.finance.yahoo.com/quote/DIA/history?period1=885254400&period2=1583625600&interval=1d&filter=history&frequency=1d
    //https://ca.finance.yahoo.com/quote/TD.TO/history?p=TD.TO
    public StringBuffer getYahooScreenPage(String symbol, String url) {
        ServiceAFweb.getServerObj().setCntInterRequest(ServiceAFweb.getServerObj().getCntInterRequest() + 1);

        StringBuffer result = new StringBuffer();
//        String url = String.format("https://ca.finance.yahoo.com/quote/%s/history?p=%s", symbol, symbol);
        HttpGet request = new HttpGet(url);
//        logger.info("getPage " + url);

        request.addHeader("User-Agent", "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13");
        try {

            ///Addding proxy //////////////////////
            if (CKey.PROXY == true) {
                HttpHost proxy = new HttpHost(ServiceAFweb.PROXYURL, 8080, "http");

                RequestConfig config = RequestConfig.custom()
                        .setProxy(proxy)
                        .build();
                request.setConfig(config);
            }
            ///Addding proxy /////////////////

            HttpResponse response = client.execute(request, context);
//            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            HttpClientUtils.closeQuietly(response);
            return result;
        } catch (Exception ex) {
            logger.info("getPage Exception " + ex);
        }
        ServiceAFweb.getServerObj().setCntInterException(ServiceAFweb.getServerObj().getCntInterException() + 1);

        return result;
    }

    //https://ca.finance.yahoo.com/quote/DIA/history?period1=885254400&period2=1583625600&interval=1d&filter=history&frequency=1d
    //https://ca.finance.yahoo.com/quote/TD.TO/history?p=TD.TO
    public ArrayList<AFstockInfo> getHistoricalScreen(String symbol, long startDate, long endDate) throws IOException {

//        String url = String.format("https://ca.finance.yahoo.com/quote/%s/history?period1=885254400&period2=1583625600&interval=1d&filter=history&frequency=1d", symbol);
        String url = String.format("https://ca.finance.yahoo.com/quote/%s/history?p=%s", symbol, symbol);
//        String url = String.format("https://ca.finance.yahoo.com/quote/%s/history?period1=%s&period2=%s&interval=1d&events=history&frequency=1d", symbol, startDate, endDate);
        StringBuffer input = getYahooScreenPage(symbol, url);
        StringTag sTag = new StringTag(input);
        //https://ca.finance.yahoo.com/quote/dia/history?p=dia        
        //Date	Open	High	Low	Close*	Adj Close**	Volume
        //Mar. 06, 2020	253.67	260.10	252.43	258.85	258.85	7,370,200        

        String item = sTag.GetFirstText("Adj Close", 0);
        item = sTag.GetNextText();  //Volume

        ArrayList StockArray = new ArrayList();

        int LineNum = 0;
        String inLine = "";
        boolean conFlag = true;
        while (conFlag) {

            try {
                for (int j = 0; j < 7; j++) {
                    String stDateTmp = sTag.GetNextText();  //Mar. 06, 2020
                    if (stDateTmp.indexOf("Close price adjusted") != -1) {
                        conFlag = false;
                        break;
                    }
                    String stDate = StockInfoUtils.parseHistDateScreen(stDateTmp); //1995-04-14
                    if (stDate == null) {
                        conFlag = false;
                        break;
                    }
                    String stOpen = sTag.GetNextText();
                    if (stOpen.equals("Dividend")) {
                        break;
                    }
                    String stHigh = sTag.GetNextText();
                    if (stHigh.equals("Dividend")) {
                        break;
                    }
                    String stLow = sTag.GetNextText();
                    // Stock Split
                    // will get the Dividend line in here
                    if (stLow.equals("Dividend")) {
                        break;
                    }
                    String stClose = sTag.GetNextText();
                    if (stClose.equals("Dividend")) {
                        break;
                    }
                    String stAdjClose = sTag.GetNextText();
                    String stVolume = sTag.GetNextText();

                    LineNum++;
                    inLine = stDate + "," + stOpen + "," + stHigh + "," + stLow + "," + stClose + "," + stAdjClose + "," + stVolume;
                    //Date,Open,High,Low,Close,Adj Close,Volume
                    if (inLine.indexOf("Date,Open") != -1) {
                        continue;
                    }
                    //1995-04-14,null,null,null,null,null,null
                    if (inLine.indexOf("null,null,null") != -1) {
                        continue;
                    }
                    if (inLine.indexOf("Dividend") != -1) {
                        continue;
                    }
                    if (inLine.indexOf("-,-,-,-,-") != -1) {
                        continue;
                    }
                    AFstockInfo StockD = StockInfoUtils.parseCSVLine(inLine);
                    if (StockD == null) {
                        logger.info("getHistoricalScreen Exception " + symbol + " " + inLine);
                        break;
                    }

                    StockArray.add(StockD);
                }

            } catch (Exception ex) {
                logger.info("getHistoricalScreen Exception " + inLine);
                conFlag = false;
                break;
            }
        }
//        logger.info("getHistoricalScreen  " + symbol + " " + StockArray.size());
        return StockArray;

    }

    // always the earliest day first
    // always the earliest day first
    public ArrayList<AFstockInfo> getHistoricalData(String symbol, long startDate, long endDate) throws IOException {
        ///// some issue in the weekend get error essage
        // <!doctype html public "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
        ///// some issue in the weekend get error essage
        ServiceAFweb.AFSleep1Sec(5);
        ///// some issue in the weekend get error essage
        ///// some issue in the weekend get error essage

        // try to get the max 6 year
        int Max6YrStock = CKey.DATA6YEAR;
        String crumb = getCrumb(symbol);
        if (crumb == null) {
            logger.info("getHistoricalData crumb empyt " + symbol);
            return null;
        } else if (crumb.length() == 0) {
            logger.info("getHistoricalData crumb empyt " + symbol);
            return null;
        }
        ///// some issue in the weekend get error essage
        ///// some issue in the weekend get error essage
        ServiceAFweb.AFSleep1Sec(5);
        ///// some issue in the weekend get error essage
        ///// some issue in the weekend get error essage
        String url = String.format("https://query1.finance.yahoo.com/v7/finance/download/%s?period1=%s&period2=%s&interval=1d&events=history&crumb=%s", symbol, startDate, endDate, crumb);
        HttpGet request = new HttpGet(url);
//        logger.info("getHistoricalData " + url);

        int LineNum = 0;

        BufferedReader input = null;
        ArrayList StockArray = new ArrayList();

//        request.addHeader("User-Agent", "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13");
        //http://blog.bradlucas.com/posts/2017-06-04-yahoo-finance-quote-download-java/
        request.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:74.0) Gecko/20100101 Firefox/74.0");
        HttpResponse response = client.execute(request, context);
//            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        HttpEntity entity = response.getEntity();

//            String reasonPhrase = response.getStatusLine().getReasonPhrase();
//            int statusCode = response.getStatusLine().getStatusCode();
//
//            System.out.println(String.format("statusCode: %d", statusCode));
//            System.out.println(String.format("reasonPhrase: %s", reasonPhrase));
        if (entity != null) {
            input = new BufferedReader(new InputStreamReader(entity.getContent()));
            String inLine = "";
            while ((inLine = input.readLine()) != null) {
                LineNum++;

//                    System.out.println("Num:"+LineNum+" "+ inLine);
                //Date,Open,High,Low,Close,Adj Close,Volume
                if (inLine.indexOf("Date,Open") != -1) {
//                        System.out.println("Num:" + LineNum + " " + inLine);
                    continue;
                }
                //1995-04-14,null,null,null,null,null,null
                if (inLine.indexOf("null,null,null") != -1) {
//                        System.out.println("Num:" + LineNum + " " + inLine);
                    continue;
                }
                AFstockInfo StockD = StockInfoUtils.parseCSVLine(inLine);

                if (StockD == null) {
                    logger.info("getHistoricalData Exception " + symbol + " " + inLine);
                    return null;
                }

                // does not work
//                if (Max6YrStock <= 0) {
//                    break;
//                }
//                Max6YrStock--;
                StockArray.add(StockD);
            }
            Collections.reverse(StockArray);

        }
        HttpClientUtils.closeQuietly(response);
//        logger.info("getHistoricalData added record " + LineNum);

        return StockArray;

    }

}
