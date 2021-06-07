/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.model.account;

import com.vpumlmodel.afweb.performance;

/**
 *
 * @author eddy
 */
public class PerformanceObj extends performance {

    private int indexDate;
    private String updateDateD = "";
    private PerformData performData;

    public PerformanceObj() {
        PerformData perfData = new PerformData();
        setPerformData(perfData);
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
     * @return the performData
     */
    public PerformData getPerformData() {
        return performData;
    }

    /**
     * @param performData the performData to set
     */
    public void setPerformData(PerformData performData) {
        this.performData = performData;
    }

    /**
     * @return the indexDate
     */
    public int getIndexDate() {
        return indexDate;
    }

    /**
     * @param indexDate the indexDate to set
     */
    public void setIndexDate(int indexDate) {
        this.indexDate = indexDate;
    }
}
