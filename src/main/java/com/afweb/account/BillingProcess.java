/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.account;

import com.afweb.model.*;
import com.afweb.model.account.*;
import com.afweb.service.*;
import com.afweb.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import java.util.logging.Logger;

/**
 *
 * @author eddyko
 */
public class BillingProcess {

    public static final int NO_PAYMENT_1 = 55;
    public static final int NO_PAYMENT_2 = 56;

    protected static Logger logger = Logger.getLogger("BillingProcess");
    private static ArrayList custProcessNameArray = new ArrayList();

    private ArrayList UpdateStockNNprocessNameArray(ServiceAFweb serviceAFWeb) {
        if (custProcessNameArray != null && custProcessNameArray.size() > 0) {
            return custProcessNameArray;
        }
        ArrayList custNameArray = serviceAFWeb.getAccountImp().getCustomerNList(0);
        if (custNameArray != null) {
            custProcessNameArray = custNameArray;
        }
        return custProcessNameArray;
    }

    public void processUserBillingAll(ServiceAFweb serviceAFWeb) {
        ServiceAFweb.lastfun = "processUserBillingAll";
        logger.info("> updateUserBillingAll ");

        UpdateStockNNprocessNameArray(serviceAFWeb);
        if (custProcessNameArray == null) {
            return;
        }
        if (custProcessNameArray.size() == 0) {
            return;
        }

//        String printName = "";
//        for (int i = 0; i < custProcessNameArray.size(); i++) {
//            printName += custProcessNameArray.get(i) + ",";
//        }
//        logger.info("processUserBillingAll " + printName);
        String LockName = null;
        Calendar dateNow = TimeConvertion.getCurrentCalendar();
        long lockDateValue = dateNow.getTimeInMillis();

        LockName = "BP_" + CKey.AF_SYSTEM; // + ServiceAFweb.getServerObj().getServerName();
//        LockName = LockName.toUpperCase().replace(CKey.WEB_SRV.toUpperCase(), "W");
        long lockReturn = serviceAFWeb.setLockNameProcess(LockName, ConstantKey.NN_LOCKTYPE, lockDateValue, ServiceAFweb.getServerObj().getSrvProjName() + "_ProcessTrainNeuralNet");
        boolean testing = false;
        if (ServiceAFweb.mydebugtestflag == true) {
            lockReturn = 1;
        }
        if (lockReturn > 0) {
            long LastServUpdateTimer = System.currentTimeMillis();
            long lockDate5Min = TimeConvertion.addMinutes(LastServUpdateTimer, 15); // add 3 minutes

            while (true) {
                long currentTime = System.currentTimeMillis();
                if (testing == true) {
                    currentTime = 0;
                }
                if (lockDate5Min < currentTime) {
//                    logger.info("ProcessTrainNeuralNet exit after 15 minutes");
                    break;
                }

                if (custProcessNameArray.size() == 0) {
                    break;
                }
                String custName = (String) custProcessNameArray.get(0);
                CustomerObj customer = serviceAFWeb.getCustomerObjByName(custName);
                if (customer != null) {
                    if ((customer.getType() == CustomerObj.INT_ADMIN_USER)) {
                        //                            || (customer.getType() == CustomerObj.INT_FUND_USER)) {
                        ;
                    } else {
                        try {
                            this.updateUserBilling(serviceAFWeb, customer);
                        } catch (Exception ex) {
                            logger.info("> updateUserBillingAll Exception " + ex.getMessage());
                        }
                    }

                }
                custProcessNameArray.remove(0);

            }  // end for loop
            serviceAFWeb.removeNameLock(LockName, ConstantKey.NN_LOCKTYPE);
//            logger.info("ProcessTrainNeuralNet " + LockName + " unlock LockName");
        }
//        logger.info("> updateUserBillingAll ... done");
    }
////////////////////////////////////////////////////

    public static boolean isSystemAccount(CustomerObj customer) {
        boolean byPassPayment = false;
        if ((customer.getType() == CustomerObj.INT_ADMIN_USER)) {
            byPassPayment = true;
        }
        if (customer.getUsername().equals(CKey.FUND_MANAGER_USERNAME)) {
            byPassPayment = true;
        } else if (customer.getUsername().equals(CKey.INDEXFUND_MANAGER_USERNAME)) {
            byPassPayment = true;
        } else if (customer.getUsername().equals(CKey.G_USERNAME)) {
            byPassPayment = true;
        } else if (customer.getUsername().equals(CKey.API_USERNAME)) {
            byPassPayment = true;
        }
        return byPassPayment;
    }

    public int updateUserBilling(ServiceAFweb serviceAFWeb, CustomerObj customer) {
        ServiceAFweb.lastfun = "updateUserBilling";

        Timestamp currentDate = TimeConvertion.getCurrentTimeStamp();
        Date currDate = new java.sql.Date(currentDate.getTime());
        if (customer == null) {
            return 0;
        }

        AccountObj account = serviceAFWeb.getAccountImp().getAccountByType(customer.getUsername(), null, AccountObj.INT_TRADING_ACCOUNT);
        // get last bill
        ArrayList<BillingObj> billingObjList = serviceAFWeb.getAccountImp().getBillingByCustomerAccountID(customer.getUsername(), null, account.getId(), 2);
        if (billingObjList == null) {
            // create first bill 
            createUserBilling(serviceAFWeb, customer, account, null);
            return 0;
        }
        if (billingObjList.size() == 0) {
            // create first bill 
            createUserBilling(serviceAFWeb, customer, account, null);
            return 0;
        }
        boolean firstBill = false;
        if (billingObjList.size() == 1) {
            firstBill = true;
        }
        BillingObj billing = billingObjList.get(0);

        // check if current bill cycle expire
        if (currDate.getTime() < billing.getUpdatedatel()) {
            return 0;
        }

        int status = billing.getStatus();

        float userBalance = customer.getBalance();
        float fPayment = customer.getPayment();

        boolean createBillFlag = true;
        boolean sendMsg = false;
        String msg = "";

        String custN = customer.getEmail();
        if ((custN == null) || (custN.length() == 0)) {
            custN = "customer";
        }
        custN = customer.getFirstname() + "_" + custN;

        if (status == ConstantKey.INITIAL) {
            boolean byPassPayment = BillingProcess.isSystemAccount(customer);

            if (byPassPayment == true) {
                userBalance = fPayment;
            }
            BillData billData = null;
            float credit = 0;
            String billingDataSt = billing.getData();
            try {
                if ((billingDataSt != null) && (billingDataSt.length() > 0)) {
                    billingDataSt = billingDataSt.replaceAll("#", "\"");
                    billData = new ObjectMapper().readValue(billingDataSt, BillData.class);
                    credit = billData.getCredit();
                }
            } catch (Exception ex) {
            }

            if (userBalance >= fPayment) {
                //the remaining goes to the next invoice.
                userBalance = userBalance - fPayment;
                customer.setBalance(userBalance);
                customer.setPayment(0);

                // transaction
                int result = serviceAFWeb.systemCustStatusPaymentBalance(customer.getUsername(), null, customer.getPayment() + "", customer.getBalance() + "");

                billing.setStatus(ConstantKey.COMPLETED);

                billing.setBalance(fPayment);
                result = serviceAFWeb.getAccountImp().updateAccountBillingStatusPaymentData(billing.getId(), billing.getStatus(), billing.getPayment(), billing.getBalance(), billing.getData());
                // transaction
                // send email disable
                NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
                String currency = formatter.format(fPayment);
                msg = "The " + custN + " account bill id (" + billing.getId() + ") payment " + currency + " is completed! Thank you.";
                sendMsg = true;
                logger.info("Billing***Completed user " + custN + ", billing id " + billing.getId());

                //////// reset feature list to clear delfund
                processFeat(serviceAFWeb, customer);

            } else {
//                Date entryDate = billing.getUpdatedatedisplay();
                long billcycleDate = billing.getUpdatedatel();
                long dateWeek = TimeConvertion.nextWeek(billcycleDate);

                if (firstBill == true) {
                    dateWeek = TimeConvertion.nextWeek(dateWeek);
                }
                int subStatus = billing.getSubstatus();
                if (subStatus == NO_PAYMENT_1) {
                    if (currDate.getTime() > dateWeek) {
                        if (customer.getStatus() != ConstantKey.DISABLE) {
                            if (subStatus != NO_PAYMENT_2) {
//                            if (fPayment < 2) {
//                                //ignore if payment less than 4 dollor
//                                billing.setSubstatus(NO_PAYMENT_2);
//                                serviceAFWeb.getAccountImp().updateAccountBillingStatus(billing.getId(), billing.getStatus(), billing.getSubstatus());
//                                return 1;
//                            }
                                billing.setSubstatus(NO_PAYMENT_2);

                                customer.setStatus(ConstantKey.DISABLE);
                                int result = serviceAFWeb.systemCustStatusPaymentBalance(customer.getUsername(), customer.getStatus() + "", null, null);
                                result = serviceAFWeb.getAccountImp().updateAccountBillingStatus(billing.getId(), billing.getStatus(), billing.getSubstatus());

                                // send email disable
                                msg = "The " + custN + " account had been disabled due to outstanding payment! Thank you for using IIS.";
                                sendMsg = true;
                                logger.info("Billing***Disable user " + custN + ", billing id " + billing.getId());
                            }
                        }
                    }
                }
                if (currDate.getTime() > billcycleDate) {
                    if ((subStatus == NO_PAYMENT_1) || (subStatus == NO_PAYMENT_2)) {
                        ;
                    } else {
                        billing.setSubstatus(NO_PAYMENT_1);
                        int result = serviceAFWeb.getAccountImp().updateAccountBillingStatus(billing.getId(), billing.getStatus(), billing.getSubstatus());

                        // send email reminder
                        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
                        String currency = formatter.format(fPayment);
                        msg = "The " + custN + " account  bill (" + billing.getId() + ") has past due " + currency + " amount! Please submit the payment now to iisweb88@gmail.com.";
                        sendMsg = true;
                        logger.info("Billing***PastDue user " + custN + ", billing id " + billing.getId());
                    }
                }

            }
            createBillFlag = false;
        }
//        if (status == ConstantKey.COMPLETED) {
//            // check for next bill
//            createUserBilling(serviceAFWeb, customer, billing);
//        }
        // check for next bill

        if (sendMsg == true) {
            String tzid = "America/New_York"; //EDT
            TimeZone tz = TimeZone.getTimeZone(tzid);
            java.sql.Date d = new java.sql.Date(TimeConvertion.currentTimeMillis());
//                                DateFormat format = new SimpleDateFormat("M/dd/yyyy hh:mm a z");
            DateFormat format = new SimpleDateFormat(" hh:mm a");
            format.setTimeZone(tz);
            String ESTtime = format.format(d);

            String msgSt = ESTtime + " " + msg;

            serviceAFWeb.getAccountImp().addAccountMessage(account, ConstantKey.COM_BILLMSG, msg);
            AccountObj accountAdminObj = serviceAFWeb.getAdminObjFromCache();
            serviceAFWeb.getAccountImp().addAccountMessage(accountAdminObj, ConstantKey.COM_BILLMSG, msg);

            // send email
            DateFormat formatD = new SimpleDateFormat("M/dd/yyyy hh:mm a");
            formatD.setTimeZone(tz);
            String ESTdateD = formatD.format(d);
            String msgD = ESTdateD + " " + msg;
            serviceAFWeb.getAccountImp().addAccountEmailMessage(account, ConstantKey.COM_BILLMSG, msgD);
        }
//        
        if (createBillFlag == true) {
            int retCreatebill = createUserBilling(serviceAFWeb, customer, account, billing);
        }
        return 1;
    }

    public int processFeat(ServiceAFweb serviceAFWeb, CustomerObj customer) {
        ServiceAFweb.lastfun = "processFeat";

        String portfolio = customer.getPortfolio();
        CustPort custPortfilio = null;
        try {
            if ((portfolio != null) && (portfolio.length() > 0)) {
                portfolio = portfolio.replaceAll("#", "\"");
                custPortfilio = new ObjectMapper().readValue(portfolio, CustPort.class);
            }
        } catch (Exception ex) {
        }
        if (custPortfilio == null) {
            return 0;
        }
        ArrayList<String> featL = custPortfilio.getFeatL();
        if (featL == null) {
            return 0;
        }
        ArrayList<String> featNewL = this.testfeatL(featL);
        custPortfilio.setFeatL(featNewL);
        try {

            String portfStr = new ObjectMapper().writeValueAsString(custPortfilio);
            serviceAFWeb.getAccountImp().updateCustomerPortfolio(customer.getUsername(), portfStr);
            return 1;
        } catch (Exception ex) {
        }
        return 1;
    }

    private ArrayList<String> testfeatL(ArrayList<String> featOrgL) {
        ArrayList<String> featL = new ArrayList();

        if (featL == null) {
            return featL;
        }
        featL.addAll(featOrgL);
        ArrayList<String> delfeatL = new ArrayList();
        for (int i = 0; i < featL.size(); i++) {
            String feat = featL.get(i);
            if (feat.indexOf("delfund") != -1) {
                String defFundIdSt = feat.replace("delfund", "");
                try {
                    int defFundId = Integer.parseInt(defFundIdSt);
                    delfeatL.add("delfund" + defFundId);
                    delfeatL.add("fund" + defFundId);
                } catch (Exception e) {
                }
            }
        }
        if (delfeatL.size() == 0) {
            return featL;
        }
        for (int i = 0; i < delfeatL.size(); i++) {
            String delfeat = featL.get(i);
            for (int j = 0; j < featL.size(); j++) {
                String feat = featL.get(j);
                if (delfeat.equals(feat)) {
                    featL.remove(j);
                }
            }
        }
        return featL;
    }

    public int createUserBilling(ServiceAFweb serviceAFWeb, CustomerObj customer, AccountObj account, BillingObj billing) {
        ServiceAFweb.lastfun = "createUserBilling";
        if (customer.getType() == CustomerObj.INT_ADMIN_USER) {
            return 1;
        }
        Date startDate = customer.getStartdate();
        long billCycleDate = startDate.getTime();

        if (billing != null) {
            long lastBillDate = billing.getUpdatedatel();
            billCycleDate = TimeConvertion.addMonths(lastBillDate, 1);
        }
        BillData billData = new BillData();

        Timestamp cDate = TimeConvertion.getCurrentTimeStamp();
        Date curDate = new java.sql.Date(cDate.getTime());
        long date5day = TimeConvertion.addDays(curDate.getTime(), 5);

        if (date5day > billCycleDate) {

            String custN = customer.getEmail();
            if ((custN == null) || (custN.length() == 0)) {
                custN = "customer";
            }
            custN = customer.getFirstname() + "_" + custN;

            String portfolio = customer.getPortfolio();
            String portfStr;
            CustPort custPortfilio = new CustPort();
            try {
                if ((portfolio != null) && (portfolio.length() > 0)) {
                    portfolio = portfolio.replaceAll("#", "\"");
                    custPortfilio = new ObjectMapper().readValue(portfolio, CustPort.class);
                } else {
                    portfStr = new ObjectMapper().writeValueAsString(custPortfilio);
                    serviceAFWeb.getAccountImp().updateCustomerPortfolio(customer.getUsername(), portfStr);

                }
            } catch (Exception ex) {
                logger.info("createUserBilling exception");
            }

            if (custPortfilio.getnPlan() != -1) {

                int plan = custPortfilio.getnPlan();

//                int substatus = account.getSubstatus();
//                float investment = account.getInvestment();
//                float balance = account.getBalance();
//                float servicefee = account.getServicefee();
//                serviceAFWeb.getAccountImp().updateAccountStatusByAccountID(account.getId(), substatus, investment, balance, servicefee);
                // update the plan
                customer.setSubstatus(plan);
                serviceAFWeb.getAccountImp().updateCustStatusSubStatus(customer, customer.getStatus(), customer.getSubstatus());

            }

            float payment = customer.getPayment();
            float prevOwning = payment;

            int subType = customer.getSubstatus();  // price plan
            float fInvoice = 0;
            switch (subType) {
                case ConstantKey.INT_PP_BASIC:
                    billData.setFeat(ConstantKey.PP_BASIC);
                    fInvoice = ConstantKey.INT_PP_BASIC_PRICE;
                    break;
                case ConstantKey.INT_PP_STANDARD:
                    billData.setFeat(ConstantKey.PP_STANDARD);
                    fInvoice = ConstantKey.INT_PP_STANDARD_PRICE;
                    break;
                case ConstantKey.INT_PP_PEMIUM:
                    billData.setFeat(ConstantKey.PP_PEMIUM);
                    fInvoice = ConstantKey.INT_PP_PEMIUM_PRICE;
                    break;
                case ConstantKey.INT_PP_DELUXEX2:
                    billData.setFeat(ConstantKey.PP_DELUXEX2);
                    fInvoice = ConstantKey.INT_PP_DELUXEX2_PRICE;
                    break;
                case ConstantKey.INT_PP_API:
                    billData.setFeat(ConstantKey.PP_API);
                    fInvoice = ConstantKey.INT_PP_API_PRICE;
                    break;
            }
            billData.setCurPaym(fInvoice);  ///// for the plan invoice
            customer.setPayment(fInvoice);
            int result = 0;

            //Add feature
            float featureInvoice = 0;
            ArrayList<String> featL = custPortfilio.getFeatL();
            ArrayList<String> featNewL = this.testfeatL(featL);
            int featCnt = featNewL.size();
            if (featCnt > 0) {
                featureInvoice = (featCnt * FUND30_FeaturePrice);
                billData.setService(featureInvoice);
                fInvoice += featureInvoice;
            }
            if (custPortfilio.getServ() > 0) {
                billData.setService(billData.getService() + custPortfilio.getServ());
                fInvoice += custPortfilio.getServ();
            }
            if (custPortfilio.getCred() > 0) {
                billData.setCredit(billData.getCredit() + custPortfilio.getCred());
            }
            try {
                custPortfilio.setnPlan(-1);
                custPortfilio.setServ(0);
                custPortfilio.setCred(0);
                portfStr = new ObjectMapper().writeValueAsString(custPortfilio);
                serviceAFWeb.getAccountImp().updateCustomerPortfolio(customer.getUsername(), portfStr);
            } catch (JsonProcessingException ex) {
            }

            // first bill alreay add the payment
            // but the next bill need to add prev owning
            boolean firstBill = false;
            if (billing != null) {
                billData.setPrevOwn(prevOwning);
                payment = fInvoice + prevOwning - billData.getCredit();
                customer.setPayment(payment);
                result = serviceAFWeb.systemCustStatusPaymentBalance(customer.getUsername(), null, customer.getPayment() + "", null);
            } else {
                // first bill
                firstBill = true;
                if (payment == 0) {
                    payment = fInvoice - billData.getCredit();
                    customer.setPayment(payment);
                    result = serviceAFWeb.systemCustStatusPaymentBalance(customer.getUsername(), null, customer.getPayment() + "", null);
                }
            }

            String data = "";
            String nameSt = "";
            try {

                nameSt = new ObjectMapper().writeValueAsString(billData);
                nameSt = nameSt.replaceAll("\"", "#");
                data = nameSt;
            } catch (JsonProcessingException ex) {
            }

            float balance = 0;
            result = serviceAFWeb.getAccountImp().addAccountBilling(customer.getUsername(), account, payment, balance, data, billCycleDate);

            String tzid = "America/New_York"; //EDT
            TimeZone tz = TimeZone.getTimeZone(tzid);
            java.sql.Date d = new java.sql.Date(billCycleDate);
            DateFormat format = new SimpleDateFormat("M/dd/yyyy");
            format.setTimeZone(tz);
            String billcycleESTtime = format.format(d);

            NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
            String currency = formatter.format(payment);
            String msg = "The " + custN + " account bill on " + billcycleESTtime + " invoice for the amount " + currency + " is ready! Please submit the payment now.";

            tzid = "America/New_York"; //EDT
            tz = TimeZone.getTimeZone(tzid);
            d = new java.sql.Date(TimeConvertion.currentTimeMillis());
//                                DateFormat format = new SimpleDateFormat("M/dd/yyyy hh:mm a z");
            format = new SimpleDateFormat(" hh:mm a");
            format.setTimeZone(tz);
            String ESTtime = format.format(d);

            String msgSt = ESTtime + " " + msg;
//            String compassMsgSt = ServiceAFweb.compress(msgSt);
            serviceAFWeb.getAccountImp().addAccountMessage(account, ConstantKey.COM_BILLMSG, msgSt);
            AccountObj accountAdminObj = serviceAFWeb.getAdminObjFromCache();
            serviceAFWeb.getAccountImp().addAccountMessage(accountAdminObj, ConstantKey.COM_BILLMSG, msgSt);

            // send email
            DateFormat formatD = new SimpleDateFormat("M/dd/yyyy hh:mm a");
            formatD.setTimeZone(tz);
            String ESTdateD = formatD.format(d);
            String msgD = ESTdateD + " " + msg;
            msgD += "<p>Payment is done through e.transfer from your bank to the iisweb payment email address (iisweb88@gmail.com)."
                    + "<br>The e.transfer question is the 'iisweb-' plus user login email (e.g. iisweb-email@domain.com) and the answer is the user login email.";

//            compassMsgSt = ServiceAFweb.compress(msgD);
            serviceAFWeb.getAccountImp().addAccountEmailMessage(account, ConstantKey.COM_BILLMSG, msgD);

            logger.info("Billing***create user " + custN + ", billing cycle " + billcycleESTtime + ", payment=" + payment);

            /// Adding credit to other mutural fund user
            /// Adding credit to other mutural fund user            
            for (int i = 0; i < featNewL.size(); i++) {
                String feat = featNewL.get(i);
                if (feat.indexOf("fund") != -1) {
                    String FundIdSt = feat.replace("fund", "");
                    try {
                        int FundId = Integer.parseInt(FundIdSt);
                        AccountObj accFund = serviceAFWeb.getAccountImp().getAccountByAccountID(FundId);
                        CustomerObj custFund = serviceAFWeb.getCustomerByAccoutObj(accFund);
                        portfolio = custFund.getPortfolio();
                        custPortfilio = new CustPort();
                        if ((portfolio != null) && (portfolio.length() > 0)) {
                            portfolio = portfolio.replaceAll("#", "\"");
                            custPortfilio = new ObjectMapper().readValue(portfolio, CustPort.class);
                        } else {
                            portfStr = new ObjectMapper().writeValueAsString(custPortfilio);
                            serviceAFWeb.getAccountImp().updateCustomerPortfolio(custFund.getUsername(), portfStr);
                        }
                        //////update credit to the fund mgr
                        float featureFundP = FUND30_FeaturePrice / 2;
                        custPortfilio.setCred(custPortfilio.getCred() + featureFundP);
                        portfStr = new ObjectMapper().writeValueAsString(custPortfilio);
                        serviceAFWeb.getAccountImp().updateCustomerPortfolio(custFund.getUsername(), portfStr);

                    } catch (Exception e) {
                    }
                }
            }

            if (customer.getType() == CustomerObj.INT_GUEST_USER) {
                if (firstBill == false) {
                    this.processMonthEvent(serviceAFWeb);

                }
            }
            return result;
        }
        return 0;
    }

    public void processMonthEvent(ServiceAFweb serviceAFWeb) {
        // update multal fund every month

    }

    public static float FUND30_FeaturePrice = 30;

    public int updateFundFeat(ServiceAFweb serviceAFWeb, CustomerObj customer, AccountObj accFund) {
        ServiceAFweb.lastfun = "updateFundFeat";
        logger.info(">updateFundFeat " + accFund.getAccountname());

        // should be transaction problem
        // Need to refesh customer from DB to get the portfolio
        // Need to refesh customer from DB to get the portfolio        
        String name = customer.getUsername();
        customer = serviceAFWeb.getCustomerObjByName(name);
        String portfolio = customer.getPortfolio();
        String portfStr;
        CustPort custPortfilio = new CustPort();
        try {
            if ((portfolio != null) && (portfolio.length() > 0)) {
                portfolio = portfolio.replaceAll("#", "\"");
                custPortfilio = new ObjectMapper().readValue(portfolio, CustPort.class);
            } else {
                portfStr = new ObjectMapper().writeValueAsString(custPortfilio);
                serviceAFWeb.getAccountImp().updateCustomerPortfolio(customer.getUsername(), portfStr);
            }

            /// adding feature
            //proate to bill cycle
            //proate to bill cycle    
            Date startDate = customer.getStartdate();
            long billCycleDate = startDate.getTime();

            AccountObj account = serviceAFWeb.getAccountImp().getAccountByType(customer.getUsername(), null, AccountObj.INT_TRADING_ACCOUNT);
            // get last bill
            ArrayList<BillingObj> billingObjList = serviceAFWeb.getAccountImp().getBillingByCustomerAccountID(customer.getUsername(), null, account.getId(), 2);
            boolean firstBill = false;
            if (billingObjList != null) {
                if (billingObjList.size() > 0) {
                    BillingObj billing = billingObjList.get(0);
                    billCycleDate = billing.getUpdatedatel();
                }
            }

            billCycleDate = TimeConvertion.endOfDayInMillis(billCycleDate);

            long NextbillCycleDate = TimeConvertion.addMonths(billCycleDate, 1);

            long currDate = TimeConvertion.getCurrentTimeStamp().getTime();
            currDate = TimeConvertion.endOfDayInMillis(currDate);

            int prorateDay = 0;
            if (billCycleDate > currDate) {
                long prorateDayl = (billCycleDate - currDate) / TimeConvertion.DAY_TIME_TICK;
                prorateDay = (int) prorateDayl;
            } else {
                long prorateDayl = (NextbillCycleDate - currDate) / TimeConvertion.DAY_TIME_TICK;
                prorateDay = (int) prorateDayl;
            }

            float featureP = 0;
            float featureFundP = 0;

            long nextmonthl = (NextbillCycleDate - billCycleDate) / TimeConvertion.DAY_TIME_TICK;
            int nextMonDay = (int) nextmonthl;

            float prorate = prorateDay * FUND30_FeaturePrice / nextMonDay;
            int iprorate = (int) (prorate * 100);
            float tempfeatureP = iprorate;
            tempfeatureP /= 100;
            if (tempfeatureP > 2) {
                featureP = tempfeatureP;

                iprorate = iprorate / 2;
                featureFundP = iprorate;
                featureFundP /= 100;
                featureFundP = featureP - featureFundP;
            }

            // update bill payment for the customer
            custPortfilio.setServ(custPortfilio.getServ() + featureP);
            portfStr = new ObjectMapper().writeValueAsString(custPortfilio);
            serviceAFWeb.getAccountImp().updateCustomerPortfolio(customer.getUsername(), portfStr);

            CustomerObj custFund = serviceAFWeb.getCustomerByAccoutObj(accFund);
            portfolio = custFund.getPortfolio();
            custPortfilio = new CustPort();
            if ((portfolio != null) && (portfolio.length() > 0)) {
                portfolio = portfolio.replaceAll("#", "\"");
                custPortfilio = new ObjectMapper().readValue(portfolio, CustPort.class);
            } else {
                portfStr = new ObjectMapper().writeValueAsString(custPortfilio);
                serviceAFWeb.getAccountImp().updateCustomerPortfolio(custFund.getUsername(), portfStr);
            }
            //////update credit to the fund mgr
            custPortfilio.setCred(custPortfilio.getCred() + featureFundP);
            portfStr = new ObjectMapper().writeValueAsString(custPortfilio);
            serviceAFWeb.getAccountImp().updateCustomerPortfolio(custFund.getUsername(), portfStr);

        } catch (Exception ex) {
            logger.info("createUserBilling exception");
        }

        return 0;
    }
/////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////

    public static String SYS_CASH = "CASH_ACC";  // checking cash account
    public static int INT_SYS_CASH = 10;
    public static String SYS_TOTAL = "TOTAL";
    public static int INT_SYS_TOTAL = 11;
    public static String SYS_REVENUE = "R_REVENUE";
    public static int INT_SYS_REVENUE = 20;
    public static String R_USER_PAYMENT = "R_USER_PAYMENT";
    public static int INT_R_USER_PAYMENT = 22;
    public static String SYS_EXPENSE = "E_EXPENSE";
    public static int INT_SYS_EXPENSE = 40;
    public static String E_COST_SERVICE = "E_COST_SERVICE";
    public static int INT_E_COST_SERVICE = 42;
    public static String E_USER_WITHDRAWAL = "E_USER_WITHDRAWAL";
    public static int INT_E_USER_WITHDRAWAL = 44;

    public static String E_DEPRECATE = "E_DEPRECATE";
    public static int INT_E_DEPRECATE = 46;
    public static String E_TAX = "E_TAX";
    public static int INT_E_TAX = 48;

    public static String allEntry[] = {SYS_CASH, SYS_REVENUE, SYS_EXPENSE, R_USER_PAYMENT,
        E_COST_SERVICE, E_USER_WITHDRAWAL, E_DEPRECATE, E_TAX};
    public static int allEntryId[] = {INT_SYS_CASH, INT_SYS_REVENUE, INT_SYS_EXPENSE, INT_R_USER_PAYMENT,
        INT_E_COST_SERVICE, INT_E_USER_WITHDRAWAL, INT_E_DEPRECATE, INT_E_TAX};

    public int insertAccountTAX(ServiceAFweb serviceAFWeb, CustomerObj customer, String name, float expense, String data) {
        AccountObj accountAdminObj = serviceAFWeb.getAdminObjFromCache();
        if (name.length() == 0) {
            name = E_TAX;
        }

        int result = serviceAFWeb.getAccountImp().addAccountingEntry(name, accountAdminObj, expense, 0, data, 0);
        result = serviceAFWeb.getAccountImp().addAccountingEntry(SYS_CASH, accountAdminObj, expense, 0, data, 0);

        return result;

    }
    
    public int insertAccountExpense(ServiceAFweb serviceAFWeb, CustomerObj customer, String name, float expense, float rate, String data) {
        AccountObj accountAdminObj = serviceAFWeb.getAdminObjFromCache();
        if (name.length() == 0) {
            name = SYS_EXPENSE;
        }
//        billObj.setPayment(debit);
//        billObj.setBalance(credit);

        float accExpense = expense;
        if (rate > 0) {
            accExpense = expense * rate / 100;
        }
        String ExSt = "Expense: " + expense + " for rate " + rate + "%. ";
        data = ExSt + data;

        int result = serviceAFWeb.getAccountImp().addAccountingEntry(name, accountAdminObj, accExpense, 0, data, 0);
        result = serviceAFWeb.getAccountImp().addAccountingEntry(SYS_CASH, accountAdminObj, expense, 0, data, 0);

        return result;

    }

    public int insertAccountingExCostofGS(ServiceAFweb serviceAFWeb, CustomerObj customer, String name, float expense, int curYear, String data) {

        AccountObj accountAdminObj = serviceAFWeb.getAdminObjFromCache();
        if (name.length() == 0) {
            name = SYS_EXPENSE;
        }
//        billObj.setPayment(debit);
//        billObj.setBalance(credit);

        // Cach is one time only
        int result = serviceAFWeb.getAccountImp().addAccountingEntry(SYS_CASH, accountAdminObj, expense, 0, data, 0);

        /////////////////////////
        // need to create multiple entry for cur year and next year
        ////////////////////////        
        if (curYear == 0) {
            result = serviceAFWeb.getAccountImp().addAccountingEntry(name, accountAdminObj, expense, 0, data, 0);
            return result;
        }
        Calendar dateNow = TimeConvertion.getCurrentCalendar();
        long entrytime = dateNow.getTimeInMillis();
        float remainExpense = expense;
        Date dateSt = new Date(entrytime);
        String DepSt = "CostofGoodSold: " + expense + " for 1 year from " + dateSt.toString() + ". ";
        data = DepSt + data;

        // 0- 11
        int monNum = TimeConvertion.getMonthNum(entrytime);
        monNum += 1; // start 1 - 12
        int remMonNum = (12 - monNum) + 1;
        float curExpense = (expense / 12) * remMonNum;
        curExpense = (float) (Math.round(curExpense * 100.0) / 100.0);
        if (curExpense > 0) {
            result = serviceAFWeb.getAccountImp().addAccountingEntry(name, accountAdminObj, curExpense, 0, data, entrytime);
        }
        remainExpense = expense - curExpense;
        if (remainExpense > 0) {
            entrytime = TimeConvertion.addMonths(entrytime, 12);
            result = serviceAFWeb.getAccountImp().addAccountingEntry(name, accountAdminObj, remainExpense, 0, data, entrytime);
        }
        return result;
    }

    public int insertAccountExDeprecation(ServiceAFweb serviceAFWeb, CustomerObj customer, String name, float expense, float rate, String data) {

        AccountObj accountAdminObj = serviceAFWeb.getAdminObjFromCache();
        if (name.length() == 0) {
            name = SYS_EXPENSE;
        }
//        billObj.setPayment(debit);
//        billObj.setBalance(credit);

        // Cach is one time only
        int result = serviceAFWeb.getAccountImp().addAccountingEntry(SYS_CASH, accountAdminObj, expense, 0, data, 0);
        /////////////////////////
        // need to create multiple entry for deplication
        ////////////////////////        
        if (rate == 0) {
            rate = 100;
        }
        if (rate == 100) {
            result = serviceAFWeb.getAccountImp().addAccountingEntry(name, accountAdminObj, expense, 0, data, 0);
            return result;
        }

        int yearCnt = (int) (100 / rate);

        /// Max 5 year
        if (yearCnt > 5) {
            yearCnt = 5;
        }
        Calendar dateNow = TimeConvertion.getCurrentCalendar();
        long entrytime = dateNow.getTimeInMillis();
        float remainExpense = expense;

        Date dateSt = new Date(entrytime);
        String DepSt = "Deprecation: " + expense + " for " + yearCnt + "Yr from " + dateSt.toString() + ". ";
        data = DepSt + data;

        for (int i = 0; i < yearCnt; i++) {
            float exDeplication = expense * rate / 100;
            exDeplication = (float) (Math.round(exDeplication * 100.0) / 100.0);
            if (i > 0) {
                entrytime = TimeConvertion.addMonths(entrytime, 12);
            }

            /// first year will prorated to the end of year
            if (i == 0) {
                int monNum = TimeConvertion.getMonthNum(entrytime);
                monNum += 1; // start 1 - 12
                int remMonNum = (12 - monNum) + 1;
                float curExpense = (exDeplication / 12) * remMonNum;
                curExpense = (float) (Math.round(curExpense * 100.0) / 100.0);
                exDeplication = curExpense;
            }

            //////// last year will claim the rest of expense
            if (i == (yearCnt - 1)) {
                exDeplication = remainExpense;
            }
            if (exDeplication > 0) {
                result = serviceAFWeb.getAccountImp().addAccountingEntry(name, accountAdminObj, exDeplication, 0, data, entrytime);
            }
            remainExpense = remainExpense - exDeplication;
            if (remainExpense < 0) {
                break;
            }
        }

        return result;

    }

    public int insertAccountRevenue(ServiceAFweb serviceAFWeb, CustomerObj customer, String name, float revenue, String data) {
//        if (customer != null) {
//            boolean byPassPayment = BillingProcess.isSystemAccount(customer);
//            if (byPassPayment == true) {
//                return 0;
//            }
//        }
        AccountObj accountAdminObj = serviceAFWeb.getAdminObjFromCache();
        if (name.length() == 0) {
            name = SYS_REVENUE;
        }
//        billObj.setPayment(debit);
//        billObj.setBalance(credit);        
        int result = serviceAFWeb.getAccountImp().addAccountingEntry(name, accountAdminObj, 0, revenue, data, 0);
        result = serviceAFWeb.getAccountImp().addAccountingEntry(SYS_CASH, accountAdminObj, 0, revenue, data, 0);

        return result;

    }

    public int removeAccountingEntryById(ServiceAFweb serviceAFWeb, int id) {
        return serviceAFWeb.getAccountImp().removeAccountingByTypeId(ConstantKey.INT_ACC_TRAN, id);
    }

    public AccEntryObj getAccountingEntryById(ServiceAFweb serviceAFWeb, int id) {
        ArrayList<BillingObj> billingObjList = serviceAFWeb.getAccountImp().getAccountingByTypeId(ConstantKey.INT_ACC_TRAN, id);

        if (billingObjList == null) {
            return null;
        }
        if (billingObjList.size() > 0) {
            BillingObj accTran = billingObjList.get(0);
            AccEntryObj accEntry = new AccEntryObj();
            accEntry.setId(accTran.getId());
            String dateSt = accTran.getUpdatedatedisplay().toString();
            accEntry.setDateSt(dateSt);
            accEntry.setName(accTran.getName());
            //        billObj.setPayment(debit);
            //        billObj.setBalance(credit);
            accEntry.setDebit(accTran.getPayment());
            accEntry.setCredit(accTran.getBalance());
            accEntry.setComment(accTran.getData());

            return accEntry;
        }
        return null;
    }

    public AccReportObj getAccountReportYearByName(ServiceAFweb serviceAFWeb, String name, int year) {
        int lastYear = 0;
        if (year != 0) {
            lastYear = year * 12;
        }
        AccReportObj reportObj = new AccReportObj();

        // begin 2021 01 01  (updatedatel)  end 2021 12 31
        long BeginingYear = DateUtil.getFirstDayCurrentYear();
        long EndingYear = TimeConvertion.addMonths(BeginingYear, 12);

        if (lastYear != 0) {
            BeginingYear = TimeConvertion.addMonths(BeginingYear, lastYear);
            EndingYear = TimeConvertion.addMonths(EndingYear, lastYear);
        }

        EndingYear = TimeConvertion.addDays(EndingYear, -1);

        reportObj.setBeginl(BeginingYear);
        reportObj.setBegindisplay(new java.sql.Date(BeginingYear));
        reportObj.setEndl(EndingYear);
        reportObj.setEnddisplay(new java.sql.Date(EndingYear));

        // begin 2021 01 01  (updatedatel)  end 2021 12 31
        ArrayList<BillingObj> billingObjList = serviceAFWeb.getAccountImp().getAccountingByNameType(name, ConstantKey.INT_ACC_TRAN, BeginingYear, EndingYear);
        if (billingObjList == null) {
            return reportObj;
        }

        ArrayList accTotalEntryBal = new ArrayList();
        for (int i = 0; i < billingObjList.size(); i++) {
            BillingObj accTran = billingObjList.get(i);
            AccEntryObj accEntry = new AccEntryObj();
            accEntry.setId(accTran.getId());
            String dateSt = accTran.getUpdatedatedisplay().toString();
            accEntry.setDateSt(dateSt);
            accEntry.setName(accTran.getName());
            //        billObj.setPayment(debit);
            //        billObj.setBalance(credit);
            accEntry.setDebit(accTran.getPayment());
            accEntry.setCredit(accTran.getBalance());
            accEntry.setComment(accTran.getData());
            accTotalEntryBal.add(accEntry);
        }
        reportObj.setAccEntryBal(accTotalEntryBal);
        return reportObj;
    }

    public AccReportObj getAccountReportYear(ServiceAFweb serviceAFWeb, int year) {
        int lastYear = 0;
        if (year != 0) {
            lastYear = year * 12;
        }

        AccReportObj reportObj = new AccReportObj();

        // begin 2021 01 01  (updatedatel)  end 2021 12 31
        long BeginingYear = DateUtil.getFirstDayCurrentYear();
        long EndingYear = TimeConvertion.addMonths(BeginingYear, 12);

        if (lastYear != 0) {
            BeginingYear = TimeConvertion.addMonths(BeginingYear, lastYear);
            EndingYear = TimeConvertion.addMonths(EndingYear, lastYear);
        }

        EndingYear = TimeConvertion.addDays(EndingYear, -1);

        reportObj.setBeginl(BeginingYear);
        reportObj.setBegindisplay(new java.sql.Date(BeginingYear));
        reportObj.setEndl(EndingYear);
        reportObj.setEnddisplay(new java.sql.Date(EndingYear));

        // begin 2021 01 01  (updatedatel)  end 2021 12 31
        ArrayList<BillingObj> billingObjList = serviceAFWeb.getAccountImp().getAccountingByType(ConstantKey.INT_ACC_TRAN, BeginingYear, EndingYear);
        if (billingObjList == null) {
            billingObjList = new ArrayList();
        }
        ArrayList<AccEntryObj> accTotalEntryBal = new ArrayList();

        Date curDate = new java.sql.Date(TimeConvertion.currentTimeMillis());
        String curDateSt = curDate.toString();

        for (int i = 0; i < allEntry.length; i++) {
            AccEntryObj accEntry = new AccEntryObj();
            accEntry.setId(allEntryId[i]);
            accEntry.setDateSt(curDateSt);
            accEntry.setName(allEntry[i]);
            accTotalEntryBal.add(accEntry);
        }
        AccEntryObj accTotalEntry = new AccEntryObj();
        accTotalEntry.setId(INT_SYS_TOTAL);
        accTotalEntry.setDateSt(curDateSt);
        accTotalEntry.setName(SYS_TOTAL);

        for (int i = 0; i < billingObjList.size(); i++) {
            BillingObj accTran = billingObjList.get(i);

            for (int j = 0; j < accTotalEntryBal.size(); j++) {
                AccEntryObj accEntryT = accTotalEntryBal.get(j);
                if (accEntryT.getName().equals(accTran.getName())) {
                    //        billObj.setPayment(debit);
                    //        billObj.setBalance(credit);
                    accEntryT.setDebit(accEntryT.getDebit() + accTran.getPayment());
                    accEntryT.setCredit(accEntryT.getCredit() + accTran.getBalance());

                    if (accEntryT.getName().equals(SYS_CASH)) {
                        continue;
                    }
                    accTotalEntry.setDebit(accTotalEntry.getDebit() + accTran.getPayment());
                    accTotalEntry.setCredit(accTotalEntry.getCredit() + accTran.getBalance());

                }
            }
        }
        accTotalEntryBal.add(accTotalEntry);
        reportObj.setAccTotalEntryBal(accTotalEntryBal);

        return reportObj;
    }
    //////////////////////////////
}
