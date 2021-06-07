
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

        var accObj = null;
        for (i = 0; i < accObjList.length; i++) {
            var accObjTmp = accObjList[i];
            if (accObjTmp.id == accId) {
                accObj = accObjTmp;
                break;
            }
        }
        if (accObj == null) {
            window.location.href = "index.html";
        }
        var StNListCnt = iisWebObj.StNListCnt;
        var trFilter = "";
        if (iisWebObj.trFilter != null) {
            trFilter = iisWebObj.trFilter;
        }
        var iisMsgSession = "iisMsgSession";
        var msgObjStr = window.localStorage.getItem(iisMsgSession);
//        msgObjStr ="This feature does not allow for GUEST account";
        if (msgObjStr !== "") {
            functionAlertConfirm(msgObjStr, function ok() {
            });
        }

        var stockObjListStr = iisWebObj.stockObjListStr;
        var stockObjList = JSON.parse(stockObjListStr);
        var sockId = iisWebObj.sockId;
        console.log(sockId);

        var stockObj = null;
        for (i = 0; i < stockObjList.length; i++) {
            var stockObjTmp = stockObjList[i];
            if (stockObjTmp.id == sockId) {
                stockObj = stockObjTmp;
                break;
            }
        }
        if (stockObj == null) {
            window.location.href = "index.html";
        }
        var trNameStr = iisWebObj.trName;


        var trObjListStr = iisWebObj.trObjListStr;
        var trObjList = JSON.parse(trObjListStr);
        var trObjacc = null;
        var percentSt = "";
        var close = 0;
        var preClose = 0;
        if (stockObj.afstockInfo != null) {
            close = stockObj.afstockInfo.fclose;
            preClose = stockObj.prevClose;
            var percent = 100 * (close - preClose) / preClose;
            if (percent > 0) {
                percentSt = "<font style= color:green>" + percent.toFixed(2) + '%' + "</font>";
            } else {
                percentSt = "<font style= color:red>" + percent.toFixed(2) + '%' + "</font>";
            }
        }

        var stockData = null;
        var stockD = stockObj.data;
        try {
            if (stockD !== "") {
                var objDataStr = stockD.replaceAll('#', '"');
                var objData = JSON.parse(objDataStr);

                if (objData != null) {
                    stockData = objData;
                }
            }
        } catch (err) {
        }

        var stStr = 'Trading Model Listing<br>';
        var stStatus = "";
        if (stockObj.substatus == 12) { //ConstantKey.STOCK_SPLIT STOCK_DETLA = 12
            stStatus = "<font style= color:red>St: PriceDif>20%</font>";
        }
        if (stockObj.substatus == 10) { //ConstantKey.STOCK_SPLIT STOCK_SPLIT = 10
            stStatus = "<font style= color:red>St: Split</font>";
        }
        if (stockObj.substatus == 2) { //INITIAL = 2;
            stStatus = "St: Init";
        }
        if (stockObj.substatus == 0) { //INITIAL = 2;
            stStatus = "<font style= color:green>St: Ready</font>";
        }



        stStr += stockObj.stockname + '<br>' + stockObj.updateDateD + " " + stStatus + '<br>' +
                'Pre Cl:' + preClose + '  Close:' + close + '  Per:' + percentSt
        $("#0").html('<h1>' + stStr + '</h1>');

        $("#accheader").html(' ' + accObj.accountname + ' ' + stockObj.symbol + ' ' + '<a href="#page-intro"><small>Help</small></a>');


        for (i = 0; i < trObjList.length; i++) {
            var trObj = trObjList[i];
            console.log(trObj);
            var nameId = trObj.id;

            if (custObj.type == 99) {
                if (trObj.trname === "TR_ACC") {
                    trObjacc = trObj;
                } else if (trObj.trname === "TR_MACD") {
                    ;
                } else if (trObj.trname === "TR_NN1") {
                    ;
                } else if (trObj.trname === "TR_NN2") {
                    ;
                } else if (trObj.trname === "TR_NN3") {
                    ;
                } else {
                    continue;
                }
            } else {
                if (trObj.trname === "TR_ACC") {
                    trObjacc = trObj;
                } else if (trObj.trname === "TR_NN1") {
                    ;
                } else if (trObj.trname === "TR_NN2") {
                    ;
//                } else if (trObj.trname === "TR_NN3") {
//                    ;
                } else {
                    continue;
                }
            }


            if (trObj.trname === trNameStr) {
                ;
            } else {
                continue;
            }

//https://demos.jquerymobile.com/1.1.2/docs/content/content-grids.html
            var htmlName = '<div class="ui-grid-b">';
            var dispName = trObj.trname;
            if (trObj.trname === "TR_ACC") {
                var status = trObj.status;
                if (status == 2) { //int PENDING = 2;
                    dispName = 'PENDING'
                    htmlName += '<div class="ui-block-a" style="color:red" ><strong>' + dispName + '</strong></div>';
                } else {
                    dispName = "ACCOUNT"
                    htmlName += '<div class="ui-block-a" ><strong>' + dispName + '</strong></div>';
                }
            } else {
                htmlName += '<div class="ui-block-a" style="color:SteelBlue" ><strong>' + dispName + '</strong></div>';
            }

            var deltaTotal = 0;

            var sharebalance = 0;
            var signal = "B";
            if (trObj.trsignal == S_BUY) {
                sharebalance = trObj.longamount;
                if (trObj.longshare > 0) {
                    if (close > 0) {
                        deltaTotal = (close - (trObj.longamount / trObj.longshare)) * trObj.longshare;
                    }
                }
                signal = "B";
                htmlName += '<div class="ui-block-b" style="color:green;width:20%">:' + signal + '</div>';
            } else if (trObj.trsignal == S_SELL) {
                signal = "S";
                sharebalance = trObj.shortamount;
                if (trObj.shortshare > 0) {
                    if (close > 0) {
                        deltaTotal = ((trObj.shortamount / trObj.shortshare) - close) * trObj.shortshare;
                    }
                }
                htmlName += '<div class="ui-block-b" style="color:red;width:20%">:' + signal + '</div>';
            } else {
                signal = "E";
                htmlName += '<div class="ui-block-b" style="width:20%">:' + signal + '</div>';
            }


            var total = trObj.balance + sharebalance;
            total = total - trObj.investment;

            if (stockObj.substatus === 0) {
                total = total + deltaTotal;
            }
            var totalSt = Number(total.toFixed(0)).toLocaleString('en-US', {style: 'currency', currency: 'USD'});
            totalSt = totalSt.replace(".00", "");
            var totalPer = 100 * total / TRADING_AMOUNT;
            totalSt += "(" + totalPer.toFixed(1) + "%)";
            htmlName += '<div class="ui-block-c">Profit: ' + totalSt + '</div>';
            htmlName += '</div>';

            var status = trObj.status;
            if (status == 2) { //int PENDING = 2;
                htmlName += 'Pending on delete when the signal is exited. <br>'
            }
            var comment = "Training in progress ...";
            if (trObj.comment != "") {
                if (trObj.comment != "null") {
                    try {
                        var objDataStr = trObj.comment.replaceAll('#', '"');
                        var objData = JSON.parse(objDataStr);
                        if (objData != null) {
                            if (objData.conf != "") {
                                comment = objData.conf + " n" + objData.nn + " t" + objData.tt;
                            }
                        }
                    } catch (err) {
                    }
                }
            }
            if (trObj.trname === "TR_MACD") {
                htmlName += 'Technical Indicator for MACD';
            } else if (trObj.trname === "TR_NN1") {
                comment = 'Auto AI (MACD) ' + comment;
            } else if (trObj.trname === "TR_NN2") {
                comment = 'Auto AI (EMA) -Beta ' + comment;
            } else if (trObj.trname === "TR_ACC") {

                var link = trObj.linktradingruleid;
                var trObjlink = null;
                for (j = 0; j < trObjList.length; j++) {
                    var trObjtmp = trObjList[j];
                    if (link == trObjtmp.type) {
                        trObjlink = trObjtmp;
                        break;
                    }
                }
                if (trObjlink != null) {
                    if (trObjlink.type == 0) {
                        htmlName += 'Manual user buy sell Transaction';
                    } else {
                        htmlName += 'Auto Trading Signal from ' + trObjlink.trname;
                    }
                }
            }

            var htmlBtn = '<div id="myidbtn" data-role="footer" data-theme="b" >';
            htmlBtn += '<a href="#" id="' + nameId + '" type="graph"  value="' + trObj.trname + '" data-icon="myicongraph" data-role="button" data-theme="a">Chart</a>';
            htmlBtn += '<a href="#" id="' + nameId + '" type="table"  value="' + trObj.trname + '" data-icon="myicontable" data-role="button" data-theme="a">Perf</a>';

            htmlBtn += '</div>';
            htmlName += htmlBtn;

            $("#myid").append('<li id="' + nameId + '"><a href="#">' + htmlName + '</a></li>');

            if ((trObj.trname === "TR_NN1") || (trObj.trname === "TR_NN2")) {
                $("#myid").append('<li></li>');
                $("#myid").append(comment);
                $("#myid").append('<li></li>');
                $("#myid").append('This is system simulated transactions with AI neural Networking.');
                $("#myid").append('<li></li>');

            }
        } // end for loop

//
        var buttonGraph = false;
        var type = "";
        var trname = "";
//        
        $("[id*=myidbtn] a").click(function () {
            buttonGraph = true;
            type = $(this).attr('type');
            trname = $(this).attr('value');

            ////////////////////////////////
            if (type === "buy") {
                if (trname != null) {
                    var symbol = stockObj.symbol;
                    symbol = symbol.replace(".", "_");
//"/cust/{username}/acc/{accountid}/st/{stockidsymbol}/tr/{trname}/tran/{signal}/order
                    var sig = S_BUY;
                    var trObj;
                    for (j = 0; j < trObjList.length; j++) {
                        var trObjtmp = trObjList[j];
                        if (trObjtmp.trname === trname) {
                            trObj = trObjtmp;
                            break;
                        }
                    }
                    if (trObj.trsignal == sig) {
                        window.location.href = "#page_index";
                    }
                    if (confirm('Do you want to Buy Transaction?')) {
                        ;
                    } else {
                        window.location.href = "#page_index";
                        return;
                    }
                    var urlSt = iisurl + "cust/" + custObj.username + "/acc/" + accId + "/st/" + symbol + "/tr/" + trname + "/tran/" + sig + "/order";
                    console.log(urlSt);
                    $.ajax({
                        url: urlSt,
                        crossDomain: true,
                        cache: false,
                        beforeSend: function () {
                            $("#loader").show();
                        },
                        success: function (result) {
                            console.log(result);
                            window.location.href = "accountsttr_1.html";
                        }
                    });
                }
            }
            if (type === "sell") {
                if (trname !== null) {
                    var symbol = stockObj.symbol;
                    symbol = symbol.replace(".", "_");
//"/cust/{username}/acc/{accountid}/st/{stockidsymbol}/tr/{trname}/tran/{signal}/order                    
                    var sig = S_SELL;
                    var trObj;
                    for (j = 0; j < trObjList.length; j++) {
                        var trObjtmp = trObjList[j];
                        if (trObjtmp.trname === trname) {
                            trObj = trObjtmp;
                            break;
                        }
                    }
                    if (trObj.trsignal == sig) {
                        window.location.href = "#page_index";
                    }
                    if (confirm('Do you want to Sell Transaction?')) {
                        ;
                    } else {
                        window.location.href = "#page_index";
                        return;
                    }
                    var urlSt = iisurl + "cust/" + custObj.username + "/acc/" + accId + "/st/" + symbol + "/tr/" + trname + "/tran/" + sig + "/order";
                    console.log(urlSt);
                    $.ajax({
                        url: urlSt,
                        crossDomain: true,
                        cache: false,
                        beforeSend: function () {
                            $("#loader").show();
                        },
                        success: function (result) {
                            console.log(result);
                            window.location.href = "accountsttr_1.html";
                        }
                    });
                }
            }

            if (type === "exit") {
                if (trname != null) {
                    var symbol = stockObj.symbol;
                    symbol = symbol.replace(".", "_");
//"/cust/{username}/acc/{accountid}/st/{stockidsymbol}/tr/{trname}/tran/{signal}/order
                    var sig = S_NEUTRAL;
                    var trObj;
                    for (j = 0; j < trObjList.length; j++) {
                        var trObjtmp = trObjList[j];
                        if (trObjtmp.trname === trname) {
                            trObj = trObjtmp;
                            break;
                        }
                    }
                    if (trObj.trsignal == sig) {
                        window.location.href = "#page_index";
                    }
                    if (confirm('Do you want to Exit Buy or Sell Transaction?')) {
                        ;
                    } else {
                        window.location.href = "#page_index";
                        return;
                    }
                    var urlSt = iisurl + "cust/" + custObj.username + "/acc/" + accId + "/st/" + symbol + "/tr/" + trname + "/tran/" + sig + "/order";
                    console.log(urlSt);
                    $.ajax({
                        url: urlSt,
                        crossDomain: true,
                        cache: false,
                        beforeSend: function () {
                            $("#loader").show();
                        },
                        success: function (result) {
                            console.log(result);
                            window.location.href = "accountsttr_1.html";
                        }
                    });
                }
            }
            if (type === "graph") {
                $("#grtxt1").show(0);
                if (trname !== null) {
                    setTimeout(function () {
                        $("#grtxt1").hide(0);
                    }, 6000);
                    var symbol = stockObj.symbol;
                    $("#graphheader1").html("Display graph - " + stockObj.symbol);
                    symbol = symbol.replace(".", "_");
                    var resultURL = iisurl + "cust/" + custObj.username + "/acc/" + accId + "/st/" + symbol + "/tr/" + trname + "/tran/history/chart?month=6";
//                resultURL = "https://iiswebsrv.herokuapp.com/cust/guest/acc/3/st/hou_to/tr/tr_macd/tran/history/chart";
                    $("#spaceimage").attr("src", resultURL);
                    window.location.href = "#page_graph";
                }
            }
            if (type === "table") {
                if (trname !== null) {
                    var symbol = stockObj.symbol;
                    $("#tablehheader").html("Trading Performance - " + stockObj.symbol);

                    symbol = symbol.replace(".", "_");
                    var urlSt = iisurl + "cust/" + custObj.username + "/acc/" + accId + "/st/" + symbol + "/tr/" + trname + "/perf";
                    console.log(urlSt);
                    $.ajax({
                        url: urlSt,
                        crossDomain: true,
                        cache: false,
                        beforeSend: function () {
                            $("#loader").show();
                        },
                        success: function (resultPerfList) {
                            console.log(resultPerfList);
                            if (resultPerfList != null) {
                                if (resultPerfList.length === 0) {

                                    $("#myidperf").html('<li">No Transaction</li>');
                                    window.location.href = "#page_table";
                                    return;
                                }
                                var PerfObj = resultPerfList[0];
                                var trsignal = PerfObj.performData.trsignal;
                                var perfStart = PerfObj.performData.fromdate;
                                var perfEnd = PerfObj.updatedatedisplay;

                                var balance = PerfObj.balance;
                                var shareAmount = 0;
                                if (trsignal === S_BUY) {
                                    shareAmount = PerfObj.performData.share * PerfObj.performData.close;
                                    balance += shareAmount;
                                }
                                if (trsignal === S_SELL) {
                                    shareAmount = PerfObj.performData.share * PerfObj.performData.close;
                                    balance += shareAmount;
                                }
                                // so that to show the balacne positive
                                balance += PerfObj.investment;

                                var balanceSt = Number(balance).toLocaleString('en-US', {style: 'currency', currency: 'USD'});
                                var netprofitSt = Number(PerfObj.grossprofit).toLocaleString('en-US', {style: 'currency', currency: 'USD'});
                                var investmentSt = Number(shareAmount).toLocaleString('en-US', {style: 'currency', currency: 'USD'});

                                var percent = 100 * (PerfObj.grossprofit / TRADING_AMOUNT);
                                var percentSt = Number(percent.toFixed(2)).toLocaleString('en');

                                var htmlName = "";
                                htmlName += '<div class="ui-grid-a">';
                                htmlName += '<div class="ui-block-a" ><strong>' + 'Netprofit: ' + netprofitSt + '</strong></div>';
                                htmlName += '<div class="ui-block-b" >Profit(%): ' + percentSt + '%</div>';
                                htmlName += '</div>';
                                htmlName += '<br>';
                                htmlName += '<div class="ui-grid-a">';
                                htmlName += '<div class="ui-block-a" >Date: ' + perfEnd + '</div>';
                                htmlName += '<div class="ui-block-b" >From: ' + perfStart + '</div>';
                                htmlName += '</div>';
//                                htmlName += '<div class="ui-grid-a">';
//                                htmlName += '<br>';
//                                htmlName += '<div class="ui-grid-a">';
//                                htmlName += '<div class="ui-block-a" >' + 'Balance: ' + balanceSt + '</div>';
//                                htmlName += '<div class="ui-block-b" >' + 'Invest: ' + investmentSt + '</div>';
//                                htmlName += '</div>';

                                htmlName += '<div class="ui-grid-a">';
                                htmlName += '<div class="ui-block-a" >' + 'rating: ' + PerfObj.rating.toFixed(2) + '</div>';
                                htmlName += '<div class="ui-block-b" >' + 'numtrade: ' + PerfObj.numtrade + '</div>';
                                htmlName += '</div>';
                                htmlName += '<br>';

                                htmlName += '<div class="ui-grid-a">';
                                htmlName += '<div class="ui-block-a" >' + 'numwin: ' + PerfObj.performData.numwin + '</div>';
                                htmlName += '<div class="ui-block-b" >' + 'numloss: ' + PerfObj.performData.numloss + '</div>';
                                htmlName += '</div>';

                                htmlName += '<div class="ui-grid-a">';
                                htmlName += '<div class="ui-block-a" >' + 'maxwin: ' + PerfObj.performData.maxwin.toFixed(2) + '</div>';
                                htmlName += '<div class="ui-block-b" >' + 'maxloss: ' + PerfObj.performData.maxloss.toFixed(2) + '</div>';
                                htmlName += '</div>';

                                htmlName += '<div class="ui-grid-a">';
                                htmlName += '<div class="ui-block-a" >' + 'holdtime: ' + PerfObj.performData.holdtime + '</div>';
                                htmlName += '<div class="ui-block-b" >' + 'maxholdtime: ' + PerfObj.performData.maxholdtime + '</div>';
                                htmlName += '</div>';

                                $("#myidperf").html('<li">' + htmlName + '</li>');
                                window.location.href = "#page_table";

                            }
                        }
                    });
                    window.location.href = "#page_table";
                }
            }
        });

        $("ul[id*=myid] button").click(function () {
            buttonGraph = true;
            var trname = $(this).attr('value');
            if (trname != null) {
                var symbol = stockObj.symbol;
                symbol = symbol.replace(".", "_");
                var resultURL = iisurl + "cust/" + custObj.username + "/acc/" + accId + "/st/" + symbol + "/tr/" + trname + "/tran/history/chart?month=6";
//                resultURL = "https://iiswebsrv.herokuapp.com/cust/guest/acc/3/st/hou_to/tr/tr_macd/tran/history/chart";
                $("#spaceimage").attr("src", resultURL);

                window.location.href = "#page_graph";
            }


        });


        $("ul[id*=myid] li").click(function () {
//            alert($(this).html()); // gets innerHTML of clicked li
//            alert($(this).text()); // gets text contents of clicked li
            var nameId = $(this).attr('id');
            console.log(nameId);
            if (nameId == 0) {
//                alert(nameId);
                return;
            }
            if (buttonGraph == true) {
                buttonGraph = false;
                return;
            }

            var trObj = null;
            for (i = 0; i < trObjList.length; i++) {
                var trObjTmp = trObjList[i];
                if (trObjTmp.id == nameId) {
                    trObj = trObjTmp;
                    break;
                }
            }
            if (trObj == null) {
                return;
            }
            var trName = trObj.trname;
            var iisWebObj = {'custObjStr': custObjStr, 'iisurlStr': iisurlStr, 'accObjListStr': accObjListStr,
                'StNListCnt': StNListCnt, 'accId': accId, 'trFilter': trFilter, 'stockObjListStr': stockObjListStr, 'sockId': sockId,
                'trObjListStr': trObjListStr, 'trName': trName};

            window.localStorage.setItem(iisWebSession, JSON.stringify(iisWebObj));
            window.location.href = "accounttran_1.html";
        });


        $("#y1btn").click(function () {
            $("#grtxt1").show(0);
            if (trname !== null) {
                setTimeout(function () {
                    $("#grtxt1").hide(0);
                }, 4000);

                var symbol = stockObj.symbol;
                $("#graphheader").html("Display graph - " + stockObj.symbol);
                symbol = symbol.replace(".", "_");
                var resultURL = iisurl + "cust/" + custObj.username + "/acc/" + accId + "/st/" + symbol + "/tr/" + trname + "/tran/history/chart?month=3";
//                resultURL = "https://iiswebsrv.herokuapp.com/cust/guest/acc/3/st/hou_to/tr/tr_macd/tran/history/chart";
                $("#spaceimage").attr("src", resultURL);

                window.location.href = "#page_graph";
            }
        });

        $("#y2btn").click(function () {
            $("#grtxt2").show(0);
            if (trname !== null) {
                setTimeout(function () {
                    $("#grtxt2").hide(0);
                }, 4000);

                var symbol = stockObj.symbol;
                $("#graphheader2").html("Display graph - " + stockObj.symbol);
                symbol = symbol.replace(".", "_");
                var resultURL = iisurl + "cust/" + custObj.username + "/acc/" + accId + "/st/" + symbol + "/tr/" + trname + "/tran/history/chart?month=6";
//                resultURL = "https://iiswebsrv.herokuapp.com/cust/guest/acc/3/st/hou_to/tr/tr_macd/tran/history/chart";
                $("#spaceimage2").attr("src", resultURL);

                $("#grtxt2").show(0).delay(1000).hide(0);
                window.location.href = "#page_graph2";
            }
        });

        $("#y3btn").click(function () {
            $("#grtxt3").show(0);
            if (trname !== null) {
                setTimeout(function () {
                    $("#grtxt3").hide(0);
                }, 4000);

                var symbol = stockObj.symbol;
                $("#graphheader3").html("Display graph - " + stockObj.symbol);
                symbol = symbol.replace(".", "_");
                var resultURL = iisurl + "cust/" + custObj.username + "/acc/" + accId + "/st/" + symbol + "/tr/" + trname + "/tran/history/chart?month=12";
//                resultURL = "https://iiswebsrv.herokuapp.com/cust/guest/acc/3/st/hou_to/tr/tr_macd/tran/history/chart";
                $("#spaceimage3").attr("src", resultURL);

                $("#grtxt3").show(0).delay(1000).hide(0);
                window.location.href = "#page_graph3";
            }
        });

        $("#y21btn").click(function () {
            $("#grtxt1").show(0);
            if (trname !== null) {
                setTimeout(function () {
                    $("#grtxt1").hide(0);
                }, 4000);

                var symbol = stockObj.symbol;
                $("#graphheader").html("Display graph - " + stockObj.symbol);
                symbol = symbol.replace(".", "_");
                var resultURL = iisurl + "cust/" + custObj.username + "/acc/" + accId + "/st/" + symbol + "/tr/" + trname + "/tran/history/chart?month=3";
//                resultURL = "https://iiswebsrv.herokuapp.com/cust/guest/acc/3/st/hou_to/tr/tr_macd/tran/history/chart";
                $("#spaceimage").attr("src", resultURL);

                window.location.href = "#page_graph";
            }
        });

        $("#y22btn").click(function () {
            $("#grtxt2").show(0);
            if (trname !== null) {
                setTimeout(function () {
                    $("#grtxt2").hide(0);
                }, 4000);

                var symbol = stockObj.symbol;
                $("#graphheader2").html("Display graph - " + stockObj.symbol);
                symbol = symbol.replace(".", "_");
                var resultURL = iisurl + "cust/" + custObj.username + "/acc/" + accId + "/st/" + symbol + "/tr/" + trname + "/tran/history/chart?month=6";
//                resultURL = "https://iiswebsrv.herokuapp.com/cust/guest/acc/3/st/hou_to/tr/tr_macd/tran/history/chart";
                $("#spaceimage2").attr("src", resultURL);

                $("#grtxt2").show(0).delay(1000).hide(0);
                window.location.href = "#page_graph2";
            }
        });

        $("#y23btn").click(function () {
            $("#grtxt3").show(0);
            if (trname !== null) {
                setTimeout(function () {
                    $("#grtxt3").hide(0);
                }, 4000);

                var symbol = stockObj.symbol;
                $("#graphheader3").html("Display graph - " + stockObj.symbol);
                symbol = symbol.replace(".", "_");
                var resultURL = iisurl + "cust/" + custObj.username + "/acc/" + accId + "/st/" + symbol + "/tr/" + trname + "/tran/history/chart?month=12";
//                resultURL = "https://iiswebsrv.herokuapp.com/cust/guest/acc/3/st/hou_to/tr/tr_macd/tran/history/chart";
                $("#spaceimage3").attr("src", resultURL);

                $("#grtxt3").show(0).delay(1000).hide(0);
                window.location.href = "#page_graph3";
            }
        });

        $("#y31btn").click(function () {
            $("#grtxt1").show(0);
            if (trname !== null) {
                setTimeout(function () {
                    $("#grtxt1").hide(0);
                }, 4000);

                var symbol = stockObj.symbol;
                $("#graphheader").html("Display graph - " + stockObj.symbol);
                symbol = symbol.replace(".", "_");
                var resultURL = iisurl + "cust/" + custObj.username + "/acc/" + accId + "/st/" + symbol + "/tr/" + trname + "/tran/history/chart?month=3";
//                resultURL = "https://iiswebsrv.herokuapp.com/cust/guest/acc/3/st/hou_to/tr/tr_macd/tran/history/chart";
                $("#spaceimage").attr("src", resultURL);

                window.location.href = "#page_graph";
            }
        });

        $("#y32btn").click(function () {
            $("#grtxt2").show(0);
            if (trname !== null) {
                setTimeout(function () {
                    $("#grtxt2").hide(0);
                }, 4000);

                var symbol = stockObj.symbol;
                $("#graphheader2").html("Display graph - " + stockObj.symbol);
                symbol = symbol.replace(".", "_");
                var resultURL = iisurl + "cust/" + custObj.username + "/acc/" + accId + "/st/" + symbol + "/tr/" + trname + "/tran/history/chart?month=6";
//                resultURL = "https://iiswebsrv.herokuapp.com/cust/guest/acc/3/st/hou_to/tr/tr_macd/tran/history/chart";
                $("#spaceimage2").attr("src", resultURL);

                $("#grtxt2").show(0).delay(1000).hide(0);
                window.location.href = "#page_graph2";
            }
        });

        $("#y33btn").click(function () {
            $("#grtxt3").show(0);
            if (trname !== null) {
                setTimeout(function () {
                    $("#grtxt3").hide(0);
                }, 4000);

                var symbol = stockObj.symbol;
                $("#graphheader3").html("Display graph - " + stockObj.symbol);
                symbol = symbol.replace(".", "_");
                var resultURL = iisurl + "cust/" + custObj.username + "/acc/" + accId + "/st/" + symbol + "/tr/" + trname + "/tran/history/chart?month=12";
//                resultURL = "https://iiswebsrv.herokuapp.com/cust/guest/acc/3/st/hou_to/tr/tr_macd/tran/history/chart";
                $("#spaceimage3").attr("src", resultURL);

                $("#grtxt3").show(0).delay(1000).hide(0);
                window.location.href = "#page_graph3";
            }
        });

        $("#configbtn").click(function () {
            if (custObj.username.toUpperCase() === "GUEST") {
                msgObjStr = "This feature does not allow for GUEST account";
                window.localStorage.setItem(iisMsgSession, msgObjStr);
                window.location.href = "accountsttr.html";
                return;
//                alert("Not supproted feature for GUEST accont");
//                window.location.href = "accountsttr.html";
//                return;
            }
            var trNum = trObjacc.linktradingruleid;
            var trName = "TR_NN2";
            if (trNum == 0) {
                trName = "TR_ACC";
            } else if (trNum == 2) {
                trName = "TR_MACD";
            } else if (trNum == 4) {
                trName = "TR_NN1";
            } else if (trNum == 5) {
                trName = "TR_NN2";
            }
            $('#myidtrmodel').val(trName).attr("selected", "selected");

            window.location.href = "#page_conf";
        });

        $("#savesubmit").click(function () {

            var tr = $('#myidtrmodel').val();
            console.log(tr);
//            var answer = confirm('Do you want to save changes?');
//            if (answer) {
//                ;
//            } else {
//                window.location.href = "#page_index";
//                return;
//            }

            //"/cust/{username}/acc/{accountid}/st/{stockid or symbol}/tr/{trname}/linktr/{linkopt or trname}"
            $.ajax({
                url: iisurl + "/cust/" + custObj.username + "/acc/" + accId + "/st/" + sockId + "/tr/TR_ACC/linktr/" + tr,
                crossDomain: true,
                cache: false,
                success: handleResult
            }); // use promises

            // add cordova progress indicator https://www.npmjs.com/package/cordova-plugin-progress-indicator

            function handleResult(result) {
                //MAX_ALLOW_STOCK_ERROR = 100 ; NEW = 1; EXISTED = 2
                console.log(result);
                if (result == 1) {
                    window.location.href = "accountsttr_1.html";
                }
                window.location.href = "accountsttr_1.html";
            }
        });

        $('a#link').click(function () {
//            alert("Hello");
//            window.location.href = "#page-intro";
            window.location.href = "accountsttrmsg.html";

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

