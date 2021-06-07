/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.service;

import com.afweb.util.CKey;

/**
 *
 * @author eddyko
 */
public class Javamain {

    public static void checkParameterFlag(String cmd) {

        if (cmd.indexOf("flagNNLearningSignal") != -1) {
            ServiceAFweb.flagNNLearningSignal = true;

        } else if (cmd.indexOf("flagNNReLearning") != -1) {
            ServiceAFweb.flagNNReLearning = true;
        } else if (cmd.indexOf("processNNSignalAdmin") != -1) {
            ServiceAFweb.processNNSignalAdmin = true;
        } else if (cmd.indexOf("processRestinputflag") != -1) {
            ServiceAFweb.processRestinputflag = true;
        } else if (cmd.indexOf("processRestAllStockflag") != -1) {
            ServiceAFweb.processRestAllStockflag = true;
        } else if (cmd.indexOf("initLocalRemoteNN") != -1) {
            ServiceAFweb.initLocalRemoteNN = true;

        } else if (cmd.indexOf("otherphp1mysqlflag") != -1) {
            CKey.OTHER_PHP1_MYSQL = true;
            CKey.SERVER_TIMMER_URL = CKey.URL_PATH_OP;
            ServiceRemoteDB.setURL_PATH(CKey.URL_PATH_OP_DB_PHP1 + CKey.WEBPOST_OP_PHP);

        } else if (cmd.indexOf("localmysqlflag") != -1) {
            CKey.SQL_DATABASE = CKey.LOCAL_MYSQL;

        } else if (cmd.indexOf("backupFlag") != -1) {
            CKey.backupFlag = true;
        } else if (cmd.indexOf("restoreFlag") != -1) {
            CKey.restoreFlag = true;
        } else if (cmd.indexOf("restoreNNonlyFlag") != -1) {
            CKey.restoreNNonlyFlag = true;

        } else if (cmd.indexOf("proxyflag") != -1) {
            CKey.PROXY = true;
        } else if (cmd.indexOf("nndebugflag") != -1) {
            CKey.NN_DEBUG = true;
            CKey.UI_ONLY = true;

        } else if (cmd.indexOf("delayrestoryflag") != -1) {
            CKey.DELAY_RESTORE = true;

        } else if (cmd.indexOf("processEmailFlag") != -1) {
            ServiceAFweb.processEmailFlag = true;

        } else if (cmd.indexOf("processNeuralNetFlag") != -1) {
            ServiceAFweb.processNeuralNetFlag = true;

        } else if (cmd.indexOf("nn1testflag") != -1) {
            ServiceAFweb.nn1testflag = true;
        } else if (cmd.indexOf("nn1testflag") != -1) {
            ServiceAFweb.nn1testflag = true;
        } else if (cmd.indexOf("nn2testflag") != -1) {
            ServiceAFweb.nn2testflag = true;
        } else if (cmd.indexOf("nn3testflag") != -1) {
            ServiceAFweb.nn3testflag = true;
        } else if (cmd.indexOf("nn30testflag") != -1) {
            ServiceAFweb.nn30testflag = true;

        } else if (cmd.indexOf("forceMarketOpen") != -1) {
            ServiceAFweb.forceMarketOpen = true;
        } else if (cmd.indexOf("mydebugtestNN3flag") != -1) {
            ServiceAFweb.mydebugtestNN3flag = true;
            ServiceAFweb.forceMarketOpen = true;

        } else if (cmd.indexOf("mydebugtestflag") != -1) {
            CKey.NN_DEBUG = true;
            CKey.UI_ONLY = true;
            ServiceAFweb.mydebugtestflag = true;

        }

    }

    /**
     * @param args the command line arguments
     */
    public static void javamain(String[] args) {
        // TODO code application logic here
        ServiceAFweb srv = new ServiceAFweb();

        if (args != null) {
            if (args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    String cmd = args[i];
                    checkParameterFlag(cmd);
                } // loop
            }
        }

        while (true) {
            srv.timerHandler("");
            ServiceAFweb.AFSleep1Sec(1);
        }

    }
}
