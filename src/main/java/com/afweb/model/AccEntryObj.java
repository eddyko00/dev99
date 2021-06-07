/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.model;

/**
 *
 * @author eddyko
 */
public class AccEntryObj {

    /**
     * @return the debit
     */
    public float getDebit() {
        return debit;
    }

    /**
     * @param debit the debit to set
     */
    public void setDebit(float debit) {
        this.debit = debit;
    }

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

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the dateSt
     */
    public String getDateSt() {
        return dateSt;
    }

    /**
     * @param dateSt the dateSt to set
     */
    public void setDateSt(String dateSt) {
        this.dateSt = dateSt;
    }
    private int id;
    private String dateSt;
    private int type;
    private String name;
    //        billObj.setPayment(debit);
    //        billObj.setBalance(credit);
    
    private float debit=0;
    private float credit=0;    
    private String comment = "";
}
