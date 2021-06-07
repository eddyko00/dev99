package com.vpumlmodel.stock;

public class stock {

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

	private stockInfo[] stockInfo;
	private int id;
	private String symbol;
	private String stockname;
	private int status;
	private int substatus;
	private java.sql.Date updatedatedisplay;
	private long updatedatel;
	private int failedupdate;
	private float longterm;
	private float shortterm;
	private float direction;
        private String data;

    /**
     * @return the stockInfo
     */
    public stockInfo[] getStockInfo() {
        return stockInfo;
    }

    /**
     * @param stockInfo the stockInfo to set
     */
    public void setStockInfo(stockInfo[] stockInfo) {
        this.stockInfo = stockInfo;
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
     * @return the stockname
     */
    public String getStockname() {
        return stockname;
    }

    /**
     * @param stockname the stockname to set
     */
    public void setStockname(String stockname) {
        this.stockname = stockname;
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
     * @return the failedupdate
     */
    public int getFailedupdate() {
        return failedupdate;
    }

    /**
     * @param failedupdate the failedupdate to set
     */
    public void setFailedupdate(int failedupdate) {
        this.failedupdate = failedupdate;
    }

    /**
     * @return the longterm
     */
    public float getLongterm() {
        return longterm;
    }

    /**
     * @param longterm the longterm to set
     */
    public void setLongterm(float longterm) {
        this.longterm = longterm;
    }

    /**
     * @return the shortterm
     */
    public float getShortterm() {
        return shortterm;
    }

    /**
     * @param shortterm the shortterm to set
     */
    public void setShortterm(float shortterm) {
        this.shortterm = shortterm;
    }

    /**
     * @return the direction
     */
    public float getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(float direction) {
        this.direction = direction;
    }


}