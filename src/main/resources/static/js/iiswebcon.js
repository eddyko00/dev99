
var app = {

// Application Constructor
    initialize: function () {

        $(document).ready(function () {

//            
            document.getElementById("txt-first-name").setAttribute('value', custObj.firstname);
            document.getElementById("txt-last-name").setAttribute('value', custObj.lastname);

            document.getElementById("txt-email-address").setAttribute('value', custObj.email);
//            document.getElementById("txt-password").setAttribute('value', custObj.password);

            var pp = "Basic Plan - Max 2 stocks";
            if (custObj.substatus == 0) {
                pp = "Basic Plan - Max 2 stocks";
            } else if (custObj.substatus == 10) {
                pp = "Standard Plan - Max 8 stocks";
            } else if (custObj.substatus == 20) {
                pp = "Premium Plan - Max 20 stocks";
            } else if (custObj.substatus == 20) {
                pp = "Delux Plan - Max 40 stocks";                
            } else if (custObj.substatus == 90) {
                pp = "API Plan - Max 1000 stocks";                
            }
            document.getElementById("txt-cur-plan").setAttribute('value', pp);

            var dataSt = custObj.portfolio;
            try {
                if (dataSt != null) {
                    if (dataSt !== "") {
                        dataSt = dataSt.replaceAll('#', '"');
                        var detailObj = JSON.parse(dataSt);
                        if (detailObj != null) {
                            if (detailObj.nPlan !== -1) {
                                if (custObj.substatus != detailObj.nPlan) {
                                    var pp = "Basic Plan - Max 2 stocks";
                                    if (detailObj.nPlan == 0) {
                                        pp = "Basic Plan - Max 2 stocks";
                                    } else if (detailObj.nPlan == 10) {
                                        pp = "Premium Plan - Max 10 stocks";
                                    } else if (detailObj.nPlan == 20) {
                                        pp = "Deluxe Plan - Max 20 stocks";
                                    } else if (detailObj.nPlan == 90) {
                                        pp = "API Plan - Max 1000 stocks";                                        
                                    }
                                    document.getElementById("txt-cur-plan").setAttribute('value', '*Pending to change* ' + pp);
                                }
                            }
                        }
                    }
                }
            } catch (err) {

            }
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
        var accId = iisWebObj.accId;
        console.log(accId);

        var iisMsgSession = "iisMsgSession";
        var msgObjStr = window.localStorage.getItem(iisMsgSession);
//        msgObjStr ="This feature does not allow for GUEST account";
        if (msgObjStr !== "") {
            functionAlertConfirm(msgObjStr, function ok() {
            });
        }

        $("#btn-submit").click(function () {
            var txtfirstname = document.getElementById("txt-first-name").value;
            var txtlastname = document.getElementById("txt-last-name").value;
            var txtemailaddress = document.getElementById("txt-email-address").value;
            var txtpassword = document.getElementById("txt-password").value;
            if (txtpassword ==="") {
                txtpassword = custObj.password;
            }
            if (txtpassword.length < 4) {
                msgObjStr = "The password must > 4 characters";
                window.localStorage.setItem(iisMsgSession, msgObjStr);
                window.location.href = "conf.html";
                return;
            }
            var plan = $('#pricemodel').val();
//            console.log(plan);

//          ""/cust/{username}/acc/{accountid}/custupdate?email=&pass=&firstName=&lastName=&plan=""
//          SUCC = 1;  EXISTED = 2; FAIL =0;
            $.ajax({
                url: iisurl + "/cust/" + custObj.username + "/acc/" + accId + "/custupdate?email=" + txtemailaddress + "&pass=" + txtpassword + "&firstName="
                        + txtfirstname + "&lastName=" + txtlastname + "&plan=" + plan,
                crossDomain: true,
                cache: false,
                success: handleResult
            }); // use promises

            // add cordova progress indicator https://www.npmjs.com/package/cordova-plugin-progress-indicator

            function handleResult(result) {
//          SUCC = 1;  EXISTED = 2; FAIL =0;
                var webMsg = result.webMsg;
                console.log(webMsg);
                var resultID = webMsg.resultID;

                msgObjStr = '';
                // 0 - general fail, 1 - successful, 2 - email fail 3 - password
                if (resultID === 0) {
                    msgObjStr = "Update failed. Please try again.";
                }
                if (resultID === 2) {
                    msgObjStr = "Update failed. Please correct the email address.";
                }
                if (resultID === 3) {
                    msgObjStr = "Update failed. Please use another password.";
                }
                var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr, 'accId': accId};
                window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));
                window.localStorage.setItem(iisMsgSession, msgObjStr);
                window.location.href = "conf_1.html";

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





