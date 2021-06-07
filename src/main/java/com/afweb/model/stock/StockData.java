/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.model.stock;

/**
 *
 * @author eddyko
 */
public class StockData {

    /**
     * @return the pCl
     */
    public float getpCl() {
        return pCl;
    }

    /**
     * @param pCl the pCl to set
     */
    public void setpCl(float pCl) {
        this.pCl = pCl;
    }

    /**
     * @return the chDir
     */
    public int getChDir() {
        return chDir;
    }

    /**
     * @param chDir the chDir to set
     */
    public void setChDir(int chDir) {
        this.chDir = chDir;
    }

    /**
     * @return the upDn
     */
    public int getUpDn() {
        return upDn;
    }

    /**
     * @param upDn the upDn to set
     */
    public void setUpDn(int upDn) {
        this.upDn = upDn;
    }

    /**
     * @return the top
     */
    public int getTop() {
        return top;
    }

    /**
     * @param top the top to set
     */
    public void setTop(int top) {
        this.top = top;
    }

    /**
     * @return the rec
     */
    public int getRec() {
        return rec;
    }

    /**
     * @param rec the rec to set
     */
    public void setRec(int rec) {
        this.rec = rec;
    }
    private int chDir=0;
    private int upDn=0;
    private int top=0;
    private float pCl=0; // predict close
    private int rec=0; // recommended

}
