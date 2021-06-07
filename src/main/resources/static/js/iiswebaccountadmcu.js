
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
        var tabName = "COMM";
        if (iisWebObj.tabName != null) {
            tabName = iisWebObj.tabName;
        }
        var CustNListStr = iisWebObj.CustNListStr;
        var CustNListCnt = iisWebObj.CustNListCnt;

        var cuObjStr = iisWebObj.cuObjStr;

        var cuObjListStr = iisWebObj.cuObjListStr;
        var cuObjList = JSON.parse(cuObjListStr);
        if (cuObjList == null) {
            window.location.href = "index.html";
        }
        if (cuObjList.length != 1) {
            window.location.href = "index.html";
        }
        var cuObj = cuObjList[0];  // pick the first one

        $("#accheader").html("Admin Control");

        $("#myid").html(" "); //clear the field

        if (cuObjListStr !== "") {
            var cuObjList = JSON.parse(cuObjListStr);

            var htmlhead = '<div class="ui-grid-b">';
            htmlhead += '<div class="ui-block-a" style="width:30%"><strong>Date</strong></div>';
            htmlhead += '<div class="ui-block-b" style="width:5%"></div>';
            htmlhead += '<div class="ui-block-c">Msg</div>';
            htmlhead += '</div>';

            $("#myid").html('<li id="0" >' + htmlhead + '</li>');

            for (i = 0; i < cuObjList.length; i++) {
                var cuObj = cuObjList[i];
                var cuId = cuObj.id;

                var htmlName = '<div class="ui-grid-b">';
                htmlName += '<div class="ui-block-a" style="width:20%"><strong>' + cuObj.startdate + '</strong></div>';
                htmlName += '<div class="ui-block-b" style="width:5%"> </div>';
                htmlName += '<div class="ui-block-c">' + cuObj.username
                        + ' First:' + cuObj.firstname
                        + ' Last:' + cuObj.lastname

                        + '<br>Type:' + cuObj.type
                        + ' Status:' + cuObj.status
                        + ' SubStatus:' + cuObj.substatus

                        + '<br>Bill date:' + cuObj.updatedatedisplay

                        + '<br>Balance:' + cuObj.balance
                        + '  AmountDue:' + cuObj.payment

                        + '</div>';
                htmlName += '</div>';

                $("#myid").append('<li id="' + cuId + '" >' + htmlName + '</li>');

                if (cuObj.status !== 0) {
                    $("#logheader").hide();
                }
                if (cuObj.type === 99) {
                    $("#logheader").hide();
                    $("#statusheader").hide();
                }
            }
        }


        $("ul[id*=myid] li").click(function () {
//            alert($(this).html()); // gets innerHTML of clicked li
//            alert($(this).text()); // gets text contents of clicked li
            var Id = $(this).attr('id');
            console.log(Id);
            if (Id === 0) {
//                alert(accId);
                return;
            }

            return;
//            var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr};
//            window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));
//            window.location.href = "#";
        });


        $("#statussubmit").click(function () {
            var subStatus = document.getElementById("statusSt").value;
            if (subStatus === "") {
                window.location.href = "accountadm.html";
                return;
            }

            var customername = cuObj.username;
            var subS = subStatus;
            //cust/{username}/uisys/{custid}/cust/{customername}/update?status=&payment=&balance="
            $.ajax({
                url: iisurl + "/cust/" + custObj.username + "/uisys/" + custObj.id + "/cust/" + customername
                        + "/update?status=" + subS,
                crossDomain: true,
                cache: false,
                success: handleResult
            }); // use promises

            // add cordova progress indicator https://www.npmjs.com/package/cordova-plugin-progress-indicator
            function handleResult(result) {
                console.log(result);
                // result 1 = good, 0 = error                
                var resultmsg = "Enable Status result: " + result;
                if (result == '1') {
                    resultmsg += "  - success";
                } else {
                    resultmsg += "  - fail";
                }
                alert(resultmsg);

                var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr,
                    'cuObjStr': cuObjStr};
                window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));

                window.location.href = "accountadmcu_1.html";
            }
        });
        
        $("#accpaidsubmit").click(function () {
            var accpaid = document.getElementById("accpaid").value;
            if (accpaid === "") {
                window.location.href = "accountadm.html";
                return;
            }

            var customername = cuObj.username;
            var balance = accpaid;
            //cust/{username}/uisys/{custid}/cust/{customername}/update?status=&payment=&balance="
            $.ajax({
                url: iisurl + "/cust/" + custObj.username + "/uisys/" + custObj.id + "/cust/" + customername
                        + "/update?balance=" + balance + "&reason=R_USER_PAYMENT",
                crossDomain: true,
                cache: false,
                success: handleResult
            }); // use promises

            // add cordova progress indicator https://www.npmjs.com/package/cordova-plugin-progress-indicator
            function handleResult(result) {
                console.log(result);
                var resultmsg = "Account Balance Update result: " + result;
                if (result == '1') {
                    resultmsg += "  - success";
                } else {
                    resultmsg += "  - fail";
                }
                alert(resultmsg);


                var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr,
                    'cuObjStr': cuObjStr};
                window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));

                window.location.href = "accountadmcu_1.html";
            }
        });
        

        $("#accdrawsubmit").click(function () {
            var accdraw = document.getElementById("accdraw").value;
            if (accdraw === "") {
                window.location.href = "accountadm.html";
                return;
            }

            var customername = cuObj.username;
            var balance = -accdraw;
            //cust/{username}/uisys/{custid}/cust/{customername}/update?status=&payment=&balance="
            $.ajax({
                url: iisurl + "/cust/" + custObj.username + "/uisys/" + custObj.id + "/cust/" + customername
                        + "/update?balance=" + balance + "&reason=R_USER_WITHDRAWAL",
                crossDomain: true,
                cache: false,
                success: handleResult
            }); // use promises

            // add cordova progress indicator https://www.npmjs.com/package/cordova-plugin-progress-indicator
            function handleResult(result) {
                console.log(result);
                var resultmsg = "Account Balance Update result: " + result;
                if (result == '1') {
                    resultmsg += "  - success";
                } else {
                    resultmsg += "  - fail";
                }
                alert(resultmsg);


                var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr,
                    'cuObjStr': cuObjStr};
                window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));

                window.location.href = "accountadmcu_1.html";
            }
        });


        $("#accbalancesubmit").click(function () {
            var accbalance = document.getElementById("accbalance").value;
            if (accbalance === "") {
                window.location.href = "accountadm.html";
                return;
            }

            var customername = cuObj.username;
            var balance = accbalance;
            //cust/{username}/uisys/{custid}/cust/{customername}/update?status=&payment=&balance="
            $.ajax({
                url: iisurl + "/cust/" + custObj.username + "/uisys/" + custObj.id + "/cust/" + customername
                        + "/update?balance=" + balance,
                crossDomain: true,
                cache: false,
                success: handleResult
            }); // use promises

            // add cordova progress indicator https://www.npmjs.com/package/cordova-plugin-progress-indicator
            function handleResult(result) {
                console.log(result);
                var resultmsg = "Account Balance Update result: " + result;
                if (result == '1') {
                    resultmsg += "  - success";
                } else {
                    resultmsg += "  - fail";
                }
                alert(resultmsg);


                var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr,
                    'cuObjStr': cuObjStr};
                window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));

                window.location.href = "accountadmcu_1.html";
            }
        });

        $("#accpaymentsubmit").click(function () {
            var accbalance = document.getElementById("accpayment").value;
            if (accbalance === "") {
                window.location.href = "accountadm.html";
                return;
            }

            var customername = cuObj.username;
            var balance = accbalance;
            //cust/{username}/uisys/{custid}/cust/{customername}/update?status=&payment=&balance="
            $.ajax({
                url: iisurl + "/cust/" + custObj.username + "/uisys/" + custObj.id + "/cust/" + customername
                        + "/update?payment=" + balance,
                crossDomain: true,
                cache: false,
                success: handleResult
            }); // use promises

            // add cordova progress indicator https://www.npmjs.com/package/cordova-plugin-progress-indicator
            function handleResult(result) {
                console.log(result);
                var resultmsg = "Account payment Update result: " + result;
                if (result == '1') {
                    resultmsg += "  - success";
                } else {
                    resultmsg += "  - fail";
                }
                alert(resultmsg);


                var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr,
                    'cuObjStr': cuObjStr};
                window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));

                window.location.href = "accountadmcu_1.html";
            }
        });



        $("#loginsubmit").click(function () {
//            var r = confirm("Do you want to remote login?");
//            if (r == false) {
//                return;
//            }
            var txemail = cuObj.username;
            var txtpassword = cuObj.password;

            $.ajax({
                url: iisurl + "cust/login?email=" + txemail + "&pass=" + txtpassword,
                crossDomain: true,
                cache: false,
                success: handleResult
            }); // use promises

            // add cordova progress indicator https://www.npmjs.com/package/cordova-plugin-progress-indicator

            function handleResult(result) {

                var custObj = result.custObj;
                console.log(custObj);

                var custObjStr = JSON.stringify(custObj, null, '\t');
                var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr};
                window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));

//                var iisWebObjStr = window.localStorage.getItem('iisWebSession');
//                var iisWebObj = JSON.parse(iisWebObjStr);
//                console.log(iisWebObj);

                if (custObj != null) {
                    window.location.href = "account_1.html";
                } else {

                }
            }
        });



// example        
//alert("AJAX request successfully completed");
//var jsonObj = JSON.parse(jsonStr);
//var jsonPretty = JSON.stringify(jsonObj, null, '\t');

    }
};
app.initialize();





