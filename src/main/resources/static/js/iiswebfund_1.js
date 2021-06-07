
var app = {

// Application Constructor
    initialize: function () {

        $(document).ready(function () {

        });



//        var iisurl = "https://iiswebsrv.herokuapp.com/";
//        var iisMsgSession = "iisMsgSession";

        var iisWebSession = "iisWebSession";
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
         var accId = iisWebObj.accId
         
//var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr, 'accId': accId};
      
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
//var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr, 'accId': accId,
//      'fundObjListStr': fundObjListStr, 'fundBestObjListStr': fundBestObjListStr};
//        
        $.ajax({
            url: iisurl + "/cust/" + custObj.username + "/acc/" + accId + "/fundbestlist",
            crossDomain: true,
            cache: false,
            beforeSend: function () {
                $("#loader").show();
            },
            error: function () {
                alert('Network failure. Please try again later.');
                window.location.href = "index.html";
            },
            success: function (resultFundBestObjList) {
                console.log(resultFundBestObjList);
//                if (resultAccObjList === "") {
//                    window.location.href = "index.html";
//                }
                var fundBestObjListStr = "";
                if (resultFundBestObjList !== "") {
                    if (resultFundBestObjList.length > 0) {
                        fundBestObjListStr = JSON.stringify(resultFundBestObjList, null, '\t');
                    }
                }

                $.ajax({
                    url: iisurl + "/cust/" + custObj.username + "/acc/" + accId + "/fundlink",
                    crossDomain: true,
                    cache: false,
                    beforeSend: function () {
                        $("#loader").show();
                    },
                    error: function () {
                        alert('Network failure. Please try again later.');
                        window.location.href = "index.html";
                    },
                    success: function (resultFundObjList) {
                        console.log(resultFundObjList);
//                if (resultAccObjList === "") {
//                    window.location.href = "index.html";
//                }
                        var fundObjListStr = "";
                        if (resultFundObjList !== "") {
                            if (resultFundObjList.length > 0) {
                                fundObjListStr = JSON.stringify(resultFundObjList, null, '\t');
                            }
                        }
                        var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr, 'accId': accId,
                            'fundObjListStr': fundObjListStr, 'fundBestObjListStr': fundBestObjListStr};
                        window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));
                        window.location.href = "fund.html";

                    }
                });

            }
        });

    }
};
app.initialize();





