
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
            if (accObjTmp.type == 140) { //INT_ADMIN_ACCOUNT = 140;
                accObj = accObjTmp;
                break;
            }
        }
        if (accObj == null) {
            window.location.href = "index.html";
        }
        var cuObjStr = iisWebObj.cuObjStr;
        var cuObj = JSON.parse(cuObjStr);
        if (cuObj == null) {
            window.location.href = "index.html";
        }
        var tabName = "COMM";
        if (iisWebObj.tabName != null) {
            tabName = iisWebObj.tabName;
        }
        var CustNListStr = iisWebObj.CustNListStr;
        var CustNListCnt = iisWebObj.CustNListCnt;

        if (cuObj.cmd === 'name') {
            var username = cuObj.username;
            //cust/{username}/uisys/{custid}/custlist?name=");
            $.ajax({
                url: iisurl + "/cust/" + custObj.username + "/uisys/" + custObj.id + "/custlist?name=" + username,
                crossDomain: true,
                cache: false,
                beforeSend: function () {
                    $("#loader").show();
                },
                error: function () {
                    alert('Network failure. Please try again later.');
                    window.location.href = "index.html";
                },
                success: function (resultCuObjList) {
                    console.log(resultCuObjList);

                    if (resultCuObjList != null) {
                        var cuObjListStr = JSON.stringify(resultCuObjList, null, '\t');
                        var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr,
                            'CustNListStr': CustNListStr, 'CustNListCnt': CustNListCnt, 'tabName': tabName,
                            'cuObjStr': cuObjStr, 'cuObjListStr': cuObjListStr};
                        window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));

                        if (resultCuObjList.length == 0) {
                            window.location.href = "accountadm_1.html";
                            return;
                        }
                        window.location.href = "accountadmcu.html";
                    }
                }
            });
        }
    }
};
app.initialize();





