package com.afweb.model.account;

import com.afweb.model.stock.AFstockInfo;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author eddy
 */
public class StockTRHistoryObj {

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

    private String symbol;
    private String trname;
    private int type;
    private float parm1;
    private float parm2;
    private float parm3;
    private float parm4;    
    private float parm5;
    private float parm6;    
    private float parm7;    
    private float parm8;      
    private String parmSt1;     
    private float close;    
    private float volume;   
    private int trsignal;
    private long updateDatel;
    private String updateDateD = "";
    private AFstockInfo afstockInfo=null;
    private ArrayList data=null;

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
     * @return the parm1
     */
    public float getParm1() {
        return parm1;
    }

    /**
     * @param parm1 the parm1 to set
     */
    public void setParm1(float parm1) {
        this.parm1 = parm1;
    }

    /**
     * @return the parm2
     */
    public float getParm2() {
        return parm2;
    }

    /**
     * @param parm2 the parm2 to set
     */
    public void setParm2(float parm2) {
        this.parm2 = parm2;
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
     * @return the updateDateD
     */
    public String getUpdateDateD() {
        return updateDateD;
    }

    /**
     * @param updateDateD the updateDateD to set
     */
    public void setUpdateDateD(String updateDateD) {
        this.updateDateD = updateDateD;
    }

    /**
     * @return the parm3
     */
    public float getParm3() {
        return parm3;
    }

    /**
     * @param parm3 the parm3 to set
     */
    public void setParm3(float parm3) {
        this.parm3 = parm3;
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
     * @return the updateDatel
     */
    public long getUpdateDatel() {
        return updateDatel;
    }

    /**
     * @param updateDatel the updateDatel to set
     */
    public void setUpdateDatel(long updateDatel) {
        this.updateDatel = updateDatel;
    }

    /**
     * @return the afstockInfo
     */
    public AFstockInfo getAfstockInfo() {
        return afstockInfo;
    }

    /**
     * @param afstockInfo the afstockInfo to set
     */
    public void setAfstockInfo(AFstockInfo afstockInfo) {
        this.afstockInfo = afstockInfo;
    }

    /**
     * @return the data
     */
    public ArrayList getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(ArrayList data) {
        this.data = data;
    }

    /**
     * @return the parm4
     */
    public float getParm4() {
        return parm4;
    }

    /**
     * @param parm4 the parm4 to set
     */
    public void setParm4(float parm4) {
        this.parm4 = parm4;
    }

    /**
     * @return the parm5
     */
    public float getParm5() {
        return parm5;
    }

    /**
     * @param parm5 the parm5 to set
     */
    public void setParm5(float parm5) {
        this.parm5 = parm5;
    }

    /**
     * @return the parm6
     */
    public float getParm6() {
        return parm6;
    }

    /**
     * @param parm6 the parm6 to set
     */
    public void setParm6(float parm6) {
        this.parm6 = parm6;
    }

    /**
     * @return the parm7
     */
    public float getParm7() {
        return parm7;
    }

    /**
     * @param parm7 the parm7 to set
     */
    public void setParm7(float parm7) {
        this.parm7 = parm7;
    }

    /**
     * @return the parm8
     */
    public float getParm8() {
        return parm8;
    }

    /**
     * @param parm8 the parm8 to set
     */
    public void setParm8(float parm8) {
        this.parm8 = parm8;
    }

    /**
     * @return the parmSt1
     */
    public String getParmSt1() {
        return parmSt1;
    }

    /**
     * @param parmSt1 the parmSt1 to set
     */
    public void setParmSt1(String parmSt1) {
        this.parmSt1 = parmSt1;
    }
}
