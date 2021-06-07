package com.vpumlmodel.account;

public class transationOrder {

	private tradingRule tradingrule;
	private int id;
	private String trname;
	private int trsignal;
	private int status;
	private int type;
	private java.sql.Date entrydatedisplay;
	private long entrydatel;
	private float share;
	private float avgprice;
	private String symbol;
        private String comment;
	private int stockid;
	private int accountid;
	private int tradingruleid;

    /**
     * @return the tradingrule
     */
    public tradingRule getTradingrule() {
        return tradingrule;
    }

    /**
     * @param tradingrule the tradingrule to set
     */
    public void setTradingrule(tradingRule tradingrule) {
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
     * @return the entrydatedisplay
     */
    public java.sql.Date getEntrydatedisplay() {
        return entrydatedisplay;
    }

    /**
     * @param entrydatedisplay the entrydatedisplay to set
     */
    public void setEntrydatedisplay(java.sql.Date entrydatedisplay) {
        this.entrydatedisplay = entrydatedisplay;
    }

    /**
     * @return the entrydatel
     */
    public long getEntrydatel() {
        return entrydatel;
    }

    /**
     * @param entrydatel the entrydatel to set
     */
    public void setEntrydatel(long entrydatel) {
        this.entrydatel = entrydatel;
    }

    /**
     * @return the share
     */
    public float getShare() {
        return share;
    }

    /**
     * @param share the share to set
     */
    public void setShare(float share) {
        this.share = share;
    }

    /**
     * @return the avgprice
     */
    public float getAvgprice() {
        return avgprice;
    }

    /**
     * @param avgprice the avgprice to set
     */
    public void setAvgprice(float avgprice) {
        this.avgprice = avgprice;
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
     * @return the tradingruleid
     */
    public int getTradingruleid() {
        return tradingruleid;
    }

    /**
     * @param tradingruleid the tradingruleid to set
     */
    public void setTradingruleid(int tradingruleid) {
        this.tradingruleid = tradingruleid;
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