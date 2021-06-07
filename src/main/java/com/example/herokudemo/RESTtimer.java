/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.herokudemo;

import com.afweb.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import java.util.logging.Logger;

/**
 *
 * @author eddy
 */
public class RESTtimer {

    protected static Logger logger = Logger.getLogger("service");

    private static String timerMsg = null;

    public static String serverURL_0 = "";

    public void RestTimerHandler() {

        if (getEnv.checkLocalPC() == true) {
            ;
        } else {
            //////// force to stop (free host has limited resource)
            if (HerokuDemoApplication.timerSchCnt > 40) {
                if (HerokuDemoApplication.timerSchCnt == 41) {
                    logger.info("RestTimerHandler: disabled");
                }
                serverURL_0 = "stop";
            }
//            Calendar dateNow = TimeConvertion.getCurrentCalendar();
//            long lockDateValue = dateNow.getTimeInMillis();
//            String tzid = "America/New_York"; //EDT
//            TimeZone tz = TimeZone.getTimeZone(tzid);
//            java.util.Date d = new java.util.Date(lockDateValue);
//            DateFormat format = new SimpleDateFormat("M/dd/yyyy HH z");
//            format.setTimeZone(tz);
//            String ESTdate = format.format(d);  //4/03/2020 04:47 PM EDT
//            String[] arrOfStr = ESTdate.split(" ");
//            int hr = Integer.parseInt(arrOfStr[1]);
//            if (CKey.SERVERDB_URL.equals(CKey.URL_PATH_OP)) {
//                if ((hr >= 10) && (hr <= 11)) {
//                    serverURL_0 = "";
//                } else if ((hr >= 17) && (hr <= 18)) {
//                    serverURL_0 = "";
//                } else {
//                    if (HerokuDemoApplication.timerSchCnt > 5) {
//                        if (serverURL_0.equals("startop")) {
//                            ;
//                        } else {
//                            serverURL_0 = "stop";
//                        }
//                    }
//                }
//            } else if (CKey.SERVERDB_URL.equals(CKey.URL_PATH_HERO)) {
//                if ((hr >= 8) && (hr <= 20)) {
//                    serverURL_0 = "";
//                } else {
////                    if (HerokuDemoApplication.timerSchCnt > 5) {
////                        serverURL_0 = "stop";
////                    }
//                }
//            }

        }
        RestTimerHandler0(CKey.SERVER_TIMMER_URL);
    }

    private static int timerCnt0 = 0;
    private static int timerExceptionCnt0 = 0;
    private static long lastTimer0 = 0;
    private static long timerServ0 = 0;

    public void RestTimerHandler0(String urlStr) {

        if (timerServ0 == 0) {
            timerServ0 = System.currentTimeMillis();
        }
        timerCnt0++;
        if (timerCnt0 < 0) {
            timerCnt0 = 0;
        }

        if (timerExceptionCnt0 > 2) {
            long currentTime = System.currentTimeMillis();
            long lockDate1Min = TimeConvertion.addMinutes(lastTimer0, 1); // add 1 minutes
            if (lockDate1Min < currentTime) {
                timerExceptionCnt0 = 0;
            }
            return;
        }
        lastTimer0 = System.currentTimeMillis();
        timerMsg = "timerThreadServ=" + timerServ0 + "-timerCnt=" + timerCnt0 + "-ExceptionCnt=" + timerExceptionCnt0;
        // // too much log
//        logger.info(timerMsg);
        try {

            // Create Client
            String url = "";
            if (serverURL_0.length() == 0) {
                url = urlStr + "/timerhandler?resttimerMsg=" + timerMsg;

            } else if (serverURL_0.equals("startop")) {
                url = urlStr + "/timerhandler?resttimerMsg=" + timerMsg;

            } else if (serverURL_0.equals("stop")) {
                return;
            } else {
                url = serverURL_0 + "/timerhandler?resttimerMsg=" + timerMsg;
            }

            if (getEnv.checkLocalPC() == true) {
                url = AFwebService.localTimerURL + AFwebService.webPrefix + "/timerhandler?resttimerMsg=" + timerMsg;
            }
            RESTtimerREST restAPI = new RESTtimerREST();
            String ret = restAPI.sendRequest(RESTtimerREST.METHOD_GET, url, null, null, false);
//            logger.info(ret);

            timerExceptionCnt0--;
            if (timerExceptionCnt0 < 0) {
                timerExceptionCnt0 = 0;
            }
        } catch (Exception ex) {
//            if (CKey.NN_DEBUG == true) {
////                logger.info("RestTimerHandler0 Failed with HTTP Error ");
//            }
        }
        timerExceptionCnt0++;
    }

//    private static int timerCnt = 0;
//    private static int timerExceptionCnt = 0;
//    private static long lastTimer = 0;
//    private static long timerServ = 0;
//
//    public void RestTimerHandler1(String urlStr) {
//
//        if (serverURL_1.equals("stop")) {
//            return;
//        }
//        if (timerServ == 0) {
//            timerServ = System.currentTimeMillis();
//        }
//        timerCnt++;
//        if (timerCnt < 0) {
//            timerCnt = 0;
//        }
//
//        if (timerExceptionCnt > 2) {
//            long currentTime = System.currentTimeMillis();
//            long lockDate1Min = TimeConvertion.addMinutes(lastTimer, 1); // add 1 minutes
//            if (lockDate1Min < currentTime) {
//                timerExceptionCnt = 0;
//            }
//            return;
//        }
//        lastTimer = System.currentTimeMillis();
//        timerMsg = "timerThreadServ=" + timerServ + "-timerCnt=" + timerCnt + "-ExceptionCnt=" + timerExceptionCnt;
//        // // too much log
////        logger.info(timerMsg);
//        try {
//
//            // Create Client
//            String url = "";
//            if (serverURL_1.length() == 0) {
//                url = urlStr + "/timerhandler?resttimerMsg=" + timerMsg;
//            } else {
//                url = serverURL_1 + "/timerhandler?resttimerMsg=" + timerMsg;
//            }
//            RESTtimerREST restAPI = new RESTtimerREST();
//            String ret = restAPI.sendRequest(RESTtimerREST.METHOD_GET, url, null, null, CKey.PROXY);
//
//            timerExceptionCnt--;
//            if (timerExceptionCnt < 0) {
//                timerExceptionCnt = 0;
//            }
//        } catch (Exception ex) {
//            logger.info("RestTimerHandler1 Failed with HTTP Error ");
//        }
//        timerExceptionCnt++;
//    }
}
