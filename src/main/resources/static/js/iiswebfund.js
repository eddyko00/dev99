
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

//var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr, 'accId': accId,
//      'fundObjListStr': fundObjListStr, 'fundBestObjListStr': fundBestObjListStr};

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


        var selectFundObj = null;

//var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr, 'accId': accId,
//     'fundObjListStr': fundObjListStr, 'fundBestObjListStr': fundBestObjListStr, 'fundId': fundId};

        $("#accheader").html("Fund Mgr Account" + ' ' + '<a href="#page-intro"><small>Help</small></a>');

        $("#myid").html(" "); //clear the field
        for (i = 0; i < fundObjList.length; i++) {
            var accObj = fundObjList[i];
            console.log(accObj);
            var accName = accObj.accountname;
            var accId = accObj.id;

            var htmlName = '';
            htmlName += '<li id="' + accId + '"><a href="#">';
            var subStatus = "<font style= color:red>Status:Pending Remove </font>";
            if (accObj.substatus === 0) {
                subStatus = "<font style= color:green>Status:Subscribed </font>";
            }
            htmlName += '<br>Account: ' + accName + ' ' + subStatus;

            if (accObj.type === INT_MUTUAL_FUND_ACCOUNT) { //INT_MUTUAL_FUND_ACCOUNT = 120;
                var total = accObj.investment + accObj.balance;
                var investSt = Number(accObj.investment).toLocaleString('en-US', {style: 'currency', currency: 'USD'});
                var balSt = Number(accObj.balance).toLocaleString('en-US', {style: 'currency', currency: 'USD'});

                var totSt = Number(total).toLocaleString('en-US', {style: 'currency', currency: 'USD'});
                htmlName += '<br>Acc Bal: ' + investSt + ' Cur Bal: ' + balSt + ' Total: ' + totSt;

                var htmlBtn = '<div id="myidbtn" data-theme="a" >';
                htmlBtn += '<a href="#" id="' + accObj.id + '" type="remove" data-role="button" data-theme="a">Remove Subscription</a>';

                htmlBtn += '</div>';
                htmlName += htmlBtn;
            }

            htmlName += '</a></li>';
            $("#myid").append(htmlName);
        }

        if (fundObjList.length === 0) {
            $("#myid").html("No Fund Manager subscription "); //clear the field
        }


        $("#fundid").html("  "); //clear the field
        var fundAvailable = false;
        for (i = 0; i < fundBestObjList.length; i++) {
            var accObj = fundBestObjList[i];
            console.log(accObj);

            var subscribed = false;
            for (j = 0; j < fundObjList.length; j++) {
                var accfeatObj = fundObjList[j];
                if (accfeatObj.accountname === accObj.accountname) {
                    subscribed = true;
                    break;
                }
            }
            if (subscribed === true) {
                continue;
            }
            var accName = accObj.accountname;
            var accId = accObj.id;

            var htmlName = '';
            htmlName += '<li id="' + accId + '"><a href="#">';

            htmlName += '<br>Account: ' + accName;

            if (accObj.type === INT_MUTUAL_FUND_ACCOUNT) { //INT_MUTUAL_FUND_ACCOUNT = 120;
                var total = accObj.investment + accObj.balance;
                var investSt = Number(accObj.investment).toLocaleString('en-US', {style: 'currency', currency: 'USD'});
                var balSt = Number(accObj.balance).toLocaleString('en-US', {style: 'currency', currency: 'USD'});

                var totSt = Number(total).toLocaleString('en-US', {style: 'currency', currency: 'USD'});
                htmlName += '<br>Acc Bal: ' + investSt + ' Cur Bal: ' + balSt + ' Total: ' + totSt;
                fundAvailable = true;
            }

            htmlName += '</a></li>';
            $("#fundid").append(htmlName);
        }

        if (fundAvailable == false) {
            $("#fundid").html("No Fund Manager Avaliable "); //clear the field
        }

        var removeFund = false;
        $("ul[id*=myid] li").click(function () {
            if (removeFund === true) {
                removeFund = false;
                return;
            }
            var fundId = $(this).attr('id');
            console.log(fundId);
            if (fundId == 0) {
//                alert(accId);
                return;
            }

            var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr, 'accId': accId,
                'fundObjListStr': fundObjListStr, 'fundBestObjListStr': fundBestObjListStr, 'fundId': fundId};
            window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));
            window.location.href = "fundst_1.html";
        });



        $("[id*=myidbtn] a").click(function () {
            var fundId = $(this).attr('id');
            console.log(fundId);
            if (fundId == 0) {
//                alert(accId);
                return;
            }
            var type = $(this).attr('type');
            if (type === 'remove') {
                removeFund = true;
            }

            var fundObj = null;
            for (i = 0; i < fundObjList.length; i++) {
                var fundObjTmp = fundObjList[i];
                if (fundObjTmp.id == fundId) {
                    fundObj = fundObjTmp;
                    break;
                }
            }
            if (fundObj === null) {
                return;
            }
            selectFundObj = fundObj;
            $("#fdelheader").html("Fund Mgr: " + selectFundObj.accountname);

            window.location.href = "#page-remove";

        });

        $("ul[id*=fundid] li").click(function () {
//            alert($(this).html()); // gets innerHTML of clicked li
//            alert($(this).text()); // gets text contents of clicked li
            var fundId = $(this).attr('id');
            console.log(fundId);
            if (fundId == 0) {
//                alert(accId);
                return;
            }

            var fundObj = null;
            for (i = 0; i < fundBestObjList.length; i++) {
                var fundObjTmp = fundBestObjList[i];
                if (fundObjTmp.id == fundId) {
                    fundObj = fundObjTmp;
                    break;
                }
            }
            if (fundObj === null) {
                return;
            }
            selectFundObj = fundObj;
            $("#fundheader").html("Fund Mgr: " + selectFundObj.accountname);

            window.location.href = "#page-add";

        });


        $("#subaddbtn").click(function () {
            if (selectFundObj === null) {
                return;
            }
            console.log(selectFundObj.id);

            $.ajax({
                url: iisurl + "/cust/" + custObj.username + "/acc/" + accId + "/fundlink/" + selectFundObj.id + "/add",
                crossDomain: true,
                cache: false,
                success: handleResult
            }); // use promises

            function handleResult(result) {
//          SUCC = 1;  EXISTED = 2; FAIL =0;
                window.location.href = "fund_1.html";

            }
        });


        $("#subremovebtn").click(function () {
            if (selectFundObj === null) {
                return;
            }
            console.log(selectFundObj.id);

            $.ajax({
                url: iisurl + "/cust/" + custObj.username + "/acc/" + accId + "/fundlink/" + selectFundObj.id + "/remove",
                crossDomain: true,
                cache: false,
                success: handleResult
            }); // use promises

            function handleResult(result) {
//          SUCC = 1;  EXISTED = 2; FAIL =0;
                window.location.href = "fund_1.html";

            }
        });



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





