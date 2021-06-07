/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.model.account;

/**
 *
 * @author eddy
 */
public class PerformData {


    private int trsignal;
    private float close;
    private float share;

    private int numwin;
    private int numloss;
    private float maxwin;
    private float maxloss;
    private float avgwin;
    private float avgloss;
    private float ratioavgwinloss;
    private float totalwin;
    private float totalloss;
    private int holdtime;    
    private int minholdtime;
    private int maxholdtime;
    private java.sql.Date fromdate;


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
     * @return the close
     */
    public float getClose() {
        return close;
    }

    /**
     * @param close the close to set
     */
    public void setClose(float close) {
        this.close = close;
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
     * @return the numwin
     */
    public int getNumwin() {
        return numwin;
    }

    /**
     * @param numwin the numwin to set
     */
    public void setNumwin(int numwin) {
        this.numwin = numwin;
    }

    /**
     * @return the numloss
     */
    public int getNumloss() {
        return numloss;
    }

    /**
     * @param numloss the numloss to set
     */
    public void setNumloss(int numloss) {
        this.numloss = numloss;
    }

    /**
     * @return the maxwin
     */
    public float getMaxwin() {
        return maxwin;
    }

    /**
     * @param maxwin the maxwin to set
     */
    public void setMaxwin(float maxwin) {
        this.maxwin = maxwin;
    }

    /**
     * @return the maxloss
     */
    public float getMaxloss() {
        return maxloss;
    }

    /**
     * @param maxloss the maxloss to set
     */
    public void setMaxloss(float maxloss) {
        this.maxloss = maxloss;
    }

    /**
     * @return the avgwin
     */
    public float getAvgwin() {
        return avgwin;
    }

    /**
     * @param avgwin the avgwin to set
     */
    public void setAvgwin(float avgwin) {
        this.avgwin = avgwin;
    }

    /**
     * @return the avgloss
     */
    public float getAvgloss() {
        return avgloss;
    }

    /**
     * @param avgloss the avgloss to set
     */
    public void setAvgloss(float avgloss) {
        this.avgloss = avgloss;
    }

    /**
     * @return the ratioavgwinloss
     */
    public float getRatioavgwinloss() {
        return ratioavgwinloss;
    }

    /**
     * @param ratioavgwinloss the ratioavgwinloss to set
     */
    public void setRatioavgwinloss(float ratioavgwinloss) {
        this.ratioavgwinloss = ratioavgwinloss;
    }

    /**
     * @return the totalwin
     */
    public float getTotalwin() {
        return totalwin;
    }

    /**
     * @param totalwin the totalwin to set
     */
    public void setTotalwin(float totalwin) {
        this.totalwin = totalwin;
    }

    /**
     * @return the totalloss
     */
    public float getTotalloss() {
        return totalloss;
    }

    /**
     * @param totalloss the totalloss to set
     */
    public void setTotalloss(float totalloss) {
        this.totalloss = totalloss;
    }

    /**
     * @return the maxholdtime
     */
    public int getMaxholdtime() {
        return maxholdtime;
    }

    /**
     * @param maxholdtime the maxholdtime to set
     */
    public void setMaxholdtime(int maxholdtime) {
        this.maxholdtime = maxholdtime;
    }

    /**
     * @return the minholdtime
     */
    public int getMinholdtime() {
        return minholdtime;
    }

    /**
     * @param minholdtime the minholdtime to set
     */
    public void setMinholdtime(int minholdtime) {
        this.minholdtime = minholdtime;
    }

    /**
     * @return the holdtime
     */
    public int getHoldtime() {
        return holdtime;
    }

    /**
     * @param holdtime the holdtime to set
     */
    public void setHoldtime(int holdtime) {
        this.holdtime = holdtime;
    }

    /**
     * @return the fromdate
     */
    public java.sql.Date getFromdate() {
        return fromdate;
    }

    /**
     * @param fromdate the fromdate to set
     */
    public void setFromdate(java.sql.Date fromdate) {
        this.fromdate = fromdate;
    }

}