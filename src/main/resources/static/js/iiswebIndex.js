
var app = {

// Application Constructor
    initialize: function () {

        $(document).ready(function () {
            // after the page elements are all loaded, then run the script
            // Set the input field with unique ID #email to a value
//            $("#txt-email").val('GUEST');
//            $("#txt-password").val('guest');

        });
        $("#accheader").html('Welcome - v' + iis_ver);

//        var iisurl = "https://iiswebsrv.herokuapp.com/";
        var iisMsgSession = "iisMsgSession";
        var iisWebSession = "iisWebSession";
//        var custObj = 'custObj';
//        var accList = 'accList';

        var iisurlStr = iisurl;

        $(document).keypress(function (event) {
            var keycode = (event.keyCode ? event.keyCode : event.which);
            if (keycode == '13') {
                var txemail = document.getElementById("txt-email").value;
                var txtpassword = document.getElementById("txt-password").value;

                if (txemail === "") {
                    if (txtpassword === "") {
                        txemail = "GUEST";
                        txtpassword = "guest";
                    }
                }
                if (txemail === "00") {
                    iisurlStr = iisurl_LOCAL;
                }
                if (txemail === "111") {
                    iisurlStr = iisurl_HERO;
                }
                if (txemail === "1111") {
                    iisurlStr = iisurl_OP;
                }
                $.ajax({
                    url: iisurl + "cust/login?email=" + txemail + "&pass=" + txtpassword,
                    crossDomain: true,
                    cache: false,
                    error: function () {
                        alert('Network failure. Please try again later.');
                        window.location.href = "index.html";
                    },
                    success: handleResult
                }); // use promises

                // add cordova progress indicator https://www.npmjs.com/package/cordova-plugin-progress-indicator

                function handleResult(result) {
                    if (result === null) {
                        $('#error_message').fadeIn().html('Network error. Please try again later. ');
                        return;
                    }
                    if (result.webMsg.resultID === 100) {
                        $('#error_message').fadeIn().html('System is in maintenance. Please try again later. ');
                        return;
                    }
                    var version = result.webMsg.ver * 1;
                    if (parseFloat(version) > parseFloat(iis_ver)) {
                        $('#error_message').fadeIn().html('Please upgrade to newer version v' + result.webMsg.ver);
                        return;
                    }
                    var custObj = result.custObj;
                    console.log(custObj);

                    var custObjStr = JSON.stringify(custObj, null, '\t');
                    var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr};
                    window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));


                    if (custObj != null) {
                        window.location.href = "account_1.html";
                    } else {

//                    $('#error_message').fadeIn().html(jsonStr);
                        $('#error_message').fadeIn().html('Incorrect email/password. Please try again.');
                    }
                }
            }
        });


        $("#btn-submit").click(function () {
            var txtfirstname = document.getElementById("txt-first-name").value;
            var txtlastname = document.getElementById("txt-last-name").value;
            var txtemailaddress = document.getElementById("txt-email-address-signup").value;
            var txtpassword = document.getElementById("txt-password-signup").value;
            var txtpasswordconfirm = document.getElementById("txt-password-confirm-signup").value;
            var pricemodelconfirm = document.getElementById("pricemodel").value;

            if (txtpassword !== txtpasswordconfirm) {
                $('#error_message-signup').fadeIn().html("Password does not match with confirmed password. Please try again.");
            }
//          "/cust/add?email={email}&pass={pass}&firstName={firstName}&lastName={lastName}"
//          SUCC = 1;  EXISTED = 2; FAIL =0;
            $.ajax({
                url: iisurl + "/cust/add?email=" + txtemailaddress + "&pass=" + txtpassword + "&firstName=" + txtfirstname 
                        + "&lastName=" + txtlastname + "&plan=" + pricemodelconfirm,
                crossDomain: true,
                cache: false,
                error: function () {
                    alert('Network failure. Please try again later.');
                    window.location.href = "index.html";
                },
                success: handleResult
            }); // use promises

            // add cordova progress indicator https://www.npmjs.com/package/cordova-plugin-progress-indicator

            function handleResult(result) {
//          SUCC = 1;  EXISTED = 2; FAIL =0;
                var webMsg = result.webMsg;
                console.log(webMsg);
                var resultID = webMsg.resultID;
                if (resultID == 1) {
                    $("#txt-email").val(txtemailaddress);
                    // Set the input field with unique ID #name
                    $("#txt-password").val(txtpassword);
                    window.location.href = "#page-signup-succeeded";
                } else {
                    var errorM = "Please try again.";
                    if (resultID == 2) {
                        var errorM = "The customer account has already existed. Please try again.";
                    }
                    $('#error_message-signup').fadeIn().html(errorM);
                }
            }
        });

        $("#chck-rememberme").click(function () {
            $("#btn-login").attr("disabled", !this.checked);
        });

        $("#btn-login").click(function () {
            var txemail = document.getElementById("txt-email").value;
            var txtpassword = document.getElementById("txt-password").value;

            if (txemail === "") {
                if (txtpassword === "") {
                    txemail = "GUEST";
                    txtpassword = "guest";
                }
            }
            if (txemail === "00") {
                iisurlStr = iisurl_LOCAL;
            }
            if (txemail === "111") {
                iisurlStr = iisurl_HERO;
            }
            if (txemail === "1111") {
                iisurlStr = iisurl_OP;
            }


            $.ajax({
                url: iisurl + "cust/login?email=" + txemail + "&pass=" + txtpassword,
                crossDomain: true,
                cache: false,
                error: function () {
                    alert('Network failure. Please try again later.');
                    window.location.href = "index.html";
                },
                success: handleResult
            }); // use promises

            // add cordova progress indicator https://www.npmjs.com/package/cordova-plugin-progress-indicator

            function handleResult(result) {

                if (result === null) {
                    $('#error_message').fadeIn().html('Network error. Please try again later. ');
                    return;
                }

                if (result.webMsg.resultID === 100) {
                    $('#error_message').fadeIn().html('System is in maintenance. Please try again later. ');
                    return;
                }
                var version = result.webMsg.ver * 1;
                if (parseFloat(version) > parseFloat(iis_ver)) {
                    $('#error_message').fadeIn().html('Please upgrade to newer version v' + result.webMsg.ver);
                    return;
                }

                var custObj = result.custObj;
                console.log(custObj);

                var custObjStr = JSON.stringify(custObj, null, '\t');
                var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr};
                window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));

                var webMsg = result.webMsg;
                if (webMsg.resultID == 0) {
                    if (custObj != null) {
                        window.location.href = "account_1.html";
                        return;
                    }
                }
                var reMsg = "Incorrect email/password. Please try again.";
                if (webMsg.resultID == 2) {
                    reMsg = "Account in processing. Please try again later. ";
                }
                if (webMsg.resultID == 1) {
                    reMsg = "Account is disabled. ";
                }
                $('#error_message').fadeIn().html(reMsg);

            }
        });

// example        
//alert("AJAX request successfully completed");
//var jsonObj = JSON.parse(jsonStr);
//var jsonPretty = JSON.stringify(jsonObj, null, '\t');

    }
};
app.initialize();





