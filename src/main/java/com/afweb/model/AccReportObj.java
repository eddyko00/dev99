/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.model;

import java.util.ArrayList;

/**
 *
 * @author eddyko
 */
public class AccReportObj {

    /**
     * @return the accTotalEntryBal
     */
    public ArrayList getAccTotalEntryBal() {
        return accTotalEntryBal;
    }

    /**
     * @param accTotalEntryBal the accTotalEntryBal to set
     */
    public void setAccTotalEntryBal(ArrayList accTotalEntryBal) {
        this.accTotalEntryBal = accTotalEntryBal;
    }

    /**
     * @return the begindisplay
     */
    public java.sql.Date getBegindisplay() {
        return begindisplay;
    }

    /**
     * @param begindisplay the begindisplay to set
     */
    public void setBegindisplay(java.sql.Date begindisplay) {
        this.begindisplay = begindisplay;
    }

    /**
     * @return the beginl
     */
    public long getBeginl() {
        return beginl;
    }

    /**
     * @param beginl the beginl to set
     */
    public void setBeginl(long beginl) {
        this.beginl = beginl;
    }

    /**
     * @return the enddisplay
     */
    public java.sql.Date getEnddisplay() {
        return enddisplay;
    }

    /**
     * @param enddisplay the enddisplay to set
     */
    public void setEnddisplay(java.sql.Date enddisplay) {
        this.enddisplay = enddisplay;
    }

    /**
     * @return the endl
     */
    public long getEndl() {
        return endl;
    }

    /**
     * @param endl the endl to set
     */
    public void setEndl(long endl) {
        this.endl = endl;
    }


    /**
     * @return the accEntryBal
     */
    public ArrayList getAccEntryBal() {
        return accEntryBal;
    }

    /**
     * @param accEntryBal the accEntryBal to set
     */
    public void setAccEntryBal(ArrayList accEntryBal) {
        this.accEntryBal = accEntryBal;
    }

    private java.sql.Date begindisplay;
    private long beginl;
    private java.sql.Date enddisplay;
    private long endl;

    
    private ArrayList accTotalEntryBal = new ArrayList();
    private ArrayList accEntryBal = new ArrayList();
}
