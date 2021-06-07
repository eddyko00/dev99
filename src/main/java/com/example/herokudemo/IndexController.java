package com.example.herokudemo;

import com.afweb.model.*;
import com.afweb.model.account.*;
import com.afweb.model.stock.*;
import com.afweb.util.*;
import com.afweb.service.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

//https://www.baeldung.com/spring-cors
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(origins = "http://localhost:8383")
@RestController
public class IndexController {

    private static AFwebService afWebService = new AFwebService();

//    @RequestMapping("/index")
//    public String indexMessage() {
//        return "index";
//    }
    @GetMapping("/")
    public String index() {
        return "Hello there! I'm running v" + CKey.iis_ver;
    }

    /////////////////////////////////////////////////////////////////////////    
    @RequestMapping(value = "helphelp", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ArrayList SystemHelpPage() {

        ArrayList arrayString = new ArrayList();

        arrayString.add("/server");
//        arrayString.add("/server/filepath");
//        arrayString.add("/server/filepath/set?path=&name=&string= ");
//        arrayString.add("/server/filepath/read?path=&name=");
//        arrayString.add("/server/url0 - 0-local, 1- Heroku, 2- OP");
//        arrayString.add("/server/url0/set?url=stop");
//        arrayString.add("/server/dburl");
//        arrayString.add("/server/dburl/set?url=");         
        arrayString.add("/helphelp");
        arrayString.add("/st?length={0 for all}");
        arrayString.add("/st/{symbol}");
        arrayString.add("/st/{symbol}/history?length={0 for all}");
        arrayString.add("/st/add/{symbol}");
        arrayString.add("/st/remove/{symbol}");
        arrayString.add("/st/deleteinfo/{symbol}");
        arrayString.add("/st/cleanallinfo");
        //
        arrayString.add("/cust/add?email={email}&pass={pass}&firstName={firstName}&lastName={lastName}&plan=");
        arrayString.add("/cust/login?email={email}&pass={pass}");
//        arrayString.add("/cust/{username}/login&pass={pass}");

        arrayString.add("/cust/{username}/acc");
        arrayString.add("/cust/{username}/acc/{accountid}");

        arrayString.add("/cust/{username}/acc/{accountid}/emailcomm?length={0 for all} - default 20");
        arrayString.add("/cust/{username}/acc/{accountid}/emailcomm/removeemail?idlist=");
        arrayString.add("/cust/{username}/acc/{accountid}/comm?length={0 for all} - default 20");
        arrayString.add("/cust/{username}/acc/{accountid}/comm/add?data=");
        arrayString.add("/cust/{username}/acc/{accountid}/comm/remove?idlist=");
        arrayString.add("/cust/{username}/acc/{accountid}/comm/remove/{id}");

        arrayString.add("/cust/{username}/acc/{accountid}/clearfundbalance");
        arrayString.add("/cust/{username}/acc/{accountid}/fundbestlist");
        arrayString.add("/cust/{username}/acc/{accountid}/fundlink");
        arrayString.add("/cust/{username}/acc/{accountid}/fundlink/{accfundid}/add");
        arrayString.add("/cust/{username}/acc/{accountid}/fundlink/{accfundid}/remove");
        arrayString.add("/cust/{username}/acc/{accountid}/fundlink/{accfundid}/st?length={0 for all} - default 20");
        arrayString.add("/cust/{username}/acc/{accountid}/fundlink/{accfundid}/st/{stockid or symbol}/tr");
        arrayString.add("/cust/{username}/acc/{accountid}/fundlink/{accfundid}/st/{stockid or symbol}/tr/{trname}/tran/history/chart?year=");
        arrayString.add("/cust/{username}/acc/{accountid}/fundlink/{accfundid}/st/{stockid or symbol}/tr/{trname}/perf?length=");

        arrayString.add("/cust/{username}/acc/{accountid}/billing?length= (default/Max 12)");
        arrayString.add("/cust/{username}/acc/{accountid}/billing/{billid}/remove");
        arrayString.add("/cust/{username}/acc/{accountid}/banner?ver=");
        arrayString.add("/cust/{username}/acc/{accountid}/custacc");
        arrayString.add("/cust/{username}/acc/{accountid}/custupdate?email=&pass=&firstName=&lastName=&plan=");

        arrayString.add("/cust/{username}/acc/{accountid}/stname");
        arrayString.add("/cust/{username}/acc/{accountid}/st?trname=&filter= (Max 50)&length= (default 20 Max 50)");
        arrayString.add("/cust/{username}/acc/{accountid}/st/add/{symbol}");
        arrayString.add("/cust/{username}/acc/{accountid}/st/remove/{symbol}");
        arrayString.add("/cust/{username}/acc/{accountid}/st/addsymbol?symbol={symbol}");
        arrayString.add("/cust/{username}/acc/{accountid}/st/removesymbol?symbol={symbol}");
        arrayString.add("/cust/{username}/acc/{accountid}/st/{stockid or symbol}/tr");
        arrayString.add("/cust/{username}/acc/{accountid}/st/{stockid or symbol}/tr/{trname}");
        arrayString.add("/cust/{username}/acc/{accountid}/st/{stockid or symbol}/tr/{trname}/linktr/{linkopt or trname}");
        arrayString.add("/cust/{username}/acc/{accountid}/st/{stockid or symbol}/tr/{trname}/tran");
        arrayString.add("/cust/{username}/acc/{accountid}/st/{stockid or symbol}/tr/{trname}/tran/history");
        arrayString.add("/cust/{username}/acc/{accountid}/st/{stockid or symbol}/tr/{trname}/tran/history/chart?month=");
//        arrayString.add("/cust/{username}/acc/{accountid}/st/{stockid or symbol}/tr/{trname}/tran/history/chartfile");
        arrayString.add("/cust/{username}/acc/{accountid}/st/{stockid or symbol}/tr/{trname}/tran/clear");
        arrayString.add("/cust/{username}/acc/{accountid}/st/{stockid or symbol}/tr/{trname}/tran/{signal}/order");
        arrayString.add("/cust/{username}/acc/{accountid}/st/{stockid or symbol}/tr/{trname}/perf");
        arrayString.add("/cust/{username}/acc/{accountid}/st/{stockid or symbol}/tr/{trname}/perf/history");
//        arrayString.add("/cust/{username}/acc/{accountid}/st/{stockid or symbol}/tr/{trname}/perf/history/display");
//        arrayString.add("/cust/{username}/acc/{accountid}/st/{stockidsymbol}/tr/{trname}/tran/history/chart?path={filePath}");

        arrayString.add("/cust/{username}/uisys/{custid}/custnlist?length={0 for all} - default 20");
        arrayString.add("/cust/{username}/uisys/{custid}/custlist?name=");
        arrayString.add("/cust/{username}/uisys/{custid}/custlist?length={0 for all} - default 20");
        arrayString.add("/cust/{username}/uisys/{custid}/accounting/update?payment=&balance=&reason=&rate=&comment=");
        arrayString.add("/cust/{username}/uisys/{custid}/accounting/deprecation?payment=&rate=&reason=&comment=");
        arrayString.add("/cust/{username}/uisys/{custid}/accounting/costofgoodsold?payment=&curyear=&reason=&comment=");
        arrayString.add("/cust/{username}/uisys/{custid}/accounting/tax?payment=&reason=&comment=");

        arrayString.add("/cust/{username}/uisys/{custid}/accounting/report?name=&year=");
        arrayString.add("/cust/{username}/uisys/{custid}/accounting/entry/{id}");
        arrayString.add("/cust/{username}/uisys/{custid}/accounting/entry/{id}/remove");

        arrayString.add("/cust/{username}/uisys/{custid}/lock");
        arrayString.add("/cust/{username}/uisys/{custid}/timer");
        arrayString.add("/cust/{username}/uisys/{custid}/cust/{customername}/update?status=&payment=&balance=&reason=");

        arrayString.add("/cust/{username}/sys/cust/{customername}/status/{status}/substatus/{substatus}");
        arrayString.add("/cust/{username}/sys/cust/{customername}/removecustomer");
        arrayString.add("/cust/{username}/sys/custchangeapi?email={email}");
        arrayString.add("/cust/{username}/sys/custchangefund?email={email}");

        arrayString.add("/cust/{username}/sys/expiredcustlist?length={0 for all}");
        arrayString.add("/cust/{username}/sys/expiredStocklist?length={0 for all}");

        arrayString.add("/cust/{username}/sys/stop");
        arrayString.add("/cust/{username}/sys/clearlock");
        arrayString.add("/cust/{username}/sys/start");
        arrayString.add("/cust/{username}/sys/resetdb");

        arrayString.add("/cust/{username}/sys/clearnninput");
        arrayString.add("/cust/{username}/sys/clearallnntran");
        arrayString.add("/cust/{username}/sys/clearnn2tran?tr=");

        arrayString.add("/cust/{username}/sys/autonnflag");
        arrayString.add("/cust/{username}/sys/autonnflag/enable");
        arrayString.add("/cust/{username}/sys/autonnflag/disable");

        arrayString.add("/cust/{username}/sys/globalfundmgr");
        arrayString.add("/cust/{username}/sys/performfundmgr");
        arrayString.add("/cust/{username}/sys/processfundmgr");

//        arrayString.add("/cust/{username}/sys/deletenn1table");
        //DB Backup
//        arrayString.add("/cust/{username}/sys/downloaddb");
        //DB restore
//        arrayString.add("/cust/{username}/sys/cleandb");
//        arrayString.add("/cust/{username}/sys/restoredb");
//        arrayString.add("/cust/{username}/sys/request");
        arrayString.add("/cust/{username}/sys/lock");
        arrayString.add("/cust/{username}/sys/lock/{lockname}/type/{type}");
        arrayString.add("/cust/{username}/sys/lock/{lockname}/type/{type}/renewlock");
        arrayString.add("/cust/{username}/sys/lock/{lockname}/type/{type}/value/{lockdate}/comment/{comment}/setlock");
        arrayString.add("/cust/{username}/sys/lock/{lockname}/type/{type}/removelock");

//        arrayString.add("/cust/{username}/sys/neuralnet/{name}/release");
//        arrayString.add("/cust/{username}/sys/neuralnet/{name}/type/{type}/weight0");
//        arrayString.add("/cust/{username}/sys/neuralnet/{name}/type/{type}/weight1");
//        arrayString.add("/cust/{username}/sys/neuralnet/{name}/updateweight0");
//        arrayString.add("/cust/{username}/sys/neuralnet/{name}/updateweight1");
        return arrayString;
    }

    @RequestMapping(value = "/api/help", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ArrayList SystemAPIHelpPage() {

        ArrayList arrayString = new ArrayList();
        //
        arrayString.add("/api/cust/add?email={email}&pass={pass}&firstName={firstName}&lastName={lastName}");
        arrayString.add("/api/cust/login?email={email}&pass={pass}");

        arrayString.add("/api/cust/{username}/acc");
        arrayString.add("/api/cust/{username}/acc/{accountid}");

        arrayString.add("/api/cust/{username}/acc/{accountid}/comm?length= (default/Max 20)");
        arrayString.add("/api/cust/{username}/acc/{accountid}/comm/remove?idlist= (-1 delete all)");
        arrayString.add("/api/cust/{username}/acc/{accountid}/comm/remove/{id}");

        arrayString.add("/api/cust/{username}/acc/{accountid}/billing?length= (default/Max 12)");

        arrayString.add("/api/cust/{username}/acc/{accountid}/stname");
        arrayString.add("/api/cust/{username}/acc/{accountid}/st?trname=&filter= (Max 50)&length= (default 20 Max 50)");
        arrayString.add("/api/cust/{username}/acc/{accountid}/st/add/{symbol}");
        arrayString.add("/api/cust/{username}/acc/{accountid}/st/remove/{symbol}");
        arrayString.add("/api/cust/{username}/acc/{accountid}/st/addsymbol?symbol={symbol}");
        arrayString.add("/api/cust/{username}/acc/{accountid}/st/removesymbol?symbol={symbol}");

        arrayString.add("/api/cust/{username}/acc/{accountid}/st/{stockid or symbol}/tr");
        arrayString.add("/api/cust/{username}/acc/{accountid}/st/{stockid or symbol}/tr/{trname}");
        arrayString.add("/api/cust/{username}/acc/{accountid}/st/{stockid or symbol}/tr/{trname}/linktr/{linkopt or trname}");
        arrayString.add("/api/cust/{username}/acc/{accountid}/st/{stockid or symbol}/tr/{trname}/tran");

        arrayString.add("/api/cust/{username}/acc/{accountid}/st/{stockid or symbol}/tr/{trname}/perf");

        return arrayString;
    }
//////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/cust/{username}/sys/mysql", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String getmysql(
            @PathVariable("username") String username,
            @RequestBody String input
    ) {
        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
        RequestObj sqlObj = new RequestObj();
        try {
            sqlObj = new ObjectMapper().readValue(input, RequestObj.class);
        } catch (IOException ex) {
            return "";
        }
        CustomerObj cust = afWebService.getCustomerIgnoreMaintenance(username, null);
        if (cust != null) {
            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                System.out.println(sqlObj.getReq());
                if (sqlObj.getCmd().equals("1")) {
                    return afWebService.SystemRemoteGetMySQL(sqlObj.getReq());
                } else if (sqlObj.getCmd().equals("2")) {
                    return afWebService.SystemRemoteUpdateMySQL(sqlObj.getReq());
                } else if (sqlObj.getCmd().equals("3")) {
                    return afWebService.SystemRemoteUpdateMySQLList(sqlObj.getReq());
                }
            }
        }
        return "";
    }

    @RequestMapping(value = "/server", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ArrayList getServerObj() {

        return afWebService.getServerList();
    }

    @RequestMapping(value = "/server/filepath", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String getServerFileP() {
        return ServiceAFweb.FileLocalPath;
    }

    @RequestMapping(value = "/server/sysfilepath", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String getServerFileDir() {
        String userDirectory = Paths.get("").toAbsolutePath().toString();
        return userDirectory;
    }

    @RequestMapping(value = "/server/filepath/set", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String setServerfileP(
            @RequestParam(value = "path", required = false) String pathSt,
            @RequestParam(value = "name", required = false) String nameSt,
            @RequestParam(value = "string", required = false) String St,
            HttpServletRequest request, HttpServletResponse response
    ) {

        if (pathSt != null) {
            if (pathSt.length() > 0) {
                ServiceAFweb.FileLocalPath = pathSt.trim();
                return "done...";
            }
        }
        if (St != null) {
            if (St.length() > 0) {
                String fileName = "sys.txt";
                if (nameSt != null) {
                    if (nameSt.length() > 0) {
                        fileName = nameSt;
                    }
                }
                ArrayList msgWrite = new ArrayList();
                msgWrite.add(St);
                boolean ret = ServiceAFweb.SystemFilePut(fileName, msgWrite);
                return "done...";
            }
        }
        return "";
    }

    @RequestMapping(value = "/server/filepath/read", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String readServerfileP(
            @RequestParam(value = "path", required = false) String pathSt,
            @RequestParam(value = "name", required = true) String nameSt,
            HttpServletRequest request, HttpServletResponse response
    ) {

        if (pathSt != null) {
            if (pathSt.length() > 0) {
                ServiceAFweb.FileLocalPath = pathSt.trim();
                return "done...";
            }
        }
        if (nameSt != null) {
            if (nameSt.length() > 0) {
                ArrayList msgRead = new ArrayList();
                boolean ret = ServiceAFweb.SystemFilePut(nameSt, msgRead);
                StringBuffer msgWrite = new StringBuffer();
                for (int i = 0; i < msgRead.size(); i++) {
                    msgWrite.append(msgRead.get(i));
                }

                return msgWrite.toString();
            }
        }
        return "";
    }

    @RequestMapping(value = "/server/mysqldb", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String getServerLocalDbURL() {
        return ServiceAFweb.URL_LOCALDB;
    }

    @RequestMapping(value = "/server/mysqldb/set", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String setServerLocalDbURL(
            @RequestParam(value = "url", required = true) String urlSt,
            HttpServletRequest request, HttpServletResponse response
    ) {

        ServiceAFweb.URL_LOCALDB = urlSt.trim();
        //restart ServiceAFweb
        afWebService.SystemStart();
        return "done...";
    }

    @RequestMapping(value = "/server/dburl", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String getServerDBURL() {
        return ServiceRemoteDB.getURL_PATH();
    }

//    @RequestMapping(value = "/server/dburl/sethero", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    String getServerDBURLOPHER() {
//        ServiceRemoteDB.setURL_PATH(CKey.URL_PATH_HERO_DBDB_PHP + CKey.WEBPOST_HERO_PHP);
//        return ServiceRemoteDB.getURL_PATH();
//    }
    @RequestMapping(value = "/server/dburl/setherodb", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String getServerDBURLOPHERDB() {
        ServiceRemoteDB.setURL_PATH(CKey.URL_PATH_HERO_DBDB_PHP + CKey.WEBPOST_HERO_PHP);
        return ServiceRemoteDB.getURL_PATH();
    }

    @RequestMapping(value = "/server/dburl/setop", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String getServerDBURLOP() {
        ServiceRemoteDB.setURL_PATH(CKey.URL_PATH_OP_DB_PHP1 + CKey.WEBPOST_OP_PHP);
        return ServiceRemoteDB.getURL_PATH();
    }

    @RequestMapping(value = "/server/dburl/set", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String setServerDBURL(
            @RequestParam(value = "url", required = true) String urlSt,
            HttpServletRequest request, HttpServletResponse response
    ) {
        ServiceRemoteDB.setURL_PATH(urlSt.trim());
        return "done...";
    }

    @RequestMapping(value = "/server/url0", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String getServerURL() {
        String url0 = RESTtimer.serverURL_0;
        if (url0.length() == 0) {
            url0 = CKey.SERVER_TIMMER_URL;
        }
        return url0;
    }

    @RequestMapping(value = "/server/url0/sethero", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String getServerURLHERO() {
        RESTtimer.serverURL_0 = CKey.URL_PATH_HERO;
        return RESTtimer.serverURL_0;
    }

    @RequestMapping(value = "/server/url0/set", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    String setServerURL(
            @RequestParam(value = "url", required = true) String urlSt,
            HttpServletRequest request, HttpServletResponse response
    ) {

        RESTtimer.serverURL_0 = urlSt.trim();
        return "done...";
    }

    @RequestMapping(value = "/timerhandler", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    WebStatus timerHandlerREST(
            @RequestParam(value = "resttimerMsg", required = false) String resttimerMsg
    ) {

        WebStatus msg = new WebStatus();
        msg.setResult(true);
        msg.setResultID(ConstantKey.ENABLE);

        //process timer handler
        int timerCnt = afWebService.timerHandler(resttimerMsg);

        msg.setResponse("timerCnt " + timerCnt);
        return msg;
    }
//
//    ///cust/add?email={email}&pass={pass}&firstName={firstName}&lastName={lastName}&plan=
//    @RequestMapping(value = "/cust/add", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    LoginObj addCustomerPassword(
//            @RequestParam(value = "email", required = true) String emailSt,
//            @RequestParam(value = "pass", required = true) String passSt,
//            @RequestParam(value = "firstName", required = false) String firstNameSt,
//            @RequestParam(value = "lastName", required = false) String lastNameSt,
//            @RequestParam(value = "plan", required = false) String planSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            LoginObj loginObj = new LoginObj();
//            loginObj.setCustObj(null);
//            WebStatus webStatus = new WebStatus();
//            webStatus.setResultID(100);
//            loginObj.setWebMsg(webStatus);
//            return loginObj;
//        }
////       SUCC = 1;  EXISTED = 2; FAIL =0;
//        LoginObj loginObj = afWebService.addCustomerPassword(emailSt, passSt, firstNameSt, lastNameSt, planSt);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return loginObj;
//    }
//
//    @RequestMapping(value = "/cust/login", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    LoginObj getCustObjLogin(
//            @RequestParam(value = "email", required = true) String emailSt,
//            @RequestParam(value = "pass", required = true) String passSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            LoginObj loginObj = new LoginObj();
//            loginObj.setCustObj(null);
//            WebStatus webStatus = new WebStatus();
//            webStatus.setResultID(100);
//            loginObj.setWebMsg(webStatus);
//            return loginObj;
//        }
//        if (emailSt == null) {
//            return null;
//        }
//        if (passSt == null) {
//            return null;
//        }
//        if (emailSt.equals("11")) {
//            emailSt = "admin1";
//            if (passSt.equals("00")) {
//                passSt = "Passw0rd";
//            }
//        } else if (emailSt.equals("22")) {
//            emailSt = "fundmgr";
//            if (passSt.equals("00")) {
//                passSt = "Passw0rd";
//            }
//        } else if (emailSt.equals("33")) {
//            emailSt = "indexmgr";
//            if (passSt.equals("00")) {
//                passSt = "Passw0rd";
//            }
//        } else if (emailSt.equals("00")) {
//            emailSt = "admin1";
//            if (passSt.equals("00")) {
//                passSt = "Passw0rd";
//            }
//        } else if (emailSt.equals("111")) {
//            emailSt = "admin1";
//            if (passSt.equals("00")) {
//                passSt = "Passw0rd";
//            }
//        } else if (emailSt.equals("1111")) {
//            emailSt = "admin1";
//            if (passSt.equals("00")) {
//                passSt = "Passw0rd";
//            }
//        }
//
//        LoginObj loginObj = afWebService.getCustomerEmailLogin(emailSt, passSt);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return loginObj;
//    }
//
////    @RequestMapping(value = "/cust/{username}/login", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
////    public @ResponseBody
////    LoginObj getCustObjUserLogin(
////            @PathVariable("username") String username,
////            @RequestParam(value = "pass", required = true) String passSt,
////            HttpServletRequest request, HttpServletResponse response
////    ) {
////        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
////        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
////            LoginObj loginObj = new LoginObj();
////            loginObj.setCustObj(null);
////            WebStatus webStatus = new WebStatus();
////            webStatus.setResultID(100);
////            loginObj.setWebMsg(webStatus);
////            return loginObj;
////        }
////        if (passSt == null) {
////            return null;
////        }
////        LoginObj loginObj = afWebService.getCustomerLogin(username, passSt);
////        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
////        return loginObj;
////    }
//    //"/cust/{username}/acc/{accountid}/custupdate?email=&pass=&firstName=&lastName=&plan="
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/custupdate", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    LoginObj updateCustomerPassword(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @RequestParam(value = "email", required = false) String emailSt,
//            @RequestParam(value = "pass", required = false) String passSt,
//            @RequestParam(value = "firstName", required = false) String firstNameSt,
//            @RequestParam(value = "lastName", required = false) String lastNameSt,
//            @RequestParam(value = "plan", required = false) String planSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            LoginObj loginObj = new LoginObj();
//            loginObj.setCustObj(null);
//            WebStatus webStatus = new WebStatus();
//            webStatus.setResultID(100);
//            loginObj.setWebMsg(webStatus);
//            return loginObj;
//        }
////       SUCC = 1;  EXISTED = 2; FAIL =0;
//        LoginObj loginObj = afWebService.updateCustomerPassword(username, accountid, emailSt, passSt, firstNameSt, lastNameSt, planSt);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return loginObj;
//    }
//
//    //"/cust/{username}/acc/{accountid}/custacc"
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/custacc", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    LoginObj getCustomerAcc(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            LoginObj loginObj = new LoginObj();
//            loginObj.setCustObj(null);
//            WebStatus webStatus = new WebStatus();
//            webStatus.setResultID(100);
//            loginObj.setWebMsg(webStatus);
//            return loginObj;
//        }
////       SUCC = 1;  EXISTED = 2; FAIL =0;
//        LoginObj loginObj = afWebService.getCustomerAccLogin(username, accountid);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return loginObj;
//    }
//
//    @RequestMapping(value = "/cust/{username}/acc", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getAccountList(
//            @PathVariable("username") String username,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        ArrayList accountList = afWebService.getAccountList(username, null);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return accountList;
//    }
//
//    //("/cust/{username}/acc/{accountid}/fundbestlist");
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/fundbestlist", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList<AccountObj> getAccountBestFundList(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        ArrayList<AccountObj> accList = afWebService.getFundAccounBestFundList(username, null);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return accList;
//    }
//
//    //"/cust/{username}/acc/{accountid}/fundlink");
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/fundlink", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList<AccountObj> getAccountFundList(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        ArrayList<AccountObj> accList = afWebService.getFundAccountByCustomerAccountID(username, null, accountid);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return accList;
//    }
//
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    AccountObj getAccount(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        AccountObj account = afWebService.getAccountByCustomerAccountID(username, null, accountid);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return account;
//    }
//
//    // "/cust/{username}/acc/{accountid}/banner?ver="
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/banner?ver=", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList<String> getAccountBannerList(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @RequestParam(value = "ver", required = false) String verSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        ArrayList<String> messageList = new ArrayList();
//        messageList.add("" + CKey.iis_ver);
//
//        if (verSt != null) {
//            float version = Float.parseFloat(verSt);
//            if (CKey.iis_ver > version) {
//                // return update messagemessage
//                messageList.add("Please upgrade the app to version v1.1");
//            }
//        }
//
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return messageList;
//    }
//
//    //"/cust/{username}/acc/{accountid}/clearfundbalance");
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/clearfundbalance", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int getAccountfundbalance(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return 0;
//        }
//
//        int ret = afWebService.SystemFundClearfundbalance(username, null, accountid);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return ret;
//    }
//
//    // "/cust/{username}/acc/{accountid}/billing?length="
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/billing", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList<BillingObj> getAccountBillingList(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @RequestParam(value = "length", required = false) String lengthSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        int length = 12;
//        if (lengthSt != null) {
//            length = Integer.parseInt(lengthSt);
//            if (length > 12) {
//                length = 12;
//            }
//        }
//
//        ArrayList<BillingObj> billingObjList = afWebService.getBillingByCustomerAccountID(username, null, accountid, length);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return billingObjList;
//    }
//
//    // "/cust/{username}/acc/{accountid}/billing/{billid}/remove"
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/billing/{billid}/remove", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int getAccountBillingDel(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("billid") String billid,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return 0;
//        }
//
//        int ret = afWebService.removeBillingByCustomerAccountID(username, null, accountid, billid);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return ret;
//    }
//
////            arrayString.add("/cust/{username}/acc/{accountid}/comm/add?data=");  
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/comm/add", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int getAccountCommAdd(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @RequestParam(value = "data", required = false) String dataSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return -1;
//        }
//        int ret = afWebService.addCommByCustAccountID(username, null, accountid, dataSt);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return ret;
//    }
//
//    //"/cust/{username}/acc/{accountid}/emailcomm?length=" 
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/emailcomm", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList<CommObj> getAccountEmailCommList(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @RequestParam(value = "length", required = false) String lengthSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        int length = 20;
//        if (lengthSt != null) {
//            length = Integer.parseInt(lengthSt);
//        }
//        ArrayList<CommObj> commObjList = afWebService.getCommEmaiByCustomerAccountID(username, null, accountid, length);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return commObjList;
//    }
//
//    //"/cust/{username}/acc/{accountid}/comm?length=" 
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/comm", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList<CommObj> getAccountCommList(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @RequestParam(value = "length", required = false) String lengthSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        int length = 20;
//        if (lengthSt != null) {
//            length = Integer.parseInt(lengthSt);
//            if (length > 20) {
//                length = 20;
//            }
//        }
//        ArrayList<CommObj> commObjList = afWebService.getCommByCustomerAccountID(username, null, accountid, length);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return commObjList;
//    }
//
//    //"/cust/{username}/acc/{accountid}/emailcomm/removeemail?idlist=");
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/emailcomm/removeemail", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int getAccountCommListRemoveemail(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @RequestParam(value = "idlist", required = true) String idlist,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return 0;
//        }
//        if (idlist == null) {
//            return 0;
//        }
//        if (idlist.length() == 0) {
//            return 0;
//        }
//        int ret = 1;
//        try {
//            String[] idlistArray = idlist.split(",");
//            for (int i = 0; i < idlistArray.length; i++) {
//                String idSt = idlistArray[i];
//                int comid = Integer.parseInt(idSt);
//                if (comid == -1) {
//                    ret = afWebService.removeAllEmailByCustomerAccountID(username, null, accountid);
//                } else {
//                    ret = afWebService.removeCommByID(username, null, accountid, comid + "");
//                }
//            }
//        } catch (Exception ex) {
//            ret = 0;
//        }
//
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return ret;
//    }
//
//    //"/cust/{username}/acc/{accountid}/comm/remove?idlist=");
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/comm/remove", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int getAccountCommListRemove(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @RequestParam(value = "idlist", required = true) String idlist,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return 0;
//        }
//        if (idlist == null) {
//            return 0;
//        }
//        if (idlist.length() == 0) {
//            return 0;
//        }
//        int ret = 1;
//        try {
//            String[] idlistArray = idlist.split(",");
//            for (int i = 0; i < idlistArray.length; i++) {
//                String idSt = idlistArray[i];
//                int comid = Integer.parseInt(idSt);
//                if (comid == -1) {
//                    ret = afWebService.removeAllCommByCustomerAccountID(username, null, accountid);
//                } else {
//                    ret = afWebService.removeCommByID(username, null, accountid, comid + "");
//                }
//            }
//        } catch (Exception ex) {
//            ret = 0;
//        }
//
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return ret;
//    }
//
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/comm/remove/{comid}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int getAccountCommListRemoveID(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("comid") String comid,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return 0;
//        }
//        int ret = afWebService.removeCommByID(username, null, accountid, comid);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return ret;
//    }
//
//    ///////////////////////////////////////
//    // /cust/{username}/acc/{accountid}/fundlink/{accfundid}/add
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/fundlink/{accfundid}/add", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int getAccountStockFundAdd(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("accfundid") String accfundid,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return 0;
//        }
//
//        int ret = afWebService.getFundAccountAddAccundFund(username, null, accountid, accfundid);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return ret;
//    }
//
//    // /cust/{username}/acc/{accountid}/fundlink/{accfundid}/remove
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/fundlink/{accfundid}/remove", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int getAccountStockFundRemove(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("accfundid") String accfundid,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return 0;
//        }
//
//        int ret = afWebService.getFundAccountRemoveAcocuntFund(username, null, accountid, accfundid);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return ret;
//    }
//
//    //("/cust/{username}/acc/{accountid}/fundlink/{accfundid}/st?length={0 for all} - default 20")
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/fundlink/{accfundid}/st", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getAccountStockFundStockList(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("accfundid") String accfundid,
//            @RequestParam(value = "length", required = false) String lengthSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        int length = 20;
//        if (lengthSt != null) {
//            length = Integer.parseInt(lengthSt);
//        }
//
//        ArrayList returnList = afWebService.getFundStockListByAccountID(username, null, accountid, accfundid, length);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return returnList;
//    }
//
//    // "/cust/{username}/acc/{accountid}/stname"
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/stname", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getAccountStockName_StockList(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @RequestParam(value = "length", required = false) String lengthSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//
//        ArrayList returnList = afWebService.getStockNameListByAccountID(username, null, accountid);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return returnList;
//    }
//
//    // "/cust/{username}/acc/{accountid}/st?trname=&filter= (Max 50)&length= (default 20 Max 50)"
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/st", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getAccountStock_StockList(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @RequestParam(value = "trname", required = false) String trnameSt,
//            @RequestParam(value = "filter", required = false) String filterSt,
//            @RequestParam(value = "length", required = false) String lengthSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        int length = 20;
//        if (lengthSt != null) {
//            length = Integer.parseInt(lengthSt);
//            if (length > 50) {
//                length = 50;
//            }
//        }
//        String trname = ConstantKey.TR_ACC;
//        if (trnameSt != null) {
//            trname = trnameSt;
//        }
//        ArrayList returnList = afWebService.getStockListByAccountIDTRname(username, null, accountid, trname, filterSt, length);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return returnList;
//    }
//
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/st/addsymbol", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int addAccountStock_stockSym(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @RequestParam(value = "symbol", required = true) String symbol,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return 0;
//        }
//        //MAX_ALLOW_STOCK_ERROR = 100 ; NEW = 1; EXISTED = 2
//        int result = afWebService.addAccountStockByCustAcc(username, null, accountid, symbol);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return result;
//    }
//
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/st/add/{symbol}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int addAccountStock_stock(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("symbol") String symbol,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return 0;
//        }
//        int result = afWebService.addAccountStockByCustAcc(username, null, accountid, symbol);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return result;
//    }
//
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/st/removesymbol", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int removeAccountStock_stockSym(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @RequestParam(value = "symbol", required = true) String symbol,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return 0;
//        }
//        int result = afWebService.removeAccountStockByUserNameAccId(username, null, accountid, symbol);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return result;
//    }
//
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/st/remove/{symbol}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int removeAccountStock_stock(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("symbol") String symbol,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return 0;
//        }
//        int result = afWebService.removeAccountStockByUserNameAccId(username, null, accountid, symbol);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return result;
//    }
//
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/st/{stockidsymbol}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    AFstockObj getAccountStock_Stock(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("stockidsymbol") String stockidsymbol,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        AFstockObj retObj = afWebService.getStockByAccountIDStockID(username, null, accountid, stockidsymbol);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return retObj;
//    }
//
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/st/{stockidsymbol}/tr/{trname}/tran/{signal}/order", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int setAccountStockTran(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("stockidsymbol") String stockidsymbol,
//            @PathVariable("trname") String trname,
//            @PathVariable("signal") String signalSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return 0;
//        }
//        int signal = Integer.parseInt(signalSt);
//        int ret = afWebService.addAccountStockTran(username, null, accountid, stockidsymbol, trname, signal);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return ret;
//
//    }
//
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/st/{stockidsymbol}/tr", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getAccountStock(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("stockidsymbol") String stockidsymbol,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        ArrayList returnList = afWebService.getAccountStockTRListByAccountID(username, null, accountid, stockidsymbol);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return returnList;
//
//    }
//
//    // "/cust/{username}/acc/{accountid}/fundlink/{accfundid}/st/{stockid or symbol}/tr}")
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/fundlink/{accfundid}/st/{stockidsymbol}/tr", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    TradingRuleObj getAccountFundStockByTRname(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("accfundid") String accfundid,
//            @PathVariable("stockidsymbol") String stockidsymbol,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        TradingRuleObj returnObj = afWebService.getFundAccountStockTRByTRname(username, null, accountid, accfundid, stockidsymbol, "TR_ACC");
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return returnObj;
//    }
//
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/fundlink/{accfundid}/st/{stockidsymbol}/tr/{trname}/tran", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getFundAccountStockTran(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("accfundid") String accfundid,
//            @PathVariable("stockidsymbol") String stockidsymbol,
//            @PathVariable("trname") String trname,
//            @RequestParam(value = "length", required = false) String lengthSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        int length = 0; //20;
//        if (lengthSt != null) {
//            length = Integer.parseInt(lengthSt);
//        }
//        ArrayList returnList = afWebService.getFundAccountStockTRTranListByAccountID(username, null, accountid, accfundid, stockidsymbol, trname, length);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return returnList;
//    }
//
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/st/{stockidsymbol}/tr/{trname}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    TradingRuleObj getAccountStockByTRname(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("stockidsymbol") String stockidsymbol,
//            @PathVariable("trname") String trname,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        TradingRuleObj returnObj = afWebService.getAccountStockTRByTRname(username, null, accountid, stockidsymbol, trname);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return returnObj;
//    }
//
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/st/{stockidsymbol}/tr/{trname}/linktr/{linktype}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int setAccountStockTRoption(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("stockidsymbol") String stockidsymbol,
//            @PathVariable("trname") String trname,
//            @PathVariable("linktype") String linktypeST,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return 0;
//        }
//        int ret = 0;
//        ret = afWebService.setAccountStockTRoption(username, null, accountid, stockidsymbol, trname, linktypeST.toUpperCase());
//
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return ret;
//    }
//
//    //"/cust/{username}/acc/{accountid}/st/{stockidsymbol}/tr/{trname}/tran?length=0"
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/st/{stockidsymbol}/tr/{trname}/tran", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getAccountStockTran(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("stockidsymbol") String stockidsymbol,
//            @PathVariable("trname") String trname,
//            @RequestParam(value = "length", required = false) String lengthSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        int length = 0; //20;
//        if (lengthSt != null) {
//            length = Integer.parseInt(lengthSt);
//        }
//        ArrayList returnList = afWebService.getAccountStockTRTranListByAccountID(username, null, accountid, stockidsymbol, trname, length);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return returnList;
//    }
//
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/st/{stockidsymbol}/tr/{trname}/tran/history", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getAccountStockHistory(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("stockidsymbol") String stockidsymbol,
//            @PathVariable("trname") String trname,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        ArrayList returnList = afWebService.getAccountStockTRListHistory(username, null, accountid, stockidsymbol, trname);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return returnList;
//    }
//
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/st/{stockidsymbol}/tr/{trname}/tran/history/display", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getAccountStockHistoryDisplay(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("stockidsymbol") String stockidsymbol,
//            @PathVariable("trname") String trname,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        ArrayList returnList = afWebService.getAccountStockTRListHistoryDisplay(username, null, accountid, stockidsymbol, trname);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return returnList;
//    }
//
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/st/{stockidsymbol}/tr/{trname}/tran/history/chartfile", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    String getAccountStockHistoryChart(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("stockidsymbol") String stockidsymbol,
//            @PathVariable("trname") String trname,
//            @RequestParam(value = "path", required = false) String pathSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return "";
//        }
//
//        String ret = afWebService.getAccountStockTRLIstCurrentChartFile(username, null, accountid, stockidsymbol, trname, pathSt);
////        String ret = afWebService.getAccountStockTRListHistoryChart(username, null, accountid, stockidsymbol, trname, pathSt);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return ret;
//    }
//
//    //("/cust/{username}/acc/{accountid}/fundlink/{accfundid}/st/{stockid or symbol}/tr/{trname}/tran/history/chart?month=");   
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/fundlink/{accfundid}/st/{stockidsymbol}/tr/{trname}/tran/history/chart", method = RequestMethod.GET, produces = {MediaType.IMAGE_JPEG_VALUE})
//    public @ResponseBody
//    byte[] getAccountFundStockHistoryChartDisplay(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("accfundid") String accfundid,
//            @PathVariable("stockidsymbol") String stockidsymbol,
//            @PathVariable("trname") String trname,
//            @RequestParam(value = "month", required = false) String monthSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//
//        byte[] ret = afWebService.getFundAccountStockTRLIstCurrentChartDisplay(username, null, accountid, accfundid, stockidsymbol, trname, monthSt);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return ret;
//    }
//
//    //"/cust/{username}/acc/{accountid}/st/{stockid or symbol}/tr/{trname}/tran/history/chart?month="
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/st/{stockidsymbol}/tr/{trname}/tran/history/chart", method = RequestMethod.GET, produces = {MediaType.IMAGE_JPEG_VALUE})
//    public @ResponseBody
//    byte[] getAccountStockHistoryChartDisplay(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("stockidsymbol") String stockidsymbol,
//            @PathVariable("trname") String trname,
//            @RequestParam(value = "month", required = false) String monthSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//
//        byte[] ret = afWebService.getAccountStockTRLIstCurrentChartDisplay(username, null, accountid, stockidsymbol, trname, monthSt);
////        byte[] ret = afWebService.getAccountStockTRListHistoryChartDisplay(username, null, accountid, stockidsymbol, trname, pathSt);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return ret;
//    }
//
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/st/{stockidsymbol}/tr/{trname}/tran/clear", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int getAccountStockClrTran(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("stockidsymbol") String stockidsymbol,
//            @PathVariable("trname") String trname,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return 0;
//        }
//        int ret = afWebService.getAccountStockTRClrTranByAccountID(username, null, accountid, stockidsymbol, trname);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return ret;
//    }
//
//    //("/cust/{username}/acc/{accountid}/fundlink/{accfundid}/st/{stockid or symbol}/tr/{trname}/perf");
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/fundlink/{accfundid}/st/{stockidsymbol}/tr/{trname}/perf", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getFundAccountStockTranPerf(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("accfundid") String accfundid,
//            @PathVariable("stockidsymbol") String stockidsymbol,
//            @PathVariable("trname") String trname,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        int length = 0;
//
//        ArrayList returnList = afWebService.getFundAccountStockTRPerfList(username, null, accountid, accfundid, stockidsymbol, trname, length);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return returnList;
//    }
//
//    // "/cust/{username}/acc/{accountid}/st/{stockidsymbol}/tr/{trname}/perf"
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/st/{stockidsymbol}/tr/{trname}/perf", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getAccountStockTranPerf(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("stockidsymbol") String stockidsymbol,
//            @PathVariable("trname") String trname,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        int length = 0;
//        ArrayList returnList = afWebService.getAccountStockTRPerfList(username, null, accountid, stockidsymbol, trname, length);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return returnList;
//    }
//
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/st/{stockidsymbol}/tr/{trname}/perf/history", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getAccountStockTranPerfHistory(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("stockidsymbol") String stockidsymbol,
//            @PathVariable("trname") String trname,
//            @RequestParam(value = "length", required = false) String lengthSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        int length = 0;
//        if (lengthSt != null) {
//            length = Integer.parseInt(lengthSt);
//        }
//        ArrayList returnList = afWebService.getAccountStockTRPerfHistory(username, null, accountid, stockidsymbol, trname, length);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return returnList;
//    }
//
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/st/{stockidsymbol}/tr/{trname}/perf/historyreinvest", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getAccountStockTranPerfHistoryReinvest(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("stockidsymbol") String stockidsymbol,
//            @PathVariable("trname") String trname,
//            @RequestParam(value = "length", required = false) String lengthSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        int length = 0;
//        if (lengthSt != null) {
//            length = Integer.parseInt(lengthSt);
//        }
//        ArrayList returnList = afWebService.getAccountStockTRPerfHistoryReinvest(username, null, accountid, stockidsymbol, trname, length);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return returnList;
//    }
//
//    @RequestMapping(value = "/cust/{username}/acc/{accountid}/st/{stockidsymbol}/tr/{trname}/perf/history/display", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getAccountStockPerfHistoryDisplay(
//            @PathVariable("username") String username,
//            @PathVariable("accountid") String accountid,
//            @PathVariable("stockidsymbol") String stockidsymbol,
//            @PathVariable("trname") String trname,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//
//        ArrayList returnList = afWebService.getAccountStockTRPerfHistoryDisplay(username, null, accountid, stockidsymbol, trname);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//
//        return returnList;
//    }
//
//    ///////////////////////////////////////
//    @RequestMapping(value = "/st", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getStockList(
//            @RequestParam(value = "length", required = false) String lengthSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        int length = 0; //20;
//        if (lengthSt != null) {
//            length = Integer.parseInt(lengthSt);
//        }
//        ArrayList stockNameList = afWebService.getStockArray(length);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return stockNameList;
//    }
//
//    /**
//     *
//     * @param symbol
//     * @return
//     */
//    @RequestMapping(value = "/st/{symbol}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    AFstockObj getStockRT(
//            @PathVariable("symbol") String symbol,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        AFstockObj stock = afWebService.getStockRealTime(symbol);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return stock;
//    }
//
//    @RequestMapping(value = "/st/{symbol}/history", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getStockHistory(
//            @PathVariable("symbol") String symbol,
//            @RequestParam(value = "length", required = false) String lengthSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        int length = 0; //20;
//        if (lengthSt != null) {
//            length = Integer.parseInt(lengthSt);
//        }
//        ArrayList stockInfoList = afWebService.getStockHistorical(symbol, length);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return stockInfoList;
//    }
//
//    @RequestMapping(value = "/st/add/{symbol}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int addStock(
//            @PathVariable("symbol") String symbol,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return 0;
//        }
//        int result = afWebService.addStock(symbol);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return result;
//    }
//
//    @RequestMapping(value = "/st/remove/{symbol}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int DisableStock(
//            @PathVariable("symbol") String symbol,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return 0;
//        }
//        int result = afWebService.disableStock(symbol);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return result;
//    }
//
//    @RequestMapping(value = "/st/cleanallinfo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int cleanAllStockInfo(
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return 0;
//        }
//        int result = afWebService.cleanAllStockInfo();
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return result;
//    }
//
//    @RequestMapping(value = "/st/deleteinfo/{symbol}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int deleteStockInfo(
//            @PathVariable("symbol") String symbol,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return 0;
//        }
//        int result = afWebService.removeStockInfo(symbol);
//        ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//        return result;
//    }
//
//    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    WebStatus serverPing() {
//        WebStatus msg = new WebStatus();
//
//        msg = afWebService.serverPing();
//        return msg;
//    }
//
//    //////////////
//    @RequestMapping(value = "/cust/{username}/sys/stop", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    WebStatus SystemStop(@PathVariable("username") String username) {
//        WebStatus msg = new WebStatus();
//
//        if (username.toLowerCase().equals(CKey.ADMIN_USERNAME.toLowerCase())) {
//            msg.setResponse(afWebService.SystemStop());
//            msg.setResult(true);
//            return msg;
//        }
//
////        CustomerObj cust = afWebService.getCustomerIgnoreMaintenance(username, null);
////        if (cust != null) {
////            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
////                msg.setResponse(afWebService.SystemStop());
////                msg.setResult(true);
////                return msg;
////            }
////        }
//        return null;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/start", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    WebStatus SystemStart(@PathVariable("username") String username) {
//        WebStatus msg = new WebStatus();
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            if (username.toLowerCase().equals(CKey.ADMIN_USERNAME.toLowerCase())) {
//                msg.setResponse(afWebService.SystemStart());
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        CustomerObj cust = afWebService.getCustomerIgnoreMaintenance(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                msg.setResponse(afWebService.SystemStart());
//                msg.setResult(true);
//                return msg;
//            }
//        }
//        return null;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/cleandb", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    WebStatus SystemCleanDB(@PathVariable("username") String username) {
//        WebStatus msg = new WebStatus();
//        if (CKey.UI_ONLY == true) {
//            return null;
//        }
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            if (username.toLowerCase().equals(CKey.ADMIN_USERNAME.toLowerCase())) {
//                msg.setResponse(afWebService.SystemCleanDBData());
//                msg.setResult(true);
//                return msg;
//            }
//        }
//        CustomerObj cust = afWebService.getCustomerIgnoreMaintenance(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                msg.setResponse(afWebService.SystemCleanDBData());
//                msg.setResult(true);
//                return msg;
//            }
//        }
//        return null;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/resetdb", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    WebStatus SystemResetDB(@PathVariable("username") String username) {
//        WebStatus msg = new WebStatus();
//        // remote is stopped
//        if (CKey.UI_ONLY == true) {
//            return null;
//        }
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            if (username.toLowerCase().equals(CKey.ADMIN_USERNAME.toLowerCase())) {
//                msg.setResponse(afWebService.SystemRestDBData());
//                msg.setResult(true);
//                return msg;
//            }
//        }
//        CustomerObj cust = afWebService.getCustomerIgnoreMaintenance(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                msg.setResponse(afWebService.SystemRestDBData());
//                msg.setResult(true);
//                return msg;
//            }
//        }
//        return null;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/clearlock", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    WebStatus SystemClearLock(@PathVariable("username") String username) {
//        WebStatus msg = new WebStatus();
//        // remote is stopped
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            if (username.toLowerCase().equals(CKey.ADMIN_USERNAME.toLowerCase())) {
//                msg.setResponse(afWebService.SystemClearLock());
//                msg.setResult(true);
//                return msg;
//            }
//        }
//        CustomerObj cust = afWebService.getCustomerIgnoreMaintenance(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                msg.setResponse(afWebService.SystemClearLock());
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        return null;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/clearnninput", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    WebStatus SystemClearNNinput(@PathVariable("username") String username) {
//        WebStatus msg = new WebStatus();
//        // remote is stopped
//
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            if (username.toLowerCase().equals(CKey.ADMIN_USERNAME.toLowerCase())) {
//                msg.setResponse(afWebService.SystemClearNNinput());
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        CustomerObj cust = afWebService.getCustomerIgnoreMaintenance(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                msg.setResponse(afWebService.SystemClearNNinput());
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        return null;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/globalfundmgr", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    WebStatus getSystemFundMgr(@PathVariable("username") String username) {
//        WebStatus msg = new WebStatus();
//        // remote is stopped
//
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            if (username.toLowerCase().equals(CKey.ADMIN_USERNAME.toLowerCase())) {
//                msg.setResponse("System in Maintenance");
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        CustomerObj cust = afWebService.getCustomerIgnoreMaintenance(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                msg.setResponse("" + afWebService.SystemFundResetGlobal());
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        return null;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/performfundmgr", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    WebStatus getSystemPerfFundMgr(@PathVariable("username") String username) {
//        WebStatus msg = new WebStatus();
//        // remote is stopped
//
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            if (username.toLowerCase().equals(CKey.ADMIN_USERNAME.toLowerCase())) {
//                msg.setResponse("System in Maintenance");
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        CustomerObj cust = afWebService.getCustomerIgnoreMaintenance(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                msg.setResponse("" + afWebService.SystemFundSelectBest());
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        return null;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/processfundmgr", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    WebStatus getProcessFundMgr(@PathVariable("username") String username) {
//        WebStatus msg = new WebStatus();
//        // remote is stopped
//
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            if (username.toLowerCase().equals(CKey.ADMIN_USERNAME.toLowerCase())) {
//                msg.setResponse("System in Maintenance");
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        CustomerObj cust = afWebService.getCustomerIgnoreMaintenance(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                msg.setResponse("" + afWebService.SystemFundPocessAddRemove());
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        return null;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/deletenn1table", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    WebStatus getDeleteNN1(@PathVariable("username") String username) {
//        WebStatus msg = new WebStatus();
//        // remote is stopped
//
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            if (username.toLowerCase().equals(CKey.ADMIN_USERNAME.toLowerCase())) {
//                msg.setResponse("" + afWebService.SystemDeleteNN1Table());
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        CustomerObj cust = afWebService.getCustomerIgnoreMaintenance(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                msg.setResponse("" + afWebService.SystemDeleteNN1Table());
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        return null;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/autonnflag", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    WebStatus getSystemNNFlag(@PathVariable("username") String username) {
//        WebStatus msg = new WebStatus();
//        // remote is stopped
//
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            if (username.toLowerCase().equals(CKey.ADMIN_USERNAME.toLowerCase())) {
//                msg.setResponse("" + ServiceAFweb.processNeuralNetFlag);
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        CustomerObj cust = afWebService.getCustomerIgnoreMaintenance(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                msg.setResponse("" + ServiceAFweb.processNeuralNetFlag);
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        return null;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/autonnflag/enable", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    WebStatus setSystemNNFlag(@PathVariable("username") String username) {
//        WebStatus msg = new WebStatus();
//        // remote is stopped
//
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            if (username.toLowerCase().equals(CKey.ADMIN_USERNAME.toLowerCase())) {
//                ServiceAFweb.processNeuralNetFlag = true;
//
//                msg.setResponse("" + ServiceAFweb.processNeuralNetFlag);
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        CustomerObj cust = afWebService.getCustomerIgnoreMaintenance(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                ServiceAFweb.processNeuralNetFlag = true;
//
//                msg.setResponse("" + ServiceAFweb.processNeuralNetFlag);
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        return null;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/autonnflag/disable", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    WebStatus setdisableSystemNNFlag(@PathVariable("username") String username) {
//        WebStatus msg = new WebStatus();
//        // remote is stopped
//
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            if (username.toLowerCase().equals(CKey.ADMIN_USERNAME.toLowerCase())) {
//                ServiceAFweb.processNeuralNetFlag = false;
//
//                msg.setResponse("" + ServiceAFweb.processNeuralNetFlag);
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        CustomerObj cust = afWebService.getCustomerIgnoreMaintenance(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                ServiceAFweb.processNeuralNetFlag = false;
//
//                msg.setResponse("" + ServiceAFweb.processNeuralNetFlag);
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        return null;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/clearallnntran", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    WebStatus SystemClearAllNNtran(@PathVariable("username") String username) {
//        WebStatus msg = new WebStatus();
//        // remote is stopped
//
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            if (username.toLowerCase().equals(CKey.ADMIN_USERNAME.toLowerCase())) {
//                msg.setResponse(afWebService.SystemClearNNtran(ConstantKey.SIZE_TR));
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        CustomerObj cust = afWebService.getCustomerIgnoreMaintenance(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                msg.setResponse(afWebService.SystemClearNNtran(ConstantKey.SIZE_TR));
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        return null;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/clearnn2tran", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    WebStatus SystemClearNNtran(
//            @PathVariable("username") String username,
//            @RequestParam(value = "tr", required = false) String trSt
//    ) {
//        WebStatus msg = new WebStatus();
//        // remote is stopped
//
//        int defTR = ConstantKey.INT_TR_NN2;
//
//        if (trSt != null) {
//            defTR = Integer.parseInt(trSt);
//        }
//
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            if (username.toLowerCase().equals(CKey.ADMIN_USERNAME.toLowerCase())) {
//
//                msg.setResponse(afWebService.SystemClearNNtran(defTR));
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        CustomerObj cust = afWebService.getCustomerIgnoreMaintenance(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                msg.setResponse(afWebService.SystemClearNNtran(defTR));
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        return null;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/downloaddb", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    WebStatus SystemDownloadDB(@PathVariable("username") String username) {
//        WebStatus msg = new WebStatus();
//        // remoote is stopped
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            if (username.toLowerCase().equals(CKey.ADMIN_USERNAME.toLowerCase())) {
//                msg.setResponse(afWebService.SystemDownloadDBData());
//                msg.setResult(true);
//                return msg;
//            }
//        }
//        CustomerObj cust = afWebService.getCustomerIgnoreMaintenance(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                msg.setResponse(afWebService.SystemDownloadDBData());
//                msg.setResult(true);
//                return msg;
//            }
//        }
//        return null;
//
//    }
//
//    ///// Restore DB need the following
//    ////  SystemStop
//    ////  SystemCleanDBData
//    ////  SystemUploadDBData
//    ///// Restore DB need the following   
//    @RequestMapping(value = "/cust/{username}/sys/restoredb", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    WebStatus SystemRestoreDB(@PathVariable("username") String username) {
//        WebStatus msg = new WebStatus();
//        // remote is stopped
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            if (username.toLowerCase().equals(CKey.ADMIN_USERNAME.toLowerCase())) {
//                msg.setResponse(afWebService.SystemRestoreDBData());
//                msg.setResult(true);
//                return msg;
//            }
//        }
//
//        CustomerObj cust = afWebService.getCustomerIgnoreMaintenance(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                msg.setResponse(afWebService.SystemRestoreDBData());
//                msg.setResult(true);
//                return msg;
//            }
//        }
//        return null;
//    }
//
//    ///cust/{username}/uisys/{custid}/custnlist?length={0 for all} - default 20");
//    @RequestMapping(value = "/cust/{username}/uisys/{custid}/custnlist", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getUICustNList(
//            @PathVariable("username") String username,
//            @PathVariable("custid") String custidSt,
//            @RequestParam(value = "length", required = false) String lengthSt) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        int length = 0; //20;
//        if (lengthSt != null) {
//            length = Integer.parseInt(lengthSt);
//        }
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (custidSt.equals(cust.getId() + "")) {
//                if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                    ArrayList custNameList = afWebService.getCustomerNList(length);
//                    ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                    return custNameList;
//                }
//            }
//        }
//        return null;
//
//    }
//
//    //"/cust/{username}/uisys/{custid}/accounting/update?payment=&balance=&reason=&rate=&comment="
//    @RequestMapping(value = "/cust/{username}/uisys/{custid}/accounting/update", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int updateAccoundingEntry(
//            @PathVariable("username") String username,
//            @PathVariable("custid") String custidSt,
//            @RequestParam(value = "payment", required = false) String paymentSt,
//            @RequestParam(value = "balance", required = false) String balanceSt,
//            @RequestParam(value = "reason", required = false) String reasonSt,
//            @RequestParam(value = "rate", required = false) String rateSt,
//            @RequestParam(value = "comment", required = false) String commentSt
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                if (custidSt.equals(cust.getId() + "")) {
//                    //updating the real customer in custSt not the addmin user
//                    int result = afWebService.updateAccountingEntryPaymentBalance(username, paymentSt, balanceSt, reasonSt, rateSt, commentSt);
//                    ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                    return result;
//                }
//            }
//        }
//        return 0;
//    }
//
//    //"/cust/{username}/uisys/{custid}/accounting/tax?payment=&reason=&comment=
//    @RequestMapping(value = "/cust/{username}/uisys/{custid}/accounting/tax", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int updateAccoundingEntryTAX(
//            @PathVariable("username") String username,
//            @PathVariable("custid") String custidSt,
//            @RequestParam(value = "payment", required = false) String paymentSt,
//            @RequestParam(value = "reason", required = false) String reasonSt,
//            @RequestParam(value = "comment", required = false) String commentSt
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                if (custidSt.equals(cust.getId() + "")) {
//                    //updating the real customer in custSt not the addmin user
//                    int result = afWebService.insertAccountTAX(username, paymentSt, reasonSt, commentSt);
//                    ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                    return result;
//                }
//            }
//        }
//        return 0;
//    }
//
//    //"/cust/{username}/uisys/{custid}/accounting/costofgoodsold?payment=&curyear=&reason=&comment=
//    @RequestMapping(value = "/cust/{username}/uisys/{custid}/accounting/costofgoodsold", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int updateAccoundingEntrycostofGS(
//            @PathVariable("username") String username,
//            @PathVariable("custid") String custidSt,
//            @RequestParam(value = "payment", required = false) String paymentSt,
//            @RequestParam(value = "curyear", required = false) String curyearSt,
//            @RequestParam(value = "reason", required = false) String reasonSt,
//            @RequestParam(value = "comment", required = false) String commentSt
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                if (custidSt.equals(cust.getId() + "")) {
//                    //updating the real customer in custSt not the addmin user
//                    int result = afWebService.updateAccountingExCostofGS(username, paymentSt, curyearSt, reasonSt, commentSt);
//                    ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                    return result;
//                }
//            }
//        }
//        return 0;
//    }
//
//    //"/cust/{username}/uisys/{custid}/accounting/deprecation?payment=&rate=&reason=&comment="
//    @RequestMapping(value = "/cust/{username}/uisys/{custid}/accounting/deprecation", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int updateAccoundingEntryDeprecation(
//            @PathVariable("username") String username,
//            @PathVariable("custid") String custidSt,
//            @RequestParam(value = "payment", required = false) String paymentSt,
//            @RequestParam(value = "rate", required = false) String rateSt,
//            @RequestParam(value = "reason", required = false) String reasonSt,
//            @RequestParam(value = "comment", required = false) String commentSt
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                if (custidSt.equals(cust.getId() + "")) {
//                    //updating the real customer in custSt not the addmin user
//                    int result = afWebService.updateAccountingExDeprecation(username, paymentSt, rateSt, reasonSt, commentSt);
//                    ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                    return result;
//                }
//            }
//        }
//        return 0;
//    }
//
//    //("/cust/{username}/uisys/{custid}/accounting/report?name=&year=");
//    @RequestMapping(value = "/cust/{username}/uisys/{custid}/accounting/report", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    AccReportObj getUIAccountReport(
//            @PathVariable("username") String username,
//            @PathVariable("custid") String custidSt,
//            @RequestParam(value = "name", required = false) String nameSt,
//            @RequestParam(value = "year", required = false) String yeatSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//        int year = 0;
//        if (yeatSt != null) {
//            year = Integer.parseInt(yeatSt);
//        }
//
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (custidSt.equals(cust.getId() + "")) {
//                if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                    AccReportObj accReportObj = afWebService.getAccountingReportByCustomerByName(username, null, nameSt, year);
//                    ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                    return accReportObj;
//                }
//            }
//        }
//        return null;
//    }
//
//    //("/cust/{username}/uisys/{custid}/accounting/entry/{id}");
//    @RequestMapping(value = "/cust/{username}/uisys/{custid}/accounting/entry/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    AccEntryObj getUIAccountReportId(
//            @PathVariable("username") String username,
//            @PathVariable("custid") String custidSt,
//            @PathVariable("id") String idSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return null;
//        }
//
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (custidSt.equals(cust.getId() + "")) {
//                if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                    AccEntryObj accEntry = afWebService.getAccountingEntryByCustomerById(username, null, idSt);
//                    ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                    return accEntry;
//                }
//            }
//        }
//        return null;
//    }
//
//    //("/cust/{username}/uisys/{custid}/accounting/entry/{id}/remove");
//    @RequestMapping(value = "/cust/{username}/uisys/{custid}/accounting/entry/{id}/remove", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int removeUIAccountReportId(
//            @PathVariable("username") String username,
//            @PathVariable("custid") String custidSt,
//            @PathVariable("id") String idSt,
//            HttpServletRequest request, HttpServletResponse response
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return 0;
//        }
//
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (custidSt.equals(cust.getId() + "")) {
//                if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                    ////////
//
//                    int ret = afWebService.removeAccountingEntryById(username, null, idSt);
//                    ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                    return ret;
//                }
//            }
//        }
//        return 0;
//    }
//
//    ///cust/{username}/uisys/{custid}/custlist?name&length={0 for all} - default 20");
//    @RequestMapping(value = "/cust/{username}/uisys/{custid}/custlist", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getUICustList(
//            @PathVariable("username") String username,
//            @PathVariable("custid") String custidSt,
//            @RequestParam(value = "name", required = false) String nameSt,
//            @RequestParam(value = "length", required = false) String lengthSt) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        int length = 0; //20;
//        if (lengthSt != null) {
//            length = Integer.parseInt(lengthSt);
//        }
//        ArrayList custObjList = new ArrayList();
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (custidSt.equals(cust.getId() + "")) {
//                if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                    if (nameSt != null) {
//                        NameObj nameObj = new NameObj(nameSt);
//                        String UserName = nameObj.getNormalizeName();
//                        CustomerObj cutObj = afWebService.getCustomerObjByName(UserName);
//                        custObjList.add(cutObj);
//                    } else {
//
//                        custObjList = afWebService.getCustomerList(length);
//                    }
//
//                    ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                    return custObjList;
//                }
//            }
//        }
//        return null;
//
//    }
//
////    ///cust/{username}/sys/custlist?length={0 for all} - default 20
////    @RequestMapping(value = "/cust/{username}/sys/custlist", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
////    public @ResponseBody
////    ArrayList getCustList(
////            @PathVariable("username") String username,
////            @RequestParam(value = "length", required = false) String lengthSt) {
////        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
////        int length = 0; //20;
////        if (lengthSt != null) {
////            length = Integer.parseInt(lengthSt);
////        }
////        CustomerObj cust = afWebService.getCustomerPassword(username, null);
////        if (cust != null) {
////            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
////                ArrayList custNameList = afWebService.getCustomerList(length);
////                ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
////                return custNameList;
////            }
////        }
////        return null;
////
////    }
//    @RequestMapping(value = "/cust/{username}/sys/expiredcustlist", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getExpiredCustList(
//            @PathVariable("username") String username,
//            @RequestParam(value = "length", required = false) String lengthSt) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        int length = 0; //20;
//        if (lengthSt != null) {
//            length = Integer.parseInt(lengthSt);
//        }
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                ArrayList custNameList = afWebService.getExpiredCustomerList(length);
//                ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                return custNameList;
//            }
//        }
//        return null;
//
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/expiredStocklist", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getExpiredStocklist(
//            @PathVariable("username") String username,
//            @RequestParam(value = "length", required = false) String lengthSt) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        int length = 0; //20;
//        if (lengthSt != null) {
//            length = Integer.parseInt(lengthSt);
//        }
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                ArrayList stNameList = afWebService.getDisableStockNameList(length);
//                ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                return stNameList;
//            }
//        }
//        return null;
//
//    }
//
//    //"/cust/{username}/uisys/{custid}/timer"
//    @RequestMapping(value = "/cust/{username}/uisys/{custid}/timer", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    WebStatus getUITimer(
//            @PathVariable("username") String username,
//            @PathVariable("custid") String custidSt
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                if (custidSt.equals(cust.getId() + "")) {
//                    WebStatus msg = new WebStatus();
//                    msg.setResult(true);
//                    msg.setResultID(ConstantKey.ENABLE);
//
//                    //process timer handler
//                    int timerCnt = afWebService.timerHandler("starttimer");
//
//                    msg.setResponse("timerCnt " + timerCnt);
//                    ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                    return msg;
//                }
//            }
//        }
//        return null;
//    }
//
//    //"/cust/{username}/uisys/{custid}/lock"
//    @RequestMapping(value = "/cust/{username}/uisys/{custid}/lock", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getUILockAll(
//            @PathVariable("username") String username,
//            @PathVariable("custid") String custidSt
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                if (custidSt.equals(cust.getId() + "")) {
//                    ArrayList result = afWebService.getAllLock();
//                    ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                    return result;
//                }
//            }
//        }
//        return null;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/lock", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    ArrayList getLockAll(
//            @PathVariable("username") String username
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            if (username.toLowerCase().equals(CKey.ADMIN_USERNAME.toLowerCase())) {
//                ArrayList result = afWebService.getAllLock();
//                return result;
//            }
//        }
//
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                ArrayList result = afWebService.getAllLock();
//                ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                return result;
//            }
//        }
//        return null;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/lock/{lockname}/type/{type}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    AFLockObject getLockName(
//            @PathVariable("username") String username,
//            @PathVariable("lockname") String lockname,
//            @PathVariable("type") String type
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                int locktype = Integer.parseInt(type);
//                AFLockObject result = afWebService.getLockName(lockname, locktype);
//                ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                return result;
//            }
//        }
//        return null;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/lock/{lockname}/type/{type}/renewlock", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int setRenewLock(
//            @PathVariable("username") String username,
//            @PathVariable("lockname") String name,
//            @PathVariable("type") String type
//    ) {
//
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                int locktype = Integer.parseInt(type);
//                int result = afWebService.setRenewLock(name, locktype);
//                ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                return result;
//            }
//        }
//        return 0;
//    }
//
/////////////////////////////
//    @RequestMapping(value = "/cust/{username}/sys/neuralnet/{name}/release", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int releaseNeuralNetObj(
//            @PathVariable("username") String username,
//            @PathVariable("name") String name
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//
//                int result = afWebService.releaseNeuralNetObj(name);
//                ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                return result;
//            }
//        }
//        return 0;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/neuralnet/{name}/type/{type}/weight0", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    AFneuralNet getNeuralNetObjWeight0(
//            @PathVariable("username") String username,
//            @PathVariable("name") String name,
//            @PathVariable("type") String type
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                int nnType = Integer.parseInt(type);
//                AFneuralNet result = afWebService.getNeuralNetObjWeight0(name, nnType);
//                ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                return result;
//            }
//        }
//        return null;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/neuralnet/{name}/type/{type}/weight1", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    AFneuralNet getNeuralNetObjWeight1(
//            @PathVariable("username") String username,
//            @PathVariable("name") String name,
//            @PathVariable("type") String type
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                int nnType = Integer.parseInt(type);
//                AFneuralNet result = afWebService.getNeuralNetObjWeight1(name, nnType);
//                ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                return result;
//            }
//        }
//        return null;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/neuralnet/{name}/updateweight0", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int setNeuralNetObjWeight0(
//            @PathVariable("username") String username,
//            @PathVariable("name") String name,
//            @RequestBody String input
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        AFneuralNet afNeuralNet = null;
//        try {
////            int size = input.length();
////            input = input.substring(1, size - 1);
//            afNeuralNet = new ObjectMapper().readValue(input, AFneuralNet.class);
//        } catch (IOException ex) {
//            return 0;
//        }
//
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                int result = afWebService.setNeuralNetObjWeight0(afNeuralNet);
//                ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                return result;
//            }
//        }
//        return 0;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/neuralnet/{name}/updateweight1", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int setNeuralNetObjWeight1(
//            @PathVariable("username") String username,
//            @PathVariable("name") String name,
//            @RequestBody String input
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        AFneuralNet afNeuralNet = null;
//        try {
////            int size = input.length();
////            input = input.substring(1, size - 1);
//
//            afNeuralNet = new ObjectMapper().readValue(input, AFneuralNet.class);
//        } catch (IOException ex) {
//            return 0;
//        }
//
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                int result = afWebService.setNeuralNetObjWeight1(afNeuralNet);
//                ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                return result;
//            }
//        }
//        return 0;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/request", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    RequestObj SystemSQLRequest(
//            @PathVariable("username") String username,
//            @RequestBody String input
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        RequestObj sqlReq = null;
//        try {
//            sqlReq = new ObjectMapper().readValue(input, RequestObj.class);
//        } catch (IOException ex) {
//            return null;
//        }
//
//        if (ServiceAFweb.getServerObj().isSysMaintenance() == true) {
//            if (username.toLowerCase().equals(CKey.ADMIN_USERNAME.toLowerCase())) {
//                RequestObj sqlResp = afWebService.SystemSQLRequest(sqlReq);
//                return sqlResp;
//            }
//        }
//
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                RequestObj sqlResp = afWebService.SystemSQLRequest(sqlReq);
//                ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                return sqlResp;
//            }
//        }
//        return null;
//    }
//
////////////////
//    @RequestMapping(value = "/cust/{username}/sys/lock/{lockname}/type/{type}/value/{lockdate}/comment/{comment}/setlock", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int setLockName(
//            @PathVariable("username") String username,
//            @PathVariable("lockname") String name,
//            @PathVariable("type") String type,
//            @PathVariable("lockdate") String lockdate,
//            @PathVariable("comment") String comment
//    ) {
//
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                int locktype = Integer.parseInt(type);
//                long lockdatel = Long.parseLong(lockdate);
//                int result = afWebService.setLockName(name, locktype, lockdatel, comment);
//                ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                return result;
//            }
//        }
//        return 0;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/lock/{lockname}/type/{type}/removelock", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int getRemoveLock(
//            @PathVariable("username") String username,
//            @PathVariable("lockname") String name,
//            @PathVariable("type") String type
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                int locktype = Integer.parseInt(type);
//                int result = afWebService.removeNameLock(name, locktype);
//                ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                return result;
//            }
//        }
//        return 0;
//    }
//
//    @RequestMapping(value = "/cust/{username}/sys/cust/{customername}/status/{status}/substatus/{substatus}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int updateCustomer(
//            @PathVariable("username") String username,
//            @PathVariable("customername") String customername,
//            @PathVariable("status") String status,
//            @PathVariable("substatus") String substatus
//    ) {
//
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (customername == null) {
//            return 0;
//        }
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                int result = afWebService.updateCustStatusSubStatus(customername, status, substatus);
//                ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                return result;
//            }
//        }
//        return 0;
//    }
//
////        arrayString.add("/cust/{username}/sys/custchangeapi?email={email}"); 
//    @RequestMapping(value = "/cust/{username}/sys/custchangeapi", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int ChAPICustomer(
//            @PathVariable("username") String username,
//            @RequestParam(value = "email", required = true) String emailSt
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//
//        if (emailSt == null) {
//            return 0;
//        }
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                int result = afWebService.changeAPICustomer(emailSt);
//                ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                return result;
//            }
//        }
//        return 0;
//    }
//
////        arrayString.add("/cust/{username}/sys/custchangefund?email={email}");  
//    @RequestMapping(value = "/cust/{username}/sys/custchangefund", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int ChFundCustomer(
//            @PathVariable("username") String username,
//            @RequestParam(value = "email", required = true) String emailSt
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//
//        if (emailSt == null) {
//            return 0;
//        }
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                int result = afWebService.changeFundCustomer(emailSt);
//                ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                return result;
//            }
//        }
//        return 0;
//    }
//
////        arrayString.add("/cust/{username}/sys/cust/{customername}/removecustomer");    
//    @RequestMapping(value = "/cust/{username}/sys/cust/{customername}/removecustomer", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int removeCustomer(
//            @PathVariable("username") String username,
//            @PathVariable("customername") String customername
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//
//        if (customername == null) {
//            return 0;
//        }
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                int result = afWebService.removeCustomer(customername);
//                ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                return result;
//            }
//        }
//        return 0;
//    }
//
//    //"/cust/{username}/uisys/{custid}/cust/{customername}/update?status=&payment=&balance=&reason="
//    @RequestMapping(value = "/cust/{username}/uisys/{custid}/cust/{customername}/update", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    int updateCustAllStatus(
//            @PathVariable("username") String username,
//            @PathVariable("customername") String customername,
//            @PathVariable("custid") String custidSt,
//            @RequestParam(value = "status", required = false) String statusSt,
//            @RequestParam(value = "payment", required = false) String paymentSt,
//            @RequestParam(value = "balance", required = false) String balanceSt,
//            @RequestParam(value = "reason", required = false) String reasonSt
//    ) {
//        ServiceAFweb.getServerObj().setCntControRequest(ServiceAFweb.getServerObj().getCntControRequest() + 1);
//        if (customername == null) {
//            return 0;
//        }
//        CustomerObj cust = afWebService.getCustomerPassword(username, null);
//        if (cust != null) {
//            if (cust.getType() == CustomerObj.INT_ADMIN_USER) {
//                if (custidSt.equals(cust.getId() + "")) {
//                    //updating the real customer in custSt not the addmin user
//                    int result = afWebService.updateAddCustStatusPaymentBalance(customername, statusSt, paymentSt, balanceSt, reasonSt);
//                    ServiceAFweb.getServerObj().setCntControlResp(ServiceAFweb.getServerObj().getCntControlResp() + 1);
//                    return result;
//                }
//            }
//        }
//        return 0;
//    }
//
//    /////////////////////////////////////////////////////////////////////////    
//    @RequestMapping(value = "/timer")
//    public ModelAndView timerPage() {
//        ModelAndView model = new ModelAndView("helloWorld");
//
//        model.addObject("message", AFwebService.getServerObj().getServerName() + " " + AFwebService.getServerObj().getVerString() + "</br>"
//                + AFwebService.getServerObj().getLastServUpdateESTdate() + "</br>"
//                + AFwebService.getServerObj().getTimerMsg() + "</br>" + AFwebService.getServerObj().getTimerThreadMsg());
//        return model;
//    }
//
//    @RequestMapping(value = "/timerthread", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public @ResponseBody
//    WebStatus timerThread() {
//
//        WebStatus msg = new WebStatus();
//        msg.setResult(true);
//        msg.setResultID(ConstantKey.ENABLE);
//
//        //process timer handler
//        int timerCnt = afWebService.timerThread();
//
//        msg.setResponse("timerCnt " + timerCnt);
//
//        return msg;
//    }

}
