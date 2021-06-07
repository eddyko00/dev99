/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.model.account;

import com.vpumlmodel.account.account;

/**
 *
 * @author eddy
 */
public class AccountObj extends account {

// Account - type

    public static final String TRADING_ACCOUNT = "Trading"; // buy , sell
    public static final int INT_TRADING_ACCOUNT = 110;

    public static final String MUTUAL_FUND_ACCOUNT = "Fund";
    public static final int INT_MUTUAL_FUND_ACCOUNT = 120;

    public static final String ADMIN_ACCOUNT = "Admin"; 
    public static final int INT_ADMIN_ACCOUNT = 140;

//    public static final int MAX_ALLOW_STOCK = 20;
    public static final int MAX_ALLOW_STOCK_ERROR = 100;
// Account - type    
}
