package com.vpumlmodel.account;

import com.vpumlmodel.afweb.*;

public class account {

    private customer customer;
    private tradingRule[] tradingrule;
    private int id;
    private String accountname;
    private int type;
    private int status;
    private int substatus;
    private java.sql.Date startdate;
    private java.sql.Date updatedatedisplay;
    private long updatedatel;
    private float investment;
    private float balance;
    private float servicefee;
    private String portfolio="";
    private int linkaccountid;
    private int customerid;

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
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
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
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the substatus
     */
    public int getSubstatus() {
        return substatus;
    }

    /**
     * @param substatus the substatus to set
     */
    public void setSubstatus(int substatus) {
        this.substatus = substatus;
    }

    /**
     * @return the startdate
     */
    public java.sql.Date getStartdate() {
        return startdate;
    }

    /**
     * @param startdate the startdate to set
     */
    public void setStartdate(java.sql.Date startdate) {
        this.startdate = startdate;
    }

    /**
     * @return the updatedatedisplay
     */
    public java.sql.Date getUpdatedatedisplay() {
        return updatedatedisplay;
    }

    /**
     * @param updatedatedisplay the updatedatedisplay to set
     */
    public void setUpdatedatedisplay(java.sql.Date updatedatedisplay) {
        this.updatedatedisplay = updatedatedisplay;
    }

    /**
     * @return the updatedatel
     */
    public long getUpdatedatel() {
        return updatedatel;
    }

    /**
     * @param updatedatel the updatedatel to set
     */
    public void setUpdatedatel(long updatedatel) {
        this.updatedatel = updatedatel;
    }

    /**
     * @return the investment
     */
    public float getInvestment() {
        return investment;
    }

    /**
     * @param investment the investment to set
     */
    public void setInvestment(float investment) {
        this.investment = investment;
    }

    /**
     * @return the balance
     */
    public float getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(float balance) {
        this.balance = balance;
    }

    /**
     * @return the servicefee
     */
    public float getServicefee() {
        return servicefee;
    }

    /**
     * @param servicefee the servicefee to set
     */
    public void setServicefee(float servicefee) {
        this.servicefee = servicefee;
    }

    /**
     * @return the linkaccountid
     */
    public int getLinkaccountid() {
        return linkaccountid;
    }

    /**
     * @param linkaccountid the linkaccountid to set
     */
    public void setLinkaccountid(int linkaccountid) {
        this.linkaccountid = linkaccountid;
    }

    /**
     * @return the customerid
     */
    public int getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid the customerid to set
     */
    public void setCustomerid(int customerid) {
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
