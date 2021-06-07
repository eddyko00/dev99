
var iis_ver = 1.1; // need to match with CKey iis_ver
var iisurl_HERO = "https://iiswebsrv.herokuapp.com/";
var iisurl_OP = "https://iiswebsrv1.herokuapp.com/";
var iisurl_LOCAL = "http://localhost:8080/";
var iisurl = iisurl_HERO; // iisurl_LOCAL; // iisurl_HERO;  //http does not work in OP
var S_PENDING = -1;     // no trade
var S_NEUTRAL = 0;
var SS_LONG_BUY = 11;
var S_LONG_BUY = 1;
var S_BUY = 1;
var SS_SHORT_SELL = 22;
var S_SHORT_SELL = 2;
var S_SELL = 2;
var S_STOPLOSS_BUY = 3; // stop loss buy
var S_STOPLOSS_SELL = 4; // stop loss sell
var S_EXIT_LONG = 5;
var S_EXIT_SHORT = 6;
var S_EXIT = 8;
var TRADING_AMOUNT = 6000;
var INT_MUTUAL_FUND_ACCOUNT = 120;

var recMsg = ["NN1 AI cannot find a good signal pattern. You should wait unit the pattern is found.",
    "NN1 AI is able to find a signal pattern. You can follow signal.",
    "NN1 AI finds a strong related signal pattern. You should follow the signal."];
