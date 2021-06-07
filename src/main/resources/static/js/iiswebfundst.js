
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

        var fundId = iisWebObj.fundId;

        var fundObj = null;
        for (i = 0; i < fundObjList.length; i++) {
            var fundObjTmp = fundObjList[i];
            if (fundObjTmp.id == fundId) {
                fundObj = fundObjTmp;
                break;
            }
        }
        if (fundObj == null) {
            window.location.href = "index.html";
        }
//var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr, 'accId': accId, 
//      'fundObjListStr': fundObjListStr, 'fundBestObjListStr': fundBestObjListStr, 'fundId': fundId, 'stockObjListStr': stockObjListStr};


//        var physicalScreenWidth = window.screen.width * window.devicePixelRatio;
//        var physicalScreenHeight = window.screen.height * window.devicePixelRatio;

        var stockObjListStr = iisWebObj.stockObjListStr;
        var stockObjList = JSON.parse(stockObjListStr);

        $("#accheader").html('Account ' + fundObj.accountname + ' ' + '<a href="#page-intro"><small>Help</small></a>');

        var htmlhead = '<div class="ui-grid-d">';
        htmlhead += '<div class="ui-block-a" style="width:20%"><strong>Sym</strong></div>';
        htmlhead += '<div class="ui-block-b" style="text-align: center;width:20%">Sig</div>';
        htmlhead += '<div class="ui-block-c" style="text-align: center;width:20%">Trend</div>';
        htmlhead += '<div class="ui-block-d" style="text-align: center;width:20%">Daily %</div>';
        htmlhead += '<div class="ui-block-e" style="text-align: right;width:20%">perf %</div>';
        htmlhead += '</div>';
        htmlhead += '</div>';
        $("#myid").html('<li id="0" >' + htmlhead + '</li>');
        for (i = 0; i < stockObjList.length; i++) {
            var stockObj = stockObjList[i];
            console.log(stockObj);
//            var name = stockObj.symbol;
//            name += " TR:"+ stockObj.trsignal+" L:"+stockObj.longterm; 

//https://demos.jquerymobile.com/1.1.2/docs/content/content-grids.html
            var htmlName = '<div class="ui-grid-d">';
            var signal = "B";
            if (stockObj.trsignal === S_BUY) {
                signal = "B";
                htmlName += '<div class="ui-block-a" style="color:green;width:20%"><strong>' + stockObj.symbol + '</strong></div>';
                htmlName += '<div class="ui-block-b" style="text-align: center;color:green;width:20%">:' + signal + '</div>';
            } else if (stockObj.trsignal === S_SELL) {
                signal = "S";
                htmlName += '<div class="ui-block-a" style="color:red;width:20%"><strong>' + stockObj.symbol + '</strong></div>';
                htmlName += '<div class="ui-block-b" style="text-align: center;color:red;width:20%">:' + signal + '</div>';
            } else {
                signal = "E";
                htmlName += '<div class="ui-block-a" style="width:20%"><strong>' + stockObj.symbol + '</strong></div>';
                htmlName += '<div class="ui-block-b" style="text-align: center;width:20%">:' + signal + '</div>';
            }

            htmlName += '<div class="ui-block-c" style="text-align: center;width:20%">T:' + stockObj.longterm + '</div>';
            var percentSt = "";
            var perSt = "";
            var close = 0;
            var preClose = 0;
            if (stockObj.afstockInfo != null) {
                close = stockObj.afstockInfo.fclose;
                preClose = stockObj.prevClose;
                var percent = 100 * (close - preClose) / preClose;
                percentSt = percent.toFixed(1); // + '%';
                var perform = stockObj.perform;
                perSt = perform.toFixed(0); // performance '%';                
                if (perform != 0) {
                    if (perform < 10) {
                        if (perform > -10) {
                            perSt = perform.toFixed(2);
                            perSt = perSt.replace("0.00", "0");
                        }
                    }
                }

            }
            htmlName += '<div class="ui-block-d" style="text-align: center;width:20%">P:' + percentSt + ' </div>';
            htmlName += '<div class="ui-block-e" style="text-align: right;width:20%">:' + perSt + '</div>';
            htmlName += '</div>';

            var nameId = stockObj.id;
            $("#myid").append('<li id="' + nameId + '"><a href="#">' + htmlName + '</a></li>');
        }

        if (stockObjList.length > 0) {
            var accObj = fundObj;

            var total = accObj.investment + accObj.balance;
            var investSt = Number(accObj.investment).toLocaleString('en-US', {style: 'currency', currency: 'USD'});
            var balSt = Number(accObj.balance).toLocaleString('en-US', {style: 'currency', currency: 'USD'});

            var percent = 100 * (total) / (6000 * stockObjList.length);
            percentSt = percent.toFixed(2); // + '%';
            var totSt = Number(total).toLocaleString('en-US', {style: 'currency', currency: 'USD'});
            htmlName = 'Acc Bal: ' + investSt + ' Cur Bal: ' + balSt;
            htmlName += '<br>Total: ' + totSt + ' (per:' + percentSt + '%)';
            $("#myid").append('<li><strong>' + htmlName + '</strong></li>');
        }

        $("ul[id*=myid] li").click(function () {
//            alert($(this).html()); // gets innerHTML of clicked li
//            alert($(this).text()); // gets text contents of clicked li
            var nameId = $(this).attr('id');
            console.log(nameId);
            if (nameId == 0) {
//                alert(nameId);
                return;
            }
            var sockId = nameId;
            var stockObj = null;
            for (i = 0; i < stockObjList.length; i++) {
                var stockObjTmp = stockObjList[i];
                if (stockObjTmp.id == sockId) {
                    stockObj = stockObjTmp;
                    break;
                }
            }
            if (stockObj == null) {
                return;
            }
            if (stockObj.afstockInfo == null) {
                alert("Stock process not finished. Try again later");
                return;
            }

            var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr, 'accId': accId,
                'fundObjListStr': fundObjListStr, 'fundBestObjListStr': fundBestObjListStr, 'fundId': fundId, 'stockObjListStr': stockObjListStr, 'sockId': sockId};

            window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));
            window.location.href = "fundsttr_1.html";
        });

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





