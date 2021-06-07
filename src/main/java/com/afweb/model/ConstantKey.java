/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.model;

/**
 *
 * @author eddy
 */
public class ConstantKey {

    public static final String VERSION = "ver1.0";
    public static final int STOCK_LOCKTYPE = 0;
    public static final int STOCK_UPDATE_LOCKTYPE = 5;
    public static final int ACC_LOCKTYPE = 10;
    public static final int TR_LOCKTYPE = 20;
    public static final int SRV_LOCKTYPE = 30;
    public static final int ADMIN_SIGNAL_LOCKTYPE = 40;
    public static final int SIGNAL_LOCKTYPE = 45;
    public static final int NN_LOCKTYPE = 50;
    public static final int NN_TR_LOCKTYPE = 51;
    public static final int NN_ST_LOCKTYPE = 52;
    public static final int FUND_LOCKTYPE = 60;
    public static final int H2_LOCKTYPE = 100;
//////////////////////////////////////////////////////////////////
/// Price plan configuration    
    public static final String PP_BASIC = "BASIC";
    public static final int INT_PP_BASIC = 0;
    public static final int INT_PP_BASIC_NUM = 2;
    public static final float INT_PP_BASIC_PRICE = 30;
    public static final String PP_STANDARD = "STANDARD"; // standard
    public static final int INT_PP_STANDARD = 10;
    public static final int INT_PP_STANDARD_NUM = 8;
    public static final float INT_PP_STANDARD_PRICE = 60;
    public static final String PP_PEMIUM = "PREMIUM";
    public static final int INT_PP_PEMIUM = 20;
    public static final int INT_PP_PEMIUM_NUM = 20;
    public static final float INT_PP_PEMIUM_PRICE = 120;

    public static final String PP_DELUXEX2 = "DELUXE";
    public static final int INT_PP_DELUXEX2 = 40;
    public static final int INT_PP_DELUXEX2_NUM = 40;
    public static final float INT_PP_DELUXEX2_PRICE = 240;

    public static final String PP_API = "PP_API";
    public static final int INT_PP_API = 90;
    public static final int INT_PP_API_NUM = 1000;
    public static final float INT_PP_API_PRICE = 6000;
//////////////////////////////////////////////////////////////////  
//    
    public static final String MSG_OPEN = "ENABLE";
    public static final int OPEN = 0;

    public static final String MSG_CLOSE = "CLOSE";
    public static final int CLOSE = 1;

    public static final String MSG_ENABLE = "ENABLE";
    public static final int ENABLE = 0;

    public static final String MSG_DISABLE = "DISABLE";
    public static final int DISABLE = 1;

    public static final String MSG_PENDING = "PENDING";
    public static final int PENDING = 2;

    public static final String MSG_NO_ACTIVE = "NO_ACTIVATE";
    public static final int NOACT = 4;

    public static final String MSG_COMPLETED = "COMPLETED";
    public static final int COMPLETED = 5;
/////////////////////////////////////////////////////////////////
//  communication messsage
/////// SubStatus        
    public static final String MSG_NEW = "NEW";
    public static final int NEW = 1;

    public static final String MSG_EXISTED = "EXISTED";
    public static final int EXISTED = 2;

    public static final String MSG_NOTEXISTED = "NOTEXISTED";
    public static final int NOTEXISTED = 3;

    public static final String MSG_INITIAL = "INITIAL";
    public static final int INITIAL = 2;

    public static final String MSG_STOCK_SPLIT = "STOCK_SPLIT";
    public static final int STOCK_SPLIT = 10;

    public static final String MSG_STOCK_DELTA = "STOCK_DELTA";
    public static final int STOCK_DELTA = 12;
//// communication type
    public static final int INT_COM_CFG = -1;

    public static final String COM_SIGNAL = "MESSAGE";
    public static final String COM_BILLMSG = "BILLING";  // name
    public static final String COM_ADD_CUST_MSG = "ADD_CUST"; // name
    public static final int INT_TYPE_COM_SIGNAL = 0;  // type
//
    public static final String COM_SPLIT = "MSG_SPLIT";
    public static final int INT_TYPE_COM_SPLIT = 2;

    public static final String COM_EMAIL = "MSG_EMAIL";
    public static final int INT_TYPE_COM_EMAIL = 4;

    
    public static final String COM_PUB = "MSG_PUB";
    public static final String COM_FUNDMSG = "FUNDMSG";    
    public static final int INT_TYPE_COM_PUB = 6;
    
    public static final String BILLING = "BILLING";
    public static final int INT_BILLING = 10;
    public static final String ACCT_TRAN = "ACCTRAN";
    public static final int INT_ACC_TRAN = 20;
/////////////////////////////////////////////////////////////////////////    
//// Signal configuration      
    public static final String nullSt = "null";    // fix mapper object translation

    public static final String S_PENDING_ST = "pending";     // no trade
    public static final String S_NEUTRAL_ST = "neutral";
    public static final String S_LONG_BUY_ST = "long_buy";
    public static final String S_BUY_ST = "buy";
    public static final String S_SHORT_SELL_ST = "short_sell";
    public static final String S_SELL_ST = "sell";
    public static final String S_STOPLOSS_BUY_ST = "stop_loss_buy"; // stop loss buy
    public static final String S_STOPLOSS_SELL_ST = "stop_loss_sell"; // stop loss sell
    public static final String S_EXIT_LONG_ST = "exit_long";
    public static final String S_EXIT_SHORT_ST = "exit_short";

    public static final int S_PENDING = -1;     // no trade
    public static final int S_NEUTRAL = 0;
    public static final int SS_LONG_BUY = 11;
    public static final int S_LONG_BUY = 1;
    public static final int S_BUY = 1;
    public static final int SS_SHORT_SELL = 22;
    public static final int S_SHORT_SELL = 2;
    public static final int S_SELL = 2;
    public static final int S_STOPLOSS_BUY = 3; // stop loss buy
    public static final int S_STOPLOSS_SELL = 4; // stop loss sell
    public static final int S_EXIT_LONG = 5;
    public static final int S_EXIT_SHORT = 6;
    public static final int S_EXIT = 8;
//////////////////////////////////////////////////////////////////////
    //trading rule
    //******** must be capital for hte TR name ***********************
    public static final String TR_ACC = "TR_ACC";  // transaction account
    public static final int INT_TR_ACC = 0;

    public static final String TR_MV = "TR_MV";  // simulation 
    public static final int INT_TR_MV = 1;
//    public static final int INT_MV_10 = 10;
    public static final int INT_MV_20 = 20;
    public static final int INT_MV_50 = 50; //50;

    public static final int INT_TR_SMA0 = 140;  // fast
    public static final int INT_TR_SMA1 = 142;  
    
    public static final int INT_TR_EMA00 = 134;
    public static final int INT_TR_EMA0 = 130;  // fast
    //ConstantKey.INT_EMA_3, ConstantKey.INT_EMA_6
    public static final int INT_EMA_1 = 1;    
    public static final int INT_EMA_2 = 2;
    public static final int INT_EMA_3 = 3;
    public static final int INT_EMA_6 = 6;
    public static final int INT_EMA_4 = 4;

    public static final int INT_TR_EMA1 = 131;  // normal
    //ConstantKey.INT_EMA_5, ConstantKey.INT_EMA_10)
    public static final int INT_EMA_5 = 5;
    public static final int INT_EMA_10 = 10;
    public static final int INT_EMA_12 = 12;

    public static final int INT_TR_EMA2 = 132;  // slow
    //ConstantKey.INT_EMA_8, ConstantKey.INT_EMA_16
    public static final int INT_EMA_8 = 8;
    public static final int INT_EMA_16 = 16;
    public static final int INT_EMA_20 = 20;

    public static final String TR_MACD = "TR_MACD";
    public static final int INT_TR_MACD = 2;
    public static final int INT_MACD_12 = 12;
    public static final int INT_MACD_26 = 26;
    public static final int INT_MACD_9 = 9;

    public static final String TR_RSI = "TR_RSI";
    public static final int INT_TR_RSI = 3;
    public static final int INT_RSI_14 = 14;

//    public static final int INT_TR_RSI1 = 110;
//    public static final int INT_RSI_5 = 5;    
//    public static final int INT_TR_RSI2 = 111;
//    public static final int INT_RSI_7 = 7;
//    public static final String TR_ADX = "TR_ACX";
//    public static final int INT_TR_ADX1 = 120;
//    public static final int INT_ADX_5 = 5;    
//    public static final int INT_ADX_7 = 7;      
//    public static final int INT_TR_ADX2 = 121;
//    public static final int INT_ADX_14 = 14;    
    public static final String TR_NN1 = "TR_NN1"; //NN for MACD fast
    public static final int INT_TR_NN1 = 4;
    public static final String TR_NN91 = "TR_NN91"; //mirror to NN_1 
    public static final int INT_TR_NN91 = 91;
    
    public static final String TR_NN2 = "TR_NN2"; //NN for RSI
    public static final int INT_TR_NN2 = 5;
    public static final String TR_NN92 = "TR_NN92"; //mirror to NN_2 
    public static final int INT_TR_NN92 = 92;
    
    public static final String TR_NN3 = "TR_NN3"; //NN for 
    public static final int INT_TR_NN3 = 6;
    public static final String TR_NN93 = "TR_NN93"; //mirror to NN_3 
    public static final int INT_TR_NN93 = 93;
    /// make sure to updat this size whend adding more TR
    /// remember to add InitStaticData in ServiceAFweb.java
    public static final int SIZE_TR = 7;

    public static final String TR_BB = "TR_BB"; //NN for 
    public static final int INT_TR_BB = 12;
    public static final int INT_BB_M_20 = 20;
    public static final int INT_BB_SD_2 = 2;
    public static final int INT_BB_M_10 = 10;
    public static final int INT_BB_M_5 = 5;
    public static final int INT_BB_SD_1 = 1;

    public static final String TR_NN30 = "TR_NN30"; //NN for Trend 
    public static final int INT_TR_NN30 = 30;
//    public static final String TR_NN35 = "TR_NN35"; //NN for Trend 
//    public static final int INT_TR_NN35 = 35;
    public static final String TR_NN40 = "TR_NN40"; //NN2 for Trend 
    public static final int INT_TR_NN40 = 40;
    /// make sure to updat this size whend adding more TR
    /// remember to add InitStaticData in ServiceAFweb.java
    public static final String TR_NN200 = "TR_NN200";
    public static final int INT_TR_NN200 = 200;

//    public static final String TR_NN100 = "TR_NN100"; //NN for MV
//    public static final int INT_TR_NN100 = 100;
    public static final String TR_MACD0 = "TR_MACD0";   //fase
    public static final int INT_TR_MACD0 = 102;
    //ConstantKey.INT_MACD0_3, ConstantKey.INT_MACD0_6, ConstantKey.INT_MACD0_2    
    public static final int INT_MACD0_3 = 3;
    public static final int INT_MACD0_6 = 6;
    public static final int INT_MACD0_2 = 2;

    public static final String TR_MACD1 = "TR_MACD1";   // normal
    //ConstantKey.INT_MACD1_6, ConstantKey.INT_MACD1_12, ConstantKey.INT_MACD1_4
    public static final int INT_TR_MACD1 = 100;
    public static final int INT_MACD1_6 = 6;
    public static final int INT_MACD1_12 = 12;
    public static final int INT_MACD1_4 = 4;

    public static final int INT_TR_MACD2 = 103;
    //INT_MACD_12, ConstantKey.INT_MACD_26, ConstantKey.INT_MACD_9

//    public static int getTRtypeByName(String trname) {
//        trname = trname.toUpperCase();
//        if (trname.equals(TR_ACC)) {
//            return INT_TR_ACC;
//        } else if (trname.equals(TR_MV)) {
//            return INT_TR_MV;
//        } else if (trname.equals(TR_MACD)) {
//            return INT_TR_MACD;
//        } else if (trname.equals(TR_RSI)) {
//            return INT_TR_RSI;
//        } else if (trname.equals(TR_NN1)) {
//            return INT_TR_NN1;
//        } else if (trname.equals(TR_NN2)) {
//            return INT_TR_NN2;
//        } else if (trname.equals(TR_NN3)) {
//            return INT_TR_NN3;
//        }
//        return 0;
//    }
}
