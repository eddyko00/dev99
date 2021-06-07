/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.model.stock;

import com.vpumlmodel.stock.stockInfo;

/**
 *
 * @author eddy
 */
public class AFstockInfoDisplay {
        private String updateDateD = "";
        private stockInfo stockInfoObj;
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
     * @return the stockInfoObj
     */
    public stockInfo getStockInfoObj() {
        return stockInfoObj;
    }

    /**
     * @param stockInfoObj the stockInfoObj to set
     */
    public void setStockInfoObj(stockInfo stockInfoObj) {
        this.stockInfoObj = stockInfoObj;
    }
}
