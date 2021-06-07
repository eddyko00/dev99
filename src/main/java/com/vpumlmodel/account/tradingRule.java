package com.vpumlmodel.account;

public class tradingRule {

    /**
     * @return the perf
     */
    public float getPerf() {
        return perf;
    }

    /**
     * @param perf the perf to set
     */
    public void setPerf(float perf) {
        this.perf = perf;
    }

    private account account;
    private transationOrder[] transationorder;
    private int id;
    private String trname;
    private int type;
    private int status;
    private int substatus;
    private int trsignal;
    private java.sql.Date updatedatedisplay;
    private long updatedatel;
    private float investment;
    private float balance;
    private float longamount;
    private float longshare;
    private float shortshare;
    private float shortamount;
    private float perf;
    private String comment;
    private int linktradingruleid;
    private int accountid;
    private int stockid;

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
     * @return the trsignal
     */
    public int getTrsignal() {
        return trsignal;
    }

    /**
     * @param trsignal the trsignal to set
     */
    public void setTrsignal(int trsignal) {
        this.trsignal = trsignal;
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
     * @return the shortshare
     */
    public float getShortshare() {
        return shortshare;
    }

    /**
     * @param shortshare the shortshare to set
     */
    public void setShortshare(float shortshare) {
        this.shortshare = shortshare;
    }

    /**
     * @return the shortamount
     */
    public float getShortamount() {
        return shortamount;
    }

    /**
     * @param shortamount the shortamount to set
     */
    public void setShortamount(float shortamount) {
        this.shortamount = shortamount;
    }

    /**
     * @return the linktradingruleid
     */
    public int getLinktradingruleid() {
        return linktradingruleid;
    }

    /**
     * @param linktradingruleid the linktradingruleid to set
     */
    public void setLinktradingruleid(int linktradingruleid) {
        this.linktradingruleid = linktradingruleid;
    }

    /**
     * @return the accountid
     */
    public int getAccountid() {
        return accountid;
    }

    /**
     * @param accountid the accountid to set
     */
    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    /**
     * @return the stockid
     */
    public int getStockid() {
        return stockid;
    }

    /**
     * @param stockid the stockid to set
     */
    public void setStockid(int stockid) {
        this.stockid = stockid;
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
     * @return the longamount
     */
    public float getLongamount() {
        return longamount;
    }

    /**
     * @param longamount the longamount to set
     */
    public void setLongamount(float longamount) {
        this.longamount = longamount;
    }

    /**
     * @return the longshare
     */
    public float getLongshare() {
        return longshare;
    }

    /**
     * @param longshare the longshare to set
     */
    public void setLongshare(float longshare) {
        this.longshare = longshare;
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
