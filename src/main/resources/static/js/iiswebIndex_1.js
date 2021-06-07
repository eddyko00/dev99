
var app = {

// Application Constructor
    initialize: function () {

        $(document).ready(function () {

        });

        var iisWebSession = "iisWebSession";
        window.localStorage.setItem(iisWebSession, " ");
        
        var iisMsgSession = "iisMsgSession";
        window.localStorage.setItem(iisMsgSession, "");
        
        window.location.href = "index.html";
    }
};
app.initialize();





