
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
        var StNListCnt = iisWebObj.StNListCnt;
        var trFilter = "";
        if (iisWebObj.trFilter != null) {
            trFilter = iisWebObj.trFilter;
        }
        var trObjListStr = iisWebObj.trObjListStr;
        var trObjList = JSON.parse(trObjListStr);

        var trName = iisWebObj.trName;
        var trname = trName;
        var symbol = stockObj.symbol;

        $.ajax({
            url: iisurl + "cust/" + custObj.username + "/acc/" + accId + "/st/" + symbol + "/tr/" + trname + "/tran",
            crossDomain: true,
            cache: false,
            beforeSend: function () {
                $("#loader").show();
            },
            error: function () {
                alert('Network failure. Please try again later.');
                window.location.href = "index.html";
            },
            success: function (resultTranList) {
                console.log(resultTranList);

                var tranObjListStr = JSON.stringify(resultTranList, null, '\t');
                var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr,
                    'StNListCnt': StNListCnt, 'accId': accId, 'trFilter': trFilter, 'stockObjListStr': stockObjListStr, 'sockId': sockId,
                    'trObjListStr': trObjListStr, 'trName': trName, 'tranObjListStr': tranObjListStr};

                window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));

                window.location.href = "accounttran.html";
            }
        });

    }
};
app.initialize();





