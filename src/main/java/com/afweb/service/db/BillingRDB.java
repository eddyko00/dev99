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
public class BillingRDB {

    private String id;
    private String name;
    private String type;
    private String status;
    private String substatus;
    private String updatedatedisplay;
    private String updatedatel;
    private String payment;
    private String balance;
    private String data;
    private String accountid;
    private String customerid;

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the payment
     */
    public String getPayment() {
        return payment;
    }

    /**
     * @param payment the payment to set
     */
    public void setPayment(String payment) {
        this.payment = payment;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the accountid
     */
    public String getAccountid() {
        return accountid;
    }

    /**
     * @param accountid the accountid to set
     */
    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

}
