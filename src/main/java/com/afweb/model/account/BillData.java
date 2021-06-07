/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.model.account;

/**
 *
 * @author koed
 */
public class BillData {

    /**
     * @return the credit
     */
    public float getCredit() {
        return credit;
    }

    /**
     * @param credit the credit to set
     */
    public void setCredit(float credit) {
        this.credit = credit;
    }

    private String feat = "";
    private int status = 0;
    private float prevOwn = 0;
    private float curPaym = 0;
    private float tax = 0;
    private float service = 0;
    private float credit = 0;
    /**
     * @return the feat
     */
    public String getFeat() {
        return feat;
    }

    /**
     * @param feat the feat to set
     */
    public void setFeat(String feat) {
        this.feat = feat;
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
     * @return the prevOwn
     */
    public float getPrevOwn() {
        return prevOwn;
    }

    /**
     * @param prevOwn the prevOwn to set
     */
    public void setPrevOwn(float prevOwn) {
        this.prevOwn = prevOwn;
    }

    /**
     * @return the curPaym
     */
    public float getCurPaym() {
        return curPaym;
    }

    /**
     * @param curPaym the curPaym to set
     */
    public void setCurPaym(float curPaym) {
        this.curPaym = curPaym;
    }

    /**
     * @return the tax
     */
    public float getTax() {
        return tax;
    }

    /**
     * @param tax the tax to set
     */
    public void setTax(float tax) {
        this.tax = tax;
    }

    /**
     * @return the service
     */
    public float getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(float service) {
        this.service = service;
    }

}
