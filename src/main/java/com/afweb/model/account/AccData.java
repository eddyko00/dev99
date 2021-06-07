/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.model.account;

/**
 *
 * @author eddyko
 */
public class AccData {

    /**
     * @return the tt
     */
    public int getTt() {
        return tt;
    }

    /**
     * @param tt the tt to set
     */
    public void setTt(int tt) {
        this.tt = tt;
    }

    /**
     * @return the nn
     */
    public int getNn() {
        return nn;
    }

    /**
     * @param nn the nn to set
     */
    public void setNn(int nn) {
        this.nn = nn;
    }

    private String conf = "";
    private int nn = 0;
    private int tt = 0; // tecnhical indicator error

    /**
     * @return the conf
     */
    public String getConf() {
        return conf;
    }

    /**
     * @param conf the conf to set
     */
    public void setConf(String conf) {
        this.conf = conf;
    }

}
