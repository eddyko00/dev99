
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
        var accObj = null;
        for (i = 0; i < accObjList.length; i++) {
            var accObjTmp = accObjList[i];
            if (accObjTmp.type == 140) { //administration
                accObj = accObjTmp;
                break;
            }
        }
        if (accObj == null) {
            window.location.href = "index.html";
        }
        var tabName = "COMM";
        if (iisWebObj.tabName != null) {
            tabName = iisWebObj.tabName;
        }

        var CustNListStr = iisWebObj.CustNListStr;
        var CustNListCnt = iisWebObj.CustNListCnt;

        $.ajax({
            url: iisurl + "/cust/" + custObj.username + "/acc/" + accObj.id + "/comm",
            crossDomain: true,
            cache: false,
            beforeSend: function () {
                $("#loader").show();
            },
            error: function () {
                alert('Network failure. Please try again later.');
                window.location.href = "index.html";
            },
            success: function (resultCommObjList) {
                console.log(resultCommObjList);
                var commObjListStr = "";

                if (resultCommObjList !== "") {
                    commObjListStr = JSON.stringify(resultCommObjList, null, '\t');
                }

                var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr, 'commObjListStr': commObjListStr,
                    'CustNListStr': CustNListStr, 'CustNListCnt': CustNListCnt, 'tabName': tabName};
                window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));
                window.location.href = "accountadm.html";
            }
        });
    }
};
app.initialize();





