package com.vpumlmodel.stock;

public class stockInfo {

	private stock stock;
	private int id;
	private java.sql.Date entrydatedisplay;
	private long entrydatel;
	private float fopen;
	private float fclose;
	private float high;
	private float low;
	private float volume;
	private float adjustclose;
	private int stockid;

    /**
     * @return the stock
     */
    public stock getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(stock stock) {
        this.stock = stock;
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
     * @return the fopen
     */
    public float getFopen() {
        return fopen;
    }

    /**
     * @param fopen the fopen to set
     */
    public void setFopen(float fopen) {
        this.fopen = fopen;
    }

    /**
     * @return the fclose
     */
    public float getFclose() {
        return fclose;
    }

    /**
     * @return the high
     */
    public float getHigh() {
        return high;
    }

    /**
     * @param high the high to set
     */
    public void setHigh(float high) {
        this.high = high;
    }

    /**
     * @return the low
     */
    public float getLow() {
        return low;
    }

    /**
     * @param low the low to set
     */
    public void setLow(float low) {
        this.low = low;
    }

    /**
     * @return the volume
     */
    public float getVolume() {
        return volume;
    }

    /**
     * @param volume the volume to set
     */
    public void setVolume(float volume) {
        this.volume = volume;
    }

    /**
     * @return the adjustclose
     */
    public float getAdjustclose() {
        return adjustclose;
    }

    /**
     * @param adjustclose the adjustclose to set
     */
    public void setAdjustclose(float adjustclose) {
        this.adjustclose = adjustclose;
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
     * @param fclose the fclose to set
     */
    public void setFclose(float fclose) {
        this.fclose = fclose;
    }

}