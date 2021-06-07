/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.model;

import java.util.ArrayList;

/**
 *
 * @author koed
 */
public class FundM {

    /**
     * @return the perfL
     */
    public ArrayList getPerfL() {
        return perfL;
    }

    /**
     * @param perfL the perfL to set
     */
    public void setPerfL(ArrayList perfL) {
        this.perfL = perfL;
    }

    private ArrayList accL = new ArrayList();
    private ArrayList funL = new ArrayList();
    private ArrayList perfL = new ArrayList();   // pass year performance yy,$ 


    /**
     * @return the accL
     */
    public ArrayList getAccL() {
        return accL;
    }

    /**
     * @param accL the accL to set
     */
    public void setAccL(ArrayList accL) {
        this.accL = accL;
    }

    /**
     * @return the funL
     */
    public ArrayList getFunL() {
        return funL;
    }

    /**
     * @param funL the funL to set
     */
    public void setFunL(ArrayList funL) {
        this.funL = funL;
    }


}
