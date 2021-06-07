/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.service;

import com.afweb.model.*;
import com.afweb.model.stock.AFneuralNet;

import com.afweb.util.CKey;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;

import java.util.Map;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

import static org.apache.http.protocol.HTTP.USER_AGENT;

/**
 *
 * @author eddy
 */
public class ServiceAFwebREST {

    public static Logger logger = Logger.getLogger("ServiceAFwebREST");

//
//    public LoginObj addCustomerPassword(String EmailUserName, String Password, String FirstName, String LastName) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/add";
//        try {
//            // create hash map
//            HashMap newmap = new HashMap();
//            newmap.put("email", EmailUserName);
//            newmap.put("pass", Password);
//            if (FirstName != null) {
//                newmap.put("firstName", FirstName);
//            }
//            if (LastName != null) {
//                newmap.put("lastName", LastName);
//            }
////            ClientResponse response = get(subResourcePath, newmap);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, newmap, null);
//
//            LoginObj loginObj = new ObjectMapper().readValue(output, LoginObj.class);
//            return loginObj;
//
//        } catch (Exception ex) {
//            logger.info("addCustomerPassword exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//
//        }
//        return null;
//    }
//
//    public CustomerObj getCustomerPassword(String EmailUserName, String Password) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/sys/cust";
//        try {
//
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            CustomerObj custObj = new ObjectMapper().readValue(output, CustomerObj.class);
////            custObj.setUpdateDateDisplay(new java.sql.Date(custObj.getUpdateDateL()));
//            return custObj;
//
//        } catch (Exception ex) {
//            logger.info("getCustomerPassword exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//
//        return null;
//    }
//
//    public LoginObj getCustomerEmailLogin(String EmailUserName, String Password) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/login";
//        try {
//
//            HashMap newmap = new HashMap();
//            newmap.put("email", EmailUserName);
//            newmap.put("pass", Password);
////            ClientResponse response = get(subResourcePath, newmap);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, newmap, null);
//
//            LoginObj loginObj = new ObjectMapper().readValue(output, LoginObj.class);
//            return loginObj;
//
//        } catch (Exception ex) {
//            logger.info("getCustomerLogin exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//
//        return null;
//    }
//
//    public LoginObj getCustomerLogin(String EmailUserName, String Password) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/login";
//        try {
//
//            HashMap newmap = new HashMap();
//            newmap.put("pass", Password);
////            ClientResponse response = get(subResourcePath, newmap);
////            String output = response.getEntity(String.class);
//
//            String output = sendRequest_1(METHOD_GET, subResourcePath, newmap, null);
//            LoginObj loginObj = new ObjectMapper().readValue(output, LoginObj.class);
////            custObj.setUpdateDateDisplay(new java.sql.Date(custObj.getUpdateDateL()));
//            return loginObj;
//
//        } catch (Exception ex) {
//            logger.info("getCustomerLogin exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//
//        return null;
//    }
//
//    public ArrayList getAllDisableStockNameList(int length) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + CKey.ADMIN_USERNAME + "/sys/expiredStocklist?length=" + length;
//        try {
//            // default 20 list
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            ArrayList<AFstockObj> stockNameArray = null;
//            try {
//                AFstockObj[] arrayItem = new ObjectMapper().readValue(output, AFstockObj[].class);
//                List<AFstockObj> listItem = Arrays.<AFstockObj>asList(arrayItem);
//                stockNameArray = new ArrayList<AFstockObj>(listItem);
//
//            } catch (IOException ex) {
//                return null;
//            }
//            return stockNameArray;
//        } catch (Exception ex) {
//            logger.info("getExpiredStockNameList exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    public ArrayList getExpiredCustomerList(int length) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + CKey.ADMIN_USERNAME + "/sys/expiredcustlist?length=" + length;
//        try {
//            // default 20 list
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            ArrayList<CustomerObj> custObjArray = null;
//            try {
//                CustomerObj[] arrayItem = new ObjectMapper().readValue(output, CustomerObj[].class);
//                List<CustomerObj> listItem = Arrays.<CustomerObj>asList(arrayItem);
//                custObjArray = new ArrayList<CustomerObj>(listItem);
//
//            } catch (IOException ex) {
//                return null;
//            }
//            return custObjArray;
//        } catch (Exception ex) {
//            logger.info("getCustomerList exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    public ArrayList getCustomerList(int length) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + CKey.ADMIN_USERNAME + "/sys/custlist?length=" + length;
//        try {
//
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            ArrayList<CustomerObj> custObjArray = null;
//            try {
//                CustomerObj[] arrayItem = new ObjectMapper().readValue(output, CustomerObj[].class);
//                List<CustomerObj> listItem = Arrays.<CustomerObj>asList(arrayItem);
//                custObjArray = new ArrayList<CustomerObj>(listItem);
//
//            } catch (IOException ex) {
//                return null;
//            }
//            return custObjArray;
//        } catch (Exception ex) {
//            logger.info("getCustomerList exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    public ArrayList getAccountList(String EmailUserName, String Password) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/acc";
//        try {
//            HashMap newmap = null;
//            if (Password != null) {
//                newmap = new HashMap();
//                newmap.put("pass", Password);
//            }
////            ClientResponse response = get(subResourcePath, newmap);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, newmap, null);
//            ArrayList<AccountObj> accountObjArray = null;
//            try {
//                AccountObj[] arrayItem = new ObjectMapper().readValue(output, AccountObj[].class);
//                List<AccountObj> listItem = Arrays.<AccountObj>asList(arrayItem);
//                accountObjArray = new ArrayList<AccountObj>(listItem);
//
//            } catch (IOException ex) {
//                return null;
//            }
//
//            return accountObjArray;
//        } catch (Exception ex) {
//            logger.info("getAccountList exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    public int updateAccountStatusByAccountID(String EmailUserName, String Password, String AccountIDSt,
//            String substatusSt, String paymentSt, String balanceSt, String servicefeeSt) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/acc/" + AccountIDSt + "/update?substatus=" + substatusSt
//                + "&payment=" + paymentSt + "&balance=" + balanceSt + "&servicefee=" + servicefeeSt;
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//        } catch (Exception ex) {
//            logger.info("updateAccountStatusByAccountID exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return 0;
//
//    }
//
//    public AccountObj getAccountByCustomerAccountID(String EmailUserName, String Password, String AccountIDSt) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/acc/" + AccountIDSt;
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            AccountObj accObj = new ObjectMapper().readValue(output, AccountObj.class);
////            accObj.setUpdateDateDisplay(new java.sql.Date(accObj.getUpdateDateL()));
//            return accObj;
//
//        } catch (Exception ex) {
//            logger.info("getAccountByAccountID exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    public int addCommByCustomerAccountID(String EmailUserName, String Password, String AccountIDSt, String data) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/acc/" + AccountIDSt + "/comm/add?data=" + data;
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//        } catch (Exception ex) {
//            logger.info("removeCommByCustomerAccountID exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return 0;
//    }
//
//    public int removeAccountCommByID(String EmailUserName, String Password, String AccountIDSt, String IDSt) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/acc/" + AccountIDSt + "/comm/remove/" + IDSt;
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//        } catch (Exception ex) {
//            logger.info("removeCommByCustomerAccountID exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return 0;
//    }
//
//    public int removeCommByCustomerAccountID(String EmailUserName, String Password, String AccountIDSt) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/acc/" + AccountIDSt + "/comm/remove";
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//        } catch (Exception ex) {
//            logger.info("removeCommByCustomerAccountID exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return 0;
//    }
//
//    public ArrayList getCommByCustomerAccountID(String EmailUserName, String Password, String AccountIDSt) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/acc/" + AccountIDSt + "/comm";
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            ArrayList<CommObj> stockArray = null;
//            try {
//                CommObj[] arrayItem = new ObjectMapper().readValue(output, CommObj[].class);
//                List<CommObj> listItem = Arrays.<CommObj>asList(arrayItem);
//                stockArray = new ArrayList<CommObj>(listItem);
//
//            } catch (IOException ex) {
//                return null;
//            }
//
//            return stockArray;
//        } catch (Exception ex) {
//            logger.info("getBillingByCustomerAccountID exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    public ArrayList getBillingByCustomerAccountID(String EmailUserName, String Password, String AccountIDSt, int length) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/acc/" + AccountIDSt + "/billing?length=" + length;
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            ArrayList<BillingObj> stockArray = null;
//            try {
//                BillingObj[] arrayItem = new ObjectMapper().readValue(output, BillingObj[].class);
//                List<BillingObj> listItem = Arrays.<BillingObj>asList(arrayItem);
//                stockArray = new ArrayList<BillingObj>(listItem);
//
//            } catch (IOException ex) {
//                return null;
//            }
//
//            return stockArray;
//        } catch (Exception ex) {
//            logger.info("getBillingByCustomerAccountID exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    public ArrayList getStock_AccountStockList_StockByAccountID(String EmailUserName, String Password, String AccountIDSt, int length) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/acc/" + AccountIDSt + "/st?length=" + length;
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            ArrayList<AFstockObj> stockArray = null;
//            try {
//                AFstockObj[] arrayItem = new ObjectMapper().readValue(output, AFstockObj[].class);
//                List<AFstockObj> listItem = Arrays.<AFstockObj>asList(arrayItem);
//                stockArray = new ArrayList<AFstockObj>(listItem);
//
//            } catch (IOException ex) {
//                return null;
//            }
//
//            return stockArray;
//        } catch (Exception ex) {
//            logger.info("getStock_AccountStockList_StockByAccountID exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    public int updateAccountStockSignal(TRObj stockTRObj) {
//        try {
//            RequestObj sqlObj = new RequestObj();
//            sqlObj.setCmd(ServiceAFweb.updateAccountStockSignal + "");
//            String st = new ObjectMapper().writeValueAsString(stockTRObj);
//            sqlObj.setReq(st);
//            RequestObj sqlObjresp = getSQLRequest(sqlObj);
//            String output = sqlObjresp.getResp();
//            if (output == null) {
//                return 0;
//            }
//            return 1;
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ServiceAFwebREST.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return 0;
//    }
//
//    public int addAccountStock(String EmailUserName, String Password, String AccountIDSt, String symbol) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        SymbolNameObj symObj = new SymbolNameObj(symbol);
//        String SymbolInternet = symObj.getSymbolFileName();
//
//        String subResourcePath = "/cust/" + EmailUserName + "/acc/" + AccountIDSt + "/st/add/" + SymbolInternet;;
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//
//        } catch (Exception ex) {
//            logger.info("addAccountStock exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return 0;
//    }
//
//    public int removeAccountStock(String EmailUserName, String Password, String AccountIDSt, String symbol) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        SymbolNameObj symObj = new SymbolNameObj(symbol);
//        String SymbolInternet = symObj.getSymbolFileName();
//
//        String subResourcePath = "/cust/" + EmailUserName + "/acc/" + AccountIDSt + "/st/remove/" + SymbolInternet;;
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//
//        } catch (Exception ex) {
//            logger.info("removeAccountStock exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return 0;
//    }
//
//    public AFstockObj getStock_AccountStockList_ByStockID(String EmailUserName, String Password, String AccountIDSt, String stockidsymbol) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/acc/" + AccountIDSt + "/st/" + stockidsymbol;
//
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            AFstockObj stockObj = new ObjectMapper().readValue(output, AFstockObj.class);
////            stockObj.setUpdateDateDisplay(new java.sql.Date(stockObj.getUpdateDateL()));
//            return stockObj;
//        } catch (Exception ex) {
//            logger.info("getStock_AccountStockList_ByStockID exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    public TradingRuleObj getAccountStockByTRname(String EmailUserName, String Password, String AccountIDSt, String stockidsymbol, String trname) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/acc/" + AccountIDSt + "/st/" + stockidsymbol + "/tr" + "/" + trname;
//
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            TradingRuleObj tr = new ObjectMapper().readValue(output, TradingRuleObj.class);
//
//            return tr;
//        } catch (Exception ex) {
//            logger.info("getAccountStockByTRname exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    public ArrayList<StockTRHistoryObj> getAccountStockTRListHistory(String EmailUserName, String Password, String AccountIDSt, String stockidsymbol, String trname) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/acc/" + AccountIDSt + "/st/" + stockidsymbol + "/tr/" + trname + "/tran/history";
//
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            ArrayList<StockTRHistoryObj> trArray = null;
//            try {
//                StockTRHistoryObj[] arrayItem = new ObjectMapper().readValue(output, StockTRHistoryObj[].class);
//                List<StockTRHistoryObj> listItem = Arrays.<StockTRHistoryObj>asList(arrayItem);
//                trArray = new ArrayList<StockTRHistoryObj>(listItem);
//
//            } catch (IOException ex) {
//                return null;
//            }
//            return trArray;
//        } catch (Exception ex) {
//            logger.info("getAccountStockListByAccountID exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    public ArrayList getAccountStockListByAccountID(String EmailUserName, String Password, String AccountIDSt, String stockidsymbol) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/acc/" + AccountIDSt + "/st/" + stockidsymbol + "/tr";
//
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            ArrayList<TradingRuleObj> trArray = null;
//            try {
//                TradingRuleObj[] arrayItem = new ObjectMapper().readValue(output, TradingRuleObj[].class);
//                List<TradingRuleObj> listItem = Arrays.<TradingRuleObj>asList(arrayItem);
//                trArray = new ArrayList<TradingRuleObj>(listItem);
//
//            } catch (IOException ex) {
//                return null;
//            }
//            return trArray;
//        } catch (Exception ex) {
//            logger.info("getAccountStockListByAccountID exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    public int setAccountStockTran(String EmailUserName, String Password, String AccountIDSt, String stockidsymbol, String trName, int signal) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/acc/" + AccountIDSt + "/st/" + stockidsymbol + "/tr" + "/" + trName + "/tran/" + signal + "/order";
//
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//        } catch (Exception ex) {
//            logger.info("setAccountStockTran exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return 0;
//    }
//
//    public int getAccountStockClrTranByAccountID(String EmailUserName, String Password, String AccountIDSt, String stockidsymbol, String trName) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/acc/" + AccountIDSt + "/st/" + stockidsymbol + "/tr" + "/" + trName + "/tran/clear";
//
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//        } catch (Exception ex) {
//            logger.info("getAccountStockClrTranByAccountID exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return 0;
//    }
//
//    public int setAccountStockTRoption(String EmailUserName, String Password, String AccountIDSt, String stockidsymbol, String trName, String opt) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/acc/" + AccountIDSt + "/st/" + stockidsymbol + "/tr" + "/" + trName + "/linktr/" + opt;
//
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//
//        } catch (Exception ex) {
//            logger.info("setAccountStockTRoption exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return 0;
//    }
//
//    public ArrayList<PerformanceObj> getAccountStockPerfList(String EmailUserName, String Password, String AccountIDSt, String stockidsymbol, String trName, int length) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/acc/" + AccountIDSt + "/st/" + stockidsymbol + "/tr" + "/" + trName + "/perf?length=" + length;
//
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            ArrayList<PerformanceObj> trArray = null;
//            try {
//                PerformanceObj[] arrayItem = new ObjectMapper().readValue(output, PerformanceObj[].class);
//                List<PerformanceObj> listItem = Arrays.<PerformanceObj>asList(arrayItem);
//                trArray = new ArrayList<PerformanceObj>(listItem);
//            } catch (IOException ex) {
//                return null;
//            }
//            return trArray;
//        } catch (Exception ex) {
//            logger.info("getAccountStockPerfList exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    public ArrayList<TransationOrderObj> getAccountStockTranListByAccountID(String EmailUserName, String Password, String AccountIDSt, String stockidsymbol, String trName, int length) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/acc/" + AccountIDSt + "/st/" + stockidsymbol + "/tr" + "/" + trName + "/tran?length=" + length;
//
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            ArrayList<TransationOrderObj> trArray = null;
//            try {
//                TransationOrderObj[] arrayItem = new ObjectMapper().readValue(output, TransationOrderObj[].class);
//                List<TransationOrderObj> listItem = Arrays.<TransationOrderObj>asList(arrayItem);
//                trArray = new ArrayList<TransationOrderObj>(listItem);
//            } catch (IOException ex) {
//                return null;
//            }
//            return trArray;
//        } catch (Exception ex) {
//            logger.info("getAccountStockTranByAccountID exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    ArrayList getStockArray(int length) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/st?length=" + length;
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            ArrayList<AFstockObj> stockArray = null;
//            try {
//                AFstockObj[] arrayItem = new ObjectMapper().readValue(output, AFstockObj[].class);
//                List<AFstockObj> listItem = Arrays.<AFstockObj>asList(arrayItem);
//                stockArray = new ArrayList<AFstockObj>(listItem);
//            } catch (IOException ex) {
//                return null;
//            }
//            return stockArray;
//        } catch (Exception ex) {
//            logger.info("getStock_AccountStockList_StockByAccountID exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    public AFstockObj getRealTimeStockImp(String symbol) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        SymbolNameObj symObj = new SymbolNameObj(symbol);
//        String SymbolInternet = symObj.getSymbolFileName();
//        String subResourcePath = "/st/" + SymbolInternet;
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            AFstockObj stock = new ObjectMapper().readValue(output, AFstockObj.class);
////            stock.setUpdateDateDisplay(new java.sql.Date(stock.getUpdateDateL()));
//
//            return stock;
//        } catch (Exception ex) {
//            logger.info("GetRealTimeStockImp exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    public ArrayList getStockHistorical(String symbol, int length) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        SymbolNameObj symObj = new SymbolNameObj(symbol);
//        String SymbolInternet = symObj.getSymbolFileName();
//        String subResourcePath = "/st/" + SymbolInternet + "/history?length=" + length;
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            ArrayList<AFstockInfo> stockInfoArray = null;
//            try {
//                AFstockInfo[] arrayItem = new ObjectMapper().readValue(output, AFstockInfo[].class);
//                List<AFstockInfo> listItem = Arrays.<AFstockInfo>asList(arrayItem);
//                stockInfoArray = new ArrayList<AFstockInfo>(listItem);
//
//            } catch (IOException ex) {
//                return null;
//            }
//            return stockInfoArray;
//        } catch (Exception ex) {
//            logger.info("GetStockHistoricalImp exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//
//    }
//
//    public int AddFailCntStock(String symbol) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        SymbolNameObj symObj = new SymbolNameObj(symbol);
//        String SymbolInternet = symObj.getSymbolFileName();
//
//        String subResourcePath = "/st/addfailcnt/" + SymbolInternet;;
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//
//        } catch (Exception ex) {
//            logger.info("addStock exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return 0;
//    }
//
//    public int addStock(String symbol) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        SymbolNameObj symObj = new SymbolNameObj(symbol);
//        String SymbolInternet = symObj.getSymbolFileName();
//
//        String subResourcePath = "/st/add/" + SymbolInternet;;
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//
//        } catch (Exception ex) {
//            logger.info("addStock exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return 0;
//    }
//
//    public ArrayList getStockNameArray() {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + CKey.ADMIN_USERNAME + "/sys/st/allname";
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            ArrayList<String> stockNameList = new ArrayList();
//            stockNameList = new ObjectMapper().readValue(output, ArrayList.class);
//
////            log.info("getAllStockArray " + output);
//            return stockNameList;
//
//        } catch (Exception ex) {
//            logger.info("getAllStockArray exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    public int DeleteStockInfo(String symbol) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        SymbolNameObj symObj = new SymbolNameObj(symbol);
//        String SymbolInternet = symObj.getSymbolFileName();
//        String subResourcePath = "/st/deleteinfo/" + SymbolInternet;
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//
//        } catch (Exception ex) {
//            logger.info("DeleteStockInfo exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return 0;
//    }
//
//    public int disableStock(String symbol) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        SymbolNameObj symObj = new SymbolNameObj(symbol);
//        String SymbolInternet = symObj.getSymbolFileName();
//        String subResourcePath = "/st/remove/" + SymbolInternet;
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//
//        } catch (Exception ex) {
//            logger.info("removeStock exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return 0;
//    }
//
//    public ArrayList getAllLock() {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + CKey.ADMIN_USERNAME + "/sys/lock";
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            ArrayList<AFLockObject> lockArray = null;
//            try {
//                AFLockObject[] arrayItem = new ObjectMapper().readValue(output, AFLockObject[].class);
//                List<AFLockObject> listItem = Arrays.<AFLockObject>asList(arrayItem);
//                lockArray = new ArrayList<AFLockObject>(listItem);
//            } catch (IOException ex) {
//                return null;
//            }
//
//            return lockArray;
//        } catch (Exception ex) {
//            logger.info("getAllLock exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    public AFLockObject getLockName(String symbol_acc, int type) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String name = symbol_acc;
//        if (type == ConstantKey.STOCK_LOCKTYPE) {
//            SymbolNameObj symObj = new SymbolNameObj(symbol_acc);
//            name = symObj.getSymbolFileName();
//        }
//        name = name.toUpperCase();
//        String subResourcePath = "/cust/" + CKey.ADMIN_USERNAME + "/sys/lock/" + name + "/type/" + type;
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            AFLockObject lockObj = new ObjectMapper().readValue(output, AFLockObject.class);
//            return lockObj;
//
//        } catch (Exception ex) {
//            logger.info("GetLockName exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    public int setRenewLock(String symbol_acc, int type) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String name = symbol_acc;
//        if (type == ConstantKey.STOCK_LOCKTYPE) {
//            SymbolNameObj symObj = new SymbolNameObj(symbol_acc);
//            name = symObj.getSymbolFileName();
//        }
//        name = name.toUpperCase();
//        String subResourcePath = "/cust/" + CKey.ADMIN_USERNAME + "/sys/lock/" + name + "/type/" + type + "/renewlock";
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//
//        } catch (Exception ex) {
//            logger.info("setRenewLock exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//
//        }
//        return 0;
//    }
//
//    public int removeNameLock(String symbol_acc, int type) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String name = symbol_acc;
//        if (type == ConstantKey.STOCK_LOCKTYPE) {
//            SymbolNameObj symObj = new SymbolNameObj(symbol_acc);
//            name = symObj.getSymbolFileName();
//        }
//        name = name.toUpperCase();
//
//        String subResourcePath = "/cust/" + CKey.ADMIN_USERNAME + "/sys/lock/" + name + "/type/" + type + "/removelock";
//
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//
//        } catch (Exception ex) {
//            logger.info("removestockLock exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return 0;
//    }
//
//    public int setLockName(String symbol_acc, int type, long lockdatel, String comment) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String name = symbol_acc;
//        if (type == ConstantKey.STOCK_LOCKTYPE) {
//            SymbolNameObj symObj = new SymbolNameObj(symbol_acc);
//            name = symObj.getSymbolFileName();
//        }
//        name = name.toUpperCase();
//        String subResourcePath = "/cust/" + CKey.ADMIN_USERNAME + "/sys/lock/" + name + "/type/" + type + "/value/" + lockdatel + "/comment/" + comment + "/setlock";
//
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//
//        } catch (Exception ex) {
//            logger.info("setStockLock exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return 0;
//    }
///////////////
//
//    public int releaseNeuralNetObj(String name) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + CKey.ADMIN_USERNAME + "/sys/neuralnet/" + name + "/release";
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//
//        } catch (Exception ex) {
//            logger.info("getNeuralNetObj exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return 0;
//    }
//
//    public AFneuralNet getNeuralNetObjWeight0(String name, int type) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + CKey.ADMIN_USERNAME + "/sys/neuralnet/" + name + "/type/" + type + "/weight0";
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            AFneuralNet nnObj = new ObjectMapper().readValue(output, AFneuralNet.class);
//            return nnObj;
//
//        } catch (Exception ex) {
//            logger.info("getNeuralNetObj exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    public AFneuralNet getNeuralNetObjWeight1(String name, int type) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + CKey.ADMIN_USERNAME + "/sys/neuralnet/" + name + "/type/" + type + "/weight1";
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            AFneuralNet nnObj = new ObjectMapper().readValue(output, AFneuralNet.class);
//            return nnObj;
//
//        } catch (Exception ex) {
//            logger.info("getNeuralNetObj exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
    public int setNeuralNetObjWeight0(AFneuralNet nn, String URL) {
        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
        String subResourcePath = URL + "/cust/" + CKey.ADMIN_USERNAME + "/sys/neuralnet/" + nn.getName() + "/updateweight0";

        try {
            String nnSt = new ObjectMapper().writeValueAsString(nn);

//            ClientResponse response = post(subResourcePath, null, nnSt);
//            String output = response.getEntity(String.class);
            String output = sendRequest_1(METHOD_POST, subResourcePath, null, nnSt);

            int result = new ObjectMapper().readValue(output, Integer.class);
            return result;
        } catch (Exception ex) {
            logger.info("setStockLock exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
        }
        return 0;
    }
//
//    public int setNeuralNetObjWeight1(AFneuralNet nn) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + CKey.ADMIN_USERNAME + "/sys/neuralnet/" + nn.getName() + "/updateweight1";
//
//        try {
//            String nnSt = new ObjectMapper().writeValueAsString(nn);
//
////            ClientResponse response = post(subResourcePath, null, nnSt);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_POST, subResourcePath, null, nnSt);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//        } catch (Exception ex) {
//            logger.info("setStockLock exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return 0;
//    }
//
//    //////////////////////////////////////////
//
//    public int updateStockInfoTransaction(StockInfoTranObj stockInfoTran) {
//        try {
//            RequestObj sqlObj = new RequestObj();
//            sqlObj.setCmd(ServiceAFweb.updateStockInfoTransaction + "");
//            String st = new ObjectMapper().writeValueAsString(stockInfoTran);
//            sqlObj.setReq(st);
//            RequestObj sqlObjresp = getSQLRequest(sqlObj);
//            String output = sqlObjresp.getResp();
//            if (output == null) {
//                return 0;
//            }
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//        } catch (Exception ex) {
//            logger.info("updateStockInfoTransaction exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//
//        }
//        return 0;
//    }
//
//    public int removeCustomer(String customername) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + CKey.ADMIN_USERNAME + "/sys/cust/" + customername + "/removeCustomer" + customername;
//        try {
//
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//
//        } catch (Exception ex) {
//            logger.info("removeCustomer exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return 0;
//    }
//
//    public int updateCustAllStatus(String customername,
//            String statusSt, String paymentSt, String balanceSt) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + CKey.ADMIN_USERNAME + "/sys/cust/" + customername + "/update?status=" + statusSt
//                + "&payment=" + paymentSt + "&balance=" + balanceSt;
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//
//        } catch (Exception ex) {
//            logger.info("updateCustAllStatus exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return 0;
//    }
//
//    public int updateCustStatusSubStatus(String customername, String statusSt, String substatusSt) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//
//        String subResourcePath = "/cust/" + CKey.ADMIN_USERNAME + "/sys/cust/" + customername + "/status/" + statusSt + "/substatus/" + substatusSt;
//        try {
//
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            int result = new ObjectMapper().readValue(output, Integer.class);
//            return result;
//
//        } catch (Exception ex) {
//            logger.info("removeCustomer exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return 0;
//    }
//
//    public ArrayList getAllAccountStockNameListExceptionAdmin(String EmailUserName, String Password, String AccountIDSt) {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/cust/" + EmailUserName + "/sys/acc/" + AccountIDSt + "/NameListExceptionAdmin";
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            ArrayList<String> stockNameList = new ArrayList();
//            stockNameList = new ObjectMapper().readValue(output, ArrayList.class);
//
//            return stockNameList;
//        } catch (Exception ex) {
//            logger.info("getAllAccountStockNameListExceptionAdmin exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }
//
//    /cust/{username}/acc/{accountid}/stname

    public ArrayList<String> getRESTAccountStockNameList(String EmailUserName, String AccountIDSt, String URL) {
        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
        String subResourcePath = URL + "/cust/" + EmailUserName + "/acc/" + AccountIDSt + "/stname";
        try {

//            ClientResponse response = get(subResourcePath, null);
//            String output = response.getEntity(String.class);
            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
            ArrayList<String> stockNameList = new ArrayList();
            stockNameList = new ObjectMapper().readValue(output, ArrayList.class);

            return stockNameList;

        } catch (Exception ex) {
            logger.info("getRESTAccountStockNameList exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
        }
        return null;
    }
//
//    public ArrayList getServerList() {
//        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
//        String subResourcePath = "/server";
//        try {
////            ClientResponse response = get(subResourcePath, null);
////            String output = response.getEntity(String.class);
//            String output = sendRequest_1(METHOD_GET, subResourcePath, null, null);
//            ArrayList<ServerObj> serverArray = null;
//            try {
//                ServerObj[] arrayItem = new ObjectMapper().readValue(output, ServerObj[].class);
//                List<ServerObj> listItem = Arrays.<ServerObj>asList(arrayItem);
//                serverArray = new ArrayList<ServerObj>(listItem);
//
//            } catch (IOException ex) {
//                return null;
//            }
//            return serverArray;
//        } catch (Exception ex) {
//            logger.info("getServerList exception " + ex);
//            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
//        }
//        return null;
//    }

    public RequestObj getSQLRequest(RequestObj sqlObj, String URL) {
        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
        String subResourcePath = URL + "/cust/" + CKey.ADMIN_USERNAME + "/sys/request";

        try {
            String sqlSt = new ObjectMapper().writeValueAsString(sqlObj);

            String output = sendRequest_1(METHOD_POST, subResourcePath, null, sqlSt);

            RequestObj sqlResp = new ObjectMapper().readValue(output, RequestObj.class);
            return sqlResp;
        } catch (Exception ex) {
            logger.info("getSQLRequest exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
        }
        return null;
    }
    // operations names constants
    private static final String METHOD_POST = "post";
    private static final String METHOD_GET = "get";

////////////////////////////////////////
//    private String sendRequest_1(String method, String subResourcePath, Map<String, String> queryParams, String bodyParams) throws Exception {
//        String response = null;
//        for (int i = 0; i < 4; i++) {
//            try {
//                response = sendRequest_Process_1(method, subResourcePath, queryParams, bodyParams);
//                if (response != null) {
//                    return response;
//                }
//            } catch (Exception ex) {
//
//            }
//
//            System.out.println("sendRequest " + subResourcePath + " Rety " + (i + 1));
//        }
//        response = sendRequest_Process_1(method, subResourcePath, queryParams, bodyParams);
//        return response;
//    }
//
//    private String sendRequest_Process_1(String method, String subResourcePath, Map<String, String> queryParams, String bodyElement)
//            throws Exception {
//        try {
//            String URLPath = CKey.SERVERDB_REMOTE_URL + subResourcePath;
//
//            String webResourceString = "";
//            // assume only one param
//            if (queryParams != null && !queryParams.isEmpty()) {
//                for (String key : queryParams.keySet()) {
//                    webResourceString = "?" + key + "=" + queryParams.get(key);
//                }
//            }
//
//            URLPath += webResourceString;
//            URL request = new URL(URLPath);
//            HttpURLConnection con = null; //(HttpURLConnection) request.openConnection();
//            System.out.println("Request Code:: " + URLPath);
//            if (CKey.PROXY == true) {
//                //////Add Proxy 
//                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(CKey.PROXYURL, 8080));
//                con = (HttpURLConnection) request.openConnection(proxy);
//                //////Add Proxy 
//            } else {
//                con = (HttpURLConnection) request.openConnection();
//            }
//            if (method.equals(METHOD_POST)) {
//                con.setRequestMethod("POST");
//            } else {
//                con.setRequestMethod("GET");
//            }
//            con.setRequestProperty("User-Agent", USER_AGENT);
//            con.setRequestProperty("Content-Type", "application/json; utf-8");
//
//            if (method.equals(METHOD_POST)) {
//                // For POST only - START
//                con.setDoOutput(true);
//                OutputStream os = con.getOutputStream();
//                byte[] input = bodyElement.getBytes("utf-8");
//                os.write(input, 0, input.length);
//                os.flush();
//                os.close();
//                // For POST only - END
//            }
//            int responseCode = con.getResponseCode();
//            System.out.println("Response Code:: " + responseCode);
//
//            if (responseCode >= 200 && responseCode < 300) {
//                ;
//            } else {
//                System.out.println("bodyElement :: " + bodyElement);
//                return null;
//            }
//            if (responseCode == HttpURLConnection.HTTP_OK) { //success
//                BufferedReader in = new BufferedReader(new InputStreamReader(
//                        con.getInputStream()));
//                String inputLine;
//                StringBuffer response = new StringBuffer();
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//                // print result
//                return response.toString();
//            } else {
//                log.info("POST request not worked");
//            }
//
//        } catch (Exception e) {
//            log.info("Error sending REST request:" + e);
//            throw e;
//        }
//        return null;
//    }
    private String sendRequest_1(String method, String subResourcePath, Map<String, String> queryParams, String bodyParams) {
        try {
            if (subResourcePath.indexOf("https") != -1) {
                return this.https_sendRequest_Process_Ssns(method, subResourcePath, queryParams, bodyParams);
            }
            return this.http_sendRequest_Process_Ssns(method, subResourcePath, queryParams, bodyParams);
        } catch (Exception ex) {
//            Logger.getLogger(SsnsService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private String https_sendRequest_Process_Ssns(String method, String subResourcePath, Map<String, String> queryParams, String bodyParams)
            throws Exception {
        try {

            String URLPath = subResourcePath;

            String webResourceString = "";
            // assume only one param
            if (queryParams != null && !queryParams.isEmpty()) {
                for (String key : queryParams.keySet()) {
                    webResourceString = "?" + key + "=" + queryParams.get(key);
                }
            }

            String bodyElement = bodyParams;
//            if (bodyParams != null) {
//                bodyElement = new ObjectMapper().writeValueAsString(bodyParams);
//            }

            URLPath += webResourceString;
            URL request = new URL(URLPath);

            HttpsURLConnection con = null; //(HttpURLConnection) request.openConnection();

            if (CKey.PROXY == true) {
                //////Add Proxy 
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ServiceAFweb.PROXYURL, 8080));
                con = (HttpsURLConnection) request.openConnection(proxy);
                //////Add Proxy 
            } else {
                con = (HttpsURLConnection) request.openConnection();
            }

//            String authStr = "APP_SELFSERVEUSGBIZSVC" + ":" + "soaorgid";
//            // encode data on your side using BASE64
//            byte[] bytesEncoded = Base64.encodeBase64(authStr.getBytes());
//            String authEncoded = new String(bytesEncoded);
//            con.setRequestProperty("Authorization", "Basic " + authEncoded);
            if (method.equals(METHOD_POST)) {
                con.setRequestMethod("POST");
            } else if (method.equals(METHOD_GET)) {
                con.setRequestMethod("GET");
            }
            con.setRequestProperty("User-Agent", USER_AGENT);
//            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");

            if (method.equals(METHOD_POST)) {

//                con.setRequestMethod("POST");
//                con.addRequestProperty("Accept", "application/json");
//                con.addRequestProperty("Connection", "close");
//                con.addRequestProperty("Content-Encoding", "gzip"); // We gzip our request
//                con.addRequestProperty("Content-Length", String.valueOf(bodyElement.length()));
//                con.setRequestProperty("Content-Type", "application/json"); // We send our data in JSON format
                con.setDoInput(true);
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
//                System.out.println("Response Code:: " + responseCode);
//                System.out.println("bodyElement :: " + bodyElement);
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
//            logger.info("Error sending REST request:" + e);
            throw e;
        }
        return null;
    }

    private String http_sendRequest_Process_Ssns(String method, String subResourcePath, Map<String, String> queryParams, String bodyParams)
            throws Exception {
        try {

            String URLPath = subResourcePath;

            String webResourceString = "";
            // assume only one param
            if (queryParams != null && !queryParams.isEmpty()) {
                for (String key : queryParams.keySet()) {
                    webResourceString = "?" + key + "=" + queryParams.get(key);
                }
            }

            String bodyElement = bodyParams;
//            if (bodyParams != null) {
//                bodyElement = new ObjectMapper().writeValueAsString(bodyParams);
//            }

            URLPath += webResourceString;
            URL request = new URL(URLPath);

            HttpURLConnection con = null; //(HttpURLConnection) request.openConnection();

            if (CKey.PROXY == true) {
                //////Add Proxy 
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ServiceAFweb.PROXYURL, 8080));
                con = (HttpURLConnection) request.openConnection(proxy);
                //////Add Proxy 
            } else {
                con = (HttpURLConnection) request.openConnection();
            }

//            String authStr = "APP_SELFSERVEUSGBIZSVC" + ":" + "soaorgid";
//            // encode data on your side using BASE64
//            byte[] bytesEncoded = Base64.encodeBase64(authStr.getBytes());
//            String authEncoded = new String(bytesEncoded);
//            con.setRequestProperty("Authorization", "Basic " + authEncoded);
            if (method.equals(METHOD_POST)) {
                con.setRequestMethod("POST");
            } else if (method.equals(METHOD_GET)) {
                con.setRequestMethod("GET");
            }
            con.setRequestProperty("User-Agent", USER_AGENT);
//            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
//            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");

            if (method.equals(METHOD_POST)) {
                con.setDoOutput(true);
                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = bodyElement.getBytes("utf-8");
                    os.write(input, 0, input.length);
                    os.flush();
                    os.close();
                }

            }

            int responseCode = con.getResponseCode();
            if (responseCode != 200) {
                logger.info("Response Code:: " + responseCode);
            }
            if (responseCode >= 200 && responseCode < 300) {
                ;
            } else {
//                System.out.println("Response Code:: " + responseCode);
//                System.out.println("bodyElement :: " + bodyElement);
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
//            System.out.println("Error sending REST request:" + e);
            throw e;
        }
        return null;
    }
    ////////////////////////////////////////////
    private static int sendNum = 0;

    public String getSQLRequestRemote(RequestObj sqlObj) {
        ServiceAFweb.getServerObj().setCntRESTrequest(ServiceAFweb.getServerObj().getCntRESTrequest() + 1);
        String subResourcePath = CKey.SERVER_TIMMER_URL + "/cust/" + CKey.ADMIN_USERNAME + "/sys/mysql";

        if (sqlObj.getReq().length() < 3) {
            logger.info("getSQLRequest not correct num " + sendNum + " sql " + sqlObj.getReq());
            return "";
        }

        try {
            String sqlSt = new ObjectMapper().writeValueAsString(sqlObj);

            String output = sendRequest_2(METHOD_POST, subResourcePath, null, sqlSt);
            sendNum++;
            if ((sendNum % 10) == 0) {
                logger.info("getSQLRequest sendNum " + sendNum);
            }
            return output;
        } catch (Exception ex) {
            logger.info("getSQLRequest exception " + ex);
            ServiceAFweb.getServerObj().setCntRESTexception(ServiceAFweb.getServerObj().getCntRESTexception() + 1);
        }

        return null;
    }

    private String sendRequest_2(String method, String subResourcePath, Map<String, String> queryParams, String bodyParams) throws Exception {
        String response = null;
        for (int i = 0; i < 4; i++) {
            try {
                response = sendRequest_Process_2(method, subResourcePath, queryParams, bodyParams);
                if (response != null) {
                    return response;
                }
            } catch (Exception ex) {

            }

            logger.info("sendRequest " + subResourcePath + " Rety " + (i + 1));
        }
        response = sendRequest_Process_2(method, subResourcePath, queryParams, bodyParams);
        return response;
    }

    private String sendRequest_Process_2(String method, String subResourcePath, Map<String, String> queryParams, String bodyElement)
            throws Exception {
        try {
            String URLPath = subResourcePath;

            String webResourceString = "";
            // assume only one param
            if (queryParams != null && !queryParams.isEmpty()) {
                for (String key : queryParams.keySet()) {
                    webResourceString = "?" + key + "=" + queryParams.get(key);
                }
            }

            URLPath += webResourceString;
            URL request = new URL(URLPath);
            HttpURLConnection con = null; //(HttpURLConnection) request.openConnection();
//            System.out.println("Request Code:: " + URLPath);
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
            con.setRequestProperty("Content-Type", "text/xml");
//            con.setRequestProperty("Content-Type", "application/json; utf-8");

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
//            System.out.println("Response Code:: " + responseCode);

            if (responseCode >= 200 && responseCode < 300) {
                ;
            } else {
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

}
