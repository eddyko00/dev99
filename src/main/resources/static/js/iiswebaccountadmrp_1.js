
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
            if (accObjTmp.type == 140) { //administrator
                accObj = accObjTmp;
                break;
            }
        }
        if (accObj == null) {
            window.location.href = "index.html";
        }

        var yearRpt = 0;
        if (iisWebObj.yearRpt != null) {
            yearRpt = iisWebObj.yearRpt;
        }

        ///cust/{username}/uisys/{custid}/accounting/report?year=");
        $.ajax({
            url: iisurl + "/cust/" + custObj.username + "/uisys/" + custObj.id + "/accounting/report?year="+yearRpt,
            crossDomain: true,
            cache: false,
            beforeSend: function () {
                $("#loader").show();
            },
            error: function () {
                alert('Network failure. Please try again later.');
                window.location.href = "index.html";
            },
            success: function (resultRptObj) {
                console.log(resultRptObj);

                var reportObjStr = JSON.stringify(resultRptObj, null, '\t');
                var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr,
                    'reportObjStr': reportObjStr, 'entryObjStr': "", 'yearRpt': yearRpt};

                window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));

                window.location.href = "accountadmrp.html";
            }
        });

    }
};
app.initialize();





