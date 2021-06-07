/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.service.db;

import com.vpumlmodel.account.account;
import com.vpumlmodel.account.transationOrder;

/**
 *
 * @author eddy
 */
public class TradingRuleRDB {

    /**
     * @return the perf
     */
    public String getPerf() {
        return perf;
    }

    /**
     * @param perf the perf to set
     */
    public void setPerf(String perf) {
        this.perf = perf;
    }

    private account account;
    private transationOrder[] transationorder;
    private String id;
    private String trname;
    private String type;
    private String status;
    private String substatus;
    private String trsignal;
    private String updatedatedisplay;
    private String updatedatel;
    private String investment;
    private String balance;
    private String longshare;
    private String longamount;
    private String shortshare;
    private String shortamount;
    private String perf;
    private String comment;
    private String linktradingruleid;
    private String accountid;
    private String stockid;
    private String symbol;

    /**
     * @return the account
     */
    public account getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(account account) {
        this.account = account;
    }

    /**
     * @return the transationorder
     */
    public transationOrder[] getTransationorder() {
        return transationorder;
    }

    /**
     * @param transationorder the transationorder to set
     */
    public void setTransationorder(transationOrder[] transationorder) {
        this.transationorder = transationorder;
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
     * @return the trname
     */
    public String getTrname() {
        return trname;
    }

    /**
     * @param trname the trname to set
     */
    public void setTrname(String trname) {
        this.trname = trname;
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
     * @return the trsignal
     */
    public String getTrsignal() {
        return trsignal;
    }

    /**
     * @param trsignal the trsignal to set
     */
    public void setTrsignal(String trsignal) {
        this.trsignal = trsignal;
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
     * @return the shortshare
     */
    public String getShortshare() {
        return shortshare;
    }

    /**
     * @param shortshare the shortshare to set
     */
    public void setShortshare(String shortshare) {
        this.shortshare = shortshare;
    }

    /**
     * @return the shortamount
     */
    public String getShortamount() {
        return shortamount;
    }

    /**
     * @param shortamount the shortamount to set
     */
    public void setShortamount(String shortamount) {
        this.shortamount = shortamount;
    }

    /**
     * @return the linktradingruleid
     */
    public String getLinktradingruleid() {
        return linktradingruleid;
    }

    /**
     * @param linktradingruleid the linktradingruleid to set
     */
    public void setLinktradingruleid(String linktradingruleid) {
        this.linktradingruleid = linktradingruleid;
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

    /**
     * @return the stockid
     */
    public String getStockid() {
        return stockid;
    }

    /**
     * @param stockid the stockid to set
     */
    public void setStockid(String stockid) {
        this.stockid = stockid;
    }

    /**
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
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
     * @return the longshare
     */
    public String getLongshare() {
        return longshare;
    }

    /**
     * @param longshare the longshare to set
     */
    public void setLongshare(String longshare) {
        this.longshare = longshare;
    }

    /**
     * @return the longamount
     */
    public String getLongamount() {
        return longamount;
    }

    /**
     * @param longamount the longamount to set
     */
    public void setLongamount(String longamount) {
        this.longamount = longamount;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
