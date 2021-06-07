
var app = {

// Application Constructor
    initialize: function () {

        $(document).ready(function () {

        });



//        var iisurl = "https://iiswebsrv.herokuapp.com/";
        var iisMsgSession = "iisMsgSession";
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


        $.ajax({
            url: iisurl + "/cust/" + custObj.username + "/acc/",
            crossDomain: true,
            cache: false,
            beforeSend: function () {
                $("#loader").show();
            },
            error: function () {
                alert('Network failure. Please try again later.');
                window.location.href = "index.html";
            },
            success: function (resultAccObjList) {
                console.log(resultAccObjList);
                if (resultAccObjList === "") {
                    window.location.href = "index.html";
                }
//
                var accObjListStr = JSON.stringify(resultAccObjList, null, '\t');
                var accObjList = JSON.parse(accObjListStr);

                var accObj = null;
                for (i = 0; i < accObjList.length; i++) {
                    var accObjTmp = accObjList[i];
                    if (accObjTmp.type == 110) { //INT_TRADING_ACCOUNT
                        accObj = accObjTmp;
                        break;
                    }
                }
                if (accObj == null) {
                    window.location.href = "index.html";
                }
                $.ajax({
                    url: iisurl + "/cust/" + custObj.username + "/acc/" + accObj.id + "/comm",
                    crossDomain: true,
                    cache: false,
                    beforeSend: function () {
                        $("#loader").show();
                    },

                    success: function (resultCommObjList) {
                        console.log(resultCommObjList);

                        var commObjListStr = "";
                        if (resultCommObjList !== "") {
                            if (resultCommObjList.length > 0) {
                                commObjListStr = JSON.stringify(resultCommObjList, null, '\t');
                            }
                        }

                        var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr,
                            'commObjListStr': commObjListStr};
                        window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));

                        window.localStorage.setItem(iisMsgSession, "");

                        window.location.href = "account.html";

                    }
                });
            }
        });

    }
};
app.initialize();





