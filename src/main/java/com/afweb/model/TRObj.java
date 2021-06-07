/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.model;

import com.afweb.model.account.TradingRuleObj;
import java.util.ArrayList;

/**
 *
 * @author eddy
 */
public class TRObj {
        private ArrayList <TradingRuleObj> trlist;

    /**
     * @return the trlist
     */
    public ArrayList <TradingRuleObj> getTrlist() {
        return trlist;
    }

    /**
     * @param trlist the trlist to set
     */
    public void setTrlist(ArrayList <TradingRuleObj> trlist) {
        this.trlist = trlist;
    }
}
