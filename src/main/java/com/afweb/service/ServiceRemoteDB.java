/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.service;

import com.afweb.model.ConstantKey;
import com.afweb.model.account.*;

import com.afweb.model.stock.*;
import com.afweb.service.db.*;
import com.afweb.util.CKey;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;

import static org.apache.http.protocol.HTTP.USER_AGENT;

/**
 *
 * @author eddy
 */
public class ServiceRemoteDB {

    private static ServiceAFweb serviceAFWeb = null;

    public static Logger logger = Logger.getLogger("ServiceRemoteDB");
    public static String CMD = "cmd";
    public static String CMDPOST = "sqlreq";

    public static String WEBPOST = "";
    private static String URL_PATH = "";

    public static String WEBPOST_MYSQL = CKey.WEBPOST_HERO_PHP; //"/webgetresp.php";
    public static String URLPath_MYSQL = CKey.REMOTEDB_MY_SQLURL;

//    public static String WEBPOST_REQ = "sqlreq";
//    public static String WEBPOST_ASP = "/webgetresp.asp";
//    public static String URLPath_ASP = CKey.REMOTEDB_MS_SQLURL;
    public ServiceRemoteDB() {

        URL_PATH = CKey.REMOTEDB_MY_SQLURL + WEBPOST_MYSQL;

        //openshift Database
        if (CKey.OTHER_PHP1_MYSQL == true) {
            URL_PATH = CKey.URL_PATH_OP_DB_PHP1 + CKey.WEBPOST_OP_PHP;
        }
    }

    /**
     * @return the URL_PATH
     */
    public static String getURL_PATH() {
        return URL_PATH;
    }

    /**
     * @param aURL_PATH the URL_PATH to set
     */
    public static void setURL_PATH(String aURL_PATH) {
        URL_PATH = aURL_PATH;
    }

    /**
     * @return the serviceAFWeb
     */
    public static ServiceAFweb getServiceAFWeb() {
        return serviceAFWeb;
    }

    /**
     * @param aServiceAFWeb the serviceAFWeb to set
     */
    public static void setServiceAFWeb(ServiceAFweb aServiceAFWeb) {
        serviceAFWeb = aServiceAFWeb;
    }

    public int getExecuteRemoteListDB_Mysql(ArrayList<String> sqlCMDList) {
//        log.info("postExecuteListRemoteDB_Mysql sqlCMDList " + sqlCMDList.size());
        String postSt = "";
        int MAXPostSize = 20;
        int postSize = 0;
        for (int i = 0; i < sqlCMDList.size(); i++) {

            postSize++;
            if ((postSize > MAXPostSize) || (postSt.length() > 2000)) {
                try {
                    int ret = postExecuteListRemoteDB_Mysql(postSt);
                    if (ret == 0) {
                        return ret;
                    }
                    postSize = 0;
                    postSt = "";
                } catch (Exception ex) {
                    logger.info("postExecuteListRemoteDB_Mysql exception " + ex);
                    return 0;
                }
            }

            if (postSt.length() == 0) {
                postSt = sqlCMDList.get(i);
                continue;
            }
            postSt += "~" + sqlCMDList.get(i);
        }
        try {
            if (postSt.length() == 0) {
                return 1;
            }
            int ret = postExecuteListRemoteDB_Mysql(postSt);
            return ret;
        } catch (Exception ex) {
            logger.info("postExecuteListRemoteDB_Mysql exception " + ex);
        }
        return 0;

    }

    private int postExecuteListRemoteDB_Mysql(String sqlCMDList) throws Exception {
        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        log.info("postExecuteListRemoteDB_Mysql " + sqlCMDList);
        try {
            String subResourcePath = WEBPOST;
            HashMap newmap = new HashMap();
            newmap.put(CMD, "3");

            HashMap newbodymap = new HashMap();
            newbodymap.put(CMDPOST, sqlCMDList);

            String output = sendRequest_remotesql(METHOD_POST, subResourcePath, newmap, newbodymap);

            int beg = output.indexOf("~~ ");
            int end = output.indexOf(" ~~");

            if ((beg >= end) || (beg == -1)) {
                logger.info("postExecuteListRemoteDB_Mysql output" + sqlCMDList);
                return -1;
            }
            output = output.substring(beg + 3, end);
//            String[] dataArray = output.split("~");
            String[] dataArray = splitIncludeEmpty(output, '~');
            output = dataArray[0];
            if (output == null) {
                logger.info("postExecuteListRemoteDB_Mysql array" + sqlCMDList);
                return 0;
            }
            if (output.length() == 0) {
                return 0;
            }
            return Integer.parseInt(output);

        } catch (Exception ex) {
            logger.info("postExecuteListRemoteDB_Mysql exception " + ex);

            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
            throw ex;
        }
    }

    public int postExecuteRemoteDB_RemoteMysql(String sqlCMD) throws Exception {

        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);

//        log.info("postExecuteRemoteDB_RemoteMysql " + sqlCMD);
        try {
            String subResourcePath = WEBPOST;
            HashMap newmap = new HashMap();
            newmap.put(CMD, "2");

            HashMap newbodymap = new HashMap();
            newbodymap.put(CMDPOST, sqlCMD);

            String output = sendRequest_remotesql(METHOD_POST, subResourcePath, newmap, newbodymap);

            int beg = output.indexOf("~~ ");
            int end = output.indexOf(" ~~");

            if ((beg >= end) || (beg == -1)) {
                logger.info("postExecuteRemoteDB_RemoteMysql fail " + sqlCMD);
                return -1;
            }
            output = output.substring(beg + 3, end);
            if (output.length() > 2) {
                logger.info("postExecuteRemoteDB_RemoteMysql output " + output);
            }
            String[] dataArray = splitIncludeEmpty(output, '~');
            output = dataArray[0];
            if (output == null) {
                logger.info("postExecuteRemoteDB_RemoteMysql fail" + sqlCMD);
                return 0;
            }
            if (output.length() == 0) {
                return 0;
            }
            return Integer.parseInt(output);

        } catch (Exception ex) {
            logger.info("postExecuteRemoteDB_Mysql exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
            throw ex;
        }

    }

    public int getCountRowsRemoteDB_RemoteMysql(String sqlTable) throws Exception {

        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
        try {
            String subResourcePath = WEBPOST;
            // create hash map
            HashMap newmap = new HashMap();
            newmap.put(CMD, "1");

            HashMap newbodymap = new HashMap();
            String sqlcmd = "SELECT COUNT(0) AS c FROM " + sqlTable;
            newbodymap.put(CMDPOST, sqlcmd);

            String output = sendRequest_remotesql(METHOD_POST, subResourcePath, newmap, newbodymap);

            int beg = output.indexOf("~~ ");
            int end = output.indexOf(" ~~");

            if ((beg >= end) || (beg == -1)) {
                return -1;
            }
            output = output.substring(beg + 3, end);

//            String[] dataArray = output.split("~");
            String[] dataArray = splitIncludeEmpty(output, '~');
            output = "[";
            int recSize = 1;
            for (int i = 0; i < dataArray.length; i += recSize) {
                output += "{";
                output += "\"c\":\"" + dataArray[i] + "\"";
                if (i + recSize >= dataArray.length) {
                    output += "}";
                } else {
                    output += "},";
                }
            }
            output += "]";
//            log.info("getCountRowsInTable output " + output);
            ArrayList<CountRowsRDB> arrayDB = null;
            try {
                CountRowsRDB[] arrayItem = new ObjectMapper().readValue(output, CountRowsRDB[].class);
                List<CountRowsRDB> listItem = Arrays.<CountRowsRDB>asList(arrayItem);
                arrayDB = new ArrayList<CountRowsRDB>(listItem);
            } catch (IOException ex) {
                logger.info("getCountRowsInTable exception " + output);
                return -1;
            }
            int countR = arrayDB.get(0).getCount();
            return countR;

        } catch (Exception ex) {
            logger.info("getCountRowsInTable exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
            throw ex;
        }

    }

    public ArrayList getStockSqlRemoteDB_RemoteMysql(String sqlCMD) throws Exception {

        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        log.info("getStockSqlRemoteDB_RemoteMysql sql " + sqlCMD);
        try {
            String subResourcePath = WEBPOST;
            HashMap newmap = new HashMap();
            newmap.put(CMD, "1");

            HashMap newbodymap = new HashMap();
            newbodymap.put(CMDPOST, sqlCMD);

            String output = sendRequest_remotesql(METHOD_POST, subResourcePath, newmap, newbodymap);

            int beg = output.indexOf("~~ ");
            int end = output.indexOf(" ~~");
            // create hash map
            if (beg > end) {
                return null;
            }
            output = output.substring(beg + 3, end);
            if (output.length() == 0) {
                return null;
            }
//            String[] dataArray = output.split("~");
            String[] dataArray = splitIncludeEmpty(output, '~');
            output = "[";
//"create table stock (id int(10) not null auto_increment, symbol varchar(255) not null unique, stockname varchar(255) not null, status int(10) not null, substatus int(10) not null, 
//updatedatedisplay date, updatedatel bigint(20) not null, failedupdate int(10) not null, longterm float not null, shortterm float not null, direction float not null, primary key (id))");

            int recSize = 12;
            for (int i = 0; i < dataArray.length; i += recSize) {
                output += "{";
                output += "\"id\":\"" + dataArray[i] + "\",";
                output += "\"symbol\":\"" + dataArray[i + 1] + "\",";
                output += "\"stockname\":\"" + dataArray[i + 2] + "\",";
                output += "\"status\":\"" + dataArray[i + 3] + "\",";
                output += "\"substatus\":\"" + dataArray[i + 4] + "\",";
                output += "\"updatedatedisplay\":\"" + dataArray[i + 5] + "\",";
                output += "\"updatedatel\":\"" + dataArray[i + 6] + "\",";
                output += "\"failedupdate\":\"" + dataArray[i + 7] + "\",";
                output += "\"longterm\":\"" + dataArray[i + 8] + "\",";
                output += "\"shortterm\":\"" + dataArray[i + 9] + "\",";
                output += "\"direction\":\"" + dataArray[i + 10] + "\",";
                output += "\"data\":\"" + dataArray[i + 11] + "\"";
                if (i + recSize >= dataArray.length) {
                    output += "}";
                } else {
                    output += "},";
                }
            }
            output += "]";
            return getStockSqlRemoteDB_Process(output);

        } catch (Exception ex) {
            logger.info("getStockSqlRemoteDB_RemoteMysql exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
            throw ex;
        }
    }

    private ArrayList<AFstockObj> getStockSqlRemoteDB_Process(String output) {
        if (output.equals("")) {
            return null;
        }
        ArrayList<StockRDB> arrayDB = null;
        ArrayList<AFstockObj> arrayReturn = new ArrayList();
        try {
            StockRDB[] arrayItem = new ObjectMapper().readValue(output, StockRDB[].class);
            List<StockRDB> listItem = Arrays.<StockRDB>asList(arrayItem);
            arrayDB = new ArrayList<StockRDB>(listItem);

            for (int i = 0; i < arrayDB.size(); i++) {
                StockRDB stockRDB = arrayDB.get(i);
                AFstockObj stock = new AFstockObj();
                stock.setId(Integer.parseInt(stockRDB.getId()));
                stock.setStockname(stockRDB.getStockname());
                stock.setSymbol(stockRDB.getSymbol());
                stock.setStatus(Integer.parseInt(stockRDB.getStatus()));
                stock.setSubstatus(Integer.parseInt(stockRDB.getSubstatus()));
                stock.setUpdatedatel(Long.parseLong(stockRDB.getUpdatedatel()));
                stock.setUpdatedatedisplay(new java.sql.Date(stock.getUpdatedatel()));
                stock.setFailedupdate(Integer.parseInt(stockRDB.getFailedupdate()));
                stock.setLongterm(Float.parseFloat(stockRDB.getLongterm()));
                stock.setShortterm(Float.parseFloat(stockRDB.getShortterm()));
                stock.setDirection(Float.parseFloat(stockRDB.getDirection()));
                stock.setData(stockRDB.getData());

                String tzid = "America/New_York"; //EDT
                TimeZone tz = TimeZone.getTimeZone(tzid);
                Date d = new Date(stock.getUpdatedatel());
                DateFormat format = new SimpleDateFormat("M/dd/yyyy hh:mm a z");
                format.setTimeZone(tz);
                String ESTdate = format.format(d);
                stock.setUpdateDateD(ESTdate);
                stock.setTRsignal(0);

                arrayReturn.add(stock);
            }
            return arrayReturn;
        } catch (IOException ex) {
            logger.info("getStockSqlRemoteDB_Process exception " + output);
            return null;
        }
    }

    public ArrayList<AFstockInfo> getStockInfoSqlRemoteDB_RemoteMysql(String sqlCMD) throws Exception {

        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        log.info("getStockInfoSqlRemoteDB_RemoteMysql " + sqlCMD);
        try {
            String subResourcePath = WEBPOST;
            HashMap newmap = new HashMap();
            newmap.put(CMD, "1");

            HashMap newbodymap = new HashMap();
            newbodymap.put(CMDPOST, sqlCMD);

            String output = sendRequest_remotesql(METHOD_POST, subResourcePath, newmap, newbodymap);

            int beg = output.indexOf("~~ ");
            int end = output.indexOf(" ~~");
            // create hash map
            if (beg > end) {
                return null;
            }
            output = output.substring(beg + 3, end);
            if (output.length() == 0) {
                return null;
            }
//            String[] dataArray = output.split("~");
            String[] dataArray = splitIncludeEmpty(output, '~');
            output = "[";
// "create table stockinfo (id int(10) not null auto_increment, entrydatedisplay date not null, entrydatel bigint(20) not null, 
//fopen float not null, fclose float not null, high float not null, low float not null, volume float not null, adjustclose float not null,
//stockid int(10) not null, primary key (id))");

            int recSize = 10;
            for (int i = 0; i < dataArray.length; i += recSize) {
                output += "{";
                output += "\"id\":\"" + dataArray[i] + "\",";
                output += "\"entrydatedisplay\":\"" + dataArray[i + 1] + "\",";
                output += "\"entrydatel\":\"" + dataArray[i + 2] + "\",";
                output += "\"fopen\":\"" + dataArray[i + 3] + "\",";
                output += "\"fclose\":\"" + dataArray[i + 4] + "\",";
                output += "\"high\":\"" + dataArray[i + 5] + "\",";
                output += "\"low\":\"" + dataArray[i + 6] + "\",";
                output += "\"volume\":\"" + dataArray[i + 7] + "\",";
                output += "\"adjustclose\":\"" + dataArray[i + 8] + "\",";
                output += "\"stockid\":\"" + dataArray[i + 9] + "\"";

                if (i + recSize >= dataArray.length) {
                    output += "}";
                } else {
                    output += "},";
                }
            }
            output += "]";
            return getStockInfoSqlRemoteDB_Process(output);

        } catch (Exception ex) {
            logger.info("getStockInfoSqlRemoteDB_RemoteMysql exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
            throw ex;
        }
    }

    private ArrayList<AFstockInfo> getStockInfoSqlRemoteDB_Process(String output) {
        if (output.equals("")) {
            return null;
        }
        ArrayList<StockInfoRDB> arrayDB = null;
        ArrayList<AFstockInfo> arrayReturn = new ArrayList();
        try {
            StockInfoRDB[] arrayItem = new ObjectMapper().readValue(output, StockInfoRDB[].class);
            List<StockInfoRDB> listItem = Arrays.<StockInfoRDB>asList(arrayItem);
            arrayDB = new ArrayList<StockInfoRDB>(listItem);

            for (int i = 0; i < arrayDB.size(); i++) {
                StockInfoRDB rs = arrayDB.get(i);

                AFstockInfo stocktmp = new AFstockInfo();
                stocktmp.setEntrydatel(Long.parseLong(rs.getEntrydatel()));
                stocktmp.setEntrydatedisplay(new java.sql.Date(stocktmp.getEntrydatel()));
                stocktmp.setFopen(Float.parseFloat(rs.getFopen()));
                stocktmp.setFclose(Float.parseFloat(rs.getFclose()));
                stocktmp.setHigh(Float.parseFloat(rs.getHigh()));
                stocktmp.setLow(Float.parseFloat(rs.getLow()));
                stocktmp.setVolume(Float.parseFloat(rs.getVolume()));
                stocktmp.setAdjustclose(Float.parseFloat(rs.getAdjustclose()));
                stocktmp.setStockid(Integer.parseInt(rs.getStockid()));
                stocktmp.setId(Integer.parseInt(rs.getId()));

                arrayReturn.add(stocktmp);
            }
            return arrayReturn;
        } catch (IOException ex) {
            logger.info("getStockInfoSqlRemoteDB_Process exception " + output);
            return null;
        }
    }

    public ArrayList<CustomerObj> getCustomerListSqlRemoteDB_RemoteMysql(String sqlCMD) throws Exception {

        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        log.info("getCustomerListSqlRemoteDB_RemoteMysql " + sqlCMD);
        try {
            String subResourcePath = WEBPOST;
            HashMap newmap = new HashMap();
            newmap.put(CMD, "1");

            HashMap newbodymap = new HashMap();
            newbodymap.put(CMDPOST, sqlCMD);

            String output = sendRequest_remotesql(METHOD_POST, subResourcePath, newmap, newbodymap);

            int beg = output.indexOf("~~ ");
            int end = output.indexOf(" ~~");
            // create hash map
            if (beg > end) {
                return null;
            }
            output = output.substring(beg + 3, end);
            if (output.length() == 0) {
                return null;
            }
//            String[] dataArray = output.split("~");
            String[] dataArray = splitIncludeEmpty(output, '~');
            output = "[";

            int recSize = 16;
            for (int i = 0; i < dataArray.length; i += recSize) {
                output += "{";
                output += "\"id\":\"" + dataArray[i] + "\",";
                output += "\"username\":\"" + dataArray[i + 1] + "\",";
                output += "\"password\":\"" + dataArray[i + 2] + "\",";
                output += "\"type\":\"" + dataArray[i + 3] + "\",";
                output += "\"status\":\"" + dataArray[i + 4] + "\",";
                output += "\"substatus\":\"" + dataArray[i + 5] + "\",";
                output += "\"startdate\":\"" + dataArray[i + 6] + "\",";
                output += "\"firstname\":\"" + dataArray[i + 7] + "\",";
                output += "\"lastname\":\"" + dataArray[i + 8] + "\",";
                output += "\"email\":\"" + dataArray[i + 9] + "\",";
                output += "\"payment\":\"" + dataArray[i + 10] + "\",";
                output += "\"balance\":\"" + dataArray[i + 11] + "\",";
                output += "\"portfolio\":\"" + dataArray[i + 12] + "\",";
                output += "\"data\":\"" + dataArray[i + 13] + "\",";
                output += "\"updatedatedisplay\":\"" + dataArray[i + 14] + "\",";
                output += "\"updatedatel\":\"" + dataArray[i + 15] + "\"";

                if (i + recSize >= dataArray.length) {
                    output += "}";
                } else {
                    output += "},";
                }
            }
            output += "]";
            return getCustomerListSqlRemoteDB_Process(output);

        } catch (Exception ex) {
            logger.info("getCustomerListSqlRemoteDB exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
            throw ex;
        }
    }

    private ArrayList<CustomerObj> getCustomerListSqlRemoteDB_Process(String output) {
        if (output.equals("")) {
            return null;
        }
        ArrayList<CustomerRDB> arrayDB = null;
        ArrayList<CustomerObj> arrayReturn = new ArrayList();
        try {
            CustomerRDB[] arrayItem = new ObjectMapper().readValue(output, CustomerRDB[].class);
            List<CustomerRDB> listItem = Arrays.<CustomerRDB>asList(arrayItem);
            arrayDB = new ArrayList<CustomerRDB>(listItem);

            for (int i = 0; i < arrayDB.size(); i++) {
                CustomerRDB rs = arrayDB.get(i);
                CustomerObj customer = new CustomerObj();
                customer.setId(Integer.parseInt(rs.getId()));
                customer.setUsername(rs.getUsername());
                customer.setPassword(rs.getPassword());
                customer.setType(Integer.parseInt(rs.getType()));
                customer.setStatus(Integer.parseInt(rs.getStatus()));
                customer.setSubstatus(Integer.parseInt(rs.getSubstatus()));

                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dayObj = sdf.parse(rs.getStartdate());
                customer.setStartdate(new java.sql.Date(dayObj.getTime()));
                String FN = rs.getFirstname();
                if (FN.equals(ConstantKey.nullSt)) {
                    FN = null;
                }
                String LN = rs.getLastname();
                if (LN.equals(ConstantKey.nullSt)) {
                    LN = null;
                }
                customer.setFirstname(FN);
                customer.setLastname(LN);
                customer.setEmail(rs.getEmail());
                customer.setPayment(Float.parseFloat(rs.getPayment()));
                customer.setBalance(Float.parseFloat(rs.getBalance()));
                customer.setPortfolio(rs.getPortfolio());
                customer.setData(rs.getData());
                customer.setUpdatedatel(Long.parseLong(rs.getUpdatedatel()));
                customer.setUpdatedatedisplay(new java.sql.Date(customer.getUpdatedatel()));

                String tzid = "America/New_York"; //EDT
                TimeZone tz = TimeZone.getTimeZone(tzid);
                Date d = new Date(customer.getUpdatedatel());
                DateFormat format = new SimpleDateFormat("M/dd/yyyy hh:mm a z");
                format.setTimeZone(tz);
                String ESTdate = format.format(d);
                customer.setUpdateDateD(ESTdate);

                arrayReturn.add(customer);
            }
            return arrayReturn;
        } catch (Exception ex) {
            logger.info("getCustomerListSqlRemoteDB exception " + ex);
            return null;
        }
    }

    public ArrayList getAccountListSqlRemoteDB_RemoteMysql(String sqlCMD) throws Exception {

        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        log.info("getAccountListSqlRemoteDB_RemoteMysql " + sqlCMD);
        try {
            String subResourcePath = WEBPOST;
            HashMap newmap = new HashMap();
            newmap.put(CMD, "1");

            HashMap newbodymap = new HashMap();
            newbodymap.put(CMDPOST, sqlCMD);

            String output = sendRequest_remotesql(METHOD_POST, subResourcePath, newmap, newbodymap);

            int beg = output.indexOf("~~ ");
            int end = output.indexOf(" ~~");
            // create hash map
            if (beg > end) {
                return null;
            }
            output = output.substring(beg + 3, end);
            if (output.length() == 0) {
                return null;
            }
//            String[] dataArray = output.split("~");
            String[] dataArray = splitIncludeEmpty(output, '~');
            output = "[";
//"create table account (id int(10) not null auto_increment, accountname varchar(255) not null, type int(10) not null, status int(10) not null, 
//substatus int(10) not null, updatedatedisplay date, updatedatel bigint(20) not null, startdate date, investment float not null, 
//balance float not null, servicefee float not null, linkaccountid int(10) not null, customerid int(10) not null, primary key (id))");

            int recSize = 14;
            for (int i = 0; i < dataArray.length; i += recSize) {
                output += "{";
                output += "\"id\":\"" + dataArray[i] + "\",";
                output += "\"accountname\":\"" + dataArray[i + 1] + "\",";
                output += "\"type\":\"" + dataArray[i + 2] + "\",";
                output += "\"status\":\"" + dataArray[i + 3] + "\",";
                output += "\"substatus\":\"" + dataArray[i + 4] + "\",";
                output += "\"updatedatedisplay\":\"" + dataArray[i + 5] + "\",";
                output += "\"updatedatel\":\"" + dataArray[i + 6] + "\",";
                output += "\"startdate\":\"" + dataArray[i + 7] + "\",";
                output += "\"investment\":\"" + dataArray[i + 8] + "\",";
                output += "\"balance\":\"" + dataArray[i + 9] + "\",";
                output += "\"servicefee\":\"" + dataArray[i + 10] + "\",";
                output += "\"portfolio\":\"" + dataArray[i + 11] + "\",";
                output += "\"linkaccountid\":\"" + dataArray[i + 12] + "\",";
                output += "\"customerid\":\"" + dataArray[i + 13] + "\"";

                if (i + recSize >= dataArray.length) {
                    output += "}";
                } else {
                    output += "},";
                }
            }
            output += "]";
            return getAccountListSqlRemoteDB_Process(output);

        } catch (Exception ex) {
            logger.info("getAccountListSqlRemoteDB exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
            throw ex;
        }
    }

    private ArrayList<AccountObj> getAccountListSqlRemoteDB_Process(String output) {
        if (output.equals("")) {
            return null;
        }
        ArrayList<AccountRDB> arrayDB = null;
        ArrayList<AccountObj> arrayReturn = new ArrayList();
        try {
            AccountRDB[] arrayItem = new ObjectMapper().readValue(output, AccountRDB[].class);
            List<AccountRDB> listItem = Arrays.<AccountRDB>asList(arrayItem);
            arrayDB = new ArrayList<AccountRDB>(listItem);

            for (int i = 0; i < arrayDB.size(); i++) {
                AccountRDB rs = arrayDB.get(i);
                AccountObj account = new AccountObj();

                account.setId(Integer.parseInt(rs.getId()));
                account.setAccountname(rs.getAccountname());
                account.setType(Integer.parseInt(rs.getType()));
                account.setStatus(Integer.parseInt(rs.getStatus()));
                account.setSubstatus(Integer.parseInt(rs.getSubstatus()));
                account.setUpdatedatel(Long.parseLong(rs.getUpdatedatel()));
                account.setUpdatedatedisplay(new java.sql.Date(account.getUpdatedatel()));

                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dayObj = sdf.parse(rs.getStartdate());
                account.setStartdate(new java.sql.Date(dayObj.getTime()));

                account.setInvestment(Float.parseFloat(rs.getInvestment()));
                account.setBalance(Float.parseFloat(rs.getBalance()));
                account.setServicefee(Float.parseFloat(rs.getServicefee()));
                account.setPortfolio(rs.getPortfolio());

                account.setLinkaccountid(Integer.parseInt(rs.getLinkaccountid()));
                account.setCustomerid(Integer.parseInt(rs.getCustomerid()));

                arrayReturn.add(account);
            }
            return arrayReturn;
        } catch (Exception ex) {
            logger.info("getAccountListSqlRemoteDB exception " + output);
            return null;
        }
    }

    public ArrayList getBillingListSqlRemoteDB_RemoteMysql(String sqlCMD) throws Exception {

        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        log.info("getAccountListSqlRemoteDB_RemoteMysql " + sqlCMD);
        try {
            String subResourcePath = WEBPOST;
            HashMap newmap = new HashMap();
            newmap.put(CMD, "1");

            HashMap newbodymap = new HashMap();
            newbodymap.put(CMDPOST, sqlCMD);

            String output = sendRequest_remotesql(METHOD_POST, subResourcePath, newmap, newbodymap);

            int beg = output.indexOf("~~ ");
            int end = output.indexOf(" ~~");
            // create hash map
            if (beg > end) {
                return null;
            }
            output = output.substring(beg + 3, end);
            if (output.length() == 0) {
                return null;
            }
//            String[] dataArray = output.split("~");
            String[] dataArray = splitIncludeEmpty(output, '~');
            output = "[";

            int recSize = 12;
            for (int i = 0; i < dataArray.length; i += recSize) {
                output += "{";
                output += "\"id\":\"" + dataArray[i] + "\",";
                output += "\"name\":\"" + dataArray[i + 1] + "\",";
                output += "\"type\":\"" + dataArray[i + 2] + "\",";
                output += "\"status\":\"" + dataArray[i + 3] + "\",";
                output += "\"substatus\":\"" + dataArray[i + 4] + "\",";
                output += "\"updatedatedisplay\":\"" + dataArray[i + 5] + "\",";
                output += "\"updatedatel\":\"" + dataArray[i + 6] + "\",";
                output += "\"payment\":\"" + dataArray[i + 7] + "\",";
                output += "\"balance\":\"" + dataArray[i + 8] + "\",";
                output += "\"data\":\"" + dataArray[i + 9] + "\",";
                output += "\"accountid\":\"" + dataArray[i + 10] + "\",";
                output += "\"customerid\":\"" + dataArray[i + 11] + "\"";

                if (i + recSize >= dataArray.length) {
                    output += "}";
                } else {
                    output += "},";
                }
            }
            output += "]";
            return getBillingListSqlRemoteDB_Process(output);

        } catch (Exception ex) {
            logger.info("getBillingListSqlRemoteDB_RemoteMysql exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
            throw ex;
        }
    }

    private ArrayList<BillingObj> getBillingListSqlRemoteDB_Process(String output) {
        if (output.equals("")) {
            return null;
        }
        ArrayList<BillingRDB> arrayDB = null;
        ArrayList<BillingObj> arrayReturn = new ArrayList();
        try {
            BillingRDB[] arrayItem = new ObjectMapper().readValue(output, BillingRDB[].class);
            List<BillingRDB> listItem = Arrays.<BillingRDB>asList(arrayItem);
            arrayDB = new ArrayList<BillingRDB>(listItem);

            for (int i = 0; i < arrayDB.size(); i++) {
                BillingRDB rs = arrayDB.get(i);
                BillingObj billing = new BillingObj();

                billing.setId(Integer.parseInt(rs.getId()));
                billing.setName(rs.getName());
                billing.setType(Integer.parseInt(rs.getType()));
                billing.setStatus(Integer.parseInt(rs.getStatus()));
                billing.setSubstatus(Integer.parseInt(rs.getSubstatus()));
                billing.setUpdatedatel(Long.parseLong(rs.getUpdatedatel()));
                billing.setUpdatedatedisplay(new java.sql.Date(billing.getUpdatedatel()));

                billing.setPayment(Float.parseFloat(rs.getPayment()));
                billing.setBalance(Float.parseFloat(rs.getBalance()));

                billing.setData(rs.getData());

                billing.setAccountid(Integer.parseInt(rs.getAccountid()));
                billing.setCustomerid(Integer.parseInt(rs.getCustomerid()));

                arrayReturn.add(billing);
            }
            return arrayReturn;
        } catch (Exception ex) {
            logger.info("getBillingListSqlRemoteDB_Process exception " + output);
            return null;
        }
    }

    public ArrayList getCommListSqlRemoteDB_RemoteMysql(String sqlCMD) throws Exception {

        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        log.info("getAccountListSqlRemoteDB_RemoteMysql " + sqlCMD);
        try {
            String subResourcePath = WEBPOST;
            HashMap newmap = new HashMap();
            newmap.put(CMD, "1");

            HashMap newbodymap = new HashMap();
            newbodymap.put(CMDPOST, sqlCMD);

            String output = sendRequest_remotesql(METHOD_POST, subResourcePath, newmap, newbodymap);

            int beg = output.indexOf("~~ ");
            int end = output.indexOf(" ~~");
            // create hash map
            if (beg > end) {
                return null;
            }
            output = output.substring(beg + 3, end);
            if (output.length() == 0) {
                return null;
            }
//            String[] dataArray = output.split("~");
            String[] dataArray = splitIncludeEmpty(output, '~');
            output = "[";

            int recSize = 10;
            for (int i = 0; i < dataArray.length; i += recSize) {
                output += "{";
                output += "\"id\":\"" + dataArray[i] + "\",";
                output += "\"name\":\"" + dataArray[i + 1] + "\",";
                output += "\"type\":\"" + dataArray[i + 2] + "\",";
                output += "\"status\":\"" + dataArray[i + 3] + "\",";
                output += "\"substatus\":\"" + dataArray[i + 4] + "\",";
                output += "\"updatedatedisplay\":\"" + dataArray[i + 5] + "\",";
                output += "\"updatedatel\":\"" + dataArray[i + 6] + "\",";
                output += "\"data\":\"" + dataArray[i + 7] + "\",";
                output += "\"accountid\":\"" + dataArray[i + 8] + "\",";
                output += "\"customerid\":\"" + dataArray[i + 9] + "\"";

                if (i + recSize >= dataArray.length) {
                    output += "}";
                } else {
                    output += "},";
                }
            }
            output += "]";
            return getCommListSqlRemoteDB_Process(output);

        } catch (Exception ex) {
            logger.info("getCommListSqlRemoteDB_RemoteMysql exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
            throw ex;
        }
    }

    private ArrayList<CommObj> getCommListSqlRemoteDB_Process(String output) {
        if (output.equals("")) {
            return null;
        }
        ArrayList<CommRDB> arrayDB = null;
        ArrayList<CommObj> arrayReturn = new ArrayList();
        try {
            CommRDB[] arrayItem = new ObjectMapper().readValue(output, CommRDB[].class);
            List<CommRDB> listItem = Arrays.<CommRDB>asList(arrayItem);
            arrayDB = new ArrayList<CommRDB>(listItem);

            for (int i = 0; i < arrayDB.size(); i++) {
                CommRDB rs = arrayDB.get(i);
                CommObj comm = new CommObj();

                comm.setId(Integer.parseInt(rs.getId()));
                comm.setName(rs.getName());
                comm.setType(Integer.parseInt(rs.getType()));
                comm.setStatus(Integer.parseInt(rs.getStatus()));
                comm.setSubstatus(Integer.parseInt(rs.getSubstatus()));
                comm.setUpdatedatel(Long.parseLong(rs.getUpdatedatel()));
                comm.setUpdatedatedisplay(new java.sql.Date(comm.getUpdatedatel()));

                comm.setData(rs.getData());
                comm.setAccountid(Integer.parseInt(rs.getAccountid()));
                comm.setCustomerid(Integer.parseInt(rs.getCustomerid()));

                arrayReturn.add(comm);
            }
            return arrayReturn;
        } catch (Exception ex) {
            logger.info("getCommListSqlRemoteDB_Process exception " + output);
            return null;
        }
    }

    public ArrayList<TransationOrderObj> getAccountStockTransactionListRemoteDB_RemoteMysql(String sqlCMD) throws Exception {

        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        log.info("getAccountStockTransactionListRemoteDB_RemoteMysql " + sqlCMD);
        try {
            String subResourcePath = WEBPOST;
            HashMap newmap = new HashMap();
            newmap.put(CMD, "1");

            HashMap newbodymap = new HashMap();
            newbodymap.put(CMDPOST, sqlCMD);

            String output = sendRequest_remotesql(METHOD_POST, subResourcePath, newmap, newbodymap);

            int beg = output.indexOf("~~ ");
            int end = output.indexOf(" ~~");
            // create hash map
            if (beg > end) {
                return null;
            }
            output = output.substring(beg + 3, end);
            if (output.length() == 0) {
                return null;
            }
//            String[] dataArray = output.split("~");
            String[] dataArray = splitIncludeEmpty(output, '~');
            output = "[";
//"create table transationorder (id int(10) not null auto_increment, symbol varchar(255), status int(10) not null, type int(10) not null, 
//entrydatedisplay date, entrydatel bigint(20), share float not null, avgprice float not null, trname varchar(255), 
//trsignal int(10) not null, accountid int(10) not null, stockid int(10) not null, tradingruleid int(10) not null, comment varchar(255) not null, primary key (id))");

            int recSize = 14;
            for (int i = 0; i < dataArray.length; i += recSize) {
                output += "{";
                output += "\"id\":\"" + dataArray[i] + "\",";
                output += "\"symbol\":\"" + dataArray[i + 1] + "\",";
                output += "\"status\":\"" + dataArray[i + 2] + "\",";
                output += "\"type\":\"" + dataArray[i + 3] + "\",";
                output += "\"entrydatedisplay\":\"" + dataArray[i + 4] + "\",";
                output += "\"entrydatel\":\"" + dataArray[i + 5] + "\",";
                output += "\"share\":\"" + dataArray[i + 6] + "\",";
                output += "\"avgprice\":\"" + dataArray[i + 7] + "\",";
                output += "\"trname\":\"" + dataArray[i + 8] + "\",";
                output += "\"trsignal\":\"" + dataArray[i + 9] + "\",";
                output += "\"accountid\":\"" + dataArray[i + 10] + "\",";
                output += "\"stockid\":\"" + dataArray[i + 11] + "\",";
                output += "\"tradingruleid\":\"" + dataArray[i + 12] + "\",";
                output += "\"comment\":\"" + dataArray[i + 13] + "\"";

                if (i + recSize >= dataArray.length) {
                    output += "}";
                } else {
                    output += "},";
                }
            }
            output += "]";
            return getAccountStockTransactionListRemoteDB_Process(output);

        } catch (Exception ex) {
            logger.info("getAccountStockTransactionListRemoteDB_RemoteMysql exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
            throw ex;
        }
    }

    private ArrayList<TransationOrderObj> getAccountStockTransactionListRemoteDB_Process(String output) {
        if (output.equals("")) {
            return null;
        }
        ArrayList<TransationOrderRDB> arrayDB = null;
        ArrayList<TransationOrderObj> arrayReturn = new ArrayList();
        try {
            TransationOrderRDB[] arrayItem = new ObjectMapper().readValue(output, TransationOrderRDB[].class);
            List<TransationOrderRDB> listItem = Arrays.<TransationOrderRDB>asList(arrayItem);
            arrayDB = new ArrayList<TransationOrderRDB>(listItem);

            for (int i = 0; i < arrayDB.size(); i++) {
                TransationOrderRDB rs = arrayDB.get(i);
                TransationOrderObj tran = new TransationOrderObj();

                tran.setId(Integer.parseInt(rs.getId()));
                tran.setAccountid(Integer.parseInt(rs.getAccountid()));
                tran.setAvgprice(Float.parseFloat(rs.getAvgprice()));

                tran.setEntrydatel(Long.parseLong(rs.getEntrydatel()));
                tran.setEntrydatedisplay(new java.sql.Date(tran.getEntrydatel()));
                tran.setShare(Float.parseFloat(rs.getShare()));
                tran.setStatus(Integer.parseInt(rs.getStatus()));
                tran.setStockid(Integer.parseInt(rs.getStockid()));
                tran.setSymbol(rs.getSymbol());
                tran.setTradingruleid(Integer.parseInt(rs.getTradingruleid()));
                tran.setTrname(rs.getTrname());
                tran.setComment(rs.getComment());
                tran.setTrsignal(Integer.parseInt(rs.getTrsignal()));
                tran.setType(Integer.parseInt(rs.getType()));

                String tzid = "America/New_York"; //EDT
                TimeZone tz = TimeZone.getTimeZone(tzid);
                Date d = new Date(tran.getEntrydatel());
                DateFormat format = new SimpleDateFormat("M/dd/yyyy hh:mm a z");
                format.setTimeZone(tz);
                String ESTdate = format.format(d);
                tran.setUpdateDateD(ESTdate);

                arrayReturn.add(tran);
            }
            return arrayReturn;
        } catch (IOException ex) {
            logger.info("getAccountStockTransactionListRemoteDB exception " + output);
            return null;
        }
    }

    public ArrayList<PerformanceObj> getAccountStockPerfromanceListRemoteDB_RemoteMysql(String sqlCMD) throws Exception {

        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        log.info("getAccountStockTransactionListRemoteDB_RemoteMysql " + sqlCMD);
        try {
            String subResourcePath = WEBPOST;
            HashMap newmap = new HashMap();
            newmap.put(CMD, "1");

            HashMap newbodymap = new HashMap();
            newbodymap.put(CMDPOST, sqlCMD);

            String output = sendRequest_remotesql(METHOD_POST, subResourcePath, newmap, newbodymap);

            int beg = output.indexOf("~~ ");
            int end = output.indexOf(" ~~");
            // create hash map
            if (beg > end) {
                return null;
            }
            output = output.substring(beg + 3, end);
            if (output.length() == 0) {
                return null;
            }
//            String[] dataArray = output.split("~");
            String[] dataArray = splitIncludeEmpty(output, '~');
            output = "[";
//"create table performance (id int(10) not null auto_increment, name text, type int(10) not null, startdate date, 
//updatedatedisplay date, updatedatel bigint(20) not null, investment float not null, balance float not null, rating float not null,
//netprofit float not null, grossprofit float not null, numtrade int(10) not null"
// ", accountid int(10) not null, stockid int not null, tradingruleid int(10) not null, primary key (id))");

            int recSize = 15;
            for (int i = 0; i < dataArray.length; i += recSize) {
                output += "{";
                output += "\"id\":\"" + dataArray[i] + "\",";
                output += "\"name\":\"" + dataArray[i + 1] + "\",";
                output += "\"type\":\"" + dataArray[i + 2] + "\",";
                output += "\"startdate\":\"" + dataArray[i + 3] + "\",";
                output += "\"updatedatedisplay\":\"" + dataArray[i + 4] + "\",";
                output += "\"updatedatel\":\"" + dataArray[i + 5] + "\",";
                output += "\"investment\":\"" + dataArray[i + 6] + "\",";
                output += "\"balance\":\"" + dataArray[i + 7] + "\",";
                output += "\"rating\":\"" + dataArray[i + 8] + "\",";
                output += "\"netprofit\":\"" + dataArray[i + 9] + "\",";
                output += "\"grossprofit\":\"" + dataArray[i + 10] + "\",";
                output += "\"numtrade\":\"" + dataArray[i + 11] + "\",";
                output += "\"accountid\":\"" + dataArray[i + 12] + "\",";
                output += "\"stockid\":\"" + dataArray[i + 13] + "\",";
                output += "\"tradingruleid\":\"" + dataArray[i + 14] + "\"";

                if (i + recSize >= dataArray.length) {
                    output += "}";
                } else {
                    output += "},";
                }
            }
            output += "]";
            return getAccountStockPerfromanceListRemoteDB_Process(output);

        } catch (Exception ex) {
            logger.info("getAccountStockPerfromanceListRemoteDB_Mysql exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
            throw ex;
        }
    }

    private ArrayList<PerformanceObj> getAccountStockPerfromanceListRemoteDB_Process(String output) {
        if (output.equals("")) {
            return null;
        }
        ArrayList<PerformanceRDB> arrayDB = null;
        ArrayList<PerformanceObj> arrayReturn = new ArrayList();
        try {
            PerformanceRDB[] arrayItem = new ObjectMapper().readValue(output, PerformanceRDB[].class);
            List<PerformanceRDB> listItem = Arrays.<PerformanceRDB>asList(arrayItem);
            arrayDB = new ArrayList<PerformanceRDB>(listItem);

            for (int i = 0; i < arrayDB.size(); i++) {
                PerformanceRDB rs = arrayDB.get(i);

                PerformanceObj perf = new PerformanceObj();
                perf.setId(Integer.parseInt(rs.getId()));
                perf.setAccountid(Integer.parseInt(rs.getAccountid()));
                perf.setBalance(Float.parseFloat(rs.getBalance()));
                perf.setGrossprofit(Float.parseFloat(rs.getGrossprofit()));
                perf.setInvestment(Float.parseFloat(rs.getInvestment()));
                String name = rs.getName();

                try {
                    if ((name != null) && (name.length() > 0)) {
                        name = name.replaceAll("#", "\"");
                        PerformData perfData = new ObjectMapper().readValue(name, PerformData.class);
                        perf.setPerformData(perfData);
                    }
                } catch (Exception ex) {
                    logger.info("> getAccountStockPerfromanceList exception " + ex.getMessage());
                }

                perf.setNetprofit(Float.parseFloat(rs.getNetprofit()));
                perf.setRating(Float.parseFloat(rs.getRating()));
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    Date dayObj;
                    dayObj = sdf.parse(rs.getStartdate());
                    perf.setStartdate(new java.sql.Date(dayObj.getTime()));
                } catch (ParseException ex) {
                    logger.info("> getAccountStockPerfromanceList exception " + ex.getMessage());
                }

                perf.setStockid(Integer.parseInt(rs.getStockid()));
                perf.setTradingruleid(Integer.parseInt(rs.getTradingruleid()));
                perf.setType(Integer.parseInt(rs.getType()));
                perf.setNumtrade(Integer.parseInt(rs.getNumtrade()));
                perf.setUpdatedatel(Long.parseLong(rs.getUpdatedatel()));
                perf.setUpdatedatedisplay(new java.sql.Date(perf.getUpdatedatel()));

                String tzid = "America/New_York"; //EDT
                TimeZone tz = TimeZone.getTimeZone(tzid);
                Date d = new Date(perf.getUpdatedatel());
                DateFormat format = new SimpleDateFormat("M/dd/yyyy hh:mm a z");
                format.setTimeZone(tz);
                String ESTdate = format.format(d);
                perf.setUpdateDateD(ESTdate);

                arrayReturn.add(perf);
            }
            return arrayReturn;
        } catch (IOException ex) {
            logger.info("getAccountStockPerfromanceListRemoteDB_Process exception " + output);
            return null;
        }
    }

    public ArrayList getAccountStockListSqlRemoteDB_RemoteMysql(String sqlCMD) throws Exception {

        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        log.info("getAccountStockListSqlRemoteDB_RemoteMysql " + sqlCMD);
        try {
            //"select tradingrule.*, stock.symbol as symbol  always add symbol at the end
            String subResourcePath = WEBPOST;
            HashMap newmap = new HashMap();
            newmap.put(CMD, "1");

            HashMap newbodymap = new HashMap();
            newbodymap.put(CMDPOST, sqlCMD);

            String output = sendRequest_remotesql(METHOD_POST, subResourcePath, newmap, newbodymap);

            int beg = output.indexOf("~~ ");
            int end = output.indexOf(" ~~");
            // create hash map
            if (beg > end) {
                return null;
            }
            output = output.substring(beg + 3, end);
            if (output.length() == 0) {
                return null;
            }
//            String[] dataArray = output.split("~");
            String[] dataArray = splitIncludeEmpty(output, '~');
            output = "[";

            //"select tradingrule.*, stock.symbol as symbol  always add symbol at the end
            //"select tradingrule.*, stock.symbol as symbol  always add symbol at the end
            int recSize = 20;
            for (int i = 0; i < dataArray.length; i += recSize) {
                output += "{";
                output += "\"id\":\"" + dataArray[i] + "\",";
                output += "\"trname\":\"" + dataArray[i + 1] + "\",";
                output += "\"type\":\"" + dataArray[i + 2] + "\",";
                output += "\"trsignal\":\"" + dataArray[i + 3] + "\",";
                output += "\"updatedatedisplay\":\"" + dataArray[i + 4] + "\",";
                output += "\"updatedatel\":\"" + dataArray[i + 5] + "\",";
                output += "\"status\":\"" + dataArray[i + 6] + "\",";
                output += "\"substatus\":\"" + dataArray[i + 7] + "\",";
                output += "\"investment\":\"" + dataArray[i + 8] + "\",";
                output += "\"balance\":\"" + dataArray[i + 9] + "\",";
                output += "\"longshare\":\"" + dataArray[i + 10] + "\",";
                output += "\"longamount\":\"" + dataArray[i + 11] + "\",";
                output += "\"shortshare\":\"" + dataArray[i + 12] + "\",";
                output += "\"shortamount\":\"" + dataArray[i + 13] + "\",";
                output += "\"perf\":\"" + dataArray[i + 14] + "\",";
                output += "\"linktradingruleid\":\"" + dataArray[i + 15] + "\",";
                output += "\"stockid\":\"" + dataArray[i + 16] + "\",";
                output += "\"accountid\":\"" + dataArray[i + 17] + "\",";
                output += "\"comment\":\"" + dataArray[i + 18] + "\",";
                output += "\"symbol\":\"" + dataArray[i + 19] + "\"";

                if (i + recSize >= dataArray.length) {
                    output += "}";
                } else {
                    output += "},";
                }
            }
            output += "]";
            return getAccountStockListSqlRemoteDB_Process(output);

        } catch (Exception ex) {
            logger.info("getAccountStockListSqlRemoteDB exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
            throw ex;
        }
    }

    private ArrayList<TradingRuleObj> getAccountStockListSqlRemoteDB_Process(String output) {
        if (output.equals("")) {
            return null;
        }
        ArrayList<TradingRuleRDB> arrayDB = null;
        ArrayList<TradingRuleObj> arrayReturn = new ArrayList();
        try {
            TradingRuleRDB[] arrayItem = new ObjectMapper().readValue(output, TradingRuleRDB[].class);
            List<TradingRuleRDB> listItem = Arrays.<TradingRuleRDB>asList(arrayItem);
            arrayDB = new ArrayList<TradingRuleRDB>(listItem);

            for (int i = 0; i < arrayDB.size(); i++) {
                TradingRuleRDB rs = arrayDB.get(i);
                TradingRuleObj tradingRule = new TradingRuleObj();

                tradingRule.setId(Integer.parseInt(rs.getId()));
                tradingRule.setTrname(rs.getTrname());
                tradingRule.setType(Integer.parseInt(rs.getType()));
                tradingRule.setTrsignal(Integer.parseInt(rs.getTrsignal()));

                tradingRule.setUpdatedatel(Long.parseLong(rs.getUpdatedatel()));
                tradingRule.setUpdatedatedisplay(new java.sql.Date(tradingRule.getUpdatedatel()));

                tradingRule.setStatus(Integer.parseInt(rs.getStatus()));
                tradingRule.setSubstatus(Integer.parseInt(rs.getSubstatus()));
                tradingRule.setInvestment(Float.parseFloat(rs.getInvestment()));
                tradingRule.setBalance(Float.parseFloat(rs.getBalance()));
                tradingRule.setLongshare(Float.parseFloat(rs.getLongshare()));
                tradingRule.setLongamount(Float.parseFloat(rs.getLongamount()));
                tradingRule.setShortshare(Float.parseFloat(rs.getShortshare()));
                tradingRule.setShortamount(Float.parseFloat(rs.getShortamount()));
                tradingRule.setPerf(Float.parseFloat(rs.getPerf()));

                tradingRule.setComment(rs.getComment());
                tradingRule.setLinktradingruleid(Integer.parseInt(rs.getLinktradingruleid()));
                tradingRule.setAccountid(Integer.parseInt(rs.getAccountid()));
                tradingRule.setStockid(Integer.parseInt(rs.getStockid()));

                tradingRule.setSymbol(rs.getSymbol());

                arrayReturn.add(tradingRule);
            }
            return arrayReturn;
        } catch (IOException ex) {
            logger.info("getAccountStockListSqlRemoteDB exception " + output);
            return null;
        }
    }

    public ArrayList getAllLockSqlRemoteDB_RemoteMysql(String sqlCMD) throws Exception {

        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        log.info("getAllLockSqlRemoteDB_RemoteMysql " + sqlCMD);
        try {
            String subResourcePath = WEBPOST;
            HashMap newmap = new HashMap();
            newmap.put(CMD, "1");

            HashMap newbodymap = new HashMap();
            newbodymap.put(CMDPOST, sqlCMD);

            String output = sendRequest_remotesql(METHOD_POST, subResourcePath, newmap, newbodymap);

            int beg = output.indexOf("~~ ");
            int end = output.indexOf(" ~~");
            // create hash map
            if (beg > end) {
                return null;
            }
            output = output.substring(beg + 3, end);
            if (output.length() == 0) {
                return null;
            }
//            String[] dataArray = output.split("~");
            String[] dataArray = splitIncludeEmpty(output, '~');
            output = "[";
// "create table lockobject (id int(10) not null auto_increment, lockname varchar(255) not null unique, type int(10) not null, 
//lockdatedisplay date, lockdatel bigint(20), comment varchar(255), primary key (id))");

            int recSize = 6;
            for (int i = 0; i < dataArray.length; i += recSize) {
                output += "{";
                output += "\"id\":\"" + dataArray[i] + "\",";
                output += "\"lockname\":\"" + dataArray[i + 1] + "\",";
                output += "\"type\":\"" + dataArray[i + 2] + "\",";
                output += "\"lockdatedisplay\":\"" + dataArray[i + 3] + "\",";
                output += "\"lockdatel\":\"" + dataArray[i + 4] + "\",";
                output += "\"comment\":\"" + dataArray[i + 5] + "\"";

                if (i + recSize >= dataArray.length) {
                    output += "}";
                } else {
                    output += "},";
                }
            }
            output += "]";
            return getAllLockSqlRemoteDB_Process(output);

        } catch (Exception ex) {
            logger.info("getAllLockSqlRemoteDB exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
            throw ex;
        }
    }

    private ArrayList<AFLockObject> getAllLockSqlRemoteDB_Process(String output) {
        if (output.equals("")) {
            return null;
        }
        ArrayList<LockObjectRDB> arrayDB = null;
        ArrayList<AFLockObject> arrayReturn = new ArrayList();
        try {
            LockObjectRDB[] arrayItem = new ObjectMapper().readValue(output, LockObjectRDB[].class);
            List<LockObjectRDB> listItem = Arrays.<LockObjectRDB>asList(arrayItem);
            arrayDB = new ArrayList<LockObjectRDB>(listItem);

            for (int i = 0; i < arrayDB.size(); i++) {
                LockObjectRDB rs = arrayDB.get(i);

                AFLockObject lock = new AFLockObject();
                lock.setLockname(rs.getLockname());
                lock.setType(Integer.parseInt(rs.getType()));
                lock.setLockdatel(Long.parseLong(rs.getLockdatel()));
                lock.setLockdatedisplay(new java.sql.Date(lock.getLockdatel()));

                lock.setId(Integer.parseInt(rs.getId()));
                lock.setComment(rs.getComment());

                String tzid = "America/New_York"; //EDT
                TimeZone tz = TimeZone.getTimeZone(tzid);
                Date d = new Date(lock.getLockdatel());
                DateFormat format = new SimpleDateFormat("M/dd/yyyy hh:mm a z");
                format.setTimeZone(tz);
                String ESTdate = format.format(d);
                lock.setUpdateDateD(ESTdate);

                arrayReturn.add(lock);
            }
            return arrayReturn;
        } catch (IOException ex) {
            logger.info("getAllLockSqlRemoteDB exception " + output);
            return null;
        }

    }

    public ArrayList getAllNeuralNetDataSqlRemoteDB_RemoteMysql(String sqlCMD) throws Exception {

        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        log.info("getAllNeuralNetSqlRemoteDB_Mysql " + sqlCMD);
        try {
            String subResourcePath = WEBPOST;
            HashMap newmap = new HashMap();
            newmap.put(CMD, "1");

            HashMap newbodymap = new HashMap();
            newbodymap.put(CMDPOST, sqlCMD);

            String output = sendRequest_remotesql(METHOD_POST, subResourcePath, newmap, newbodymap);

            int beg = output.indexOf("~~ ");
            int end = output.indexOf(" ~~");
            // create hash map
            if (beg > end) {
                return null;
            }
            output = output.substring(beg + 3, end);
            if (output.length() == 0) {
                return null;
            }
//            String[] dataArray = output.split("~");
            String[] dataArray = splitIncludeEmpty(output, '~');
            output = "[";
//"create table neuralnet (id int(10) not null auto_increment, name varchar(255) not null unique, status int(10) not null, type int(10) not null, 
//weight text, updatedatedisplay date, updatedatel bigint(20) not null, primary key (id))");

            int recSize = 7;
            for (int i = 0; i < dataArray.length; i += recSize) {
                output += "{";
                output += "\"id\":\"" + dataArray[i] + "\",";
                output += "\"name\":\"" + dataArray[i + 1] + "\",";
                output += "\"status\":\"" + dataArray[i + 2] + "\",";
                output += "\"type\":\"" + dataArray[i + 3] + "\",";
                output += "\"data\":\"" + dataArray[i + 4] + "\",";
                output += "\"updatedatedisplay\":\"" + dataArray[i + 5] + "\",";
                output += "\"updatedatel\":\"" + dataArray[i + 6] + "\"";

                if (i + recSize >= dataArray.length) {
                    output += "}";
                } else {
                    output += "},";
                }
            }
            output += "]";
            return getAllNeuralNetDataSqlRemoteDB_Process(output);

        } catch (Exception ex) {
            logger.info("getAllNeuralNetSqlRemoteDB exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
            throw ex;
        }
    }

    public ArrayList getAllNeuralNetSqlRemoteDB_RemoteMysql(String sqlCMD) throws Exception {

        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        log.info("getAllNeuralNetSqlRemoteDB_Mysql " + sqlCMD);
        try {
            String subResourcePath = WEBPOST;
            HashMap newmap = new HashMap();
            newmap.put(CMD, "1");

            HashMap newbodymap = new HashMap();
            newbodymap.put(CMDPOST, sqlCMD);

            String output = sendRequest_remotesql(METHOD_POST, subResourcePath, newmap, newbodymap);

            int beg = output.indexOf("~~ ");
            int end = output.indexOf(" ~~");
            // create hash map
            if (beg > end) {
                return null;
            }
            output = output.substring(beg + 3, end);
            if (output.length() == 0) {
                return null;
            }
//            String[] dataArray = output.split("~");
            String[] dataArray = splitIncludeEmpty(output, '~');
            output = "[";
//"create table neuralnet (id int(10) not null auto_increment, name varchar(255) not null unique, status int(10) not null, type int(10) not null, 
//weight text, updatedatedisplay date, updatedatel bigint(20) not null, primary key (id))");

            int recSize = 8;
            for (int i = 0; i < dataArray.length; i += recSize) {
                output += "{";
                output += "\"id\":\"" + dataArray[i] + "\",";
                output += "\"name\":\"" + dataArray[i + 1] + "\",";
                output += "\"refname\":\"" + dataArray[i + 2] + "\",";
                output += "\"status\":\"" + dataArray[i + 3] + "\",";
                output += "\"type\":\"" + dataArray[i + 4] + "\",";
                output += "\"weight\":\"" + dataArray[i + 5] + "\",";
                output += "\"updatedatedisplay\":\"" + dataArray[i + 6] + "\",";
                output += "\"updatedatel\":\"" + dataArray[i + 7] + "\"";

                if (i + recSize >= dataArray.length) {
                    output += "}";
                } else {
                    output += "},";
                }
            }
            output += "]";
            return getAllNeuralNetSqlRemoteDB_Process(output);

        } catch (Exception ex) {
            logger.info("getAllNeuralNetSqlRemoteDB exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
            throw ex;
        }
    }

    private ArrayList<AFneuralNetData> getAllNeuralNetDataSqlRemoteDB_Process(String output) {
        if (output.equals("")) {
            return null;
        }
        ArrayList<NeuralNetDataRDB> arrayDB = null;
        ArrayList<AFneuralNetData> arrayReturn = new ArrayList();
        try {
            NeuralNetDataRDB[] arrayItem = new ObjectMapper().readValue(output, NeuralNetDataRDB[].class);
            List<NeuralNetDataRDB> listItem = Arrays.<NeuralNetDataRDB>asList(arrayItem);
            arrayDB = new ArrayList<NeuralNetDataRDB>(listItem);

            for (int i = 0; i < arrayDB.size(); i++) {
                NeuralNetDataRDB rs = arrayDB.get(i);

                AFneuralNetData nn = new AFneuralNetData();
                nn.setId(Integer.parseInt(rs.getId()));
                nn.setName(rs.getName());
                nn.setStatus(Integer.parseInt(rs.getStatus()));
                nn.setType(Integer.parseInt(rs.getType()));

                String stData = rs.getData();
                stData = stData.replaceAll("#", "\"");
                nn.setData(stData);

                nn.setUpdatedatel(Long.parseLong(rs.getUpdatedatel()));
                nn.setUpdatedatedisplay(new java.sql.Date(nn.getUpdatedatel()));

                arrayReturn.add(nn);
            }
            return arrayReturn;
        } catch (IOException ex) {
            logger.info("getAllNeuralNetDataSqlRemoteDB_Process exception " + output);
            return null;
        }
    }

    private ArrayList<AFneuralNet> getAllNeuralNetSqlRemoteDB_Process(String output) {
        if (output.equals("")) {
            return null;
        }
        ArrayList<NeuralNetRDB> arrayDB = null;
        ArrayList<AFneuralNet> arrayReturn = new ArrayList();
        try {
            NeuralNetRDB[] arrayItem = new ObjectMapper().readValue(output, NeuralNetRDB[].class);
            List<NeuralNetRDB> listItem = Arrays.<NeuralNetRDB>asList(arrayItem);
            arrayDB = new ArrayList<NeuralNetRDB>(listItem);

            for (int i = 0; i < arrayDB.size(); i++) {
                NeuralNetRDB rs = arrayDB.get(i);

                AFneuralNet nn = new AFneuralNet();
                nn.setId(Integer.parseInt(rs.getId()));
                nn.setName(rs.getName());
                nn.setRefname(rs.getRefname());
                nn.setStatus(Integer.parseInt(rs.getStatus()));
                nn.setType(Integer.parseInt(rs.getType()));
                nn.setWeight(rs.getWeight());
                nn.setUpdatedatel(Long.parseLong(rs.getUpdatedatel()));
                nn.setUpdatedatedisplay(new java.sql.Date(nn.getUpdatedatel()));

                arrayReturn.add(nn);
            }
            return arrayReturn;
        } catch (IOException ex) {
            logger.info("getAllNeuralNetSqlRemoteDB exception " + output);
            return null;
        }
    }
//    

    public ArrayList getAllNameSqlRemoteDB_RemoteMysql(String sqlCMD) throws Exception {

        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        log.info("getAllNameSqlRemoteDB_RemoteMysql " + sqlCMD);
        try {
            String subResourcePath = WEBPOST;
            HashMap newmap = new HashMap();
            newmap.put(CMD, "1");

            HashMap newbodymap = new HashMap();
            newbodymap.put(CMDPOST, sqlCMD);

            String output = sendRequest_remotesql(METHOD_POST, subResourcePath, newmap, newbodymap);

            int beg = output.indexOf("~~ ");
            int end = output.indexOf(" ~~");
            // create hash map
            if (beg > end) {
                return null;
            }
            output = output.substring(beg + 3, end);
            ArrayList<String> retArray = new ArrayList();
            if (output.length() == 0) {
                return retArray;
            }

//            String[] dataArray = output.split("~");
            String[] dataArray = splitIncludeEmpty(output, '~');
            output = "[";
            int recSize = 1;
            for (int i = 0; i < dataArray.length; i += recSize) {
                output += "{";
                output += "\"name\":\"" + dataArray[i] + "\"";
                if (i + recSize >= dataArray.length) {
                    output += "}";
                } else {
                    output += "},";
                }
            }
            output += "]";
            return getAllNameSqlRemoteDB_Process(output);

        } catch (Exception ex) {
            logger.info("getAllNameSqlRemoteDB exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
            throw ex;
        }
    }

    private ArrayList<String> getAllNameSqlRemoteDB_Process(String output) {
        if (output.equals("")) {
            return null;
        }
        ArrayList<NameRDB> arrayDB = null;
        ArrayList<String> arrayReturn = new ArrayList();
        try {
            NameRDB[] arrayItem = new ObjectMapper().readValue(output, NameRDB[].class);
            List<NameRDB> listItem = Arrays.<NameRDB>asList(arrayItem);
            arrayDB = new ArrayList<NameRDB>(listItem);

            for (int i = 0; i < arrayDB.size(); i++) {
                NameRDB nameRDB = arrayDB.get(i);
                arrayReturn.add(nameRDB.getName());
            }
            return arrayReturn;
        } catch (IOException ex) {
            logger.info("getAllNameSqlRemoteDB exception " + output);
            return null;
        }
    }

    public ArrayList getAllSymbolSqlRemoteDB_RemoteMysql(String sqlCMD) throws Exception {

        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        log.info("getAllSymbolSqlRemoteDB_RemoteMysql " + sqlCMD);
        try {
            String subResourcePath = WEBPOST;
            HashMap newmap = new HashMap();
            newmap.put(CMD, "1");

            HashMap newbodymap = new HashMap();
            newbodymap.put(CMDPOST, sqlCMD);

            String output = sendRequest_remotesql(METHOD_POST, subResourcePath, newmap, newbodymap);

            int beg = output.indexOf("~~ ");
            int end = output.indexOf(" ~~");
            // create hash map
            if (beg > end) {
                return null;
            }
            output = output.substring(beg + 3, end);
            ArrayList<String> retArray = new ArrayList();
            if (output.length() == 0) {
                return retArray;
            }

//            String[] dataArray = output.split("~");
            String[] dataArray = splitIncludeEmpty(output, '~');
            output = "[";
            int recSize = 1;
            for (int i = 0; i < dataArray.length; i += recSize) {
                output += "{";
                output += "\"symbol\":\"" + dataArray[i] + "\"";
                if (i + recSize >= dataArray.length) {
                    output += "}";
                } else {
                    output += "},";
                }
            }
            output += "]";
            return getAllSymbolSqlRemoteDB_Process(output);

        } catch (Exception ex) {
            logger.info("getAllSymbolSqlRemoteDB_RemoteMysql exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
            throw ex;
        }
    }

    private ArrayList<String> getAllSymbolSqlRemoteDB_Process(String output) {
        if (output.equals("")) {
            return null;
        }
        ArrayList<SymbolRDB> arrayDB = null;
        ArrayList<String> arrayReturn = new ArrayList();
        try {
            SymbolRDB[] arrayItem = new ObjectMapper().readValue(output, SymbolRDB[].class);
            List<SymbolRDB> listItem = Arrays.<SymbolRDB>asList(arrayItem);
            arrayDB = new ArrayList<SymbolRDB>(listItem);

            for (int i = 0; i < arrayDB.size(); i++) {
                SymbolRDB nameRDB = arrayDB.get(i);
                arrayReturn.add(nameRDB.getSymbol());
            }
            return arrayReturn;
        } catch (IOException ex) {
            logger.info("getAllSymbolSqlRemoteDB exception " + output);
            return null;
        }
    }

    public ArrayList getAllIdSqlRemoteDB_RemoteMysql(String sqlCMD) throws Exception {

        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        log.info("getAllIdSqlRemoteDB_RemoteMysql " + sqlCMD);
        try {
            String subResourcePath = WEBPOST;
            HashMap newmap = new HashMap();
            newmap.put(CMD, "1");

            HashMap newbodymap = new HashMap();
            newbodymap.put(CMDPOST, sqlCMD);

            String output = sendRequest_remotesql(METHOD_POST, subResourcePath, newmap, newbodymap);

            int beg = output.indexOf("~~ ");
            int end = output.indexOf(" ~~");
            // create hash map
            if (beg > end) {
                return null;
            }
            output = output.substring(beg + 3, end);
            ArrayList<String> retArray = new ArrayList();
            if (output.length() == 0) {
                return retArray;
            }

//            String[] dataArray = output.split("~");
            String[] dataArray = splitIncludeEmpty(output, '~');
            output = "[";
            int recSize = 1;
            for (int i = 0; i < dataArray.length; i += recSize) {
                output += "{";
                output += "\"id\":\"" + dataArray[i] + "\"";
                if (i + recSize >= dataArray.length) {
                    output += "}";
                } else {
                    output += "},";
                }
            }
            output += "]";
            return getAllIdSqlRemoteDB_Process(output);

        } catch (Exception ex) {
            logger.info("getAllIdSqlRemoteDB_RemoteMysql exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
            throw ex;
        }
    }

    private ArrayList<String> getAllIdSqlRemoteDB_Process(String output) {
        if (output.equals("")) {
            return null;
        }
        ArrayList<IdRDB> arrayDB = null;
        ArrayList<String> arrayReturn = new ArrayList();
        try {
            IdRDB[] arrayItem = new ObjectMapper().readValue(output, IdRDB[].class);
            List<IdRDB> listItem = Arrays.<IdRDB>asList(arrayItem);
            arrayDB = new ArrayList<IdRDB>(listItem);

            for (int i = 0; i < arrayDB.size(); i++) {
                IdRDB nameRDB = arrayDB.get(i);
                arrayReturn.add(nameRDB.getId());
            }
            return arrayReturn;
        } catch (IOException ex) {
            logger.info("getAllIdSqlRemoteDB exception " + output);
            return null;
        }
    }

    public ArrayList getAllUserNameSqlRemoteDB_RemoteMysql(String sqlCMD) throws Exception {

        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        log.info("getAllUserNameSqlRemoteDB_RemoteMysql " + sqlCMD);
        try {
            String subResourcePath = WEBPOST;
            HashMap newmap = new HashMap();
            newmap.put(CMD, "1");

            HashMap newbodymap = new HashMap();
            newbodymap.put(CMDPOST, sqlCMD);

            String output = sendRequest_remotesql(METHOD_POST, subResourcePath, newmap, newbodymap);

            int beg = output.indexOf("~~ ");
            int end = output.indexOf(" ~~");
            // create hash map
            if (beg > end) {
                return null;
            }
            output = output.substring(beg + 3, end);
            ArrayList<String> retArray = new ArrayList();
            if (output.length() == 0) {
                return retArray;
            }

//            String[] dataArray = output.split("~");
            String[] dataArray = splitIncludeEmpty(output, '~');
            output = "[";
            int recSize = 1;
            for (int i = 0; i < dataArray.length; i += recSize) {
                output += "{";
                output += "\"username\":\"" + dataArray[i] + "\"";
                if (i + recSize >= dataArray.length) {
                    output += "}";
                } else {
                    output += "},";
                }
            }
            output += "]";
            return getAllUserNameSqlRemoteDB_Process(output);

        } catch (Exception ex) {
            logger.info("getAllNameSqlRemoteDB exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
            throw ex;
        }
    }

    private ArrayList<String> getAllUserNameSqlRemoteDB_Process(String output) {
        if (output.equals("")) {
            return null;
        }
        ArrayList<UserNameRDB> arrayDB = null;
        ArrayList<String> arrayReturn = new ArrayList();
        try {
            UserNameRDB[] arrayItem = new ObjectMapper().readValue(output, UserNameRDB[].class);
            List<UserNameRDB> listItem = Arrays.<UserNameRDB>asList(arrayItem);
            arrayDB = new ArrayList<UserNameRDB>(listItem);

            for (int i = 0; i < arrayDB.size(); i++) {
                UserNameRDB nameRDB = arrayDB.get(i);
                arrayReturn.add(nameRDB.getUsername());
            }
            return arrayReturn;
        } catch (IOException ex) {
            logger.info("getAllNameSqlRemoteDB exception " + output);
            return null;
        }
    }

    public String getAllSQLqueryRemoteDB_RemoteMysql(String sqlCMD) throws Exception {

        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        log.info("getAccountStockTransactionListRemoteDB_RemoteMysql " + sqlCMD);
        try {
            String subResourcePath = WEBPOST;
            HashMap newmap = new HashMap();
            newmap.put(CMD, "1");

            HashMap newbodymap = new HashMap();
            newbodymap.put(CMDPOST, sqlCMD);

            String output = sendRequest_remotesql(METHOD_POST, subResourcePath, newmap, newbodymap);

            int beg = output.indexOf("~~ ");
            int end = output.indexOf(" ~~");
            // create hash map
            if (beg > end) {
                return null;
            }
            output = output.substring(beg + 3, end);
            if (output.length() == 0) {
                return null;
            }

            return output;

        } catch (Exception ex) {
            logger.info("getAllSQLqueryRemoteDB_RemoteMysql exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
            throw ex;
        }
    }

    /////////////////////////////////////////////////////////////
    // operations names constants
    private static final String METHOD_POST = "post";
    private static final String METHOD_GET = "get";

    private String sendRequest_remotesql(String method, String subResourcePath, Map<String, String> queryParams, Map<String, String> bodyParams) throws Exception {
        String response = null;
        for (int i = 0; i < 4; i++) {
            try {
                response = sendRequest_Process_Mysql(method, subResourcePath, queryParams, bodyParams);

                if (response != null) {
                    return response;
                }
            } catch (Exception ex) {
                // retry
//                log.info("sendRequest " + bodyElement);
                logger.info("sendRequest " + method + " Rety " + (i + 1));
            }
        }
        response = sendRequest_Process_Mysql(method, subResourcePath, queryParams, bodyParams);

        return response;
    }

    private String sendRequest_Process_Mysql(String method, String subResourcePath, Map<String, String> queryParams, Map<String, String> bodyParams)
            throws Exception {
        try {

            String URLPath = getURL_PATH() + subResourcePath;

            String webResourceString = "";
            // assume only one param
            if (queryParams != null && !queryParams.isEmpty()) {
                for (String key : queryParams.keySet()) {
                    webResourceString = "?" + key + "=" + queryParams.get(key);
                }
            }

            String bodyElement = "";

            if (bodyParams != null && !bodyParams.isEmpty()) {
                String bodyTmp = "";
                for (String key : bodyParams.keySet()) {
                    bodyTmp = bodyParams.get(key);
                    bodyTmp = bodyTmp.replaceAll("&", "-");
                    bodyTmp = bodyTmp.replaceAll("%", "%25");
                    bodyElement = key + "=" + bodyTmp;
                }

            }

            URLPath += webResourceString;
            URL request = new URL(URLPath);
            //just for testing
//                log.info("Request:: " +URLPath);     
            boolean flagD = true;
            if (bodyElement.indexOf("select * from stockinfo where") == -1) {
                flagD = false;
            }
            if (bodyElement.indexOf("select * from stock where") == -1) {
                flagD = false;
            }
            if (flagD == true) {
                logger.info("Request Code:: " + bodyElement);
            }
            HttpURLConnection con = null; //(HttpURLConnection) request.openConnection();
            if (CKey.PROXY == true) {
                //////Add Proxy 
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ServiceAFweb.PROXYURL, 8080));
                con = (HttpURLConnection) request.openConnection(proxy);
                //////Add Proxy 
            } else {
                con = (HttpURLConnection) request.openConnection();
            }
            if (method.equals(METHOD_POST)) {
                con.setRequestMethod("POST");
            } else {
                con.setRequestMethod("GET");
            }
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
//                con.setRequestProperty("Content-Type", "application/json; utf-8");

            if (method.equals(METHOD_POST)) {
                // For POST only - START
                con.setDoOutput(true);
                OutputStream os = con.getOutputStream();
                byte[] input = bodyElement.getBytes("utf-8");
                os.write(input, 0, input.length);
                os.flush();
                os.close();
                // For POST only - END
            }

            int responseCode = con.getResponseCode();

            if (responseCode != 200) {
                logger.info("Response Code:: " + responseCode);
            }
            if (responseCode >= 200 && responseCode < 300) {
                ;
            } else {
                logger.info("Response Code:: " + responseCode);
                logger.info("bodyElement :: " + bodyElement);
                return null;
            }
            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                // print result
                return response.toString();
            } else {
                logger.info("POST request not worked");
            }

        } catch (Exception e) {
            logger.info("Error sending REST request:" + e);
            throw e;
        }
        return null;
    }

    public static String[] splitIncludeEmpty(String inputStr, char delimiter) {
        if (inputStr == null) {
            return null;
        }
        if (inputStr.charAt(inputStr.length() - 1) == delimiter) {
            // the 000webhostapp always add extra ~ at the end see the source
            inputStr += "End";
            String[] tempString = inputStr.split("" + delimiter);
            int size = tempString.length - 1;
            String[] outString = new String[size];
            for (int i = 0; i < size; i++) {
                outString[i] = tempString[i];
            }
            return outString;
        }
        return inputStr.split("" + delimiter);
    }

//////////////////////////
//    private String sendRequest_Process_Ms_sql(String method, String subResourcePath, Map<String, String> queryParams, Map<String, String> bodyParams)
//            throws Exception {
//
//        String URLPath = getURL_PATH() + subResourcePath;
//
//        String webResourceString = "";
//        // assume only one param
//        if (queryParams != null && !queryParams.isEmpty()) {
//            for (String key : queryParams.keySet()) {
//                webResourceString = "?" + key + "=" + queryParams.get(key);
//            }
//        }
//        URLPath += webResourceString;
//
//        BufferedReader in = null;
//        String resultString = null;
//        try {
//            HttpClient client = new DefaultHttpClient();
//            if (CKey.PROXY == true) {
//                //////Add Proxy 
//                HttpHost proxy = new HttpHost(ServiceAFweb.PROXYURL, 8080, "http");
//                client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
//            }
//            HttpPost requestPost = new HttpPost(URLPath);
//
//            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
//            if (bodyParams != null && !bodyParams.isEmpty()) {
//                for (String key : bodyParams.keySet()) {
//                    String bodyElement = bodyParams.get(key);
//                    bodyElement = bodyElement.replaceAll("&", "-");
//                    bodyElement = bodyElement.replaceAll("%", "%25");
//                    postParameters.add(new BasicNameValuePair(key, bodyElement));
//                }
//            }
//            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
//
//            requestPost.setEntity(formEntity);
//
//            HttpResponse response = client.execute(requestPost);
//            in = new BufferedReader(
//                    new InputStreamReader(
//                            response.getEntity().getContent()));
//
//            StringBuffer sb = new StringBuffer("");
//            String line = "";
//            String NL = System.getProperty("line.separator");
//            while ((line = in.readLine()) != null) {
//                sb.append(line + NL);
//            }
//            in.close();
//
//            resultString = sb.toString();
//            return resultString;
//
//        } catch (Exception e) {
//            // Do something about exceptions
//            e.printStackTrace();
//            throw new Exception("Error send REST request");
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//////////////////////////
//    private String sendRequest_remotesql(String method, String subResourcePath, Map<String, String> queryParams, Map<String, String> bodyParams) {
//        try {
//            if (subResourcePath.indexOf("https") != -1) {
//                return this.https_sendRequest_Process_Ssns(method, subResourcePath, queryParams, bodyParams);
//            }
//            return this.http_sendRequest_Process_Ssns(method, subResourcePath, queryParams, bodyParams);
//        } catch (Exception ex) {
////            Logger.getLogger(SsnsService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
//
//    private String https_sendRequest_Process_Ssns(String method, String subResourcePath, Map<String, String> queryParams, Map<String, String> bodyParams)
//            throws Exception {
//        try {
//
//            String URLPath = subResourcePath;
//
//            String webResourceString = "";
//            // assume only one param
//            if (queryParams != null && !queryParams.isEmpty()) {
//                for (String key : queryParams.keySet()) {
//                    webResourceString = "?" + key + "=" + queryParams.get(key);
//                }
//            }
//
//            String bodyElement = "";
//            if (bodyParams != null) {
//                bodyElement = new ObjectMapper().writeValueAsString(bodyParams);
//            }
//
//            URLPath += webResourceString;
//            URL request = new URL(URLPath);
//
//            HttpsURLConnection con = null; //(HttpURLConnection) request.openConnection();
//
//            if (CKey.PROXY == true) {
//                //////Add Proxy 
//                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ServiceAFweb.PROXYURL, 8080));
//                con = (HttpsURLConnection) request.openConnection(proxy);
//                //////Add Proxy 
//            } else {
//                con = (HttpsURLConnection) request.openConnection();
//            }
//
////            if (URLPath.indexOf(":8080") == -1) {
////            String authStr = "APP_SELFSERVEUSGBIZSVC" + ":" + "soaorgid";
////            // encode data on your side using BASE64
////            byte[] bytesEncoded = Base64.encodeBase64(authStr.getBytes());
////            String authEncoded = new String(bytesEncoded);
////            con.setRequestProperty("Authorization", "Basic " + authEncoded);
////            }
//            if (method.equals(METHOD_POST)) {
//                con.setRequestMethod("POST");
//            } else if (method.equals(METHOD_GET)) {
//                con.setRequestMethod("GET");
//            }
//            con.setRequestProperty("User-Agent", USER_AGENT);
////            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
//            con.setRequestProperty("Content-Type", "application/json");
//            con.setRequestProperty("Accept", "application/json");
//
//            if (method.equals(METHOD_POST)) {
//
////                con.setRequestMethod("POST");
////                con.addRequestProperty("Accept", "application/json");
////                con.addRequestProperty("Connection", "close");
////                con.addRequestProperty("Content-Encoding", "gzip"); // We gzip our request
////                con.addRequestProperty("Content-Length", String.valueOf(bodyElement.length()));
////                con.setRequestProperty("Content-Type", "application/json"); // We send our data in JSON format
//                con.setDoInput(true);
//                // For POST only - START                
//                con.setDoOutput(true);
//                OutputStream os = con.getOutputStream();
//                byte[] input = bodyElement.getBytes("utf-8");
//                os.write(input, 0, input.length);
//                os.flush();
//                os.close();
//                // For POST only - END
//            }
//
//            int responseCode = con.getResponseCode();
//            if (responseCode != 200) {
//                System.out.println("Response Code:: " + responseCode);
//            }
//            if (responseCode >= 200 && responseCode < 300) {
//                ;
//
//            } else {
////                System.out.println("Response Code:: " + responseCode);
////                System.out.println("bodyElement :: " + bodyElement);
//                return null;
//            }
//
//            if (responseCode == HttpURLConnection.HTTP_OK) { //success
//                BufferedReader in = new BufferedReader(new InputStreamReader(
//                        con.getInputStream()));
//                String inputLine;
//
//                StringBuffer response = new StringBuffer();
//
//                while ((inputLine = in.readLine()) != null) {
//
//                    response.append(inputLine);
//                }
//                in.close();
//                // print result
//                return response.toString();
//            } else {
//                logger.info("POST request not worked");
//            }
//
//        } catch (Exception e) {
////            logger.info("Error sending REST request:" + e);
//            throw e;
//        }
//        return null;
//    }
//
//    private String http_sendRequest_Process_Ssns(String method, String subResourcePath, Map<String, String> queryParams, Map<String, String> bodyParams)
//            throws Exception {
//        try {
//
////            String URLPath = subResourcePath;
//            String URLPath = getURL_PATH() + subResourcePath;
//            String webResourceString = "";
//            // assume only one param
//            if (queryParams != null && !queryParams.isEmpty()) {
//                for (String key : queryParams.keySet()) {
//                    webResourceString = "?" + key + "=" + queryParams.get(key);
//                }
//            }
//
//            String bodyElement = "";
//            if (bodyParams != null) {
//                bodyElement = new ObjectMapper().writeValueAsString(bodyParams);
//            }
//
//            URLPath += webResourceString;
//            URL request = new URL(URLPath);
//
//            HttpURLConnection con = null; //(HttpURLConnection) request.openConnection();
//
//            if (CKey.PROXY == true) {
//                //////Add Proxy 
//                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ServiceAFweb.PROXYURL, 8080));
//                con = (HttpURLConnection) request.openConnection(proxy);
//                //////Add Proxy 
//            } else {
//                con = (HttpURLConnection) request.openConnection();
//            }
//
////            if (URLPath.indexOf(":8080") == -1) {
////            String authStr = "APP_SELFSERVEUSGBIZSVC" + ":" + "soaorgid";
////            // encode data on your side using BASE64
////            byte[] bytesEncoded = Base64.encodeBase64(authStr.getBytes());
////            String authEncoded = new String(bytesEncoded);
////            con.setRequestProperty("Authorization", "Basic " + authEncoded);
////            }
//            if (method.equals(METHOD_POST)) {
//                con.setRequestMethod("POST");
//            } else if (method.equals(METHOD_GET)) {
//                con.setRequestMethod("GET");
//            }
//            con.setRequestProperty("User-Agent", USER_AGENT);
////            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
////            con.setRequestProperty("Content-Type", "application/json; utf-8");
//            con.setRequestProperty("Content-Type", "application/json");
//            con.setRequestProperty("Accept", "application/json");
//
//            if (method.equals(METHOD_POST)) {
//                con.setDoOutput(true);
//                try (OutputStream os = con.getOutputStream()) {
//                    byte[] input = bodyElement.getBytes("utf-8");
//                    os.write(input, 0, input.length);
//                    os.flush();
//                    os.close();
//                }
//
//            }
//
//            int responseCode = con.getResponseCode();
//            if (responseCode != 200) {
//                System.out.println("Response Code:: " + responseCode);
//            }
//            if (responseCode >= 200 && responseCode < 300) {
//                ;
//            } else {
////                System.out.println("Response Code:: " + responseCode);
////                System.out.println("bodyElement :: " + bodyElement);
//                return null;
//            }
//            if (responseCode == HttpURLConnection.HTTP_OK) { //success
//                BufferedReader in = new BufferedReader(new InputStreamReader(
//                        con.getInputStream()));
//                String inputLine;
//
//                StringBuffer response = new StringBuffer();
//
//                while ((inputLine = in.readLine()) != null) {
//
//                    response.append(inputLine);
//                }
//                in.close();
//                // print result
//                return response.toString();
//            } else {
//                logger.info("POST request not worked");
//            }
//
//        } catch (Exception e) {
////            logger.info("Error sending REST request:" + e);
//            throw e;
//        }
//        return null;
//    }
}
