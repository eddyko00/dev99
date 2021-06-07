
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
        var lockObjListStr = iisWebObj.lockObjListStr;
        var lockObjList = JSON.parse(lockObjListStr);
        var serverListStr = iisWebObj.serverListStr;
        var serverList = JSON.parse(serverListStr);

        $("#accheader").html("System Status");

        $("#myid").html(" "); //clear the field
        for (i = 0; i < lockObjList.length; i++) {
            var lockObj = lockObjList[i];
            var trStr = lockObj.lockdatedisplay + '  ' + lockObj.lockname 
            trStr = '<li>' + trStr + '</li>'
            trStr += 'T:' + lockObj.type+ ' '+lockObj.comment;
            $("#myid").append(trStr);
        }

        $("#myid").append('<br><br>');

        for (i = 0; i < serverList.length; i++) {
            var srvObj = serverList[i];
            var trStr = srvObj.lastServUpdateESTdate + '   ' + srvObj.serverName;
            trStr += '<br>Maintance:' + srvObj.sysMaintenance;
            trStr += '<br>processTimerCnt:' + srvObj.processTimerCnt + '   autoNNCnt:' + srvObj.autoNNCnt;
            trStr += '<br>Total Stock=' + srvObj.totalStock + '   Total StockAcc:' + srvObj.totalStockAcc;

            trStr += '<br><br>' + srvObj.timerMsg;
            trStr += '<br>RESTreq:' + srvObj.cntRESTrequest + '   Ex:' + srvObj.cntRESTexception;
            trStr += '<br>InterReq:' + srvObj.cntInterRequest + '   Ex:' + srvObj.cntInterException;
            trStr += '<br><br>iisurl: ' + iisurl;
            var htmlName = ' ' + trStr + ' ';
            $("#myid").append('<li ></li>' + htmlName);
        }


        $("ul[id*=myid] li").click(function () {
//            alert($(this).html()); // gets innerHTML of clicked li
//            alert($(this).text()); // gets text contents of clicked li
        });

    }
};
app.initialize();





