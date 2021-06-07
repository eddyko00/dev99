
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

        var commObjListStr = iisWebObj.commObjListStr;

        $("#accheader").html("System Control");

        var tabName = "COMM";
        if (iisWebObj.tabName != null) {
            tabName = iisWebObj.tabName;
        }

        var htmltrHeader = "";
        if (tabName === "CUST") {
            htmltrHeader += '<button type="submit" id="custtab" class="ui-btn ui-corner-all ui-shadow ui-btn-c ui-btn-icon-left"><small>CustTab</small></button>';
            htmltrHeader += '<button type="submit" id="commtab" class="ui-btn ui-corner-all ui-shadow ui-btn-b ui-btn-icon-left"><small>CommTab</small></button>';
            htmltrHeader += '<br>';
            $("#commheader").hide(0);
            $("#commid").hide(0);
        } else if (tabName === "COMM") {
            htmltrHeader += '<button type="submit" id="custtab" class="ui-btn ui-corner-all ui-shadow ui-btn-b ui-btn-icon-left"><small>CustTab</small></button>';
            htmltrHeader += '<button type="submit" id="commtab" class="ui-btn ui-corner-all ui-shadow ui-btn-c ui-btn-icon-left"><small>CommTab</small></button>';
            htmltrHeader += '<br>';
            $("#custheader").hide(0);
            $("#myid").hide();
        } else { 

        }
        $("#trheader").html(htmltrHeader);

        var numDispCust = 5;

        $("#commid").html(" "); //clear the field
        var htmlhead = '<div class="ui-grid-b">';
        htmlhead += '<div class="ui-block-a" style="width:30%"><strong>Date</strong></div>';
        htmlhead += '<div class="ui-block-b" >Id</div>';
        htmlhead += '<div class="ui-block-c" >Name</div>';
        htmlhead += '</div>';

        $("#commid").html('<li id="0" >' + htmlhead + '</li>');

        if (commObjListStr !== "") {
            var commObjList = JSON.parse(commObjListStr);

            for (i = 0; i < commObjList.length; i++) {
                var commObj = commObjList[i];
                var commId = commObj.id;

                var htmlName = '<div class="ui-grid-b">';
                htmlName += '<div class="ui-block-a" style="width:30%"><strong>' + commObj.updatedatedisplay + '</strong></div>';
                htmlName += '<div class="ui-block-b" >' + commId + '</div>';
                htmlName += '<div class="ui-block-c" >' + commObj.name + ' ' + commObj.accountid + '</div>';
                htmlName += '</div>';
                htmlName += '<p>' + commObj.data + '</p>';
                $("#commid").append('<li id="' + commId + '" >' + htmlName + '</li>');

            }
        }

        var CustNListStr = iisWebObj.CustNListStr;
        var CustNListCnt = iisWebObj.CustNListCnt;

        if (CustNListStr !== "") {
            var CustNList = JSON.parse(CustNListStr);
            $("#myid").html(" "); //clear the field

            var htmlhead = '<div class="ui-grid-a">';
            htmlhead += '<div class="ui-block-a" ><strong>Cust Name</strong></div>';
            htmlhead += '<div class="ui-block-b" ></div>';
            htmlhead += '</div>';

            $("#myid").html('<li id="0" >' + htmlhead + '</li>');
            var beg = CustNListCnt * numDispCust;
            if (beg > CustNList.length) {
                beg = CustNList.length;
            }
            var end = beg + numDispCust;
            if (end > CustNList.length) {
                end = CustNList.length;
            }
            for (i = beg; i < end; i++) {

                var CustN = CustNList[i];
                var commId = i + 1;

                var htmlName = '<div class="ui-grid-a">';
                htmlName += '<div class="ui-block-a" ><strong>' + CustN + '</strong></div>';
                htmlhead += '<div class="ui-block-b" ></div>';
                htmlName += '</div>';

                $("#myid").append('<li id="' + commId + '" ><a href="#">' + htmlName + '</a></li>');

            }
        }


        $("ul[id*=myid] li").click(function () {
//            alert($(this).html()); // gets innerHTML of clicked li
//            alert($(this).text()); // gets text contents of clicked li
            var Id = $(this).attr('id');
            console.log(Id);
            if (Id === 0) {
                return;
            }
            var username = CustNList[Id - 1];
            var cuObj = {'cmd': 'name', 'username': username,
                'firstid': '0', 'lastid': '0'};
            var cuObjStr = JSON.stringify(cuObj);

            var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr,
                'CustNListStr': CustNListStr, 'CustNListCnt': CustNListCnt, 'cuObjStr': cuObjStr, 'tabName': tabName};
            window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));

            window.location.href = "accountadmcu_1.html";

            return;

        });



        $("#custnamesubmit").click(function () {
            var username = document.getElementById("custname").value;
            if (username === "") {
                window.location.href = "accountadm.html";
                return;
            }
            var cuObj = {'cmd': 'name', 'username': username,
                'firstid': '0', 'lastid': '0'};
            var cuObjStr = JSON.stringify(cuObj);

            var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr,
                'CustNListStr': CustNListStr, 'CustNListCnt': CustNListCnt, 'cuObjStr': cuObjStr, 'tabName': tabName};
            window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));

            window.location.href = "accountadmcu_1.html";

        });

        $("#nextCust").click(function () {
            if (CustNListStr !== "") {
                var beg = (CustNListCnt + 1) * numDispCust;
                if (beg <= CustNList.length) {
                    CustNListCnt++;
                }
                var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr, 'commObjListStr': commObjListStr,
                    'CustNListStr': CustNListStr, 'CustNListCnt': CustNListCnt, 'tabName': tabName};
                window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));
                window.location.href = "accountadm.html";
                return;
            }
            // cust/{username}/uisys/{custid}/custnlist?length={0 for all} - default 20");
            $.ajax({
                url: iisurl + "/cust/" + custObj.username + "/uisys/" + custObj.id + "/custnlist?length=0",
                crossDomain: true,
                cache: false,
                success: handleResult
            }); // use promises

            // add cordova progress indicator https://www.npmjs.com/package/cordova-plugin-progress-indicator
            function handleResult(resultCustNList) {
                console.log(resultCustNList);
                var CustNListStr = JSON.stringify(resultCustNList, null, '\t');
                CustNListCnt = 0;
                var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr, 'commObjListStr': commObjListStr,
                    'CustNListStr': CustNListStr, 'CustNListCnt': CustNListCnt, 'tabName': tabName};
                window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));
                window.location.href = "accountadm.html";
            }

        });

        $("#prevCust").click(function () {
            if (CustNListStr !== "") {
                if (CustNListCnt == 0) {
                    return;
                }
                CustNListCnt--;
                var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr, 'commObjListStr': commObjListStr,
                    'CustNListStr': CustNListStr, 'CustNListCnt': CustNListCnt, 'tabName': tabName};
                window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));
                window.location.href = "accountadm.html";
                return;
            }
            // cust/{username}/uisys/{custid}/custnlist?length={0 for all} - default 20");
            $.ajax({
                url: iisurl + "/cust/" + custObj.username + "/uisys/" + custObj.id + "/custnlist?length=0",
                crossDomain: true,
                cache: false,
                success: handleResult
            }); // use promises

            // add cordova progress indicator https://www.npmjs.com/package/cordova-plugin-progress-indicator
            function handleResult(resultCustNList) {
                console.log(resultCustNList);
                var CustNListStr = JSON.stringify(resultCustNList, null, '\t');
                CustNListCnt = 0;
                var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr, 'commObjListStr': commObjListStr,
                    'CustNListStr': CustNListStr, 'CustNListCnt': CustNListCnt, 'tabName': tabName};
                window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));
                window.location.href = "accountadm.html";
            }

        });



        $("#removemsgidsubmit").click(function () {
            var msgid = document.getElementById("removemsgid").value;
            if (msgid === "") {
                window.location.href = "accountadm.html";
                return;
            }
            ///cust/{username}/acc/{accountid}/comm/remove/{id}
            $.ajax({
                url: iisurl + "/cust/" + custObj.username + "/acc/" + accObj.id + "/comm/remove?idlist=" + msgid,
                crossDomain: true,
                cache: false,
                success: handleResult
            }); // use promises

            // add cordova progress indicator https://www.npmjs.com/package/cordova-plugin-progress-indicator
            function handleResult(result) {
                console.log(result);
//                alert("Remove Message Id result: " + result);
                window.location.href = "accountadm_1.html";
            }
        });

        $("#removemsgsigsubmit").click(function () {
//            var txt;
//            var r = confirm("Confrim to clear all comm msg!");
//            if (r == true) {
//                
//            } else {
//                window.location.href = "accountadm_1.html";
//                return;
//            }
            ///cust/{username}/acc/{accountid}/comm/remove/{id}
            $.ajax({
                url: iisurl + "/cust/" + custObj.username + "/acc/" + accObj.id + "/comm/remove?idlist=-1",
                crossDomain: true,
                cache: false,
                success: handleResult
            }); // use promises

            // add cordova progress indicator https://www.npmjs.com/package/cordova-plugin-progress-indicator
            function handleResult(result) {
                console.log(result);
//                alert("Remove Message Id result: " + result);
                window.location.href = "accountadm_1.html";
            }
        });

        $("#getAllComm").click(function () {
            window.location.href = "accountadm_1.html";
        });

        $("#getEmailComm").click(function () {
            //"/cust/{username}/acc/{accountid}/emailcomm?length=            
            $.ajax({
                url: iisurl + "/cust/" + custObj.username + "/acc/" + accObj.id + "/emailcomm",
                crossDomain: true,
                cache: false,
                success: handleResult
            }); // use promises

            // add cordova progress indicator https://www.npmjs.com/package/cordova-plugin-progress-indicator
            function handleResult(resultCommObjList) {
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


        $("#custtab").click(function () {
            tabName = "CUST"
            var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr, 'commObjListStr': commObjListStr,
                'CustNListStr': CustNListStr, 'CustNListCnt': CustNListCnt, 'tabName': tabName};

            window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));
            window.location.href = "accountadm_1.html";
        });

        $("#commtab").click(function () {
            tabName = "COMM"
            var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr, 'commObjListStr': commObjListStr,
                'CustNListStr': CustNListStr, 'CustNListCnt': CustNListCnt, 'tabName': tabName};

            window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));
            window.location.href = "accountadm_1.html";
        });


// example        
//alert("AJAX request successfully completed");
//var jsonObj = JSON.parse(jsonStr);
//var jsonPretty = JSON.stringify(jsonObj, null, '\t');

    }
};
app.initialize();





