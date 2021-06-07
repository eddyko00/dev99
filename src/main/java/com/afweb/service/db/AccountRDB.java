/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.service.db;

import com.vpumlmodel.account.tradingRule;
import com.vpumlmodel.afweb.customer;

/**
 *
 * @author eddy
 */
public class AccountRDB {


    private customer customer;
    private tradingRule[] tradingrule;
    private String id;
    private String accountname;
    private String type;
    private String status;
    private String substatus;
    private String startdate;
    private String updatedatedisplay;
    private String updatedatel;
    private String investment;
    private String balance;
    private String servicefee;
    private String portfolio;
    private String linkaccountid;
    private String customerid;

    /**
     * @return the customer
     */
    public customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(customer customer) {
        this.customer = customer;
    }

    /**
     * @return the tradingrule
     */
    public tradingRule[] getTradingrule() {
        return tradingrule;
    }

    /**
     * @param tradingrule the tradingrule to set
     */
    public void setTradingrule(tradingRule[] tradingrule) {
        this.tradingrule = tradingrule;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the accountname
     */
    public String getAccountname() {
        return accountname;
    }

    /**
     * @param accountname the accountname to set
     */
    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the substatus
     */
    public String getSubstatus() {
        return substatus;
    }

    /**
     * @param substatus the substatus to set
     */
    public void setSubstatus(String substatus) {
        this.substatus = substatus;
    }

    /**
     * @return the startdate
     */
    public String getStartdate() {
        return startdate;
    }

    /**
     * @param startdate the startdate to set
     */
    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    /**
     * @return the updatedatedisplay
     */
    public String getUpdatedatedisplay() {
        return updatedatedisplay;
    }

    /**
     * @param updatedatedisplay the updatedatedisplay to set
     */
    public void setUpdatedatedisplay(String updatedatedisplay) {
        this.updatedatedisplay = updatedatedisplay;
    }

    /**
     * @return the updatedatel
     */
    public String getUpdatedatel() {
        return updatedatel;
    }

    /**
     * @param updatedatel the updatedatel to set
     */
    public void setUpdatedatel(String updatedatel) {
        this.updatedatel = updatedatel;
    }

    /**
     * @return the investment
     */
    public String getInvestment() {
        return investment;
    }

    /**
     * @param investment the investment to set
     */
    public void setInvestment(String investment) {
        this.investment = investment;
    }

    /**
     * @return the balance
     */
    public String getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(String balance) {
        this.balance = balance;
    }

    /**
     * @return the servicefee
     */
    public String getServicefee() {
        return servicefee;
    }

    /**
     * @param servicefee the servicefee to set
     */
    public void setServicefee(String servicefee) {
        this.servicefee = servicefee;
    }

    /**
     * @return the linkaccountid
     */
    public String getLinkaccountid() {
        return linkaccountid;
    }

    /**
     * @param linkaccountid the linkaccountid to set
     */
    public void setLinkaccountid(String linkaccountid) {
        this.linkaccountid = linkaccountid;
    }

    /**
     * @return the customerid
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid the customerid to set
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    /**
     * @return the portfolio
     */
    public String getPortfolio() {
        return portfolio;
    }

    /**
     * @param portfolio the portfolio to set
     */
    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }
  
}
