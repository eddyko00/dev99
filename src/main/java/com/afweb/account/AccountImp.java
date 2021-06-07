/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.account;

import com.afweb.model.AccEntryObj;
import com.afweb.model.ConstantKey;

import com.afweb.model.account.*;
import com.afweb.model.stock.AFstockObj;
//import com.afweb.stock.StockInfoDB;
import com.afweb.util.CKey;
import com.afweb.util.StringTag;
import com.afweb.util.TimeConvertion;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author eddy
 */
public class AccountImp {

    protected static Logger logger = Logger.getLogger("AccountImp");
    public AccountDB accountdb = new AccountDB();

    public void setDataSource(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        accountdb.setJdbcTemplate(jdbcTemplate);
        accountdb.setDataSource(dataSource);
    }

    public ArrayList getExpiredCustomerList(int length) {
        ArrayList customerList = new ArrayList();
        //only on type=" + CustomerObj.INT_CLIENT_BASIC_USER;
        ArrayList customerDBList = accountdb.getExpiredCustomerList(length);
        if (customerDBList != null && customerDBList.size() > 0) {
            if (length == 0) {
                // all customer
                return customerDBList;
            }
            if (length > customerDBList.size()) {
                length = customerDBList.size();
            }
            for (int i = 0; i < length; i++) {
                CustomerObj cust = (CustomerObj) customerDBList.get(i);
                customerList.add(cust);
            }
        }
        return customerList;
    }

    public ArrayList getAllIdSQL(String sql) {
//        if (CKey.SEPARATE_STOCKINFO_DB == true) {
//            if (sql.indexOf(" stockinfo ") != -1) {
//                StockInfoDB stockinfodb = new StockInfoDB();
//                return stockinfodb.getAllIdSQL(sql);
//            }
//        }
        return accountdb.getAllIdSQL(sql);
    }

    public ArrayList getAllUserNameSQL(String sql) {
        return accountdb.getAllUserNameSQL(sql);
    }

    public String getAllCustomerDBSQL(String sql) {
        return accountdb.getAllCustomerDBSQL(sql);
    }

    public String getAllAccountDBSQL(String sql) {
        return accountdb.getAllAccountDBSQL(sql);
    }

    public String getAllAccountStockDBSQL(String sql) {
        return accountdb.getAllAccountStockDBSQL(sql);
    }

    public String getAllTransationOrderDBSQL(String sql) {
        return accountdb.getAllTransationOrderDBSQL(sql);
    }

    public String getAllBillingDBSQL(String sql) {
        return accountdb.getAllBillingDBSQL(sql);
    }

    public String getAllCommDBSQL(String sql) {
        return accountdb.getAllCommDBSQL(sql);
    }

    public String getAllPerformanceDBSQL(String sql) {
        return accountdb.getAllPerformanceDBSQL(sql);
    }

    public String getAllSQLquery(String sql) {
        return accountdb.getAllSQLqueryDBSQL(sql);
    }

    public ArrayList<String> getCustomerNList(int length) {
        return accountdb.getCustomerNList(0);
    }

    public ArrayList<CustomerObj> getCustomerObjByNameList(String name) {
        return accountdb.getCustomerNameList(name);
    }

    public CustomerObj getCustomerByAccoutObj(AccountObj accObj) {
        if (accObj == null) {
            return null;
        }
        ArrayList<CustomerObj> custObjList = accountdb.getCustomerByCustId(accObj.getCustomerid());
        if (custObjList != null) {
            if (custObjList.size() > 0) {
                return custObjList.get(0);
            }
        }
        return null;
    }

    public ArrayList<CustomerObj> getCustomerFundPortfolio(String fundName, int length) {
        ArrayList customerDBList = accountdb.getCustomerFundPortfolio(fundName, length);
        return customerDBList;
    }

    public ArrayList<CustomerObj> getCustomerObjList(int length) {
        ArrayList customerList = new ArrayList();

        ArrayList customerDBList = accountdb.getCustomerList(0);
        if (customerDBList != null && customerDBList.size() > 0) {
            if (length == 0) {
                // all customer
                return customerDBList;
            }
            if (length > customerDBList.size()) {
                length = customerDBList.size();
            }
            for (int i = 0; i < length; i++) {
                String custid = (String) customerDBList.get(i);
                customerList.add(custid);
            }
        }
        return customerList;
    }

    public int updateCustStatusSubStatus(CustomerObj custObj, int status, int subStatus) {
        return accountdb.updateCustStatusSubStatus(custObj, status, subStatus);
    }

    public CustomerObj getCustomerBySystem(String UserName, String Password) {
//        logger.info("> getCustomerPassword  " + UserName);
        UserName = UserName.toUpperCase();
        CustomerObj customer = accountdb.getCustomer(UserName, Password);

        if (customer != null) {
            return customer;
        }
        return null;
    }

    //// do not expose to external interface
    public CustomerObj getCustomerByCustID(int custId) {
        CustomerObj customer = accountdb.getCustomerByCustID(custId);
        return customer;
    }

    public ArrayList<CustomerObj> getCustomerByType(int type) {
        ArrayList<CustomerObj> customerList = accountdb.getCustomerByType(type);
        return customerList;
    }

    public CustomerObj getCustomerByAccount(AccountObj accountObj) {

        CustomerObj customer = accountdb.getCustomerByAccount(accountObj);
        if (customer != null) {
            return customer;
        }
        return null;
    }

    public CustomerObj getCustomerPasswordNull(String UserName) {
//        logger.info("> getCustomerPassword  " + UserName);
        UserName = UserName.toUpperCase();
        CustomerObj customer = accountdb.getCustomer(UserName, null);
        if (customer != null) {
            return customer;
        }
        return null;
    }

    public CustomerObj getCustomerPassword(String UserName, String Password) {
//        logger.info("> getCustomerPassword  " + UserName);
        UserName = UserName.toUpperCase();
        CustomerObj customer = accountdb.getCustomer(UserName, Password);
        if (customer != null) {
            if (customer.getStatus() == ConstantKey.OPEN) {
//                Calendar dateNow = TimeConvertion.getCurrentCalendar();
//                long dateNowLong = dateNow.getTimeInMillis();
//                customer.setUpdatedatedisplay(new java.sql.Date(dateNowLong));
//                customer.setUpdatedatel(dateNowLong);
//
//                accountdb.updateCustomerUpdateDate(customer);
//                return customer;
            }
            return customer;
        }
        return null;
    }

    public CustomerObj getCustomerPasswordForce(String UserName, String Password) {
//        logger.info("> getCustomerPassword  " + UserName);
        UserName = UserName.toUpperCase();
        CustomerObj customer = accountdb.getCustomer(UserName, Password);
        if (customer != null) {
            return customer;
        }
        return null;
    }

    public int removeCommByCommID(int id) {
        return accountdb.removeCommByCommID(id);
    }

    public int removeCommByCustID(int custId) {
        return accountdb.removeCommByCustID(custId);
    }

    public int removeCommByCustObj(CustomerObj custObj, int type) {
        if (custObj == null) {
            return 0;
        }
        return accountdb.removeCommBuCustType(custObj.getId(), type);
    }

    public int removeCommByAccountType(AccountObj accountObj, int type) {
        if (accountObj == null) {
            return 0;
        }
        return accountdb.removeCommSignalByAccountType(accountObj.getId(), type);
    }

    public int removeAccountBilling(AccountObj accountObj) {
        if (accountObj == null) {
            return 0;
        }
        return accountdb.removeAccountBilling(accountObj.getId());
    }

    public int removeCustomer(CustomerObj custObj) {
        if (custObj == null) {
            return 0;
        }
        accountdb.removeCustBilling(custObj.getId());
        accountdb.removeCommBuCustType(custObj.getId(), -1);
        return accountdb.DeleteCustomer(custObj);
    }

    public int removeAccount(AccountObj accountObj) {
        if (accountObj == null) {
            return 0;
        }

        return accountdb.removeAccount(accountObj);
    }

    // result 1 = success, 2 = existed,  0 = fail
    public int addCustomer(CustomerObj newCustomer, int plan) {
        if (newCustomer == null) {
            return 0;
        }
        String userN = newCustomer.getUsername();
        userN = userN.toUpperCase();
        newCustomer.setUsername(userN);
        if (userN.equals(CKey.ADMIN_USERNAME)) {
            logger.info("> addCustomer  ADM");
        } else if (userN.equals(CKey.API_USERNAME)) {
            logger.info("> addCustomer  API");
        } else {
            logger.info("> addCustomer  " + newCustomer.getUsername());
        }
        int result = 0;
        try {
            result = accountdb.addCustomerAccount(newCustomer, plan);
        } catch (Exception ex) {
            logger.info("> addCustomer exception " + newCustomer.getUsername() + " - " + ex.getMessage());
        }
        return result;
    }

    public ArrayList<AccountObj> getAccountListByCustomerObj(CustomerObj customer) {
        ArrayList accountList = accountdb.getAccountByCustomerID(customer.getId());
        return accountList;
    }

    ////////////////////////////////
    // Security - Do not allow outside ServiceAFweb to access this function
    ////////////////////////////////
    public ArrayList<AccountObj> getAccountListByCustomerId(int custId) {
        ArrayList accountList = accountdb.getAccountByCustomerID(custId);
        return accountList;
    }

    public AccountObj getAccountByType(String UserName, String Password, int accType) {

        AccountObj account = null;
        CustomerObj customer = getCustomerPassword(UserName, Password);
        if (customer != null) {
            ArrayList accountList = accountdb.getAccountByCustomerID(customer.getId());
            if (accountList != null) {
                if (accountList.size() > 0) {
                    for (int i = 0; i < accountList.size(); i++) {
                        AccountObj accountObj = (AccountObj) accountList.get(i);
                        if (accountObj.getType() == accType) {
                            return accountObj;
                        }
                    }
                }
            }
        }
        return account;
    }

    // success 1, fail 0
    public int updateCustomer(CustomerObj newC, int accountid) {

        ArrayList accountList = accountdb.getAccountByCustomerID(newC.getId());
        if (accountList != null) {
            if (accountList.size() > 0) {
                for (int i = 0; i < accountList.size(); i++) {
                    AccountObj accountObj = (AccountObj) accountList.get(i);
                    if (accountObj.getId() == accountid) {
                        return accountdb.updateCustomer(newC);
                    }
                }
            }
        }

        return 0;
    }

    public int updateCustomerPortfolio(String customerName, String portfolio) {
        return accountdb.updateCustomerPortfolio(customerName, portfolio);
    }

    public int updateAccountPortfolio(String accountName, String portfolio) {
        return accountdb.updateAccountPortfolio(accountName, portfolio);
    }

    public int addAccountTypeSubStatus(CustomerObj customer, String accountName, int accType, int accSub) {
        return accountdb.addAccountTypeSubStatus(customer, accountName, accType, accSub);
    }

    public ArrayList getAccounBestFundList(String UserName, String Password) {

        ArrayList accountList = null;
        CustomerObj customer = getCustomerPassword(UserName, Password);
        if (customer != null) {
            int len = 10;
            accountList = accountdb.getAccounBestFundList(len);
        }
        return accountList;
    }

    public ArrayList getAccountList(String UserName, String Password) {

        ArrayList accountList = null;
        CustomerObj customer = getCustomerPassword(UserName, Password);
        if (customer != null) {
            accountList = accountdb.getAccountByCustomerID(customer.getId());
        }
        return accountList;
    }

    public ArrayList getUserNamebyAccountID(int accountID) {
        return accountdb.getUserNamebyAccountID(accountID);
    }

    public ArrayList getAllOpenAccountID() {
        return accountdb.getAllOpenAccountID();
    }

    public AccountObj getAccountObjByAccountID(int accountID) {
        ArrayList accountList = accountdb.getAccountObjByAccountID(accountID);
        if (accountList != null) {
            if (accountList.size() > 0) {
                for (int i = 0; i < accountList.size(); i++) {
                    AccountObj accountObj = (AccountObj) accountList.get(i);
                    if (accountObj.getId() == accountID) {
                        return accountObj;
                    }
                }
            }
        }
        return null;
    }

    ////// do not expose interface
    ////// do not expose interface
    ////// do not expose interface high risk
    public int systemUpdateCustAllStatus(CustomerObj custObj) {
        return accountdb.updateCustAllStatus(custObj);
    }

//http://localhost:8080/cust/admin1/sys/cust/eddy/update?substatus=10&payment=0&balance=15
    public int updateAddCustStatusPaymentBalance(String UserName,
            int status, float payment, float balance) {

        CustomerObj customer = getCustomerPasswordNull(UserName);
        if (customer != null) {
            boolean upd = false;
            if (status != -9999) {
                customer.setStatus(status);
                upd = true;
            }
            if (payment != -9999) {
                payment += customer.getPayment();
                customer.setPayment(payment);
                upd = true;
            }
            if (balance != -9999) {
                balance += customer.getBalance();
                customer.setBalance(balance);
                upd = true;
            }
            if (upd == false) {
                return 1;
            }

            ///// System admin update
            return accountdb.updateCustAllStatus(customer);
        }
        return 0;
    }

    public int setCustStatusPaymentBalance(String UserName,
            int status, float payment, float balance) {

        CustomerObj customer = getCustomerPasswordNull(UserName);
        if (customer != null) {
            boolean upd = false;
            if (status != -9999) {
                customer.setStatus(status);
                upd = true;
            }
            if (payment != -9999) {
//                payment += customer.getPayment();
                customer.setPayment(payment);
                upd = true;
            }
            if (balance != -9999) {
//                balance += customer.getBalance();
                customer.setBalance(balance);
                upd = true;
            }
            if (upd == false) {
                return 1;
            }

            ///// System admin update
            return accountdb.updateCustAllStatus(customer);
        }
        return 0;
    }

    public int updateAccountStatusByAccountID(int accountID,
            int substatus, float investment, float balance, float servicefee) {

        AccountObj accountObj = getAccountObjByAccountID(accountID);
        if (accountObj == null) {
            return 0;
        }
        accountObj.setSubstatus(substatus);
        accountObj.setInvestment(investment);
        accountObj.setBalance(balance);
        accountObj.setServicefee(servicefee);
        return accountdb.updateAccountAllStatus(accountObj);

    }

    // return 0 for error
    public int updateAccountStockComment(TradingRuleObj trObj, AccData refnameData) {
        String nameSt = "";

        try {
            nameSt = new ObjectMapper().writeValueAsString(refnameData);
            nameSt = nameSt.replaceAll("\"", "#");
        } catch (JsonProcessingException ex) {
            return 0;
        }
        return accountdb.updateAccounStockComment(trObj, nameSt);
    }

    public int updateAccounStockPref(TradingRuleObj trObj, float perf) {
        return accountdb.updateAccounStockPref(trObj, perf);
    }

    ////////////////////////////////
    // Security - Do not allow outside ServiceAFweb to access this function
    ////////////////////////////////
    public AccountObj getAccountByAccountID(int accountID) {

        AccountObj account = null;

        ArrayList accountList = accountdb.getAccountByAccountID(accountID);
        if (accountList != null) {
            if (accountList.size() > 0) {
                for (int i = 0; i < accountList.size(); i++) {
                    AccountObj accountObj = (AccountObj) accountList.get(i);
                    if (accountObj.getId() == accountID) {
                        return accountObj;
                    }
                }
            }
        }

        return account;
    }

    public AccountObj getAccountByCustomerAccountID(String UserName, String Password, int accountID) {

        AccountObj account = null;
        CustomerObj customer = getCustomerPassword(UserName, Password);
        if (customer != null) {
            ArrayList accountList = accountdb.getAccountByCustomerID(customer.getId());
            if (accountList != null) {
                if (accountList.size() > 0) {
                    for (int i = 0; i < accountList.size(); i++) {
                        AccountObj accountObj = (AccountObj) accountList.get(i);
                        if (accountObj.getId() == accountID) {
                            return accountObj;
                        }
                    }
                }
            }
        }
        return account;
    }

    ////////////////////
    public ArrayList<BillingObj> getAccountingByNameType(String name, int type, long begin, long end) {
        return accountdb.getAccountingByNameType(name, type, begin, end, 0);
    }

    public ArrayList<BillingObj> getAccountingByType(int type, long begin, long end) {
        return accountdb.getAccountingByTypeTime(type, begin, end, 0);
    }

    public int removeAccountingByTypeId(int type, int id) {
        return accountdb.removeAccountingByTypeId(type, id);
    }

    public ArrayList<BillingObj> getAccountingByTypeId(int type, int id) {
        return accountdb.getAccountingByTypeId(type, id);
    }

    public ArrayList<BillingObj> getBillingByCustomerAccountID(String UserName, String Password, int accountID, int length) {

        CustomerObj customer = getCustomerPassword(UserName, Password);
        if (customer != null) {
            ArrayList accountList = accountdb.getAccountByCustomerID(customer.getId());
            if (accountList != null) {
                if (accountList.size() > 0) {
                    for (int i = 0; i < accountList.size(); i++) {
                        AccountObj accountObj = (AccountObj) accountList.get(i);
                        if (accountObj.getId() == accountID) {
                            return accountdb.getBillingObjByAccountIDType(accountID, ConstantKey.INT_BILLING, length);
                        }
                    }
                }
            }
        }
        return null;
    }

    //// do not expose to external interface
    public int removeSysAccountBillingByName(String name) {
        return accountdb.removeAccountBillingByName(name);
    }

    public int removeBillingByCustomerAccountID(String UserName, String Password, int accountID, int billID) {

        CustomerObj customer = getCustomerPassword(UserName, Password);
        if (customer != null) {
            ArrayList accountList = accountdb.getAccountByCustomerID(customer.getId());
            if (accountList != null) {
                if (accountList.size() > 0) {
                    for (int i = 0; i < accountList.size(); i++) {
                        AccountObj accountObj = (AccountObj) accountList.get(i);
                        if (accountObj.getId() == accountID) {
                            return accountdb.removeAccountBillingByID(accountID, billID);

                        }
                    }
                }
            }
        }
        return 0;
    }

    public ArrayList<CommObj> getCommPubByCustomer(String UserName, String Password, int length) {

        CustomerObj customer = getCustomerPassword(UserName, Password);
        if (customer != null) {
            if (customer.getUsername().equals(CKey.ADMIN_USERNAME)) {
                return accountdb.getComObjByType(ConstantKey.INT_TYPE_COM_PUB, length);
            }
        }
        return null;
    }

    public ArrayList<CommObj> getCommEmailByCustomerAccountID(String UserName, String Password, int accountID, int length) {

        CustomerObj customer = getCustomerPassword(UserName, Password);
        if (customer != null) {
            if (customer.getUsername().equals(CKey.ADMIN_USERNAME)) {
                return accountdb.getComObjByType(ConstantKey.INT_TYPE_COM_EMAIL, length);
            }
        }
        return null;
    }

    public ArrayList<CommObj> getCommSignalSplitByCustomerAccountID(String UserName, String Password, int accountID, int length) {
        CustomerObj customer = getCustomerPassword(UserName, Password);
        if (customer != null) {
            ArrayList accountList = accountdb.getAccountByCustomerID(customer.getId());
            if (accountList != null) {
                if (accountList.size() > 0) {
                    for (int i = 0; i < accountList.size(); i++) {
                        AccountObj accountObj = (AccountObj) accountList.get(i);
                        if (accountObj.getId() == accountID) {
                            ArrayList<CommObj> commMsgRet = accountdb.getComSignalSplitObjByAccountID(accountID, length);

                            return commMsgRet;
                        }
                    }
                }
            }
        }
        return null;
    }

    public int addCommByCustomerAccountID(String UserName, String Password, int accountID, String data) {

        CustomerObj customer = getCustomerPassword(UserName, Password);
        if (customer != null) {
            ArrayList accountList = accountdb.getAccountByCustomerID(customer.getId());
            if (accountList != null) {
                if (accountList.size() > 0) {
                    for (int i = 0; i < accountList.size(); i++) {
                        AccountObj accountObj = (AccountObj) accountList.get(i);
                        if (accountObj.getId() == accountID) {
                            return addAccountMessage(accountObj, ConstantKey.COM_ADD_CUST_MSG, data);
                        }
                    }
                }
            }
        }
        return 0;
    }

    public int removeAllCommByType(int type) {
        return accountdb.removeAllCommByType(type);
    }

    public int removeCommByTimebefore(long timeBefore, int type) {
        return accountdb.removeCommByTimebefore(timeBefore, type);
    }

    public int removeAccountCommByID(String UserName, String Password, int accountID, int id) {

        AccountObj account = null;
        CustomerObj customer = getCustomerPassword(UserName, Password);
        if (customer != null) {
            ArrayList accountList = accountdb.getAccountByCustomerID(customer.getId());
            if (accountList != null) {
                if (accountList.size() > 0) {
                    for (int i = 0; i < accountList.size(); i++) {
                        AccountObj accountObj = (AccountObj) accountList.get(i);
                        if (accountObj.getId() == accountID) {
                            return accountdb.removeCommByCommID(id);
                        }
                    }
                }
            }
        }
        return 0;
    }

    public int removeCommByCustomerAccountIDType(String UserName, String Password, int accountID, int type) {

        AccountObj account = null;
        CustomerObj customer = getCustomerPassword(UserName, Password);
        if (customer != null) {
            ArrayList accountList = accountdb.getAccountByCustomerID(customer.getId());
            if (accountList != null) {
                if (accountList.size() > 0) {
                    for (int i = 0; i < accountList.size(); i++) {
                        AccountObj accountObj = (AccountObj) accountList.get(i);
                        if (accountObj.getId() == accountID) {
                            return accountdb.removeCommSignalByAccountType(accountID, type);
                        }
                    }
                }
            }
        }
        return 0;
    }

    public int removeCommByType(String UserName, String Password, int type) {

        CustomerObj customer = getCustomerPassword(UserName, Password);
        if (customer != null) {
            if (customer.getType() == CustomerObj.INT_ADMIN_USER) {
                return accountdb.removeCommByType(type);
            }
        }
        return 0;
    }

    public int removeCommByName(String UserName, String Password, String name) {

        CustomerObj customer = getCustomerPassword(UserName, Password);
        if (customer != null) {
            if (customer.getType() == CustomerObj.INT_ADMIN_USER) {
                return accountdb.removeCommByName(name);
            }
        }
        return 0;
    }

    public int clearAccountStockTranByAccountID(AccountObj accountObj, int stockID, String trName) {
        TradingRuleObj tr = getAccountStockIDByTRStockID(accountObj.getId(), stockID, trName);
        if (tr == null) {
            return 0;
        }

        boolean TRlinkflag = false;
        if (TRlinkflag == true) {
            tr.setLinktradingruleid(ConstantKey.INT_TR_NN1);
        }

        if (accountObj.getType() == AccountObj.INT_ADMIN_ACCOUNT) {
            return accountdb.getAccountStockClrTranByAccountID(accountObj.getId() + "", stockID + "", tr);
        }
        if (tr.getTrname().toUpperCase().equals(trName.toUpperCase())) {
            // only TR_ACC allow for user account
            if (tr.getType() == ConstantKey.INT_TR_ACC) {
                return accountdb.getAccountStockClrTranByAccountID(accountObj.getId() + "", stockID + "", tr);
            }
        }
        return 0;
    }

    public ArrayList<PerformanceObj> getAccountStockPerfList(int accountID, int stockID, String trName, int length) {
        TradingRuleObj tr = getAccountStockIDByTRStockID(accountID, stockID, trName);
        if (tr == null) {
            return null;
        }
        return accountdb.getAccountStockPerfList(accountID + "", stockID + "", tr.getId() + "", length);
    }

    public ArrayList<TransationOrderObj> getAccountStockTransList(int accountID, int stockID, String trName, int length) {

        TradingRuleObj tr = getAccountStockIDByTRStockID(accountID, stockID, trName);
        if (tr == null) {
            return null;
        }
        return accountdb.getAccountStockTransList(accountID + "", stockID + "", tr.getId() + "", length);
    }

    public boolean checkTRListByStockID(String StockID) {
        return accountdb.checkTRListByStockID(StockID);
    }

    public ArrayList<TradingRuleObj> getAccountStockTRListByAccountID(int accountId, int stockId) {
        return accountdb.getAccountStockTRListByStockIDTRname(accountId + "", stockId + "", null, 0);
    }

    public TradingRuleObj getAccountStockIDByTRStockID(int accountID, int stockID, String trName) {
        // not sure why it does not work in Open shift but work local
        return accountdb.getAccountStockByTRStockID(accountID + "", stockID + "", trName);
    }

    public ArrayList getAllAccountStockNameListExceptionAdmin(int accountId) {
        return accountdb.getAllAccountStockNameListExceptionAdmin(accountId);

    }

    public ArrayList getAccountStockNameList(int accountId) {
        return accountdb.getAccountStockNameList(accountId);
    }

    public int updateAccountStockSignal(ArrayList<TradingRuleObj> TRList) {
        return accountdb.updateAccountStockSignal(TRList);
    }

    public int updateTransactionOrder(ArrayList transSQL) {
        try {
            return accountdb.updateTransactionOrder(transSQL);
        } catch (SQLException ex) {
            logger.info("> updateTransactionOrder exception " + ex.getMessage());
        }
        return 0;
    }

    public int addAccountStockId(AccountObj accountObj, int StockID, ArrayList TRList) {

        if (accountObj != null) {
            CustomerObj cust = getCustomerByAccount(accountObj);
            int cSubTypePlan = cust.getSubstatus();

            if (accountObj.getType() != AccountObj.INT_ADMIN_ACCOUNT) {
                ArrayList stockNameList = accountdb.getAccountStockNameList(accountObj.getId());
                if (stockNameList != null) {

                    if (cSubTypePlan == ConstantKey.INT_PP_BASIC) {
                        if (stockNameList.size() >= ConstantKey.INT_PP_BASIC_NUM) {
                            return AccountObj.MAX_ALLOW_STOCK_ERROR;
                        }
                    } else if (cSubTypePlan == ConstantKey.INT_PP_STANDARD) {
                        if (stockNameList.size() >= ConstantKey.INT_PP_STANDARD_NUM) {
                            return AccountObj.MAX_ALLOW_STOCK_ERROR;
                        }
                    } else if (cSubTypePlan == ConstantKey.INT_PP_PEMIUM) {
                        if (stockNameList.size() >= ConstantKey.INT_PP_PEMIUM_NUM) {
                            return AccountObj.MAX_ALLOW_STOCK_ERROR;
                        }
                    } else if (cSubTypePlan == ConstantKey.INT_PP_DELUXEX2) {
                        if (stockNameList.size() >= ConstantKey.INT_PP_DELUXEX2_NUM) {
                            return AccountObj.MAX_ALLOW_STOCK_ERROR;
                        }
                    } else if (cSubTypePlan == ConstantKey.INT_PP_API) {
                        if (cust.getUsername().equals(CKey.API_USERNAME)) {
                            // API use is unlimited
                            ;
                        } else {
                            if (stockNameList.size() >= ConstantKey.INT_PP_API_NUM) {
                                return AccountObj.MAX_ALLOW_STOCK_ERROR;
                            }
                        }
                    }
                }
            }
            int retAdd = 0;
            for (int i = 0; i < TRList.size(); i++) {
                TradingRuleObj tr = (TradingRuleObj) TRList.get(i);
                if (tr.getTrname().equals(ConstantKey.TR_ACC)) {
                    //default to MACD
//                    tr.setLinktradingruleid(ConstantKey.INT_TR_MACD);
//                    tr.setLinktradingruleid(ConstantKey.INT_TR_NN2);
                    tr.setLinktradingruleid(ConstantKey.INT_TR_NN1);
                }
                retAdd = accountdb.addAccountStock(accountObj.getId(), StockID, tr);
            }

            if (accountObj.getType() == AccountObj.INT_ADMIN_ACCOUNT) {
                // need to add the mirror TR for Rule 5 logic
                TradingRuleObj tr = new TradingRuleObj();
                tr.setTrname(ConstantKey.TR_NN91);
                tr.setType(ConstantKey.INT_TR_NN91);
                tr.setComment("");
                retAdd = accountdb.addAccountStock(accountObj.getId(), StockID, tr);
                tr = new TradingRuleObj();
                tr.setTrname(ConstantKey.TR_NN92);
                tr.setType(ConstantKey.INT_TR_NN92);
                tr.setComment("");
                retAdd = accountdb.addAccountStock(accountObj.getId(), StockID, tr);
                tr = new TradingRuleObj();
                tr.setTrname(ConstantKey.TR_NN93);
                tr.setType(ConstantKey.INT_TR_NN93);
                tr.setComment("");
                retAdd = accountdb.addAccountStock(accountObj.getId(), StockID, tr);
            }

            return retAdd; // successful

        }
        return 0;
    }

    public int removeAccountStock(AccountObj accountObj, int StockID) {
        if (accountObj != null) {
            ArrayList<TradingRuleObj> tradingRuleList = getAccountStockTRListByAccountID(accountObj.getId(), StockID);
            if (tradingRuleList != null) {
                for (int i = 0; i < tradingRuleList.size(); i++) {
                    TradingRuleObj tradingRuleObj = tradingRuleList.get(i);
                    accountdb.getAccountStockClrTranByAccountID(accountObj.getId() + "", StockID + "", tradingRuleObj);
                }
                return accountdb.removeAccountStock(accountObj.getId(), StockID);
            }
            return 1;
        }
        return 0;
    }

    ////////////////////////////transaction order
    public int AddTransactionOrder(AccountObj accountObj, AFstockObj stock, String trName, int tran, Calendar tranDate, boolean fromSystem) {

        Calendar daOffset0 = tranDate;
        Calendar daOffset = tranDate;
        try {
            if (stock.getAfstockInfo() == null) {
                return 0;
            }
            if (tranDate == null) {
                daOffset0 = TimeConvertion.getCurrentCalendar();
                daOffset = TimeConvertion.getCurrentCalendar();
            }
            //get the current transaction order to see the last transaction
            ArrayList<TransationOrderObj> tranOrderList = getAccountStockTransList(accountObj.getId(), stock.getId(), trName, 1);
            int currentTran = ConstantKey.S_NEUTRAL;
            if (tranOrderList != null) {
                if (tranOrderList.size() > 0) {
                    TransationOrderObj tranOrder = tranOrderList.get(0);
                    currentTran = tranOrder.getTrsignal();
                    if ((currentTran == ConstantKey.S_EXIT_LONG) || (currentTran == ConstantKey.S_EXIT_SHORT)) {
                        currentTran = ConstantKey.S_NEUTRAL;
                    }
                }
            }
            int tranSiganl0 = ConstantKey.S_NEUTRAL;
            int tranSiganl = ConstantKey.S_NEUTRAL;

            switch (tran) {
                case ConstantKey.S_LONG_BUY:
                    if (currentTran == ConstantKey.S_LONG_BUY) {
                        return 0;
                    }
                    if (currentTran == ConstantKey.S_SHORT_SELL) {
                        tranSiganl0 = ConstantKey.S_EXIT_SHORT;
                        tranSiganl = ConstantKey.S_LONG_BUY;
                    }
                    if (currentTran == ConstantKey.S_NEUTRAL) {
                        tranSiganl0 = ConstantKey.S_LONG_BUY;
                    }
                    break;
                case ConstantKey.S_SHORT_SELL:
                    if (currentTran == ConstantKey.S_SHORT_SELL) {
                        return 0;
                    }
                    if (currentTran == ConstantKey.S_LONG_BUY) {
                        tranSiganl0 = ConstantKey.S_EXIT_LONG;
                        tranSiganl = ConstantKey.S_SHORT_SELL;
                    }
                    if (currentTran == ConstantKey.S_NEUTRAL) {
                        tranSiganl0 = ConstantKey.S_SHORT_SELL;
                    }
                    break;
                case ConstantKey.S_NEUTRAL:
                    if (currentTran == ConstantKey.S_LONG_BUY) {
                        tranSiganl0 = ConstantKey.S_EXIT_LONG;
                        tranSiganl = ConstantKey.S_NEUTRAL;
                    }
                    if (currentTran == ConstantKey.S_SHORT_SELL) {
                        tranSiganl0 = ConstantKey.S_EXIT_SHORT;
                        tranSiganl = ConstantKey.S_NEUTRAL;
                    }
                    break;
            }
            TradingRuleObj trObj = null;
            ArrayList transSQL = new ArrayList();

            if (tranSiganl != ConstantKey.S_NEUTRAL) {
                long newDaL = TimeConvertion.addMiniSeconds(daOffset0.getTimeInMillis(), -10);
                daOffset0 = TimeConvertion.getCurrentCalendar(newDaL);
            }

            int retTrans = 1;
            if (tranSiganl0 != ConstantKey.S_NEUTRAL) {
                //process buysell
                if (trObj == null) {
                    trObj = getAccountStockIDByTRStockID(accountObj.getId(), stock.getId(), trName);
                }
                int signal = tranSiganl0;
                Calendar dateOffset = daOffset0;
                switch (signal) {
                    case ConstantKey.S_LONG_BUY:
                        retTrans = TransactionOrderLONG_BUY(trObj, stock, signal, dateOffset, transSQL);
                        break;
                    case ConstantKey.S_EXIT_LONG:
                        retTrans = TransactionOrderEXIT_LONG(trObj, stock, signal, dateOffset, transSQL);
                        break;
                    case ConstantKey.S_SHORT_SELL:
                        retTrans = TransactionOrderSHORT_SELL(trObj, stock, signal, dateOffset, transSQL);
                        break;
                    case ConstantKey.S_EXIT_SHORT:
                        retTrans = TransactionOrderEXIT_SHORT(trObj, stock, signal, dateOffset, transSQL);
                        break;
                    default:
                        break;
                }
            }

            if (retTrans == 0) {
                return 0;
            }
            retTrans = 1;
            if (tranSiganl != ConstantKey.S_NEUTRAL) {
                //process buysell
                if (trObj == null) {
                    trObj = getAccountStockIDByTRStockID(accountObj.getId(), stock.getId(), trName);
                }
                int signal = tranSiganl;
                Calendar dateOffset = daOffset;
                switch (signal) {
                    case ConstantKey.S_LONG_BUY:
                        retTrans = TransactionOrderLONG_BUY(trObj, stock, signal, dateOffset, transSQL);
                        break;
                    case ConstantKey.S_EXIT_LONG:
                        retTrans = TransactionOrderEXIT_LONG(trObj, stock, signal, dateOffset, transSQL);
                        break;
                    case ConstantKey.S_SHORT_SELL:
                        retTrans = TransactionOrderSHORT_SELL(trObj, stock, signal, dateOffset, transSQL);
                        break;
                    case ConstantKey.S_EXIT_SHORT:
                        retTrans = TransactionOrderEXIT_SHORT(trObj, stock, signal, dateOffset, transSQL);
                        break;
                    default:
                        break;
                }
            }
            if (retTrans == 0) {
                return 0;
            }
            if (fromSystem == false) {
                if (trObj.getType() == ConstantKey.INT_TR_ACC) {
                    if (trObj.getLinktradingruleid() != 0) {
                        logger.info("> transactionOrder not allow when linking other TS");
                        return 0;
                    }
                }
            }
            if (transSQL.size() > 0) {
                int ret = updateTransactionOrder(transSQL);
                return ret;
            }

        } catch (Exception e) {
            logger.info("> transactionOrder exception " + e.getMessage());
        }
        return 0;
    }

    private int TransactionOrderEXIT_SHORT(TradingRuleObj trObj, AFstockObj stock, int siganl, Calendar dateOffset, ArrayList transSQL) {
        float curPrice = stock.getAfstockInfo().getFclose();

        float originalPrice = trObj.getShortamount() / trObj.getShortshare();
        float deltaPrice = curPrice - originalPrice; //final price - original price
        deltaPrice = -deltaPrice; // negative for exit short
        float netPrice = originalPrice + deltaPrice;

        float amount = trObj.getShortshare() * netPrice;

        trObj.setBalance(trObj.getBalance() + amount);

        // add trading order
        TransationOrderObj trOrder = new TransationOrderObj();
        trOrder.setAccountid(trObj.getAccountid());
        trOrder.setAvgprice(curPrice);
        trOrder.setEntrydatedisplay(new java.sql.Date(dateOffset.getTimeInMillis()));
        trOrder.setEntrydatel(dateOffset.getTimeInMillis());
        trOrder.setShare(trObj.getShortshare());
        trOrder.setStatus(ConstantKey.OPEN);
        trOrder.setStockid(stock.getId());
        trOrder.setSymbol(stock.getSymbol());
        trOrder.setTradingruleid(trObj.getId());
        trOrder.setTrname(trObj.getTrname());
        trOrder.setTrsignal(siganl);  //EXIT_SHORT
        trOrder.setType(ConstantKey.INITIAL);
        trOrder.setComment("");
        String trOrderSql = AccountDB.SQLaddAccountStockTransaction(trOrder);
        transSQL.add(trOrderSql);
        // add trading order                                                

        trObj.setTrsignal(trOrder.getTrsignal());
        trObj.setUpdatedatedisplay(trOrder.getEntrydatedisplay());
        trObj.setUpdatedatel(trOrder.getEntrydatel());
        trObj.setShortshare(0);
        trObj.setShortamount(0);

        //update trObj
        String trSql = AccountDB.SQLUpdateAccountStockSignal(trObj);
        transSQL.add(trSql);
        //update trObj       

        return 1;
    }

    private int TransactionOrderSHORT_SELL(TradingRuleObj trObj, AFstockObj stock, int siganl, Calendar dateOffset, ArrayList transSQL) {
        float curPrice = stock.getAfstockInfo().getFclose();
        float shareTmp = CKey.TRADING_AMOUNT / curPrice;  //$6000
        shareTmp += 0.5;
        if (shareTmp == 0) {
            shareTmp = 1;
        }
        int shareInt = (int) shareTmp;
        float amount = curPrice * shareInt;

        if (trObj.getBalance() < amount) {
            trObj.setInvestment(trObj.getInvestment() + amount);

        } else {
            trObj.setBalance(trObj.getBalance() - amount);
        }

        // add trading order
        TransationOrderObj trOrder = new TransationOrderObj();
        trOrder.setAccountid(trObj.getAccountid());
        trOrder.setAvgprice(curPrice);
        trOrder.setEntrydatedisplay(new java.sql.Date(dateOffset.getTimeInMillis()));
        trOrder.setEntrydatel(dateOffset.getTimeInMillis());
        trOrder.setShare(shareInt);
        trOrder.setStatus(ConstantKey.OPEN);
        trOrder.setStockid(stock.getId());
        trOrder.setSymbol(stock.getSymbol());
        trOrder.setTradingruleid(trObj.getId());
        trOrder.setTrname(trObj.getTrname());
        trOrder.setTrsignal(siganl);  //SHORT_SELL
        trOrder.setType(ConstantKey.INITIAL);
        trOrder.setComment("");
        String trOrderSql = AccountDB.SQLaddAccountStockTransaction(trOrder);
        transSQL.add(trOrderSql);
        // add trading order                                                

        trObj.setTrsignal(trOrder.getTrsignal());
        trObj.setUpdatedatedisplay(trOrder.getEntrydatedisplay());
        trObj.setUpdatedatel(trOrder.getEntrydatel());
        trObj.setShortshare(shareInt);
        trObj.setShortamount(amount);
        //update trObj
        String trSql = AccountDB.SQLUpdateAccountStockSignal(trObj);
        transSQL.add(trSql);
        //update trObj       

        return 1;
    }

    private int TransactionOrderEXIT_LONG(TradingRuleObj trObj, AFstockObj stock, int siganl, Calendar dateOffset, ArrayList transSQL) {
        float curPrice = stock.getAfstockInfo().getFclose();
        float amount = curPrice * trObj.getLongshare();

        trObj.setBalance(trObj.getBalance() + amount);

        // add trading order
        TransationOrderObj trOrder = new TransationOrderObj();
        trOrder.setAccountid(trObj.getAccountid());
        trOrder.setAvgprice(curPrice);
        trOrder.setEntrydatedisplay(new java.sql.Date(dateOffset.getTimeInMillis()));
        trOrder.setEntrydatel(dateOffset.getTimeInMillis());
        trOrder.setShare(trObj.getLongshare());
        trOrder.setStatus(ConstantKey.OPEN);
        trOrder.setStockid(stock.getId());
        trOrder.setSymbol(stock.getSymbol());
        trOrder.setTradingruleid(trObj.getId());
        trOrder.setTrname(trObj.getTrname());
        trOrder.setTrsignal(siganl);  //EXIT_LONG
        trOrder.setType(ConstantKey.INITIAL);
        trOrder.setComment("");
        String trOrderSql = AccountDB.SQLaddAccountStockTransaction(trOrder);
        transSQL.add(trOrderSql);
        // add trading order                                                

        trObj.setTrsignal(trOrder.getTrsignal());
        trObj.setUpdatedatedisplay(trOrder.getEntrydatedisplay());
        trObj.setUpdatedatel(trOrder.getEntrydatel());
        trObj.setLongshare(0);
        trObj.setLongamount(0);
        //update trObj
        String trSql = AccountDB.SQLUpdateAccountStockSignal(trObj);
        transSQL.add(trSql);
        //update trObj       

        return 1;
    }

    private int TransactionOrderLONG_BUY(TradingRuleObj trObj, AFstockObj stock, int siganl, Calendar dateOffset, ArrayList transSQL) {
        float curPrice = stock.getAfstockInfo().getFclose();
        float shareTmp = CKey.TRADING_AMOUNT / curPrice;  //$6000
        shareTmp += 0.5;
        if (shareTmp == 0) {
            shareTmp = 1;
        }
        int shareInt = (int) shareTmp;
        float amount = curPrice * shareInt;

        if (trObj.getBalance() < amount) {
            trObj.setInvestment(trObj.getInvestment() + amount);

        } else {
            trObj.setBalance(trObj.getBalance() - amount);
        }
        // add trading order
        TransationOrderObj trOrder = new TransationOrderObj();
        trOrder.setAccountid(trObj.getAccountid());
        trOrder.setAvgprice(curPrice);
        trOrder.setEntrydatedisplay(new java.sql.Date(dateOffset.getTimeInMillis()));
        trOrder.setEntrydatel(dateOffset.getTimeInMillis());
        trOrder.setShare(shareInt);
        trOrder.setStatus(ConstantKey.OPEN);
        trOrder.setStockid(stock.getId());
        trOrder.setSymbol(stock.getSymbol());
        trOrder.setTradingruleid(trObj.getId());
        trOrder.setTrname(trObj.getTrname());
        trOrder.setTrsignal(siganl);  //LONG_BUY
        trOrder.setType(ConstantKey.INITIAL);
        trOrder.setComment("");
        String trOrderSql = AccountDB.SQLaddAccountStockTransaction(trOrder);
        transSQL.add(trOrderSql);
        // add trading order                                                

        trObj.setTrsignal(trOrder.getTrsignal());
        trObj.setUpdatedatedisplay(trOrder.getEntrydatedisplay());
        trObj.setUpdatedatel(trOrder.getEntrydatel());
        trObj.setLongshare(shareInt);
        trObj.setLongamount(amount);
        //update trObj
        String trSql = AccountDB.SQLUpdateAccountStockSignal(trObj);
        transSQL.add(trSql);
        //update trObj       

        return 1;
    }

    ///////
    public ArrayList<CommObj> getComObjByAccoutType(int accountID, int type, int length) {
        return accountdb.getComObjByAccountType(accountID, type, length);
    }

    public ArrayList<CommObj> getComObjByAccountName(int accountID, String name) {
        return accountdb.getComObjByName(accountID, name);
    }

    public ArrayList<CommObj> getComObjByAccountID(int accountID, int length) {
        return accountdb.getComObjByAccountID(accountID, length);
    }

    public ArrayList<CommObj> getComObjByCustName(int custID, String name) {
        return accountdb.getComObjByCustName(custID, name);
    }

    public ArrayList<CommObj> getComObjByCustID(int custID) {
        return accountdb.getComObjByCustID(custID);
    }

    public CommData getCommDataObj(CommObj commObj) {
        if (commObj.getType() == ConstantKey.INT_TYPE_COM_SPLIT) {
            String name = commObj.getData();
            if ((name != null) && (name.length() > 0)) {
                name = StringTag.replaceAll("#", "\"", name);
                try {
                    CommData commData = new ObjectMapper().readValue(name, CommData.class);
                    return commData;
                } catch (IOException ex) {
                }
            }
        }
        return null;
    }

    public CommObj getCommObjByID(int commID) {
        ArrayList<CommObj> commObjList = accountdb.getComObjByID(commID);
        if (commObjList != null) {
            if (commObjList.size() == 1) {
                CommObj commObj = commObjList.get(0);
                return commObj;
            }
        }
        return null;
    }

    public int addAccountCommMessage(AccountObj accountObj, String name, int type, CommData commDataObj) {
        if (accountObj == null) {
            return -1;
        }

        CommObj message = new CommObj();
        message.setCustomerid(accountObj.getCustomerid());
        message.setAccountid(accountObj.getId());
        message.setName(name);  //ConstantKey.COM_SPLIT
        message.setType(type);  // ConstantKey.INT_COM_SPLIT

        Calendar dateNow = TimeConvertion.getCurrentCalendar();
        long dateNowLong = dateNow.getTimeInMillis();
        message.setUpdatedatedisplay(new java.sql.Date(dateNowLong));
        message.setUpdatedatel(dateNowLong);
        String msg = "";
        try {
            msg = new ObjectMapper().writeValueAsString(commDataObj);
        } catch (JsonProcessingException ex) {
        }
        msg = StringTag.replaceAll("\"", "#", msg);
        msg = StringTag.replaceAll("'", "|", msg);
        message.setData(msg);
        return accountdb.insertAccountCommData(message);
    }

    public int addAccountEmailMessage(AccountObj accountObj, String name, String msg) {
        if (accountObj == null) {
            return -1;
        }

        CommObj message = new CommObj();
        message.setCustomerid(accountObj.getCustomerid());
        message.setAccountid(accountObj.getId());
        message.setName(name); //ConstantKey.COM_SIGNAL
        message.setType(ConstantKey.INT_TYPE_COM_EMAIL);

        Calendar dateNow = TimeConvertion.getCurrentCalendar();
        long dateNowLong = dateNow.getTimeInMillis();
        message.setUpdatedatedisplay(new java.sql.Date(dateNowLong));
        message.setUpdatedatel(dateNowLong);
        msg = StringTag.replaceAll("\"", "#", msg);
        msg = StringTag.replaceAll("'", "|", msg);
        message.setData(msg);
        return accountdb.insertAccountCommData(message);
    }

    public int addAccountPUBSUBMessage(AccountObj accountObj, String name, String msg) {
        if (accountObj == null) {
            return -1;
        }

        CommObj message = new CommObj();
        message.setCustomerid(accountObj.getCustomerid());
        message.setAccountid(accountObj.getId());
        message.setName(name);
        message.setType(ConstantKey.INT_TYPE_COM_PUB);

        Calendar dateNow = TimeConvertion.getCurrentCalendar();
        long dateNowLong = dateNow.getTimeInMillis();
        message.setUpdatedatedisplay(new java.sql.Date(dateNowLong));
        message.setUpdatedatel(dateNowLong);
        msg = StringTag.replaceAll("\"", "#", msg);
        msg = StringTag.replaceAll("'", "|", msg);
        message.setData(msg);
        return accountdb.insertAccountCommData(message);
    }

    public int addAccountMessage(AccountObj accountObj, String name, String msg) {
        if (accountObj == null) {
            return -1;
        }

        CommObj message = new CommObj();
        message.setCustomerid(accountObj.getCustomerid());
        message.setAccountid(accountObj.getId());
        message.setName(name); //ConstantKey.COM_SIGNAL
        message.setType(ConstantKey.INT_TYPE_COM_SIGNAL); //ConstantKey.INT_COM_SIGNAL

        Calendar dateNow = TimeConvertion.getCurrentCalendar();
        long dateNowLong = dateNow.getTimeInMillis();
        message.setUpdatedatedisplay(new java.sql.Date(dateNowLong));
        message.setUpdatedatel(dateNowLong);
        msg = StringTag.replaceAll("\"", "#", msg);
        msg = StringTag.replaceAll("'", "|", msg);

        message.setData(msg);
        return accountdb.insertAccountCommData(message);
    }

//    public int addCommByCustName(int custID, String name, String data) {
//        CommObj message = new CommObj();
//        message.setCustomerid(custID);
//        message.setAccountid(custID);
//        message.setName(name);
//        message.setType(ConstantKey.INT_COM_CFG);
//
//        Calendar dateNow = TimeConvertion.getCurrentCalendar();
//        long dateNowLong = dateNow.getTimeInMillis();
//        message.setUpdatedatedisplay(new java.sql.Date(dateNowLong));
//        message.setUpdatedatel(dateNowLong);
//        message.setData(data);
//        return accountdb.insertAccountCommData(message);
//    }
    public int updateAccountCommSubStatusById(CommObj newA) {
        return accountdb.updateAccountCommSubStatusById(newA);
    }

//    public int updateCommByCustNameById(CommObj message) {
//        Calendar dateNow = TimeConvertion.getCurrentCalendar();
//        long dateNowLong = dateNow.getTimeInMillis();
//        message.setUpdatedatedisplay(new java.sql.Date(dateNowLong));
//        message.setUpdatedatel(dateNowLong);
//        return accountdb.updateAccountCommDataById(message);
//    }
    public ArrayList<BillingObj> getBillingObjByName(int accountID, String name, int length) {
        return accountdb.getBillingObjByName(accountID, name, length);
    }

    public ArrayList<BillingObj> getBillingObjByAccountID(int accountID, int length) {
        return accountdb.getBillingObjByAccountIDType(accountID, ConstantKey.INT_BILLING, length);
    }

    public int addAccountBilling(String name, AccountObj accountObj, float payment, float balance, String data, long billcycle) {
        if (accountObj == null) {
            return -1;
        }

        BillingObj billObj = new BillingObj();
        billObj.setCustomerid(accountObj.getCustomerid());
        billObj.setAccountid(accountObj.getId());
        billObj.setName(name);
        billObj.setType(ConstantKey.INT_BILLING);
        billObj.setStatus(ConstantKey.INITIAL);

        billObj.setUpdatedatel(billcycle);
        billObj.setUpdatedatedisplay(new java.sql.Date(billcycle));

        billObj.setPayment(payment);
        billObj.setBalance(balance);
        data = StringTag.replaceAll("\"", "#", data);
        data = StringTag.replaceAll("'", "|", data);

        billObj.setData(data);
        return accountdb.insertAccountBillingData(billObj);
    }

    public int updateAccountBillingStatusPaymentData(int billingId, int status, float payment, float balance, String data) {
        ArrayList<BillingObj> billingObjList = accountdb.getBillingObjByBillingID(billingId);
        if (billingObjList == null) {
            return -1;
        }
        if (billingObjList.size() == 1) {
            BillingObj billingObj = billingObjList.get(0);
            billingObj.setPayment(payment);
            billingObj.setBalance(balance);
            billingObj.setStatus(status);
            data = StringTag.replaceAll("\"", "#", data);
            data = StringTag.replaceAll("'", "|", data);
            billingObj.setData(data);
            return accountdb.updateAccountBillingStatusPaymentData(billingObj);
        }
        logger.info("> updateAccountBillingData error more than one billing id=" + billingId);
        return -1;
    }

    public int updateAccountBillingStatus(int billingId, int status, int subStatus) {
        ArrayList<BillingObj> billingObjList = accountdb.getBillingObjByBillingID(billingId);
        if (billingObjList == null) {
            return -1;
        }
        if (billingObjList.size() == 1) {
            BillingObj billingObj = billingObjList.get(0);
            billingObj.setStatus(status);
            billingObj.setSubstatus(subStatus);
            return accountdb.updateAccountBillingStatus(billingObj);
        }
        logger.info("> updateAccountBillingData error more than one billing id=" + billingId);
        return -1;
    }

///////
    public int addAccountingEntry(String name, AccountObj accountObj, float debit, float credit, String data, long entryDatel) {
        if (accountObj == null) {
            return -1;
        }
        BillingObj billObj = new BillingObj();
        billObj.setCustomerid(accountObj.getCustomerid());
        billObj.setAccountid(accountObj.getId());
        billObj.setName(name);
        billObj.setType(ConstantKey.INT_ACC_TRAN);
        billObj.setStatus(ConstantKey.INITIAL);

        Calendar dateNow = TimeConvertion.getCurrentCalendar();
        long entrytime = dateNow.getTimeInMillis();
        if (entryDatel > 0) {
            entrytime = entryDatel;
        }
        billObj.setUpdatedatel(entrytime);
        billObj.setUpdatedatedisplay(new java.sql.Date(entrytime));

        billObj.setPayment(debit);
        billObj.setBalance(credit);

        data = "Amt debit:" + debit + " credit:" + credit + " " + data;
        data = StringTag.replaceAll("\"", "#", data);
        data = StringTag.replaceAll("'", "|", data);
        data = StringTag.replaceAll("\\n\\r", "", data);
        billObj.setData(data);
        return accountdb.insertAccountBillingData(billObj);
    }
    ///////////////
}
