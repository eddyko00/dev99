
var app = {

// Application Constructor
    initialize: function () {

        $(document).ready(function () {

        });


//        var iisurl = "https://iiswebsrv.herokuapp.com/";
        var iisWebSession = "iisWebSession";
//        var custObj = 'custObj';
//        var accList = 'accList';

        var iisWebObjStr = window.localStorage.getItem(iisWebSession);
        var iisWebObj = JSON.parse(iisWebObjStr);
//        console.log(iisWebObj);
        var iisurlStr = iisWebObj.iisurlStr;
        iisurl = iisurlStr;

        var custObjStr = iisWebObj.custObjStr;
        if (custObjStr == null) {
            window.location.href = "index.html";
        }
        var custObj = JSON.parse(custObjStr);
        var accObjListStr = iisWebObj.accObjListStr;
        var accObjList = JSON.parse(accObjListStr);
        var accId = iisWebObj.accId;

        var accObj = null;
        for (i = 0; i < accObjList.length; i++) {
            var accObjTmp = accObjList[i];
            if (accObjTmp.id == accId) {
                accObj = accObjTmp;
                break;
            }
        }
        if (accObj == null) {
            window.location.href = "index.html";
        }
        var StNListCnt = iisWebObj.StNListCnt;
        var trFilter = "";
        if (iisWebObj.trFilter != null) {
            trFilter = iisWebObj.trFilter;
        }
        var iisMsgSession = "iisMsgSession";
        var msgObjStr = window.localStorage.getItem(iisMsgSession);
//        msgObjStr ="This feature does not allow for GUEST account";
        if (msgObjStr !== "") {
            functionAlertConfirm(msgObjStr, function ok() {
            });
        }

        var stockObjListStr = iisWebObj.stockObjListStr;
        var stockObjList = JSON.parse(stockObjListStr);
        var sockId = iisWebObj.sockId;
        console.log(sockId);

        var stockObj = null;
        for (i = 0; i < stockObjList.length; i++) {
            var stockObjTmp = stockObjList[i];
            if (stockObjTmp.id == sockId) {
                stockObj = stockObjTmp;
                break;
            }
        }
        if (stockObj == null) {
            window.location.href = "index.html";
        }
        var trObjListStr = iisWebObj.trObjListStr;
        var trObjList = JSON.parse(trObjListStr);
        var trObjacc = null;
        var percentSt = "";
        var close = 0;
        var preClose = 0;
        if (stockObj.afstockInfo != null) {
            close = stockObj.afstockInfo.fclose;
            preClose = stockObj.prevClose;
            var percent = 100 * (close - preClose) / preClose;
            if (percent > 0) {
                percentSt = "<font style= color:green>" + percent.toFixed(2) + '%' + "</font>";
            } else {
                percentSt = "<font style= color:red>" + percent.toFixed(2) + '%' + "</font>";
            }
        }

        var stockData = null;
        var stockD = stockObj.data;
        if (stockD !== "") {
            var objDataStr = stockD.replaceAll('#', '"');
            var objData = JSON.parse(objDataStr);
            if (objData != null) {
                stockData = objData;
            }
        }

        var stStr = 'Trading Model Listing<br>';
        var stStatus = "";
        if (stockObj.substatus == 12) { //ConstantKey.STOCK_SPLIT STOCK_DETLA = 12
            stStatus = "<font style= color:red>St: PriceDif>20%</font>";
        }
        if (stockObj.substatus == 10) { //ConstantKey.STOCK_SPLIT STOCK_SPLIT = 10
            stStatus = "<font style= color:red>St: Split</font>";
        }
        if (stockObj.substatus == 2) { //INITIAL = 2;
            stStatus = "St: Init";
        }
        if (stockObj.substatus == 0) { //INITIAL = 2;
            stStatus = "<font style= color:green>St: Ready</font>";
        }

        stStr += stockObj.stockname + '<br>' + stockObj.updateDateD + " " + stStatus + '<br>' +
                'Pre Close:' + preClose + '  Close:' + close + '  Per:' + percentSt
        stStr += '<br>' + 'Long trend:' + stockObj.longterm + '<br>' + 'Short trend:' + stockObj.shortterm + '<br>' +
                'Direction:' + stockObj.direction

        $("#0").html('<h1>' + stStr + '</h1>');

        $("#accheader").html(' ' + accObj.accountname + ' ' + stockObj.symbol);

        $("#myid").append('<li></li>');

        if (stockData !== null) {
            var stockMsg = "Stock Analysis:<p>";
            var rec = stockData.rec;
            if (rec < recMsg.length) {
                stockMsg += recMsg[rec];
            }
            if (stockData.pCl > 0) {
                var updown = " going up to $";
                if (stockData.pCl < close) {
                    updown = " going down to $";
                }
                stockMsg += "The predict price will be " + updown + stockData.pCl.toFixed(2) + " in the next few days.";
            }
            stockMsg += "<p>";

            var stockMsg1 = "";
            if (stockData.upDn !== 0) {
                if (stockData.upDn > 50) {
                    stockMsg1 = "The up trend is strong";
                }
                if (stockData.upDn > 70) {
                    stockMsg1 = "The up trend is very strong";
                }
                if (stockData.top !== 0) {
                    if (stockData.top === 1) {
                        stockMsg1 += " and price is near to the top.";
                    } else {
                        stockMsg1 += ".";
                    }
                }
            }
            if (stockData.upDn < -50) {
                stockMsg1 = "The down trend is strong";
                if (stockData.upDn < -70) {
                    stockMsg1 = "The down trend is very strong";
                }
                if (stockData.top !== 0) {
                    if (stockData.top === -1) {
                        stockMsg1 += " and price is near to the bottom.";
                    } else {
                        stockMsg1 += ".";
                    }
                }
            }
        }

        $("#myid").append(stockMsg + stockMsg1);



        function functionConfirm(msg, myYes, myNo, myOk) {
            var confirmBox = $("#confirm");
            confirmBox.find(".message").text(msg);
            confirmBox.find(".yes,.no,.ok").unbind().click(function () {
                confirmBox.hide();
                window.localStorage.setItem(iisMsgSession, "");
            });
            confirmBox.find(".yes").click(myYes);
            confirmBox.find(".no").click(myNo);
            confirmBox.find(".ok").click(myOk);
            confirmBox.show();
        }

        function functionAlertConfirm(msg, myYes, myNo, myOk) {
            var confirmBox = $("#alertconfirm");
            confirmBox.find(".message").text(msg);
            confirmBox.find(".yes,.no,.ok").unbind().click(function () {
                confirmBox.hide();
                window.localStorage.setItem(iisMsgSession, "");
            });
            confirmBox.find(".yes").click(myYes);
            confirmBox.find(".no").click(myNo);
            confirmBox.find(".ok").click(myOk);
            confirmBox.show();
        }

// example        
//alert("AJAX request successfully completed");
//var jsonObj = JSON.parse(jsonStr);
//var jsonPretty = JSON.stringify(jsonObj, null, '\t');

    }
};
app.initialize();

