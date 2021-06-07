package com.afweb.util;


public class CKey {

    //local pc
    public static String FileLocalPathTemp = "T:/Netbean/db/";
    public static String FileServerPathTemp = "/app/";

    public static final String ADMIN_USERNAME = "ADMIN1";
    public static final String FUND_MANAGER_USERNAME = "FUNDMGR";
    public static final String INDEXFUND_MANAGER_USERNAME = "INDEXMGR";
    public static final String API_USERNAME = "ADMAPI";
    public static final String AF_SYSTEM = "AFSYSTEM";
    public static final String G_USERNAME = "GUEST";
    public static final String E_USERNAME = "EDDY";
    public static final String MASK_PASS = "*****";

    public static final String MASTER_SRV = "MasterDBSRV";
    public static final String WEB_SRV = "WebSRV";
    //local pc
//    public static final boolean LocalPCflag = true; // true;

    //////////////////////
    public static final int REMOTE_MS_SQL = 3;////// do not use // http://eddyko00.freeasphost.net asp on freeasphost ma sql   

    // remember to update the application properties      
    public static final int LOCAL_MYSQL = 4; //jdbc:mysql://localhost:3306/db_sample     
    public static final int REMOTE_PHP_MYSQL = 2; // https://eddyko.000webhostapp.com/webgetreq.php php mysql
    public static final int DIRECT__MYSQL = 0;   //jdbc:mysql://sql9.freesqldatabase.com:3306/sql9299052 direct mysql expire 3 days

    public static int SQL_DATABASE = REMOTE_PHP_MYSQL;  //MYSQL direct db //REMOTE_MYSQL (for PHP DB proxy)

    //
    //////////////////////
    //
    public static boolean PROXY = false; //false; //true; 
    public static String PROXYURL_TMP = "webproxystatic-on.tslabc.tabceluabcs.com";
    public static boolean NN_DEBUG = false; //false; //true; 
    public static boolean UI_ONLY = false; //false Openshift; //true heroku;  
    /////////////////////
    /////////////////////
    /////////////////////
    public static boolean CACHE_STOCKH = true;      // must be true    
    public static boolean DELAY_RESTORE = false;  // true only for VM ware restore local
    public static boolean GET_STOCKHISTORY_SCREEN = false; //false //true    

    public static boolean backupFlag = false;
    public static boolean restoreFlag = false;
    public static boolean restoreNNonlyFlag = false;

    /////heroku
    /////heroku
    public static final String URL_PATH_HERO = "https://iiswebsrv.herokuapp.com";  // server timerhandler
    public static String WEBPOST_HERO_PHP = "/webgetresp.php";
    public static String URL_PATH_HERO_DBDB_PHP = "https://iiswebdb.herokuapp.com";  //DB4Free https://www.db4free.net/phpMyAdmin/

    /////heroku
    /////heroku
//    //
//    //
//    /////Openshift Login eddyko00  Pass eddyko100 -  using LOCAL_MYSQL  
//    public static final String URL_PATH_OP = "http://iisweb-web012.apps.us-west-1.starter.openshift-online.com";    
//    public static String WEBPOST_OP_PHP = "/health.php";
//    public static String URL_PATH_OP_DB_PHP1 = "http://iiswebphp-web012.apps.us-west-1.starter.openshift-online.com"; //eddyko00     
    public static final String URL_PATH_OP = "https://iiswebsrv1.herokuapp.com";  // server timerhandler
    public static String WEBPOST_OP_PHP = "/webgetresp_stock.php"; // "/webgetresp_1.php";
    public static String URL_PATH_OP_DB_PHP1 = "https://iiswebdb1.herokuapp.com"; //    

    public static final String REMOTEDB_MY_SQLURL = URL_PATH_HERO_DBDB_PHP;  //Heroku PHP DB4free too slow

//***********    
//*********** 
// server timerhandler
// server timerhandler    
    public static boolean OTHER_PHP1_MYSQL = false; //using HEROKU = false // using Other DB =  true

    public static String SERVER_TIMMER_URL = URL_PATH_HERO;  // server timerhandler OTHER_DB1 = false;
//    public static String SERVER_TIMMER_URL = URL_PATH_OP;  // server timerhandler OTHER_DB1 = true;
//
//***********    
//*********** 
//***********    
//*********** 
//**********    
//*********** 
//    public static final String SERVER_TIMER_URL = "https://iiswebtimer.herokuapp.com";  // server timerhandler    
    ////////////////////////////       
//https://www.thebalance.com/best-etfs-4173857
//https://www.etftrends.com/popular-etfs/
//    public static String FUND_PORTFOLIO = ""
//            //            + "VOO,GLD,SCHD,VEA,"
//            //            + "EEM,IEMG,VTI,IVV,VTV,XLF"
//            + "";
    public static String NN_version = "NNBP_V1";
//    public static String NN1_MACD_TRN = "tr_nn1_macd";
//    public static String NN2_MV_TRN = "tr_nn2_mv";

    public static float SPLIT_VAL = (float) 1.5;
    public static int MONTH_SIZE = 16;
    public static int SHORT_MONTH_SIZE = 2;//3;
    public static double PREDICT_THRESHOLD = 0.6;
//    
    public static double NN1_ERROR_THRESHOLD = 0.156; //0.155; // 0.159; //0.172;  
    public static double NN2_ERROR_THRESHOLD = 0.138; //0.137; //0.155;// 
    public static double NN3_ERROR_THRESHOLD = 0.156;
    public static double NN30_ERROR_THRESHOLD = 0.227; //0.226; //0.228; //0.211;  

    public static double NN40_ERROR_THRESHOLD = 0.227;
//
    public static final int NN_OUTPUT_SIZE = 2;
    public static final int NN_INPUT_SIZE = 10;
    public static final int NN1_MIDDLE_SIZE = 110; //120; 

//    public static final boolean WEIGHT_COMPASS = false;    
    public static float iis_ver = (float) 1.1;

    // must match to the nnData and nn3Data version  make sure both 
    // must match to the nnData and nn3Data version  make sure both 
    public static String version = "0.1224";


    //////////////////////
    public static final int MSSQL = 1;/////// do not use //jdbc:sqlserver://sql.freeasphost.net\\MSSQL2016;databaseName=eddyko00_SampleDB

    public static String dataSourceURL = "";
////////////////////////////  
////////////////////////////    
    public static final String COMMA = ",";
    public static final String MSG_DELIMITER = "~";
    public static final String QUOTE = "'";
    public static final String DASH = "-";
    public static final String DB_DELIMITER = "`";

    public static final int DATA6YEAR = 5 * 52 * 6;
    public static final int FAIL_STOCK_CNT = 160;

    public static final float TRADING_AMOUNT = 6000;
    public static final float TRADING_COMMISSION = 7;

    public static final String UA = "iabciswabcebemaiabcl";
    public static final String PA = "eabcddykabco100";
    public static final String UU = "eabcdabcdy.ko00@yahoo.ca";

////////////////////////////////////////////////////////////////////////////////    
//    public static final String MSG_PENDING = "PENDING";
//    public static final int PENDING = 3;
//    public static final String INT_ST_PENDING = "3";
//    //
//    public static final String MSG_PROCESS = "PROCESS";
//    public static final int PROCESS = 4;
//    public static final String INT_ST_PROCESS = "4";
//    //
//    public static final String MSG_FAIL = "FAIL";
//    public static final int FAIL = 5;
//    public static final String INT_ST_FAIL = "5";
//    //    
//    public static final String MSG_DELETE = "DELETE";
//    public static final int DELETE = 9;
//    public static final String INT_ST_DELETE = "9";
//    public static final String MSG_CANCEL = "CANCEL";
//    public static final int CANCEL = 10;
//    public static final String INT_ST_CANCEL = "10";
//    //
//    public static final String MSG_COMPLETE = "COMPLETE";
//    public static final int COMPLETE = 11;
//    public static final String INT_ST_COMPLETE = "11";
//    public static final String MSG_PARTIAL_COMPLETE = "PARTIAL_COMPLETE";
//    public static final int PARTIAL_COMPLETE = 12;
//    public static final String INT_ST_PARTIAL_COMPLETE = "12";
//    public static final int NO_PAYMENT_1 = 55;
//    public static final int NO_PAYMENT_2 = 56;
//    //Billing type
//    public static final int BILLING_SYSTEM = 121;
//    public static final int BILLING_MONTHLY = 122;
//    public static final int BILLING_SERVICE = 125;
//
//    public static String getBillingType(int type) {
//        String bType = "MTM";
//        switch (type) {
//            case BILLING_SYSTEM:
//                bType = "SYS";
//                break;
//
//            case BILLING_SERVICE:
//                bType = "SRV";
//                break;
//        }
//        return bType;
//    }
//    //Payment method
//    public static final int PAYMENT_INIT = 100;
//    public static final int PAYMENT_CREDIT_CARD = 101;
//    public static final int PAYMENT_PAY_PAL = 102;
//    public static final int PAYMENT_CHEQUE = 103;
//    public static final int PAYMENT_CASH = 104;
//    public static final int PAYMENT_CREDIT = 105;
//    public static final int PAYMENT_ADJUST = 106;
//
//    public static String getBillingMethod(int type) {
//        String bType = "intitial";
//        switch (type) {
//            case PAYMENT_PAY_PAL:
//                bType = "paypal";
//                break;
//            case PAYMENT_CASH:
//                bType = "cash";
//                break;
//            case PAYMENT_CREDIT:
//                bType = "credit";
//                break;
//            case PAYMENT_ADJUST:
//                bType = "adj";
//                break;
//            case PAYMENT_CHEQUE:
//                bType = "cheque";
//                break;
//            case PAYMENT_CREDIT_CARD:
//                bType = "credit_card";
//                break;
//        }
//        return bType;
//    }
//    //feature type 
//    public static final int F_TYPE_BASIC = 100;
//    public static final int F_TYPE_BASIC_API = 200;
//    public static final int F_TYPE_BASIC_FUND = 220;
//    public static final int F_TYPE_BASIC_SILVER = 112;
//    public static final int F_TYPE_BASIC_GOLD = 114;
//    // product and price plan 
//    // service plan pricing
//    public static boolean basicPromotion = true;
//    public static final float FEATURE_MIRROR_FUND = (float) 12.00;
//    public static final float FEATURE_BASIC = (float) 12.00;
//    public static final float FEATURE_SILVER = (float) 35.00;
//    public static final float FEATURE_GOLD = (float) 55.00;
//    public static final float FEATURE_FUNDMGR = (float) 55.00;
//    public static final float FEATURE_API = (float) 0;
//    // time constant
//    public static final long ONE_MINUTE = 60 * 1000;
//    public static final long FIVE_MINUTES = 5 * 60 * 1000;
//    public static final long TEN_MINUTES = 10 * 60 * 1000;
//    public static final long T30_MINUTES = 30 * 60 * 1000;
//    //
//    // WebService Interface Command
//    //
//    public static final String CMD_TIMER_PROCESS = "TimerProcess";
//    public static final String SUBCMD_TIMER_M = "TimerMinute";
//    public static final String SUBCMD_TIMER_H = "TimerHour";
//    public static final String SUBCMD_TIMER_D = "TimerDay";
//    public static final String SUBCMD_TIMER_W = "TimerWeek";
//    public static final String SUBCMD_TIMER_30S = "Timer30Second";
//    public static final String SUBCMD_TIMER_1M = "Timer1Minute";
//    public static final String SUBCMD_TIMER_15M = "Timer15Minute";
//    public static final String SUBCMD_TIMER_30M = "Timer30Minute";
//    public static final String SUBCMD_TIMER_1H = "Timer1Hour";
//    public static final String SUBCMD_TIMER_1D = "Timer1Day";
//    public static final String SUBCMD_TIMER_1W = "Timer1Week";
//    //
//    public static final String CMD_USER_PROCESS = "userprocess";
//    public static final String SUBCMD_LOGIN_USER = "loginuser";
//    public static final String SUBCMD_REG_USER = "registeruser";
//    public static final String SUBCMD_REST_PASS = "resetpassword";
//    public static final String SUBCMD_CONF_USER = "registerconfirm";
//    public static final String SUBCMD_UPD_USER_OPTION = "updateuseroption";
//    public static final String SUBCMD_ADD_USER = "adduser";
//    public static final String SUBCMD_USER_INFO = "adduserinfo";
//    public static final String SUBCMD_DEL_USER = "deluser";
//    public static final String SUBCMD_END_USER = "enableuser";
//    public static final String SUBCMD_DIS_USER = "disableuser";
//    public static final String SUBCMD_UPD_USER = "updateuser";
//    public static final String SUBCMD_ADD_FEAT = "addfeature";
//    public static final String SUBCMD_DEL_FEAT = "delfeature";
//    public static final String CMD_ACCOUNT_PROCESS = "accountprocess";
//    public static final String SUBCMD_ADD_MFUND_ACCOUNT = "addmfundaccount";
//    public static final String SUBCMD_ADD_ACCOUNT = "addaccount";
//    public static final String SUBCMD_ADD_MFUND_M_ACCOUNT = "addmfundmarginaccount";
//    public static final String SUBCMD_ADD_M_ACCOUNT = "addmarginaccount";
//    public static final String SUBCMD_UPD_ACCOUNT = "updateaccount";
//    public static final String SUBCMD_UPD_ACCOUNT_OPTION = "updateaccountoption";
//    public static final String SUBCMD_DEL_ACCOUNT = "delaccount";
//    public static final String SUBCMD_ADD_STOCK_ACCOUNT = "addstockaccount";
//    public static final String SUBCMD_BUY_STOCK_ACCOUNT = "buystockaccount";
//    public static final String SUBCMD_TRULE_ACCOUNT = "tradingrule";
//    public static final String SUBCMD_TRUPDATE_ACCOUNT = "tradingupdate";
//    public static final String SUBCMD_ENTER_LONG_STOCK_ACCOUNT = "enterlongstockaccount";
//    public static final String SUBCMD_ENTER_SHORT_STOCK_ACCOUNT = "entershortstockaccount";
//    public static final String SUBCMD_SELL_STOCK_ACCOUNT = "sellstockaccount";
//    public static final String SUBCMD_EXIT_LONG_STOCK_ACCOUNT = "exitlongstockaccount";
//    public static final String SUBCMD_EXIT_SHORT_STOCK_ACCOUNT = "exitshortstockaccount";
//    public static final String SUBCMD_THRESHOLD = "thresholdstock";
//    public static final String SUBCMD_REMOVE_STOCK_ACCOUNT = "removestockaccount";
//    public static final String SUBCMD_CREDIT_ACCOUNT = "creditaccount";
//    public static final String SUBCMD_DEBIT_ACCOUNT = "debitkaccount";
//    public static final String SUBCMD_UPD_ACCOUNT_PERFORM = "updateaccountperformance";
//    public static int ACC_SUBTYPE_INSUFFICIENT_CASH = 100;
//    //
//    public static final String CMD_LONG_PROCESS = "systemlongprocess";
//    public static final String SUBCMD_NET_TRAIN = "nettraining";
//    public static final String SUBCMD_ACC_PERFORMANCE = "accountperformance";
//    public static final String SUBCMD_PORT_PERFORMANCE = "portfolioperformance";
//    public static final String SUBCMD_AUDIT = "systemaudit";
//    //
//    public static final String CMD_SYSTEM_PROCESS = "systemprocess";
//    public static final String SUBCMD_EXEC_ORDER = "executeorder";
//    public static final String SUBCMD_UPD_STOCK = "updatestock";
//    public static final String SUBCMD_UPD_FUND = "updatesystemfund";
//    public static final String SUBCMD_UPD_SINGAL = "updatesystemsignal";
//    public static final String SUBCMD_SEND_EMAIL = "sendemail";
//    public static final String SUBCMD_START_IIS = "startiis";
//    public static final String SUBCMD_STOP_IIS = "stopiis";
//    // command = SendEmail, to, from, subject, msg
//    public static final String CMD_WEBMESSAGE_PROCESS = "webmessageprocess";
//    public static final String SUBCMD_UPD_MESSAGE = "updatewebmessage";
//    public static final String SUBCMD_UPD_RET_MESSAGE = "updatereturnmessage";
//    public static int CUSTOMER_DEBIT = 1;
//    public static int CUSTOMER_CREDIT = 2;
//    public static int TRADING_DEBIT = 5;
//    public static int TRADING_CREDIT = 6;
//    // configuration parameter
//    //public static boolean TrainingOnly = false;
//    public static boolean SystemUpdate = true;
//    public static String IISSlaveURL = "";
//    public static String RemoteHttp = "";
//    public static String RemoteftpUrl = "localhost";
//    public static String RemoteftpUser = "anonymous";
//    public static String RemoteftpPass = "iisystem";
//    public static int ThreadCntFTP = 2;
//    public static int ThreadCntReport = 4;
//    public static final int INT_STOCK = 1;
//    public static final int INT_FUND = 0;
//    public static final String ACC_TYPE_CIBC = "CIBC";
//    public static final String ACC_TYPE_TD = "TD";
//    public static final String ACC_TYPE_RY = "RY";
//    public static final String ACC_TYPE_BMO = "BMO";
//    public static final String ACC_TYPE_SC = "SC";
//    public static boolean AutoStart = false;
//    public static boolean TIMER_1M_flag = true;
//    public static boolean TIMER_15M_flag = true;
//    public static boolean TIMER_2H_flag = true;
//    public static boolean TIMER_1D_flag = true;
//    public static boolean EnterTime1Mflag = false;
//    public static boolean EnterTime15Mflag = false;
//    public static boolean EnterTime2Hflag = false;
//    public static boolean EnterTime1Dflag = false;
//    public static boolean ForceTime2Hflag = false;
//    public static boolean ForceTime1Dflag = false;
//    public static boolean debugTest = false;
//    public static int SystemSignalRule = 1;
//    public static boolean simulation = false;
//    public static ArrayList simPerfStat = new ArrayList();
//    public static ArrayList simTrainStat = new ArrayList();
//    public static float simPerfProfit = 0;
//    public static int simStockSignal = 0;
//    public static String simMVRating = "";
//    public static int simSignalRule = 0;
//    public static String simMV15 = "";
//    public static int MV15signal = 0;
//    /// simulation mode fake the date to the stock date
//    public static String simStockSymbol = "";
//    public static String simSymbol = "";
////    public static AFstockObj simStockObj = null;
//    public static long simCurrentDate = 0;
//    public static ArrayList simCurrentArray = null;
//    public static String simPredictValue = "";
//    public static String simReportModel = "";
//    public static String simInitialInfo = "";
//    public static float simMaxProfit = -9999;
//    public static String simMaxProfitDate = "";
//    public static float simMinProfit = 9999;
//    public static String simMinProfitDate = "";
//    public static int simSBsignal = CKey.S_NEUTRAL;
//
//    /// simulation mode fake the date to the stock date
//    public static int isStringEnableDisable(String inputValue, int defaultValue) {
//        try {
//            if (inputValue.equals(MSG_ENABLE)) {
//                return ENABLE;
//            } else if (inputValue.equals(MSG_DISABLE)) {
//                return DISABLE;
//            }
//        } catch (NumberFormatException ex) {
//        }
//        return defaultValue;
//    }
//    // Web Service Return code
//    public static final String[] RET_SUCCESS_MSG = {"0000", "Success"};
//    public static final String[] RET_FAIL_MSG = {"0001", "Fail"};
//    public static final String WS_SUCCESS = "0000" + MSG_DELIMITER + "Success";
//    public static final String WS_FAIL = "0001" + MSG_DELIMITER + "Fail";
//    public static final String WS_COMPLETE = "0002" + MSG_DELIMITER + "Complete";
//    public static final String WS_SIGNAL = "0003" + MSG_DELIMITER + "Signal";
//    public static final String WS_DB_ERROR_MSG = "0050" + MSG_DELIMITER + "DB Failure";
//    public static final String WS_INVALID_INPUT_MSG = "0051" + MSG_DELIMITER + "Invalid Parameter";
//    public static final String WS_INVALID_NAME_MSG = "0052" + MSG_DELIMITER + "Invalid Name";
//    public static final String WS_INVALID_EMAIL_MSG = "0053" + MSG_DELIMITER + "Invalid Email Address";
//    public static final String WS_INVALID_CMD_NOT_ALLOW_MSG = "0054" + MSG_DELIMITER + "Not allow - processing";
//    //
//    public static final String WS_USR_ADD_MSG = "0100" + MSG_DELIMITER + "User Added";
//    public static final String WS_USR_ADD_INIT_MSG = "0101" + MSG_DELIMITER + "Init Successful";
//    public static final String WS_USR_ALREADY_EXIST_MSG = "0110" + MSG_DELIMITER + "User Existed";
//    public static final String WS_USR_EMAIL_ALREADY_EXIST_MSG = "0111" + MSG_DELIMITER + "User Email Existed";
//    public static final String WS_USR_NOT_FOUND_MSG = "0112" + MSG_DELIMITER + "User not found";
//    public static final String WS_USR_NOT_DISABLE_MSG = "0113" + MSG_DELIMITER + "User not disable";
//    public static final String WS_USR_DISABLE_MSG = "0114" + MSG_DELIMITER + "User disabled";
//    public static final String WS_USR_MEMBER_MSG = "0115" + MSG_DELIMITER + "User has active member - need to fix member referral";
//    public static final String WS_USR_PAYMENT_MSG = "0116" + MSG_DELIMITER + "User has outstanding payment";
//    //
//    public static final String WS_USER_UPGRADE = "0118" + MSG_DELIMITER + "Upgrade account required";
//    public static final String WS_ACC_ADD_MSG = "0200" + MSG_DELIMITER + "Account Added";
//    public static final String WS_ACC_ADD_INIT_MSG = "0201" + MSG_DELIMITER + "Init Successful";
//    public static final String WS_ACC_ALREADY_EXIST_MSG = "0210" + MSG_DELIMITER + "Account Existed";
//    public static final String WS_ACC_NOT_FOUND_MSG = "0212" + MSG_DELIMITER + "Account not found";
//    public static final String WS_ACC_DISABLE_MSG = "0214" + MSG_DELIMITER + "Account disabled";
//    public static final String WS_ACC_INVALID_USR_MSG = "0216" + MSG_DELIMITER + "Account Invalid";
//    public static final String WS_ACC_INIFFICIENT_FUND_MSG = "0218" + MSG_DELIMITER + "Infufficient fund";
//    public static final String WS_ACC_SUB_STATUS_INCORRECT = "0220" + MSG_DELIMITER + "Sub Status Invalid";
//    public static final String WS_ACC_INIFFICIENT_SHARE_MSG = "0222" + MSG_DELIMITER + "Infufficient Share";
//    public static final String WS_ACC_INVALID_FUNDNAME = "0224" + MSG_DELIMITER + "Fund Name Invalid";
//    public static final String WS_STOCK_ADD_MSG = "0400" + MSG_DELIMITER + "Stock Added";
//    public static final String WS_STOCK_ADD_INIT_MSG = "0401" + MSG_DELIMITER + "Init Successful";
//    public static final String WS_STOCK_ALREADY_EXIST_MSG = "0410" + MSG_DELIMITER + "Stock Existed";
//    public static final String WS_STOCK_NOT_FOUND_MSG = "0412" + MSG_DELIMITER + "Stock not found";
//    public static final String WS_STOCK_DISABLE_MSG = "0414" + MSG_DELIMITER + "Stock disabled";
//    public static final String WS_STOCK_SHARE_NOT_EMPTY_MSG = "0416" + MSG_DELIMITER + "Shares Not 0";
//    public static final String WS_BILL_PROCESSED_MSG = "0501" + MSG_DELIMITER + "Bill processed already";
//    public static final String WS_BILL_INVALID_ID_MSG = "0502" + MSG_DELIMITER + "Bill Id not found";
//    //
//    public static final int INT_NON_MEMBER = 100;
//    public static final String NON_MEMBER = "None";
//    //
//    public static final int INT_MEMBER_1 = 101; // ADMIN
//    public static final int INT_MEMBER_2 = 102; // Eddy
//    public static final int INT_MEMBER_3 = 103; // Member1
//    public static final int INT_MEMBER_4 = 104; // Member2
//    public static final int INT_MEMBER_5 = 105; // this must be the last level
//    public static final float DEF_MEMBER_RATE = (float) 0.6; //0.6 (self), 0.4(support)
//   public static final float DEF_NEW_MEMBER_RATE = (float) 0.4; //0.6 (self), 0.4(support)
//    public static final float DEF_FEAT_RATE = (float) 0.8; 
//
////    public static final int INT_MEMBER_L0 = 0;
////    public static final int INT_MEMBER_L1 = 1;
////    public static final int INT_MEMBER_L2 = 2;
////    public static final int INT_MEMBER_L3 = 3;    
////    public static final float MEMBER_L0_RATE = (float) 0.2;
////    public static final float MEMBER_L1_RATE = (float) 0.3;
////    public static final float MEMBER_L2_RATE = (float) 0.4;
////    public static final float MEMBER_L3_RATE = (float) 0.5;
//    public static String getFeatureName(int usertype) {
//        String userT = " ";
//        // overrider the type name
//        switch (usertype) {
//            case CKey.INT_CLIENT_BASIC_USER:
//                userT = "Basic Service";
//                break;
//            case CKey.INT_GUEST_USER:
//                userT = "Guest Service";
//                break;
//            case CKey.INT_CLIENT_GOLD_USER:
//                userT = "Gold Service";
//                break;
//            case CKey.INT_CLIENT_SILVER_USER:
//                userT = "Sliver Service";
//                break;
//            case CKey.INT_API_USER:
//                userT = "API Service";
//                break;
//        }
//        return userT;
//    }
//    public static final int F_TYPE_SILVER = INT_CLIENT_SILVER_USER;
//    public static final int F_TYPE_GOLD = INT_CLIENT_GOLD_USER;
//    public static final int F_TYPE_FUND = INT_FUND_USER;
//    public static final int F_TYPE_API = INT_API_USER;
//    public static final int F_TYPE_MEMBER = INT_MEMBER_USER;
////---------------  
//    public static final String M_MEMBER_INDEX = "member fund";
//    public static final int INT_M_MEMBER_INDEX = 101;
//    public static final String M_FUND_GROWTH = "fundmgr_growth";
//    public static final int INT_M_FUND_GROWTH = 121;
//    public static final String M_FUND_TECHNOLOGY = "fundmgr_technology";
//    public static final int INT_M_FUND_TECHNOLOGY = 122;
//    public static final String M_FUND_FINANCIAL = "fundmgr_financial";
//    public static final int INT_M_FUND_FINANCIAL = 123;
//    public static final String M_FUND_INDEX = "fundmgr_index";
//    public static final int INT_M_FUND_INDEX = 125;
//
//    public static String fundMgrSubType(int i, String defaultName) {
//        if (i == INT_M_FUND_GROWTH) {
//            return M_FUND_GROWTH;
//        } else if (i == INT_M_FUND_TECHNOLOGY) {
//            return M_FUND_TECHNOLOGY;
//        } else if (i == INT_M_FUND_FINANCIAL) {
//            return M_FUND_FINANCIAL;
//        } else if (i == INT_M_FUND_INDEX) {
//            return M_FUND_INDEX;
////        } else if (i == INT_M_MEMBER_INDEX) {
////            return M_MEMBER_INDEX;
//        } else {
//        }
//
//        return defaultName;
//    }
//    //Account - marginType
////    public static final int INT_STOCK_MODEL = 1;
////    public static final int INT_MARGIN_MODEL = 2;
////    public static final int INT_CUSTOM_1_MODEL = 3;
//    //Account - mfundType
//    //Enable, Disable
//    //Account - simulationType
//    //Enable, Disable
//    //Signal
//    public static float DEFAULT_TRADE_AMOUNT = 5000;
//    public static float SERVICE_API_SMALL_RATE = (float) 0.80;
//    public static float SERVICE_API_LARGE_RATE = (float) 0.50;
//    //autotradeOverride
//    public static int AUTO_TRADE_ATS = 0;
//    public static int AUTO_TRADE_THRESHOLD = 1;
//    public static int AUTO_TRADE_COPYFUNDMGR = 2;
//    public static int AUTO_SIMULATION = 10;
//
//    public static String getAutoTradeType(int autoType) {
//        switch (autoType) {
//            case 0:
//                return "Auto IIS Trading";
//            case 1:
//                return "Threshold Trading";
//            case 2:
//                return "Mirror Member Fund";
//            case 10:
//                return "Simulation Trading";
//        }
//        return " ";
//    }
//
//    public static int isStringAutoTradeType(String inputValue, int defaultValue) {
//        try {
//            if (inputValue.equals("" + AUTO_TRADE_ATS)) {
//                return AUTO_TRADE_ATS;
//            } else if (inputValue.equals("" + AUTO_TRADE_THRESHOLD)) {
//                return AUTO_TRADE_THRESHOLD;
//            } else if (inputValue.equals("" + AUTO_TRADE_COPYFUNDMGR)) {
//                return AUTO_TRADE_COPYFUNDMGR;
//            } else if (inputValue.equals("" + AUTO_SIMULATION)) {
//                return AUTO_SIMULATION;
//            }
//        } catch (NumberFormatException ex) {
//        }
//        return defaultValue;
//    }
//    //Model
//    public static final int INT_STOCK_MODEL = 1;
//    public static final int INT_MARGIN_MODEL = 2;
//    public static final int INT_CUSTOM_1_MODEL = 3;
//    public static final int INT_MV_MODEL = 2;
//    public static final String MV_MODEL_21 = "MV_21";
//    public static final String MV_MODEL_21_VALUE = "15,1";
//    public static final int INT_SB_MODEL = 5;
//    public static final String SB_MODEL_Buy = "SB_Buy";
//    public static final String SB_MODEL_Buy_VALUE = "B";
//    public static final int INT_SELL_MODEL = 7;
//    public static final String SB_MODEL_Sell = "SB_Sell";
//    public static final String SB_MODEL_Sell_VALUE = "S";
//    public static final int INT_NS_MODEL = 0;
//    public static final String SB_MODEL_NN = "NN_AI";
//    public static final String SB_MODEL_NN_VALUE = "N";
//    public static final int INT_RSI_MODEL = 3;
//    public static final String RSI_MODEL_31 = "RSI_31";
//    public static final String RSI_MODEL_31_VALUE = "14,1";
//    public static final String RSI_MODEL_32 = "RSI_32";
//    public static final String RSI_MODEL_32_VALUE = "180,2";
//    //----------------
//    public static final int INT_MACD_MODEL = 1;
//    public static final String MACD_MODEL_11 = "MACD_11";
//    public static final String MACD_MODEL_11_VALUE = "8,17,9";
//    public static final int INT_STO_MODEL = 4;
//    public static final String STO_MODEL_41 = "STO_41";
//    public static final String STO_MODEL_41_VALUE = "14,3";
//    public static final int INT_MVF_MODEL = 8;
//    public static final String MVF_MODEL_81 = "MVF_81";
//    public static final String MVF_MODEL_81_VALUE = "3,15";
//
//    public static String getUserType(int type) {
//        String userT = "";
//        switch (type) {
//            case INT_ADMIN_USER:
//                return ADMIN_USER;
//            case INT_CLIENT_BASIC_USER:
//                return CLIENT_BASIC_USER;
//            case INT_GUEST_USER:
//                return GUEST_USER;
//            case INT_FUND_USER:
//                return FUND_USER;
//            case INT_MEMBER_USER:
//                return MEMBER_USER;
//            case INT_CLIENT_GOLD_USER:
//                return CLIENT_GOLD_USER;
//            case INT_CLIENT_SILVER_USER:
//                return CLIENT_SILVER_USER;
//            case INT_API_USER:
//                return API_USER;
//        }
//
//        return userT;
//    }
//
//    public static int isStringUserType(String inputValue, int defaultValue) {
//        try {
//            if (inputValue.equals(ADMIN_USER)) {
//                return INT_ADMIN_USER;
//            } else if (inputValue.equals(CLIENT_BASIC_USER)) {
//                return INT_CLIENT_BASIC_USER;
//            } else if (inputValue.equals(GUEST_USER)) {
//                return INT_GUEST_USER;
//            } else if (inputValue.equals(FUND_USER)) {
//                return INT_FUND_USER;
//            } else if (inputValue.equals(MEMBER_USER)) {
//                return INT_MEMBER_USER;
//            } else if (inputValue.equals(CLIENT_GOLD_USER)) {
//                return INT_CLIENT_GOLD_USER;
//            } else if (inputValue.equals(CLIENT_SILVER_USER)) {
//                return INT_CLIENT_SILVER_USER;
//            } else if (inputValue.equals(API_USER)) {
//                return INT_API_USER;
//            }
//        } catch (NumberFormatException ex) {
//        }
//        return defaultValue;
//    }
//
//    public static int isStringAccountType(String inputValue, int defaultValue) {
//        try {
//            if (inputValue.equals(CASH_ACCOUNT)) {
//                return INT_CASH_ACCOUNT;
//            } else if (inputValue.equals(TRADING_ACCOUNT)) {
//                return INT_TRADING_ACCOUNT;
//            } else if (inputValue.equals(SIM_TRADING_ACCOUNT)) {
//                return INT_SIM_TRADING_ACCOUNT;
//            }
//        } catch (NumberFormatException ex) {
//        }
//        return defaultValue;
//    }
//    public static final int LOCAL_SERVER_ID = 1;
//    public static final int REMOTE_SERVER_ID = 2;
//    public static final int LOCAL_SYSTEM_REF_ID = 0;
//    private static int systemStatus = ENABLE;
//    private static final long IDGEN_START = (long) (System.currentTimeMillis());
//    private static long idgen = IDGEN_START;
//    //Strategy 
//    public static final String TS_SYS = "System0";
//    public static final int INT_TS_SYS = 0;
//    //Trade Rule
//    public static final String TR_USER = "User Design";
//    public static final int INT_TR_USER = 0;
//    public static final String TR_SYS1 = "System1";
//    public static final int INT_TR_SYS1 = 1;
//    public static final String TR_SYS2 = "System2";
//    public static final int INT_TR_SYS2 = 2;
//    public static final String TR_SYSDEF1 = "System0";
//    public static final int INT_TR_SYSDEF1 = 3;
//    public static final String TR_SYSDEF2 = "System3";
//    public static final int INT_TR_SYSDEF2 = 4;    
//    
//    public static final String TR_CUSTOM1 = "Custom1";
//    public static final int INT_TR_CUSTOM1 = 5;
//    //
//    public static final String TR_RSI = "RSI";
//    public static final int INT_TR_RSI = 1;
//    public static final String TR_MACD = "MACD";
//    public static final int INT_TR_MACD = 2;
//    public static final String TR_MV = "MV";
//    public static final int INT_TR_MV = 3;
//    public static final String TR_SB = "SB";  // Signal buy
//    public static final int INT_TR_SB = 4;
//    public static final String TR_SS = "SS";  // Signal sell
//    public static final int INT_TR_SS = 5;
//    public static final String TR_NN = "AI";  // NN Signal
//    public static final int INT_TR_NN = 6;
//    public static final String TR_REBOUND = "REBOUND";  // wait for technical rebound signal
//    public static final int INT_TR_REBOUND = 10;
//    public static final String TR_W_TREND = "WTREND";  // wait for trend signal
//    public static final int INT_TR_W_TREND = 11;
//    public static final String TR_W_SLOSS = "SLOSS";  // force exit for stop loss
//    public static final int INT_TR_W_SLOSS = 12;
//    public static final String TR_W_TPROFIT = "TPROFIT";  // force exit for Profit taking
//    public static final int INT_TR_W_TPROFIT = 13;
//    public static final String TR_W_VOLATILITY = "VOLATILITY";  // wait for VOLATILITY
//    public static final int INT_TR_W_VOLATILITY = 14;
//    public static final String TR_W_PERFORM = "PERFORM";  // wait for PERFORM
//    public static final int INT_TR_W_PERFORM = 15;
//    public static final String TR_W_DIR = "DIRECTION";  // wait for PERFORM
//    public static final int INT_TR_W_DIR = 16;
//    //  UPDATE AFaccountInfo SET tradeRuleCfg = 'STO_RSI~MACD~MV~SB~REBOUND~WTREND~SLOSS~TPROFIT~VOLATILITY~PERFORM~End'
//    public static final String DEF_CFG_OPT =
//            //CKey.TR_STO_RSI + "~" +
//            CKey.TR_NN + "~"
//            + CKey.TR_W_SLOSS + "~"
//            + CKey.TR_W_TPROFIT + "~"
//            + "End";
//
//    // need synchonize
//    public synchronized static long getUniqueId() {
//        //idgen = idgen & 0xFFFFFF;
//        return idgen++;
//    }
//
//    public static void setSystemStatus(int status) {
//        systemStatus = status;
//    }
//
//    public static boolean isSystemEnable() {
//        if (systemStatus == ENABLE) {
//            return true;
//        }
//        return false;
//    }
////    public static boolean is1MProcessDisable() {
////        if (CKey.EnterTime1Dflag == true) {
////            return true;
////        }
////        if (CKey.EnterTime2Hflag == true) {
////            return true;
////        }
////        if (CKey.EnterTime15Mflag == true) {
////            return true;
////        }
////        return false;
////    }
//    public static int internetUpdateStatus = ENABLE;
//
//    public static void setInternetUpdateStatus(int status) {
//        internetUpdateStatus = status;
//    }
//
//    public static boolean isInternetUpdateEnable() {
//        if (internetUpdateStatus == ENABLE) {
//            return true;
//        }
//        return false;
//    }
//    public static int remoteDBUpdateStatus = ENABLE;
//
//    public static void setRemoteDBUpdateStatus(int status) {
//        remoteDBUpdateStatus = status;
//    }
//    public static String remoteDBVersion = "";
//
//    public static void setRemoteDBVersion(String version) {
//        remoteDBVersion = version;
//    }
//
//    public static boolean isRemoteDBUpdateEnable() {
//        if (remoteDBUpdateStatus == ENABLE) {
//            return true;
//        }
//        return false;
//    }
    public CKey() {
    }

}
