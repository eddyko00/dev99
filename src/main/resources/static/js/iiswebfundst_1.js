
var app = {

// Application Constructor
    initialize: function () {

        $(document).ready(function () {

        });

//        var iisurl = "https://iiswebsrv.herokuapp.com/";
        var iisMsgSession = "iisMsgSession";
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
        console.log(accId);
               
        var fundObjListStr = iisWebObj.fundObjListStr;
        var fundObjList = "";
        if (fundObjListStr != "") {
            fundObjList = JSON.parse(fundObjListStr);
        }
        
        var fundBestObjListStr = iisWebObj.fundBestObjListStr;
        var fundBestObjList = "";
        if (fundBestObjListStr != "") {
            fundBestObjList = JSON.parse(fundBestObjListStr);
        }

        var fundId = iisWebObj.fundId;
//var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr, 'accId': accId,
//     'fundObjListStr': fundObjListStr, 'fundBestObjListStr': fundBestObjListStr, 'fundId': fundId};

//var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr, 'accId': accId, 
//      'fundObjListStr': fundObjListStr, 'fundBestObjListStr': fundBestObjListStr, 'fundId': fundId, 'stockObjListStr': stockObjListStr};
        
        $.ajax({
            url: iisurl + "/cust/" + custObj.username + "/acc/" + accId + "/fundlink/" + fundId + "/st",
            crossDomain: true,
            cache: false,
            beforeSend: function () {
                $("#loader").show();
            },
            error: function () {
                alert('Network failure. Please try again later.');
                window.location.href = "index.html";
            },
            success: function (resultStockList) {
                console.log(resultStockList);
                window.localStorage.setItem(iisMsgSession, "");

                var stockObjListStr = JSON.stringify(resultStockList, null, '\t');
                var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr, 'accId': accId, 
                    'fundObjListStr': fundObjListStr, 'fundBestObjListStr': fundBestObjListStr, 'fundId': fundId, 'stockObjListStr': stockObjListStr};
                window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));
                window.location.href = "fundst.html";
            }
        });
    }
};
app.initialize();





