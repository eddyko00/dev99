/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.model;


import com.afweb.model.stock.AFstockInfo;
import java.util.ArrayList;

/**
 *
 * @author eddy
 */
public class StockInfoTranObj {
        private String normalizeName;
        private ArrayList<AFstockInfo> stockInfoList;

    /**
     * @return the normalizeName
     */
    public String getNormalizeName() {
        return normalizeName;
    }

    /**
     * @param normalizeName the normalizeName to set
     */
    public void setNormalizeName(String normalizeName) {
        this.normalizeName = normalizeName;
    }

    /**
     * @return the stockInfoList
     */
    public ArrayList<AFstockInfo> getStockInfoList() {
        return stockInfoList;
    }

    /**
     * @param stockInfoList the stockInfoList to set
     */
    public void setStockInfoList(ArrayList<AFstockInfo> stockInfoList) {
        this.stockInfoList = stockInfoList;
    }
}
