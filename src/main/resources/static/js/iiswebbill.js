
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

        var billObjListStr = iisWebObj.billObjListStr;
        var billObjList = JSON.parse(billObjListStr);


        $("#accheader").html("Account Billing" + ' ' + '<a href="#page-intro"><small>Help</small></a>');

        $("#myid").html(" "); //clear the field
        for (i = 0; i < billObjList.length; i++) {
            var billObj = billObjList[i];
            console.log(billObj);

            var billId = billObj.id;

            var htmlName = '';
            htmlName += '<br>Billing id: (' + billId + ') Account due date: ' + billObj.updatedatedisplay;

            var statusSt = 'NA';
            if (billObj.status === 2) {
                statusSt = '<font style= color:red>Amount due</font>';
            }
            if (billObj.status === 5) {
                statusSt = 'Amount paid';
            }

            var curPaySt = Number(billObj.payment).toLocaleString('en-US', {style: 'currency', currency: 'USD'});
            htmlName += '<br>Status:' + statusSt + ' invoice: ' + curPaySt;

            var dataSt = billObj.data;
            try {
                if (dataSt != null) {
                    if (dataSt !== "") {
                        dataSt = dataSt.replaceAll('#', '"');
                        var detailObj = JSON.parse(dataSt);
                        if (detailObj != null) {
                            htmlName += '<br><br>Plan:' + detailObj.feat;
                            var currencySt = Number(detailObj.curPaym).toLocaleString('en-US', {style: 'currency', currency: 'USD'});
                            htmlName += '<br>Current payment:' + currencySt;

                            currencySt = Number(detailObj.prevOwn).toLocaleString('en-US', {style: 'currency', currency: 'USD'});
                            htmlName += '<br>Previous outstanding:' + currencySt;
                            currencySt = Number(detailObj.service).toLocaleString('en-US', {style: 'currency', currency: 'USD'});
                            var currencyCSt = Number(detailObj.credit).toLocaleString('en-US', {style: 'currency', currency: 'USD'});
                            htmlName += '<br>Other service:' + currencySt + '  credit:' + currencyCSt;

                        }
                    }
                }
            } catch (err) {

            }
            $("#myid").append('<li id="' + billId + '" >' + htmlName + '</li>');

        }



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





