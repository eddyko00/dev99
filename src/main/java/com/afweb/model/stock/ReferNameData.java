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
public class ReferNameData {

    /**
     * @return the nRLCnt
     */
    public int getnRLCnt() {
        return nRLCnt;
    }

    /**
     * @param nRLCnt the nRLCnt to set
     */
    public void setnRLCnt(int nRLCnt) {
        this.nRLCnt = nRLCnt;
    }

    /**
     * @return the nRLearn
     */
    public int getnRLearn() {
        return nRLearn;
    }

    /**
     * @param nRLearn the nRLearn to set
     */
    public void setnRLearn(int nRLearn) {
        this.nRLearn = nRLearn;
    }

    /**
     * @return the mError
     */
    public double getmError() {
        return mError;
    }

    /**
     * @param mError the mError to set
     */
    public void setmError(double mError) {
        this.mError = mError;
    }
    private int nRLearn = -1;
    private int nRLCnt = 0;    
    private double mError = 0;
  

}
