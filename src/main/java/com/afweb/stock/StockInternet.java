/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.stock;

import com.afweb.model.StockInfoTranObj;
import com.afweb.yahoo.GetYahooQuotes;
import com.afweb.model.stock.*;
import com.afweb.service.ServiceAFweb;
import com.afweb.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

import java.net.InetSocketAddress;
import java.net.Proxy;

import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.logging.Logger;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.HttpClientBuilder;

import yahoofinance.YahooFinance;
import yahoofinance.util.RedirectableRequest;

/**
 *
 * @author eddy
 */
public class StockInternet {

    public static Logger logger = Logger.getLogger("StockInternet");

    public static final String QUOTES_QUERY1V7_BASE_URL = System.getProperty("yahoofinance.baseurl.quotesquery1v7", "https://query1.finance.yahoo.com/v7/finance/quote");

////    public static int HALF_YEAR_DATA = 26 * 5;
////    public static int ONE_YEAR_DATA = 52 * 5;
////    public static int TWO_YEAR_DATA = 52 * 5 * 2;
    // always the earliest day first
    // always the earliest day first
    // always the earliest day first    
    public ArrayList<AFstockInfo> GetStockHistoricalInternet(String NormalizeSymbol, int length) { // number of days in lenght
        ServiceAFweb.lastfun = "GetStockHistoricalInternet";

        GetYahooQuotes internetQuote = new GetYahooQuotes();
        ServiceAFweb.getServerObj().setCntInterRequest(ServiceAFweb.getServerObj().getCntInterRequest() + 1);
        ArrayList<AFstockInfo> StockArray = new ArrayList();
        try {
            // bugs in Yahoo start date, startDate = 0 to work
            long startDate = 0;
            Calendar dateNow = TimeConvertion.getCurrentCalendar();
            long endDate = dateNow.getTimeInMillis();
            endDate = Math.round(endDate / 1000); // yahoo site does not take the milisec 000
//            T.TO seems not working
            startDate = TimeConvertion.addMonths(dateNow.getTimeInMillis(), -5 * 12); // 5 yr data
            startDate = Math.round(startDate / 1000); // yahoo site does not take the milisec 000
//            logger.info("Start time: " + new Date(startDate));

            // always the earliest day first
            // always the earliest day first
            // always the earliest day first
            if (CKey.GET_STOCKHISTORY_SCREEN == true) {
                StockArray = getInternetHistoricalScreen(NormalizeSymbol, startDate, endDate);
            } else {
                StockArray = internetQuote.getHistoricalData(NormalizeSymbol, startDate, endDate);
            }

//            if (NormalizeSymbol.equals("HOU.TO")) {
//                logger.info("GetStockHistoricalInternet  " + NormalizeSymbol + " " + StockArray.size());
//            }
            if (StockArray == null) {
                return null;
            }

            AFstockObj stockRT = GetRealTimeStockInternet(NormalizeSymbol);

            AFstockInfo stockInfoRT = stockRT.getAfstockInfo();
            long stockRTvalue = stockInfoRT.getEntrydatel();

            Date stockRTenddate = TimeConvertion.endOfDay(new Date(stockRTvalue));

            AFstockInfo stockInfoObj = (AFstockInfo) StockArray.get(0);
            long stockInfofirst = stockInfoObj.getEntrydatel();
            Date stockInfofirstEndday = TimeConvertion.endOfDay(new Date(stockInfofirst));
            if (stockRTenddate.equals(stockInfofirstEndday)) {

                stockInfoObj.setEntrydatedisplay(new java.sql.Date(stockInfofirstEndday.getTime()));
                stockInfoObj.setEntrydatel(stockInfofirstEndday.getTime());

                stockInfoObj.setFopen(stockInfoRT.getFopen());
                stockInfoObj.setHigh(stockInfoRT.getHigh());
                stockInfoObj.setLow(stockInfoRT.getLow());
                stockInfoObj.setVolume(stockInfoRT.getVolume());
                // Real time does not have adjust coset                
//                stockInfoObj.setAdjustClose(stockInfoObj.getAdjustClose());

            } else if (stockRTvalue > stockInfofirst) {
                // Real time does not have adjust coset                
                stockInfoRT.setAdjustclose(stockInfoObj.getAdjustclose());

                // add to the first
                StockArray.add(0, stockInfoRT);
            }

            ArrayList<AFstockInfo> returnStockArray = new ArrayList();

            int returnSize = StockArray.size();
            if (length != 0) {
                if (length < returnSize) {
                    returnSize = length;
                }
            }

            for (int i = 0; i < returnSize; i++) {
                AFstockInfo stockTem = (AFstockInfo) StockArray.get(i);
                returnStockArray.add(stockTem);

            }
            return returnStockArray;

        } catch (Exception ex) {
            logger.info("GetStockHistoricalInternet Exception " + NormalizeSymbol + " " + ex);

        }
        ServiceAFweb.getServerObj().setCntInterException(ServiceAFweb.getServerObj().getCntInterException() + 1);
        return null;
    }

    public AFstockObj GetRealTimeStockInternet(String NormalizeSymbol) {
        ServiceAFweb.lastfun = "GetRealTimeStockInternet";

        ServiceAFweb.getServerObj().setCntInterRequest(ServiceAFweb.getServerObj().getCntInterRequest() + 1);
        try {
            String url = QUOTES_QUERY1V7_BASE_URL + "?symbols=" + NormalizeSymbol;
            // Get JSON from Yahoo
//            logger.info("GetRealTimeStockInternet Sending request: " + url);

            URL request = new URL(url);
            RedirectableRequest redirectableRequest = new RedirectableRequest(request, 5);
            redirectableRequest.setConnectTimeout(YahooFinance.CONNECTION_TIMEOUT);
            redirectableRequest.setReadTimeout(YahooFinance.CONNECTION_TIMEOUT);

            URLConnection connection = null;
            if (CKey.PROXY == true) {
                //////Add Proxy 
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ServiceAFweb.PROXYURL, 8080));
                connection = request.openConnection(proxy);
                //////Add Proxy 
            } else {
                connection = redirectableRequest.openConnection();
            }
            InputStreamReader is = new InputStreamReader(connection.getInputStream());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode nodeList = objectMapper.readTree(is);
            JsonNode node = null;
            if (nodeList.has("quoteResponse") && nodeList.get("quoteResponse").has("result")) {
                nodeList = nodeList.get("quoteResponse").get("result");
                for (int i = 0; i < nodeList.size(); i++) {
                    // only first nodeList
                    node = nodeList.get(i);
//                    log.info("quoteResponse: " + node);
                    break;
                }
            }
            if (node != null) {

                String symbol = node.get("symbol").asText();
                AFstockObj stock = new AFstockObj();
                stock.setSymbol(symbol);
                if (node.has("longName")) {
                    String longname = node.get("longName").asText();
                    longname = StockInfoUtils.RemoveASCIIChar(longname);
                    stock.setStockname(longname);
                } else {
                    stock.setStockname(symbol);
                }

                if (node.has("regularMarketTime")) {
                    long marketTimeLong = node.get("regularMarketTime").asLong();
                    // must be *1000???
                    marketTimeLong *= 1000;
                    Date marketTime = new Date(marketTimeLong);
                    stock.setUpdatedatedisplay(new java.sql.Date(marketTime.getTime()));
                    stock.setUpdatedatel(marketTime.getTime());

                } else {
                    Calendar dateNow = TimeConvertion.getCurrentCalendar();
                    stock.setUpdatedatedisplay(new java.sql.Date(dateNow.getTimeInMillis()));
                    stock.setUpdatedatel(dateNow.getTimeInMillis());
                }
                if (node.has("regularMarketPreviousClose") == true) {

                    String regularMarketPreviousClose = node.get("regularMarketPreviousClose").asText();
                    float fprevClose = Float.parseFloat(regularMarketPreviousClose);

                    String regularMarketOpen = node.get("regularMarketOpen").asText();
                    float fopen = Float.parseFloat(regularMarketOpen);

                    String regularMarketPrice = node.get("regularMarketPrice").asText();
                    float fclose = Float.parseFloat(regularMarketPrice);

                    String regularMarketDayHigh = node.get("regularMarketDayHigh").asText();
                    float high = Float.parseFloat(regularMarketDayHigh);

                    String regularMarketDayLow = node.get("regularMarketDayLow").asText();
                    float low = Float.parseFloat(regularMarketDayLow);

                    String regularMarketVolume = node.get("regularMarketVolume").asText();
                    float volume = Float.parseFloat(regularMarketVolume);

                    long stockInfoEnddayvalue = stock.getUpdatedatel();
                    stockInfoEnddayvalue = TimeConvertion.endOfDayInMillis(stockInfoEnddayvalue);

                    AFstockInfo stockInfoObj = new AFstockInfo();
                    stockInfoObj.setEntrydatedisplay(new java.sql.Date(stockInfoEnddayvalue));
                    stockInfoObj.setEntrydatel(stockInfoEnddayvalue);

                    stockInfoObj.setFopen(fopen);
                    stockInfoObj.setFclose(fclose);
                    stockInfoObj.setHigh(high);
                    stockInfoObj.setLow(low);
                    stockInfoObj.setVolume(volume);
                    stock.setAfstockInfo(stockInfoObj);

                    stock.setPrevClose(fprevClose);

                    // force to the symblo request
                    // force to the symblo request                
                    stock.setSymbol(NormalizeSymbol);
                    return stock;
                } else {
                    logger.info("GetRealTimeStockInternet error Market Info " + NormalizeSymbol);
                }
            }
        } catch (Exception ex) {
            logger.info("GetRealTimeStockInternet Exception " + ex);
        }
        ServiceAFweb.getServerObj().setCntInterException(ServiceAFweb.getServerObj().getCntInterException() + 1);
        return null;
    }

    HttpClient client = HttpClientBuilder.create().build();
    HttpClientContext context = HttpClientContext.create();
    // stoct split get the latest
    // https://ca.finance.yahoo.com/quote/HOU.TO/history?period1=1430611200&period2=1588291200&interval=1d&filter=history&frequency=1d
    // change Time Peroid -> 5 Y
    // Apply
    // download

    //https://ca.finance.yahoo.com/quote/DIA/history?period1=885254400&period2=1583625600&interval=1d&filter=history&frequency=1d
    //https://ca.finance.yahoo.com/quote/TD.TO/history?p=TD.TO
    public StringBuffer getInternetYahooScreenPage(String symbol, String url) {
        return getInternetYahooScreenPage(url);
    }

    public StringBuffer getInternetYahooScreenPage(String url) {
        ServiceAFweb.lastfun = "getInternetYahooScreenPage";

        ServiceAFweb.getServerObj().setCntInterRequest(ServiceAFweb.getServerObj().getCntInterRequest() + 1);

        StringBuffer result = new StringBuffer();
        HttpGet request = new HttpGet(url);

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

    public ArrayList<AFstockInfo> getInternetHistoricalScreen(String symbol, long startDate, long endDate) throws IOException {
        ServiceAFweb.lastfun = "getInternetHistoricalScreen";

        String url = String.format("https://ca.finance.yahoo.com/quote/%s/history?p=%s", symbol, symbol);
//        url = "https://ca.finance.yahoo.com/quote/HOU.TO/history?period1=0&period2=1583625600&interval=1d&filter=history&frequency=1d";
//        String url = String.format("https://ca.finance.yahoo.com/quote/%s/history?period1=885254400&period2=1583625600&interval=1d&filter=history&frequency=1d", symbol);
//        String url = String.format("https://ca.finance.yahoo.com/quote/%s/history?period1=%s&period2=%s&interval=1d&events=history&frequency=1d", symbol, startDate, endDate);
        StringBuffer input = getInternetYahooScreenPage(symbol, url);
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

                    //2:1 Stock Split here
                    String stHigh = sTag.GetNextText();
                    if (stHigh.equals("Dividend")) {
                        break;
                    }
                    if (stHigh.equals("Stock Split")) {
                        break;
                    }
                    String stLow = sTag.GetNextText();
                    // Stock Split
                    // will get the Dividend line in here
                    if (stLow.equals("Dividend")) {
                        break;
                    }
                    if (stLow.equals("Stock Split")) {
                        break;
                    }

                    String stClose = sTag.GetNextText();
                    if (stClose.equals("Dividend")) {
                        break;
                    }
                    if (stClose.equals("Stock Split")) {
                        break;
                    }
                    String stAdjClose = sTag.GetNextText();
                    String stVolume = sTag.GetNextText();

                    LineNum++;
                    inLine = stDate + "#" + stOpen + "#" + stHigh + "#" + stLow + "#" + stClose + "#" + stAdjClose + "#" + stVolume;
                    inLine = inLine.replaceAll(",", "");
                    inLine = inLine.replaceAll("#", ",");
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
                        logger.info("getInternetHistoricalScreen Exception " + symbol + " " + inLine);
                        break;
                    }

                    StockArray.add(StockD);
                }

            } catch (Exception ex) {
                logger.info("getInternetHistoricalScreen Exception " + inLine);
                conFlag = false;
                break;
            }
        }
//        logger.info("getHistoricalScreen  " + symbol + " " + StockArray.size());
        return StockArray;
    }

////////////////////////////////////////////////////        

    public static String getServerIP() {
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            return ip.getHostAddress();
        } catch (Exception e) {
        }
        return "";
    }
}
