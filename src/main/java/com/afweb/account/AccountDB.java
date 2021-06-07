/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.account;

import com.afweb.model.ConstantKey;
import com.afweb.model.account.*;

import com.afweb.service.ServiceAFweb;
import com.afweb.service.ServiceRemoteDB;
import com.afweb.util.CKey;
import com.afweb.util.TimeConvertion;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author eddy
 */
public class AccountDB {

    protected static Logger logger = Logger.getLogger("AccountDB");

    private static JdbcTemplate jdbcTemplate;
    private static DataSource dataSource;
    private ServiceRemoteDB remoteDB = new ServiceRemoteDB();

    /**
     * @return the dataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * @return the jdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * @param jdbcTemplate the jdbcTemplate to set
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList getExpiredCustomerList(int length) {
        Calendar dateNow = TimeConvertion.getCurrentCalendar();
        long dateNowLong = dateNow.getTimeInMillis();
        long cust2DayAgo = TimeConvertion.addDays(dateNowLong, -2); // 2 day ago and no update
        String sql = "select * from customer where updatedatel < " + cust2DayAgo;
        sql += " and type=" + CustomerObj.INT_CLIENT_BASIC_USER;
        return getCustomerListSQL(sql, length);
    }

    public String getAllCustomerDBSQL(String sql) {
        try {
            ArrayList<CustomerObj> entries = getCustomerListSQL(sql, 0);
            String nameST = new ObjectMapper().writeValueAsString(entries);
            return nameST;
        } catch (JsonProcessingException ex) {
        }
        return null;
    }

    public ArrayList<String> getCustomerNList(int length) {
        String sql = "select username as username from customer order by id asc";
        sql = ServiceAFweb.getSQLLengh(sql, length);
        return getAllUserNameSQL(sql);

    }

    public ArrayList<CustomerObj> getCustomerByCustId(int id) {
        String sql = "select * from customer where id='" + id + "'";
        return this.getCustomerListSQL(sql, 0);
    }

    public ArrayList<CustomerObj> getCustomerNameList(String name) {
        String sql = "select * from customer where username='" + name + "'";
        return this.getCustomerListSQL(sql, 0);
    }

    //SELECT * FROM sampledb.customer where portfolio like '%fund4%';
    public ArrayList<CustomerObj> getCustomerFundPortfolio(String fundname, int length) {
        String sql = "select * from customer customer where portfolio like '%" + fundname + "%'";
        return this.getCustomerListSQL(sql, length);
    }

    public ArrayList<CustomerObj> getCustomerList(int length) {
        String sql = "select * from customer";
        return this.getCustomerListSQL(sql, length);
    }

    public static boolean checkCallRemoteSQL_Mysql() {
        boolean ret = false;
        if (CKey.SQL_DATABASE == CKey.REMOTE_PHP_MYSQL) {
            ret = true;
        }
        return ret;
    }

    private ArrayList<CustomerObj> getCustomerListSQL(String sql, int length) {

        try {
            sql = ServiceAFweb.getSQLLengh(sql, length);

            if (checkCallRemoteSQL_Mysql() == true) {
                ArrayList custList = remoteDB.getCustomerListSqlRemoteDB_RemoteMysql(sql);
                return custList;
            }

            if (CKey.SQL_DATABASE == CKey.REMOTE_MS_SQL) {
                ArrayList custList = remoteDB.getCustomerListSqlRemoteDB_RemoteMysql(sql);
                return custList;
            }

            List<CustomerObj> entries = new ArrayList<>();
            entries.clear();
            entries = this.jdbcTemplate.query(sql, new RowMapper() {
                public CustomerObj mapRow(ResultSet rs, int rowNum) throws SQLException {
                    CustomerObj customer = new CustomerObj();
                    customer.setId(rs.getInt("id"));
                    customer.setUsername(rs.getString("username"));
                    customer.setPassword(rs.getString("password"));
                    customer.setType(rs.getInt("type"));
                    customer.setStatus(rs.getInt("status"));
                    customer.setSubstatus(rs.getInt("substatus"));
                    customer.setStartdate(new java.sql.Date(rs.getDate("startdate").getTime()));
                    customer.setFirstname(rs.getString("firstname"));
                    customer.setLastname(rs.getString("lastname"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPayment(rs.getFloat("payment"));
                    customer.setBalance(rs.getFloat("balance"));
                    customer.setPortfolio(rs.getString("portfolio"));
                    customer.setData(rs.getString("data"));
                    customer.setUpdatedatel(rs.getLong("updatedatel"));
                    //entrydatedisplay not reliable. should use entrydatel
                    customer.setUpdatedatedisplay(new java.sql.Date(customer.getUpdatedatel()));

                    String tzid = "America/New_York"; //EDT
                    TimeZone tz = TimeZone.getTimeZone(tzid);
                    Date d = new Date(customer.getUpdatedatel());
                    DateFormat format = new SimpleDateFormat("M/dd/yyyy hh:mm a z");
                    format.setTimeZone(tz);
                    String ESTdate = format.format(d);
                    customer.setUpdateDateD(ESTdate);

                    return customer;
                }
            });

            return (ArrayList) entries;
        } catch (Exception e) {
            logger.info("> getCustomerList exception " + e.getMessage());
        }
        return null;
    }

    public ArrayList<CustomerObj> getCustomerByType(int type) {
        try {
            String sql = "select * from customer where type =" + type;

            ArrayList<CustomerObj> entries = getCustomerListSQL(sql, 1);
            if (entries == null) {
                return null;
            }
            return entries;
        } catch (Exception e) {
            logger.info("> getCustomerByCustID exception " + type + " - " + e.getMessage());
        }
        return null;
    }

    public CustomerObj getCustomerByCustID(int custId) {
        CustomerObj customer = null;
        try {
            String sql = "select * from customer where id =" + custId;

            ArrayList<CustomerObj> entries = getCustomerListSQL(sql, 1);
            if (entries == null) {
                return null;
            }
            if (entries.size() != 0) {
                customer = entries.get(0);
                return customer;
            }
        } catch (Exception e) {
            logger.info("> getCustomerByCustID exception " + custId + " - " + e.getMessage());
        }
        return null;
    }

    public CustomerObj getCustomerByAccount(AccountObj accountObj) {
        CustomerObj customer = null;
        try {
            String sql = "select * from customer where id = " + accountObj.getCustomerid();

            ArrayList<CustomerObj> entries = getCustomerListSQL(sql, 1);
            if (entries == null) {
                return null;
            }
            if (entries.size() != 0) {
                customer = entries.get(0);
                return customer;
            }
        } catch (Exception e) {
            logger.info("> getCustomer exception " + accountObj.getAccountname() + " - " + e.getMessage());
        }
        return null;
    }

    public CustomerObj getCustomer(String UserName, String Password) {
        CustomerObj customer = null;
        try {
            String sql = "select * from customer where username = '" + UserName + "'";

            if (Password != null) {
                sql += " and password = '" + Password + "'";
            }

            ArrayList<CustomerObj> entries = getCustomerListSQL(sql, 1);
            if (entries == null) {
                return null;
            }
            if (entries.size() != 0) {
                customer = entries.get(0);
                return customer;
            }
        } catch (Exception e) {
            logger.info("> getCustomer exception " + UserName + " - " + e.getMessage());
        }
        return null;
    }

    public static String SQLUupdateCustAllStatus(CustomerObj cust) {
        String sqlCMD = "update customer set status=" + cust.getStatus() + ",substatus=" + cust.getSubstatus()
                + ",payment=" + cust.getPayment() + ",balance=" + cust.getBalance()
                + " where id=" + cust.getId();
        return sqlCMD;
    }

    public int updateCustAllStatus(CustomerObj custObj) {
        String sqlCMD = SQLUupdateCustAllStatus(custObj);
        try {
            processUpdateDB(sqlCMD);
            return 1;
        } catch (Exception ex) {
            logger.info("> updateCustAllStatus exception " + ex.getMessage());
        }
        return 0;
    }

    public int updateCustStatusSubStatus(CustomerObj custObj, int status, int subStatus) {

        if (custObj == null) {
            return 0;
        }
        try {
            String sqlCMD = "update customer set status=" + status + ", substatus=" + subStatus + " where id=" + custObj.getId();
            processUpdateDB(sqlCMD);
            return 1;
        } catch (Exception e) {
            logger.info("> updateCustStatus exception " + e.getMessage());
        }
        return 0;
    }

    public int updateCustomerUpdateDate(CustomerObj custObj) {

        if (custObj == null) {
            return 0;
        }
        if (custObj.getStatus() != ConstantKey.OPEN) {
            return 0;
        }
        if (custObj.getUsername().equals(CKey.ADMIN_USERNAME)) {
            return 0;
        }
        try {
            String sqlCMD = "update customer set updatedatedisplay='" + new java.sql.Date(custObj.getUpdatedatel()) + "', updatedatel=" + custObj.getUpdatedatel() + " where id=" + custObj.getId();
            processUpdateDB(sqlCMD);
            return 1;
        } catch (Exception e) {
            logger.info("> updateCustomerUpdateDate exception " + e.getMessage());
        }
        return 0;
    }

    public static String insertCustomer(CustomerObj newC) {

        String firstname = newC.getFirstname();
        if (firstname == null) {
            firstname = "";
        } else if (firstname.equals(ConstantKey.nullSt)) {
            firstname = "";
        }
        String lastname = newC.getLastname();
        if (lastname == null) {
            lastname = "";
        } else if (lastname.equals(ConstantKey.nullSt)) {
            lastname = "";
        }
        String email = newC.getEmail();
        if (email == null) {
            email = "";
        } else if (email.equals(ConstantKey.nullSt)) {
            email = "";
        }
        newC.setUpdatedatedisplay(new java.sql.Date(newC.getUpdatedatel()));
        String sqlCMD
                = "insert into customer(username, password, type, status, substatus, startdate, firstname, lastname,"
                + " email, payment, balance, portfolio, data, updatedatedisplay, updatedatel, id) values "
                + "('" + newC.getUsername() + "','" + newC.getPassword() + "'," + newC.getType()
                + "," + newC.getStatus() + "," + newC.getSubstatus() + ",'" + newC.getStartdate() + "'"
                + ",'" + firstname + "','" + lastname + "'"
                + ",'" + email + "'," + newC.getPayment() + "," + newC.getBalance() + ",'" + newC.getPortfolio() + "','" + newC.getData() + "'"
                + ",'" + newC.getUpdatedatedisplay() + "'," + newC.getUpdatedatel() + "," + newC.getId() + ")";
        return sqlCMD;
    }

    public int addCustomer(CustomerObj newC) {
        try {

            Calendar dateNow = TimeConvertion.getCurrentCalendar();
            long dateNowLong = dateNow.getTimeInMillis();
            String userN = newC.getUsername();
            userN = userN.toUpperCase();
            CustomerObj customer = getCustomer(userN, null);

            if (customer != null) {
                int status = customer.getStatus();
                // just for testing
//                status = ConstantKey.INITIAL;
                // just for testing
                if (status != ConstantKey.OPEN) {
                    customer.setStatus(ConstantKey.OPEN);
                    customer.setSubstatus(newC.getSubstatus());
                    customer.setPayment(newC.getPayment());
                    customer.setBalance(newC.getBalance());
                    this.updateCustAllStatus(customer);

                }

                newC.setUpdatedatedisplay(new java.sql.Date(dateNowLong));
                newC.setUpdatedatel(dateNowLong);

                updateCustomerUpdateDate(customer);
                return ConstantKey.EXISTED;
            }

            String sqlCMD
                    = "insert into customer(username, password, type, status, substatus, startdate, firstname, lastname,"
                    + " email, payment, balance, portfolio, data, updatedatedisplay, updatedatel) values "
                    + "('" + newC.getUsername() + "','" + newC.getPassword() + "'," + newC.getType()
                    + "," + ConstantKey.OPEN + "," + newC.getSubstatus() + ",'" + new java.sql.Date(dateNowLong) + "'"
                    + ",'" + newC.getFirstname() + "','" + newC.getLastname() + "'"
                    + ",'" + newC.getEmail() + "'," + newC.getPayment() + "," + newC.getBalance() + ",'" + newC.getPortfolio() + "','" + newC.getData() + "'"
                    + ",'" + new java.sql.Date(dateNowLong) + "'," + dateNowLong + ")";

            processUpdateDB(sqlCMD);
            return ConstantKey.NEW;
        } catch (Exception e) {
            logger.info("> addCustomer exception " + e.getMessage());

        }
        return 0;
    }

    public int updateCustomer(CustomerObj newC) {
        try {

            String userN = newC.getUsername();
            userN = userN.toUpperCase();
            CustomerObj cust = getCustomer(userN, null);

            if (cust != null) {
                int status = cust.getStatus();
                if (status != ConstantKey.OPEN) {
                    return 0;
                }

                String sqlCMD = "update customer set password='" + newC.getPassword() + "'"
                        + ",email='" + newC.getEmail() + "'"
                        + ",firstname='" + newC.getFirstname() + "',lastname='" + newC.getLastname() + "'"
                        + " where id=" + cust.getId();

                processUpdateDB(sqlCMD);
                return 1;
            }

        } catch (Exception e) {
            logger.info("> updateCustomer exception " + e.getMessage());

        }
        return 0;
    }

/////////////////////////////////////////////////////// 
    public ArrayList getAccounBestFundList(int length) {
        String sql = "select * from account where type=" + AccountObj.INT_MUTUAL_FUND_ACCOUNT;
        sql += " order by servicefee desc";
        sql = ServiceAFweb.getSQLLengh(sql, length);
        return getAccountBySQL(sql);
    }

    public ArrayList<AccountObj> getAccountByAccountID(int accID) {
        String sql = "select * from account where id=" + accID;
        sql += " order by type desc";
        return getAccountBySQL(sql);
    }

    public ArrayList<AccountObj> getAccountByCustomerID(int customerID) {
        String sql = "select * from account where customerid=" + customerID;
        sql += " order by type desc";
        return getAccountBySQL(sql);
    }

    public ArrayList getAccountByAccountName(int customerID, String accountName) {
        String sql = "select * from account where customerid=" + customerID;
        sql += " and accountname = '" + accountName + "'";
        sql += " order by type desc";
        return getAccountBySQL(sql);
    }

    public String getAllAccountDBSQL(String sql) {
        try {
            ArrayList<AccountObj> entries = getAccountBySQL(sql);
            String nameST = new ObjectMapper().writeValueAsString(entries);
            return nameST;
        } catch (JsonProcessingException ex) {
        }
        return null;
    }

    public ArrayList getUserNamebyAccountID(int accountID) {
        String sql = "select username as username from account inner join customer on account.customerid = customer.id"
                + " where account.id =" + accountID;
        return getAllUserNameSQL(sql);
    }

    public ArrayList getAllOpenAccountID() {
        String sql = "select id as id from account where status=" + ConstantKey.OPEN + " order by updatedatel asc";
        return getAllIdSQL(sql);
    }

    public ArrayList getAccountObjByAccountID(int accountID) {
        String sql = "select * from account where id=" + accountID;
        sql += " order by type desc";
        return getAccountBySQL(sql);
    }

    private ArrayList getAccountBySQL(String sql) {
        try {
            if (checkCallRemoteSQL_Mysql() == true) {
                ArrayList accList = remoteDB.getAccountListSqlRemoteDB_RemoteMysql(sql);
                return accList;
            }
            if (CKey.SQL_DATABASE == CKey.REMOTE_MS_SQL) {
                ArrayList accList = remoteDB.getAccountListSqlRemoteDB_RemoteMysql(sql);
                return accList;
            }

            List<AccountObj> entries = new ArrayList<>();
            entries.clear();
            entries = this.jdbcTemplate.query(sql, new RowMapper() {
                public AccountObj mapRow(ResultSet rs, int rowNum) throws SQLException {
                    AccountObj account = new AccountObj();

                    account.setId(rs.getInt("id"));
                    account.setAccountname(rs.getString("accountname"));
                    account.setType(rs.getInt("type"));
                    account.setStatus(rs.getInt("status"));
                    account.setSubstatus(rs.getInt("substatus"));
                    account.setUpdatedatel(rs.getLong("updatedatel"));

                    //entrydatedisplay not reliable. should use entrydatel
                    account.setUpdatedatedisplay(new java.sql.Date(account.getUpdatedatel()));
                    account.setStartdate(new java.sql.Date(rs.getDate("startdate").getTime()));

                    account.setInvestment(rs.getFloat("investment"));
                    account.setBalance(rs.getFloat("balance"));

                    account.setServicefee(rs.getFloat("servicefee"));
                    account.setPortfolio(rs.getString("portfolio"));
                    account.setLinkaccountid(rs.getInt("linkaccountid"));
                    account.setCustomerid(rs.getInt("customerid"));

                    return account;
                }
            });

            return (ArrayList) entries;

        } catch (Exception e) {
            logger.info("> getAccountBySQL exception " + sql + " - " + e.getMessage());
        }
        return null;
    }

    public ArrayList<BillingObj> getBillingObjByName(int accountID, String name, int length) {
        String sql = "select * from billing where accountid=" + accountID + " and name='" + name + "' ";

        sql += " order by updatedatel desc";
        sql = ServiceAFweb.getSQLLengh(sql, length);
        return getBillingBySQL(sql);
    }

    public ArrayList<BillingObj> getBillingObjByAccountIDType(int accountID, int type, int length) {
        String sql = "select * from billing where accountid=" + accountID + " and type=" + type;

        sql += " order by updatedatel desc";
        sql = ServiceAFweb.getSQLLengh(sql, length);
        return getBillingBySQL(sql);
    }
// begin 2021 01 01  (updatedatel)  end 2021 12 31

    public ArrayList<BillingObj> getAccountingByNameType(String name, int type, long begin, long end, int length) {

        String sql = "select * from billing where name='" + name + "' and type=" + type;
        if (begin != 0) {
            sql += " and updatedatel>" + begin;
        }
        if (end != 0) {
            sql += " and updatedatel<" + end;
        }

        sql += " order by updatedatel desc";
        sql = ServiceAFweb.getSQLLengh(sql, length);
        return getBillingBySQL(sql);
    }
// begin 2021 01 01  (updatedatel)  end 2021 12 31

    public ArrayList<BillingObj> getAccountingByTypeTime(int type, long begin, long end, int length) {

        String sql = "select * from billing where type=" + type;
        if (begin != 0) {
            sql += " and updatedatel>" + begin;
        }
        if (end != 0) {
            sql += " and updatedatel<" + end;
        }

        sql += " order by updatedatel desc";
        sql = ServiceAFweb.getSQLLengh(sql, length);
        return getBillingBySQL(sql);
    }

    public int removeAccountingByTypeId(int type, int id) {
        try {
            String deleteSQL = "delete from billing where type=" + type + " and id=" + id;
            processExecuteDB(deleteSQL);
            return 1;
        } catch (Exception e) {
            logger.info("> removeAccountingByTypeId exception " + e.getMessage());
        }
        return 0;
    }

    ArrayList<BillingObj> getAccountingByTypeId(int type, int id) {
        String sql = "select * from billing where type=" + type + " and id=" + id;
        sql += " order by updatedatel";
        return getBillingBySQL(sql);
    }

    public ArrayList<BillingObj> getBillingObjByBillingID(int billingID) {
        String sql = "select * from billing where id=" + billingID;
        sql += " order by updatedatel";
        return getBillingBySQL(sql);
    }

    private ArrayList getBillingBySQL(String sql) {
        try {
            if (checkCallRemoteSQL_Mysql() == true) {
                ArrayList accList = remoteDB.getBillingListSqlRemoteDB_RemoteMysql(sql);
                return accList;
            }
            if (CKey.SQL_DATABASE == CKey.REMOTE_MS_SQL) {
                ArrayList accList = remoteDB.getBillingListSqlRemoteDB_RemoteMysql(sql);
                return accList;
            }

            List<BillingObj> entries = new ArrayList<>();
            entries.clear();
            entries = this.jdbcTemplate.query(sql, new RowMapper() {
                public BillingObj mapRow(ResultSet rs, int rowNum) throws SQLException {
                    BillingObj billing = new BillingObj();

                    billing.setId(rs.getInt("id"));
                    billing.setName(rs.getString("name"));
                    billing.setType(rs.getInt("type"));
                    billing.setStatus(rs.getInt("status"));
                    billing.setSubstatus(rs.getInt("substatus"));
                    billing.setUpdatedatel(rs.getLong("updatedatel"));
                    //entrydatedisplay not reliable. should use entrydatel
                    billing.setUpdatedatedisplay(new java.sql.Date(billing.getUpdatedatel()));

                    billing.setPayment(rs.getFloat("payment"));
                    billing.setBalance(rs.getFloat("balance"));

                    billing.setData(rs.getString("data"));
                    billing.setAccountid(rs.getInt("accountid"));
                    billing.setCustomerid(rs.getInt("customerid"));

                    return billing;
                }
            });

            return (ArrayList) entries;

        } catch (Exception e) {
            logger.info("> getBillingBySQL exception " + sql + " - " + e.getMessage());
        }
        return null;
    }

    public ArrayList<CommObj> getComObjByCustName(int custID, String name) {
        String sql = "select * from comm where customerid=" + custID + " and name='" + name + "' ";
        sql += " order by updatedatel";
        return getCommBySQL(sql);
    }

    public ArrayList<CommObj> getComObjByCustID(int custID) {
        String sql = "select * from comm where customerid=" + custID;
        sql += " order by updatedatel";
        return getCommBySQL(sql);
    }

    public ArrayList<CommObj> getComObjByType(int type, int length) {
        String sql = "select * from comm where type=" + type;
        sql += " order by updatedatel";
        sql = ServiceAFweb.getSQLLengh(sql, length);

        return getCommBySQL(sql);
    }

//    public ArrayList<CommObj> getEmailComObjByName(String name, int length) {
//        String sql = "select * from comm where name='" + name + "' ";
//        sql += " order by updatedatel";
//        sql = ServiceAFweb.getSQLLengh(sql, length);
//
//        return getCommBySQL(sql);
//    }
    public ArrayList<CommObj> getComObjByAccountType(int accountID, int type, int length) {
        String sql = "select * from comm where accountid=" + accountID + " and type=" + type;
        sql += " order by updatedatel";
        sql = ServiceAFweb.getSQLLengh(sql, length);
        return getCommBySQL(sql);
    }

    public ArrayList<CommObj> getComObjByName(int accountID, String name) {
        String sql = "select * from comm where accountid=" + accountID + " and name='" + name + "' ";
        sql += " order by updatedatel";
        return getCommBySQL(sql);
    }

    public ArrayList<CommObj> getComSignalSplitObjByAccountID(int accountID, int length) {
//        String sql = "select * from comm where type=" + type + " and accountid=" + acountID;
        ///////// Not sure why it cannot pass as type in remote DB. Local DB is working ????????????
        ///////// Not sure why it cannot pass as type in remote DB. Local DB is working ????????????
        String sql = "select * from comm where accountid=" + accountID
                + " and (type=" + ConstantKey.INT_TYPE_COM_SIGNAL + " or type=" + ConstantKey.INT_TYPE_COM_SPLIT + ")";

        sql += " order by updatedatel";

        sql = ServiceAFweb.getSQLLengh(sql, length);
        return getCommBySQL(sql);
    }

    public ArrayList<CommObj> getComObjByAccountID(int accountID, int length) {
        String sql = "select * from comm where accountid=" + accountID;
        sql += " order by updatedatel";

        sql = ServiceAFweb.getSQLLengh(sql, length);
        return getCommBySQL(sql);
    }

    public ArrayList<CommObj> getComObjByID(int id) {
        String sql = "select * from comm where id=" + id;
        sql += " order by updatedatel";
        return getCommBySQL(sql);
    }

    private ArrayList getCommBySQL(String sql) {
        try {
            if (checkCallRemoteSQL_Mysql() == true) {
                ArrayList accList = remoteDB.getCommListSqlRemoteDB_RemoteMysql(sql);
                return accList;
            }
            if (CKey.SQL_DATABASE == CKey.REMOTE_MS_SQL) {
                ArrayList accList = remoteDB.getCommListSqlRemoteDB_RemoteMysql(sql);
                return accList;
            }

            List<CommObj> entries = new ArrayList<>();
            entries.clear();
            entries = this.jdbcTemplate.query(sql, new RowMapper() {
                public CommObj mapRow(ResultSet rs, int rowNum) throws SQLException {
                    CommObj comm = new CommObj();

                    comm.setId(rs.getInt("id"));
                    comm.setName(rs.getString("name"));
                    comm.setType(rs.getInt("type"));
                    comm.setStatus(rs.getInt("status"));
                    comm.setSubstatus(rs.getInt("substatus"));
                    comm.setUpdatedatel(rs.getLong("updatedatel"));
                    //entrydatedisplay not reliable. should use entrydatel
                    comm.setUpdatedatedisplay(new java.sql.Date(comm.getUpdatedatel()));

                    comm.setData(rs.getString("data"));
                    comm.setAccountid(rs.getInt("accountid"));
                    comm.setCustomerid(rs.getInt("customerid"));

                    return comm;
                }
            });

            return (ArrayList) entries;

        } catch (Exception e) {
            logger.info("> getCommBySQL exception " + sql + " - " + e.getMessage());

        }
        return null;
    }

    public AccountObj getAccountByType(int customerID, int type) {
        ArrayList accountList = getAccountByCustomerID(customerID);
        if (accountList == null) {
            return null;
        }
        for (int i = 0; i < accountList.size(); i++) {
            AccountObj account = (AccountObj) accountList.get(i);
            if (account.getType() == type) {
                return account;
            }
        }
        return null;
    }

    public AccountObj getAccountByCustomerAccountID(int customerID, int accountID) {
        ArrayList accountList = getAccountByCustomerID(customerID);
        if (accountList == null) {
            return null;
        }
        for (int i = 0; i < accountList.size(); i++) {
            AccountObj account = (AccountObj) accountList.get(i);
            if (account.getId() == accountID) {
                return account;
            }
        }
        return null;
    }

    public ArrayList getAllAccountStockNameListExceptionAdmin(int AdminAccountID) {
        try {
            String sql
                    = "select distinct stock.symbol as symbol from tradingrule inner join stock on "
                    + "tradingrule.stockid = stock.id where tradingrule.status=" + ConstantKey.OPEN
                    + " and tradingrule.accountid != " + AdminAccountID + " order by stock.symbol asc";;

            ArrayList entries = getAllSymbolSQL(sql);
            return (ArrayList) entries;

        } catch (Exception e) {
            logger.info("> getAllAccountStockNameListExceptionAdmin exception " + AdminAccountID + " - " + e.getMessage());
        }
        return null;
    }

    public ArrayList getAccountStockNameList(int AccountID) {
        try {
            String sql = ""
                    + "select distinct stock.symbol as symbol from tradingrule inner join stock on "
                    + "tradingrule.stockid = stock.id where tradingrule.status=" + ConstantKey.OPEN
                    + " and tradingrule.accountid=" + AccountID + " order by stock.symbol asc";

            ArrayList entries = getAllSymbolSQL(sql);
            return (ArrayList) entries;
        } catch (Exception e) {
            logger.info("> getAccountStockNameList exception " + AccountID + " - " + e.getMessage());
        }
        return null;
    }

    public TradingRuleObj getAccountStockByTRStockID(String AccountID, String StockID, String trName) {
        ArrayList TradingRuleList = getAccountStockTRListByStockIDTRname(AccountID, StockID, trName, 1);
        if (TradingRuleList != null) {
            if (TradingRuleList.size() == 1) {
                TradingRuleObj tradingRule = (TradingRuleObj) TradingRuleList.get(0);
                return tradingRule;
            }

        }
        return null;
    }

    public static String updateSplitTransactionSQL(TransationOrderObj tr) {

        String updateTRSQL
                = "update transationorder set share=" + tr.getShare() + ", avgprice=" + tr.getAvgprice()
                + " where  id=" + tr.getId();
        return updateTRSQL;
    }

    public static String insertTransaction(TransationOrderObj tr) {

        tr.setEntrydatedisplay(new java.sql.Date(tr.getEntrydatel()));

        String sqlCMD
                = "insert into transationorder(symbol, status, type, entrydatedisplay, entrydatel,"
                + " share, avgprice, trname, trsignal, comment, accountid, stockid, tradingruleid, id) values "
                + "('" + tr.getSymbol() + "'," + tr.getStatus() + "," + tr.getType()
                + ",'" + tr.getEntrydatedisplay() + "'," + tr.getEntrydatel()
                + "," + tr.getShare() + "," + tr.getAvgprice() + ",'" + tr.getTrname() + "'," + tr.getTrsignal() + ",'" + tr.getComment() + "'"
                + "," + tr.getAccountid() + "," + tr.getStockid() + "," + tr.getTradingruleid() + "," + tr.getId() + ")";

        return sqlCMD;
    }

    public static String SQLaddAccountStockTransaction(TransationOrderObj tr) {

        String sqlCMD
                = "insert into transationorder(symbol, status, type, entrydatedisplay, entrydatel,"
                + " share, avgprice, trname, trsignal, comment, accountid, stockid, tradingruleid) values "
                + "('" + tr.getSymbol() + "'," + tr.getStatus() + "," + tr.getType()
                + ",'" + tr.getEntrydatedisplay() + "'," + tr.getEntrydatel()
                + "," + tr.getShare() + "," + tr.getAvgprice() + ",'" + tr.getTrname() + "'," + tr.getTrsignal() + ",'" + tr.getComment() + "'"
                + "," + tr.getAccountid() + "," + tr.getStockid() + "," + tr.getTradingruleid() + ")";

        long currentTime = System.currentTimeMillis();
        long date5DayBefore = TimeConvertion.addDays(currentTime, -5);

        if (tr.getEntrydatel() > date5DayBefore) {
            String tzid = "America/New_York"; //EDT
            TimeZone tz = TimeZone.getTimeZone(tzid);
            Date d = new Date(tr.getEntrydatel());
            DateFormat format = new SimpleDateFormat("M/dd/yyyy hh:mm a z");
            format.setTimeZone(tz);
            String ESTdate = format.format(d);
            String messageData = "Transaction " + ESTdate + " AccountId=" + tr.getAccountid() + " " + tr.getSymbol() + " " + tr.getTrname() + " Tsignal=" + tr.getTrsignal();
            logger.info(">>>>>> insert " + messageData);
        }
        return sqlCMD;
    }

    public static String SQLUpdateAccountStockPerformance(PerformanceObj tr) {
        String nameSt = "";
        try {
            PerformData perfData = tr.getPerformData();
            nameSt = new ObjectMapper().writeValueAsString(perfData);
            nameSt = nameSt.replaceAll("\"", "#");
        } catch (JsonProcessingException ex) {
        }
        String sqlCMD = "update performance set name='" + nameSt + "',type=" + tr.getType()
                + ", startdate='" + tr.getStartdate() + "', updatedatedisplay='" + tr.getUpdatedatedisplay() + "', updatedatel=" + tr.getUpdatedatel()
                + ", investment=" + tr.getInvestment() + ", balance=" + tr.getBalance() + ",rating=" + tr.getRating()
                + ",netprofit=" + tr.getNetprofit() + ", grossprofit=" + tr.getGrossprofit() + ", numtrade=" + tr.getNumtrade()
                + " where accountid=" + tr.getAccountid() + " and stockid=" + tr.getStockid() + " and tradingruleid=" + tr.getTradingruleid();

        return sqlCMD;
    }

    public static String SQLaddAccountStockPerformance(PerformanceObj tr) {
        String nameSt = "";
        try {
            PerformData perfData = tr.getPerformData();
            nameSt = new ObjectMapper().writeValueAsString(perfData);
            nameSt = nameSt.replaceAll("\"", "#");
        } catch (JsonProcessingException ex) {
        }
        String sqlCMD
                = "insert into performance(name, type, startdate, updatedatedisplay, updatedatel,"
                + " investment, balance, rating, netprofit, grossprofit, numtrade, accountid, stockid, tradingruleid) values "
                + "('" + nameSt + "'," + tr.getType() + ",'" + tr.getStartdate()
                + "','" + tr.getUpdatedatedisplay() + "'," + tr.getUpdatedatel()
                + "," + tr.getInvestment() + "," + tr.getBalance() + "," + tr.getRating()
                + "," + tr.getNetprofit() + "," + tr.getGrossprofit() + "," + tr.getNumtrade()
                + "," + tr.getAccountid() + "," + tr.getStockid() + "," + tr.getTradingruleid() + ")";

        return sqlCMD;
    }

    public static String insertPerformance(PerformanceObj tr) {
        String nameSt = "";
        try {
            PerformData perfData = tr.getPerformData();
            nameSt = new ObjectMapper().writeValueAsString(perfData);
            nameSt = nameSt.replaceAll("\"", "#");
        } catch (JsonProcessingException ex) {
        }
        tr.setUpdatedatedisplay(new java.sql.Date(tr.getUpdatedatel()));
        String sqlCMD
                = "insert into performance(name, type, startdate, updatedatedisplay, updatedatel,"
                + " investment, balance, rating, netprofit, grossprofit, numtrade, accountid, stockid, tradingruleid, id) values "
                + "('" + nameSt + "'," + tr.getType() + ",'" + tr.getStartdate()
                + "','" + tr.getUpdatedatedisplay() + "'," + tr.getUpdatedatel()
                + "," + tr.getInvestment() + "," + tr.getBalance() + "," + tr.getRating()
                + "," + tr.getNetprofit() + "," + tr.getGrossprofit() + "," + tr.getNumtrade()
                + "," + tr.getAccountid() + "," + tr.getStockid() + "," + tr.getTradingruleid() + "," + tr.getId() + ")";

        return sqlCMD;
    }

    // ***********Make sure all lower case in sql ***************
    public ArrayList<PerformanceObj> getAccountStockPerfList(String AccountID, String StockID, String TradingRuleID, int length) {
        String sql = ""
                + "select * from performance where accountid=" + AccountID
                + " and stockid=" + StockID + " and tradingruleid=" + TradingRuleID;

        sql += " order by updatedatel desc";

        sql = ServiceAFweb.getSQLLengh(sql, length);

        ArrayList entries = getAccountStockPerfromanceList(sql);
        return entries;
    }

    public ArrayList<PerformanceObj> getAccountStockPerfromanceList(String sql) {
        try {
            if (checkCallRemoteSQL_Mysql() == true) {
                ArrayList trList = remoteDB.getAccountStockPerfromanceListRemoteDB_RemoteMysql(sql);
                return trList;
            }
            if (CKey.SQL_DATABASE == CKey.REMOTE_MS_SQL) {
                ArrayList trList = remoteDB.getAccountStockPerfromanceListRemoteDB_RemoteMysql(sql);
                return trList;
            }

            List<PerformanceObj> entries = new ArrayList<>();
            entries.clear();
            entries = this.jdbcTemplate.query(sql, new RowMapper() {
                public PerformanceObj mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PerformanceObj perf = new PerformanceObj();
                    perf.setId(rs.getInt("id"));
                    perf.setAccountid(rs.getInt("accountid"));
                    perf.setBalance(rs.getFloat("balance"));
                    perf.setGrossprofit(rs.getFloat("grossprofit"));
                    perf.setInvestment(rs.getFloat("investment"));
                    String name = rs.getString("name");

                    try {
                        if ((name != null) && (name.length() > 0)) {
                            name = name.replaceAll("#", "\"");
                            PerformData perfData = new ObjectMapper().readValue(name, PerformData.class);
                            perf.setPerformData(perfData);
                        }
                    } catch (Exception ex) {
                        logger.info("> getAccountStockPerfromanceList exception " + ex.getMessage());
                    }

                    perf.setNetprofit(rs.getFloat("netprofit"));
                    perf.setRating(rs.getFloat("rating"));
                    perf.setStartdate(new java.sql.Date(rs.getDate("startdate").getTime()));
                    perf.setStockid(rs.getInt("stockid"));
                    perf.setTradingruleid(rs.getInt("tradingruleid"));
                    perf.setType(rs.getInt("type"));
                    perf.setNumtrade(rs.getInt("numtrade"));

                    perf.setUpdatedatel(rs.getLong("updatedatel"));
                    //entrydatedisplay not reliable. should use entrydatel
                    perf.setUpdatedatedisplay(new java.sql.Date(perf.getUpdatedatel()));

                    String tzid = "America/New_York"; //EDT
                    TimeZone tz = TimeZone.getTimeZone(tzid);
                    Date d = new Date(perf.getUpdatedatel());
                    DateFormat format = new SimpleDateFormat("M/dd/yyyy hh:mm a z");
                    format.setTimeZone(tz);
                    String ESTdate = format.format(d);
                    perf.setUpdateDateD(ESTdate);

                    return perf;
                }
            });

            return (ArrayList) entries;

        } catch (Exception e) {
            logger.info("> getAccountStockPerfromanceList " + e.getMessage());

        }
        return null;
    }

    public int getAccountStockClrTranByAccountID(String AccountID, String StockID, TradingRuleObj tr) {

        String deletePerfSQL = ""
                + "delete from performance where accountid=" + AccountID
                + " and stockid=" + StockID + " and tradingruleid=" + tr.getId();

        String deleteTranSQL = ""
                + "delete from transationorder where accountid=" + AccountID
                + " and stockid=" + StockID + " and tradingruleid=" + tr.getId();

        tr.setTrsignal(0);
        tr.setStatus(ConstantKey.OPEN);
        tr.setSubstatus(ConstantKey.INITIAL);
        tr.setBalance(0);
        tr.setInvestment(0);
        tr.setLongshare(0);
        tr.setLongamount(0);
        tr.setShortshare(0);
        tr.setShortamount(0);
        tr.setPerf(0);
        Calendar dateNowUpdate = TimeConvertion.getCurrentCalendar();
        tr.setUpdatedatedisplay(new java.sql.Date(dateNowUpdate.getTimeInMillis()));
        tr.setUpdatedatel(dateNowUpdate.getTimeInMillis());

        String updateTRSQL
                = "update tradingrule set status=" + tr.getStatus() + ", substatus=" + tr.getSubstatus() + ", trsignal=" + tr.getTrsignal()
                + ",updatedatedisplay='" + tr.getUpdatedatedisplay() + "', updatedatel=" + tr.getUpdatedatel()
                + ", investment=" + tr.getInvestment() + ", balance=" + tr.getBalance() + ",longshare=" + tr.getLongshare() + ",longamount=" + tr.getLongamount() + ", shortshare=" + tr.getShortshare()
                + ", shortamount=" + tr.getShortamount() + ", perf=" + tr.getPerf()
                + ", linktradingruleid=" + tr.getLinktradingruleid()
                + " where accountid=" + tr.getAccountid() + " and stockid=" + tr.getStockid() + " and id=" + tr.getId();

        try {
            processExecuteDB(deletePerfSQL);
            processExecuteDB(deleteTranSQL);
            processExecuteDB(updateTRSQL);
        } catch (Exception ex) {
            logger.info("> getAccountStockClrTranByAccountID exception " + ex.getMessage());
        }
        return 1;

    }

    // ***********Make sure all lower case in sql ***************
    public ArrayList<TransationOrderObj> getAccountStockTransList(String AccountID, String StockID, String trID, int length) {
        String sql = ""
                + "select * from transationorder where accountid=" + AccountID
                + " and stockid=" + StockID + " and tradingruleid=" + trID;

        sql += " order by entrydatel desc";

        sql = ServiceAFweb.getSQLLengh(sql, length);

        ArrayList entries = getAccountStockTransactionList(sql);
        return entries;
    }

    public ArrayList<TransationOrderObj> getAccountStockTransactionList(String sql) {
        try {
            if (checkCallRemoteSQL_Mysql() == true) {
                ArrayList trList = remoteDB.getAccountStockTransactionListRemoteDB_RemoteMysql(sql);
                return trList;
            }
            if (CKey.SQL_DATABASE == CKey.REMOTE_MS_SQL) {
                ArrayList trList = remoteDB.getAccountStockTransactionListRemoteDB_RemoteMysql(sql);
                return trList;
            }

            List<TransationOrderObj> entries = new ArrayList<>();
            entries.clear();
            entries = this.jdbcTemplate.query(sql, new RowMapper() {
                public TransationOrderObj mapRow(ResultSet rs, int rowNum) throws SQLException {
                    TransationOrderObj tran = new TransationOrderObj();

                    tran.setId(rs.getInt("id"));
                    tran.setAccountid(rs.getInt("accountid"));
                    tran.setAvgprice(rs.getFloat("avgprice"));
                    tran.setEntrydatel(rs.getLong("entrydatel"));

                    //entrydatedisplay not reliable. should use entrydatel
//                    tran.setEntrydatedisplay(new java.sql.Date(rs.getDate("entrydatedisplay").getTime()));                    
                    tran.setEntrydatedisplay(new java.sql.Date(tran.getEntrydatel()));

                    tran.setShare(rs.getFloat("share"));
                    tran.setStatus(rs.getInt("status"));
                    tran.setStockid(rs.getInt("stockid"));
                    tran.setSymbol(rs.getString("symbol"));
                    tran.setTradingruleid(rs.getInt("tradingruleid"));
                    tran.setTrname(rs.getString("trname"));
                    tran.setTrsignal(rs.getInt("trsignal"));
                    tran.setType(rs.getInt("type"));
                    tran.setComment(rs.getString("comment"));

                    String tzid = "America/New_York"; //EDT
                    TimeZone tz = TimeZone.getTimeZone(tzid);
                    Date d = new Date(tran.getEntrydatel());
                    DateFormat format = new SimpleDateFormat("M/dd/yyyy hh:mm a z");
                    format.setTimeZone(tz);
                    String ESTdate = format.format(d);
                    tran.setUpdateDateD(ESTdate);
                    return tran;
                }
            });

            return (ArrayList) entries;

        } catch (Exception e) {
            logger.info("> getAccountStockTransactionList " + e.getMessage());

        }
        return null;
    }

    public boolean checkTRListByStockID(String StockID) {
        String sql = ""
                + "select tradingrule.*, stock.symbol as symbol from tradingrule inner join stock on "
                + "tradingrule.stockid = stock.id where stockid=" + StockID;

        sql = ServiceAFweb.getSQLLengh(sql, 1);
        ArrayList<TradingRuleObj> entries = getAccountStockList(sql);
        if (entries != null) {
            if (entries.size() > 0) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<TradingRuleObj> getAccountStockTRListByStockIDTRname(String AccountID, String StockID, String trName, int length) {

        String sql = ""
                + "select tradingrule.*, stock.symbol as symbol from tradingrule inner join stock on "
                + "tradingrule.stockid = stock.id where tradingrule.accountid=" + AccountID;
//        String sql = ""
//                + "select tradingrule.*, stock.symbol as symbol from tradingrule inner join stock on "
//                + "tradingrule.stockid = stock.id where tradingrule.status=" + ConstantKey.OPEN + " and tradingrule.accountid=" + AccountID;

        if (StockID != null) {
            sql += " and tradingrule.stockid=" + StockID;
        }

        if (trName != null) {
            if (trName.length() > 0) {
                sql += " and tradingrule.trname='" + trName + "'";
            }
        }
        sql += " order by stock.symbol, tradingrule.type asc";

        sql = ServiceAFweb.getSQLLengh(sql, length);

        ArrayList entries = getAccountStockList(sql);
        return entries;

    }

    public String getAllSQLqueryDBSQL(String sql) {
        try {
            List retList = null;
            if (checkCallRemoteSQL_Mysql() == true) {
                String retST = remoteDB.getAllSQLqueryRemoteDB_RemoteMysql(sql);
                return retST;
            }
            if (CKey.SQL_DATABASE == CKey.REMOTE_MS_SQL) {
                String retST = remoteDB.getAllSQLqueryRemoteDB_RemoteMysql(sql);
                return retST;
            }

            retList = this.jdbcTemplate.queryForList(sql);
            String retST = new ObjectMapper().writeValueAsString(retList);
            return retST;

        } catch (Exception e) {
            logger.info("> getAccountStockPerfromanceList " + e.getMessage());

        }
        return null;
    }

    public String getAllPerformanceDBSQL(String sql) {
        try {
            ArrayList<PerformanceObj> entries = getAccountStockPerfromanceList(sql);
            String nameST = new ObjectMapper().writeValueAsString(entries);
            return nameST;
        } catch (JsonProcessingException ex) {
        }
        return null;
    }

    public String getAllTransationOrderDBSQL(String sql) {
        try {
            ArrayList<TransationOrderObj> entries = getAccountStockTransactionList(sql);
            String nameST = new ObjectMapper().writeValueAsString(entries);
            return nameST;
        } catch (JsonProcessingException ex) {
        }
        return null;
    }

    public String getAllBillingDBSQL(String sql) {
        try {
            ArrayList<BillingObj> entries = getBillingBySQL(sql);
            String nameST = new ObjectMapper().writeValueAsString(entries);
            return nameST;
        } catch (JsonProcessingException ex) {
        }
        return null;
    }

    public String getAllCommDBSQL(String sql) {
        try {
            ArrayList<CommObj> entries = getCommBySQL(sql);
            String nameST = new ObjectMapper().writeValueAsString(entries);
            return nameST;
        } catch (JsonProcessingException ex) {
        }
        return null;
    }

    public String getAllAccountStockDBSQL(String sql) {
        try {
            ArrayList<TradingRuleObj> entries = getAccountStockList(sql);
            String nameST = new ObjectMapper().writeValueAsString(entries);
            return nameST;
        } catch (JsonProcessingException ex) {
        }
        return null;
    }

    public ArrayList getAccountStockList(String sql) {
        try {
            if (checkCallRemoteSQL_Mysql() == true) {
                ArrayList trList = remoteDB.getAccountStockListSqlRemoteDB_RemoteMysql(sql);
                return trList;
            }
            if (CKey.SQL_DATABASE == CKey.REMOTE_MS_SQL) {
                ArrayList trList = remoteDB.getAccountStockListSqlRemoteDB_RemoteMysql(sql);
                return trList;
            }

            List<TradingRuleObj> entries = new ArrayList<>();
            entries.clear();
            entries = this.jdbcTemplate.query(sql, new RowMapper() {
                public TradingRuleObj mapRow(ResultSet rs, int rowNum) throws SQLException {
                    TradingRuleObj tradingRule = new TradingRuleObj();

                    tradingRule.setId(rs.getInt("id"));
                    tradingRule.setTrname(rs.getString("trname"));
                    tradingRule.setType(rs.getInt("type"));
                    tradingRule.setTrsignal(rs.getInt("trsignal"));
                    tradingRule.setUpdatedatel(rs.getLong("updatedatel"));
                    //entrydatedisplay not reliable. should use entrydatel
//                    tradingRule.setUpdatedatedisplay(new java.sql.Date(rs.getDate("updatedatedisplay").getTime()));
                    tradingRule.setUpdatedatedisplay(new java.sql.Date(tradingRule.getUpdatedatel()));

                    tradingRule.setStatus(rs.getInt("status"));
                    tradingRule.setSubstatus(rs.getInt("substatus"));
                    tradingRule.setInvestment(rs.getFloat("investment"));
                    tradingRule.setBalance(rs.getFloat("balance"));
                    tradingRule.setLongshare(rs.getFloat("longshare"));
                    tradingRule.setLongamount(rs.getFloat("longamount"));
                    tradingRule.setShortshare(rs.getFloat("shortshare"));
                    tradingRule.setShortamount(rs.getFloat("shortamount"));
                    tradingRule.setPerf(rs.getFloat("perf"));
                    tradingRule.setComment(rs.getString("comment"));
                    tradingRule.setLinktradingruleid(rs.getInt("linktradingruleid"));
                    tradingRule.setAccountid(rs.getInt("accountid"));
                    tradingRule.setStockid(rs.getInt("stockid"));

                    tradingRule.setSymbol(rs.getString("symbol"));

                    return tradingRule;
                }
            });

            return (ArrayList) entries;

        } catch (Exception e) {
            logger.info("> getAccountStockList " + e.getMessage());

        }
        return null;
    }

    public static String insertAccountStock(TradingRuleObj tr) {
        tr.setUpdatedatedisplay(new java.sql.Date(tr.getUpdatedatel()));
        String sqlCMD
                = "insert into tradingrule(trname, type, trsignal, updatedatedisplay, updatedatel,"
                + " status, substatus, investment, balance, longshare, longamount, shortshare, shortamount,perf, comment, linktradingruleid, stockid, accountid, id) values "
                + "('" + tr.getTrname() + "'," + tr.getType() + "," + tr.getTrsignal()
                + ",'" + tr.getUpdatedatedisplay() + "'," + tr.getUpdatedatel()
                + "," + tr.getStatus() + "," + tr.getSubstatus() + "," + tr.getInvestment() + "," + tr.getBalance() + "," + tr.getLongshare() + "," + tr.getLongamount()
                + "," + tr.getShortshare() + "," + tr.getShortamount() + "," + tr.getPerf() + ",'" + tr.getComment() + "'," + tr.getLinktradingruleid()
                + "," + tr.getStockid() + "," + tr.getAccountid() + "," + tr.getId() + ")";

        return sqlCMD;
    }

    public int updateAccountStockSignal(ArrayList<TradingRuleObj> TRList) {
        try {
            for (int i = 0; i < TRList.size(); i++) {
                TradingRuleObj tr = (TradingRuleObj) TRList.get(i);
                String sqlCMD = "update tradingrule set trsignal=" + tr.getTrsignal() + ", status=" + tr.getStatus() + ", substatus=" + tr.getSubstatus()
                        + ",updatedatedisplay='" + tr.getUpdatedatedisplay() + "', updatedatel=" + tr.getUpdatedatel()
                        + ", investment=" + tr.getInvestment() + ", balance=" + tr.getBalance() + ",longshare=" + tr.getLongshare() + ",longamount=" + tr.getLongamount() + ", shortshare=" + tr.getShortshare()
                        + ", shortamount=" + tr.getShortamount() + ", perf=" + tr.getPerf()
                        + ", comment='" + tr.getComment() + "', linktradingruleid=" + tr.getLinktradingruleid()
                        + " where accountid=" + tr.getAccountid() + " and stockid=" + tr.getStockid() + " and type=" + tr.getType();
                processUpdateDB(sqlCMD);
//                if (CKey.NN_DEBUG == true) {
//                    logger.info("> updateAccountStockSignal cmd " + sqlCMD);
//                }
            }
            return 1;
        } catch (Exception ex) {
            logger.info("> updateAccountStockSignal exception " + ex.getMessage());
        }
        return 0;
    }

    public static String SQLUpdateAccountStockStatus(TradingRuleObj tr) {
        String sqlCMD = "update tradingrule set status=" + tr.getStatus() + ", substatus=" + tr.getSubstatus()
                + " where accountid=" + tr.getAccountid() + " and stockid=" + tr.getStockid() + " and type=" + tr.getType();

        return sqlCMD;
    }

    public static String SQLUpdateAccountStockSignal(TradingRuleObj tr) {

        String sqlCMD = "update tradingrule set trsignal=" + tr.getTrsignal()
                + ",updatedatedisplay='" + tr.getUpdatedatedisplay() + "', updatedatel=" + tr.getUpdatedatel()
                + ", investment=" + tr.getInvestment() + ", balance=" + tr.getBalance() + ",longshare=" + tr.getLongshare() + ",longamount=" + tr.getLongamount() + ", shortshare=" + tr.getShortshare()
                + ", shortamount=" + tr.getShortamount() + ", perf=" + tr.getPerf()
                + " where accountid=" + tr.getAccountid() + " and stockid=" + tr.getStockid() + " and type=" + tr.getType();

        return sqlCMD;
    }

    public int updateAccounStockComment(TradingRuleObj tr, String refname) {
        try {
            String sqlCMD = "update tradingrule set comment='" + refname + "' "
                    + " where accountid=" + tr.getAccountid() + " and stockid=" + tr.getStockid() + " and type=" + tr.getType();
            processUpdateDB(sqlCMD);
            return 1;

        } catch (Exception e) {
            logger.info("> updateAccounStocktRef exception " + e.getMessage());
        }
        return 0;
    }

    public int updateAccounStockPref(TradingRuleObj tr, float perf) {
        try {
            String sqlCMD = "update tradingrule set perf=" + perf
                    + " where accountid=" + tr.getAccountid() + " and stockid=" + tr.getStockid() + " and type=" + tr.getType();
            processUpdateDB(sqlCMD);
            return 1;

        } catch (Exception e) {
            logger.info("> updateAccounStocktRef exception " + e.getMessage());
        }
        return 0;
    }

    public int addAccountStock(int AccountID, int StockID, TradingRuleObj tr) {

        if (tr == null) {
            return 0;
        }
        try {
            TradingRuleObj tradingRuleObj = getAccountStockByTRStockID("" + AccountID, "" + StockID, tr.getTrname());
            if (tradingRuleObj != null) {

                if (tradingRuleObj.getStatus() == ConstantKey.CLOSE) {

                    String sqlCMD = "update tradingrule set trsignal=" + ConstantKey.S_NEUTRAL + ", status=" + ConstantKey.OPEN + ", substatus=" + ConstantKey.INITIAL
                            + ", investment=0, balance=0,longshare=0,longamount=0, shortshare=0, shortamount=0, perf=0"
                            + ", comment=' ', linktradingruleid=" + tr.getLinktradingruleid()
                            + " where accountid=" + tradingRuleObj.getAccountid() + " and stockid=" + tradingRuleObj.getStockid() + " and type=" + tr.getType();
                    processUpdateDB(sqlCMD);
                    logger.info("> addAccountStock " + sqlCMD);

                    return ConstantKey.NEW;
                }
                return ConstantKey.EXISTED;
            }

            Calendar dateDefault = TimeConvertion.getDefaultCalendar();
            String sqlCMD
                    = "insert into tradingrule( trname, type, trsignal, updatedatedisplay, updatedatel,"
                    + " status, substatus, investment, balance, longshare, longamount, shortshare, shortamount, perf, comment, linktradingruleid, stockid, accountid) values "
                    + "('" + tr.getTrname() + "'," + tr.getType() + "," + ConstantKey.S_NEUTRAL
                    + ",'" + new java.sql.Date(dateDefault.getTimeInMillis()) + "'," + dateDefault.getTimeInMillis()
                    + "," + ConstantKey.OPEN + "," + ConstantKey.INITIAL + ",0,0,0,0,0,0,0,''," + tr.getLinktradingruleid() + "," + StockID + "," + AccountID + ")";

            processUpdateDB(sqlCMD);
//            logger.info("> addAccountStock " + sqlCMD);
            return ConstantKey.NEW;
        } catch (Exception e) {
            logger.info("> addAccountStock exception " + e.getMessage());
        }
        return 0;
    }

    public int DeleteCustomer(CustomerObj custObj) {

        try {
            String deleteSQL = "delete from customer where id=" + custObj.getId();
            processExecuteDB(deleteSQL);
            return 1;
        } catch (Exception e) {
            logger.info("> removeAccount exception " + e.getMessage());
        }
        return 0;
    }

    public int removeAccount(AccountObj accountObj) {

        try {
            String deleteSQL = "delete from account where id=" + accountObj.getId();
            processExecuteDB(deleteSQL);
            return 1;
        } catch (Exception e) {
            logger.info("> removeAccount exception " + e.getMessage());
        }
        return 0;
    }

    public int removeAccountStock(int AccountID, int StockID) {

        try {
            String deleteSQL = "delete from tradingrule where accountid=" + AccountID + " and stockid=" + StockID;
            processExecuteDB(deleteSQL);
            return 1;
        } catch (Exception e) {
            logger.info("> removeAccountStock exception " + e.getMessage());
        }
        return 0;
    }

    public int removeCustBilling(int custID) {
        try {
            String deleteSQL = "delete from billing where customerid=" + custID;
            processExecuteDB(deleteSQL);
            return 1;
        } catch (Exception e) {
            logger.info("> removeCustBilling exception " + e.getMessage());
        }
        return 0;
    }

    public int removeAccountBillingByType(int type) {
        try {
            String deleteSQL = "delete from billing where type=" + type;
            deleteSQL += " order by updatedatel desc";

            processExecuteDB(deleteSQL);
            return 1;
        } catch (Exception e) {
            logger.info("> removeAccountBillingByName exception " + e.getMessage());
        }
        return 0;
    }

    public int removeAccountBillingByName(String name) {
        try {
            String deleteSQL = "delete from billing where name='" + name + "'";
            deleteSQL += " order by updatedatel desc";
            processExecuteDB(deleteSQL);
            return 1;
        } catch (Exception e) {
            logger.info("> removeAccountBillingByName exception " + e.getMessage());
        }
        return 0;
    }

    public int removeAccountBillingByID(int AccountID, int BillID) {
        try {
            String deleteSQL = "delete from billing where accountid=" + AccountID + " and id=" + BillID;
            processExecuteDB(deleteSQL);
            return 1;
        } catch (Exception e) {
            logger.info("> removeAccountBilling exception " + e.getMessage());
        }
        return 0;
    }

    public int removeAccountBilling(int AccountID) {
        try {
            String deleteSQL = "delete from billing where accountid=" + AccountID;
            processExecuteDB(deleteSQL);
            return 1;
        } catch (Exception e) {
            logger.info("> removeAccountBilling exception " + e.getMessage());
        }
        return 0;
    }

    public int insertAccountBillingData(BillingObj newA) {

        String sqlCMD
                = "insert into billing( name, type, status, substatus, updatedatedisplay, updatedatel, payment, balance, data, accountid, customerid) values "
                + "('" + newA.getName() + "'," + newA.getType() + "," + newA.getStatus() + "," + newA.getSubstatus()
                + ",'" + newA.getUpdatedatedisplay() + "'," + newA.getUpdatedatel() + "," + newA.getPayment() + "," + newA.getBalance()
                + ",'" + newA.getData() + "'"
                + "," + newA.getAccountid() + "," + newA.getCustomerid() + ")";

        try {
            processExecuteDB(sqlCMD);
            return 1;
        } catch (Exception e) {
            logger.info("> insertAccountBillingData exception " + e.getMessage());
        }
        return 0;
    }

    public int updateAccountBillingStatusPaymentData(BillingObj newA) {
        String sqlCMD = "update billing set updatedatedisplay='" + new java.sql.Date(newA.getUpdatedatel()) + "', updatedatel=" + newA.getUpdatedatel()
                + ", status=" + newA.getStatus() + ", payment=" + newA.getPayment() + ", balance=" + newA.getBalance() + ",data='" + newA.getData() + "' where id=" + newA.getId();
        try {
            processExecuteDB(sqlCMD);
            return 1;
        } catch (Exception e) {
            logger.info("> updateAccountBillingStatusPaymentData exception " + e.getMessage());
        }
        return 0;
    }

    public int updateAccountBillingStatus(BillingObj newA) {
        String sqlCMD = "update billing set updatedatedisplay='" + new java.sql.Date(newA.getUpdatedatel()) + "', updatedatel=" + newA.getUpdatedatel()
                + ", status=" + newA.getStatus() + ", substatus=" + newA.getSubstatus() + " where id=" + newA.getId();
        try {
            processExecuteDB(sqlCMD);
            return 1;
        } catch (Exception e) {
            logger.info("> updateAccountBillingStatus exception " + e.getMessage());
        }
        return 0;
    }

    public static String insertBillingObj(BillingObj newA) {
        newA.setUpdatedatedisplay(new java.sql.Date(newA.getUpdatedatel()));

        String sqlCMD
                = "insert into billing( name, type, status, substatus, updatedatedisplay, updatedatel, payment, balance, data, accountid, customerid, id) values "
                + "('" + newA.getName() + "'," + newA.getType() + "," + newA.getStatus() + "," + newA.getSubstatus()
                + ",'" + newA.getUpdatedatedisplay() + "'," + newA.getUpdatedatel()
                + "," + newA.getPayment() + "," + newA.getBalance() + ",'" + newA.getData() + "'"
                + "," + newA.getAccountid() + "," + newA.getCustomerid() + "," + newA.getId() + ")";

        return sqlCMD;
    }

    public int removeCommByCustID(int custId) {
        try {
            String deleteSQL = "delete from comm where customerid=" + custId;
            processExecuteDB(deleteSQL);
            return 1;
        } catch (Exception e) {
            logger.info("> removeCommByCustID exception " + e.getMessage());
        }
        return 0;
    }

    public int removeCommByTimebefore(long timeBefore, int type) {
        try {
            String deleteSQL = "delete from comm where type=" + type + " and updatedatel<" + timeBefore;
            processExecuteDB(deleteSQL);
            return 1;
        } catch (Exception e) {
            logger.info("> removeCommByTimebefore exception " + e.getMessage());
        }
        return 0;
    }

    public int removeAllCommByType(int type) {
        try {
            String deleteSQL = "delete from comm where type=" + type;
            processExecuteDB(deleteSQL);
            return 1;
        } catch (Exception e) {
            logger.info("> removeAccountCommByID exception " + e.getMessage());
        }
        return 0;
    }

    public int removeCommByCommID(int id) {
        try {
            String deleteSQL = "delete from comm where id=" + id;
            processExecuteDB(deleteSQL);
            return 1;
        } catch (Exception e) {
            logger.info("> removeAccountCommByID exception " + e.getMessage());
        }
        return 0;
    }

    public int removeCommBuCustType(int custID, int type) {
        try {
            String deleteSQL = "delete from comm where customerid=" + custID + " and type=" + type;
            if (type == -1) {
                deleteSQL = "delete from comm where customerid=" + custID;
            }
//            String deleteSQL = "delete from comm where customerid=" + custID + " and type=" + ConstantKey.INT_TYPE_COM_SIGNAL;
            processExecuteDB(deleteSQL);
            return 1;
        } catch (Exception e) {
            logger.info("> removeAccountComm exception " + e.getMessage());
        }
        return 0;
    }

    public int removeCommSignalByAccountType(int AccountID, int type) {
        try {
            String deleteSQL = "delete from comm where accountid=" + AccountID + " and type=" + type;
            if (type == -1) {
                deleteSQL = "delete from comm where accountid=" + AccountID;
            }
//            String deleteSQL = "delete from comm where accountid=" + AccountID + " and type=" + ConstantKey.INT_TYPE_COM_SIGNAL;
            processExecuteDB(deleteSQL);
            return 1;
        } catch (Exception e) {
            logger.info("> removeAccountComm exception " + e.getMessage());
        }
        return 0;
    }

    public int removeCommByType(int type) {
        try {
            String deleteSQL = "delete from comm where type=" + type;
            processExecuteDB(deleteSQL);
            return 1;
        } catch (Exception e) {
            logger.info("> removeCommByType exception " + e.getMessage());
        }
        return 0;
    }

    public int removeCommByName(String name) {
        try {
            String deleteSQL = "delete from comm where name='" + name + "' ";
            processExecuteDB(deleteSQL);
            return 1;
        } catch (Exception e) {
            logger.info("> removeCommByName exception " + e.getMessage());
        }
        return 0;
    }

    public int insertAccountCommData(CommObj newA) {

        String sqlCMD
                = "insert into comm( name, type, status, substatus, updatedatedisplay, updatedatel, data, accountid, customerid) values "
                + "('" + newA.getName() + "'," + newA.getType() + "," + newA.getStatus() + "," + newA.getSubstatus()
                + ",'" + newA.getUpdatedatedisplay() + "'," + newA.getUpdatedatel()
                + ",'" + newA.getData() + "'"
                + "," + newA.getAccountid() + "," + newA.getCustomerid() + ")";

        try {
            processExecuteDB(sqlCMD);
            return 1;
        } catch (Exception e) {
            logger.info("> updateAccountCommData exception " + e.getMessage());
        }
        return 0;
    }

    public int updateAccountCommSubStatusById(CommObj newA) {
        String sqlCMD = "update comm set updatedatedisplay='" + new java.sql.Date(newA.getUpdatedatel())
                + "', updatedatel=" + newA.getUpdatedatel() + ",substatus=" + newA.getSubstatus() + " where id=" + newA.getId();
        try {
            processExecuteDB(sqlCMD);
            return 1;
        } catch (Exception e) {
            logger.info("> updateAccountCommSubStatusById exception " + e.getMessage());
        }
        return 0;
    }

    public int updateAccountCommDataById(CommObj newA) {
        String sqlCMD = "update comm set updatedatedisplay='" + new java.sql.Date(newA.getUpdatedatel())
                + "', updatedatel=" + newA.getUpdatedatel() + ",data='" + newA.getData() + "' where id=" + newA.getId();
        try {
            processExecuteDB(sqlCMD);
            return 1;
        } catch (Exception e) {
            logger.info("> updateAccountCommData exception " + e.getMessage());
        }
        return 0;
    }

    public static String insertCommObj(CommObj newA) {
        newA.setUpdatedatedisplay(new java.sql.Date(newA.getUpdatedatel()));

        String sqlCMD
                = "insert into comm( name, type, status, substatus, updatedatedisplay, updatedatel, data, accountid, customerid, id) values "
                + "('" + newA.getName() + "'," + newA.getType() + "," + newA.getStatus() + "," + newA.getSubstatus()
                + ",'" + newA.getUpdatedatedisplay() + "'," + newA.getUpdatedatel()
                + ",'" + newA.getData() + "'"
                + "," + newA.getAccountid() + "," + newA.getCustomerid() + "," + newA.getId() + ")";

        return sqlCMD;
    }

    public static String insertAccountObj(AccountObj newA) {
        newA.setUpdatedatedisplay(new java.sql.Date(newA.getUpdatedatel()));

        String sqlCMD
                = "insert into account( accountname, type, status, substatus, updatedatedisplay, updatedatel, startdate, investment, balance, servicefee, portfolio, linkaccountid, customerid, id) values "
                + "('" + newA.getAccountname() + "'," + newA.getType() + "," + newA.getStatus() + "," + newA.getSubstatus()
                + ",'" + newA.getUpdatedatedisplay() + "'," + newA.getUpdatedatel()
                + ",'" + newA.getStartdate() + "'," + newA.getInvestment() + "," + newA.getBalance() + "," + newA.getServicefee() + ",'" + newA.getPortfolio() + "'"
                + "," + newA.getLinkaccountid() + "," + newA.getCustomerid() + "," + newA.getId() + ")";

        return sqlCMD;
    }

    public int updateCustomerPortfolio(String username, String portfolio) {
        portfolio = portfolio.replaceAll("\"", "#");
        String sqlCMD = "update customer set portfolio='" + portfolio + "'"
                + " where username='" + username + "'";
        try {
            processUpdateDB(sqlCMD);
            return 1;
        } catch (Exception ex) {
            logger.info("> updateCustomerPortfolio exception " + ex.getMessage());
        }
        return 0;
    }

    public int updateAccountPortfolio(String accountName, String portfolio) {
        portfolio = portfolio.replaceAll("\"", "#");

        String sqlCMD = "update account set portfolio='" + portfolio + "'"
                + " where accountname='" + accountName + "'";
        try {
            processUpdateDB(sqlCMD);
            return 1;
        } catch (Exception ex) {
            logger.info("> updateAccountPortfoli exception " + ex.getMessage());
        }
        return 0;
    }

    public static String SQLUpupdateAccountAllStatus(AccountObj acc) {
        String sqlCMD = "update account set status=" + acc.getStatus() + ",substatus=" + acc.getSubstatus()
                + ",investment=" + acc.getInvestment() + ",balance=" + acc.getBalance() + ",servicefee=" + acc.getServicefee()
                + " where id=" + acc.getId();
        return sqlCMD;
    }

    public int updateAccountAllStatus(AccountObj account) {
        String sqlCMD = SQLUpupdateAccountAllStatus(account);
        try {
            processUpdateDB(sqlCMD);
            return 1;
        } catch (Exception ex) {
            logger.info("> updateAccountPortfoli exception " + ex.getMessage());
        }
        return 0;
    }

    public int addAccountTypeSubStatus(CustomerObj customer, String accountName, int accType, int accSubStatus) {

        if (customer == null) {
            return 0;
        }
        try {
            Calendar dateNow = TimeConvertion.getCurrentCalendar();
            long dateNowLong = dateNow.getTimeInMillis();

            AccountObj account = null;
            ArrayList accountList = this.getAccountByAccountName(customer.getId(), accountName);

            if (accountList != null) {
                if (accountList.size() > 0) {
                    account = (AccountObj) accountList.get(0);
                }
            }
            if (account != null) {
                return ConstantKey.EXISTED;
            }
            Calendar dateDefault = TimeConvertion.getDefaultCalendar();

            //substatus = price plan
            String sqlCMD
                    = "insert into account( accountname, type, status, substatus, updatedatedisplay, updatedatel, startdate, investment, balance, servicefee, portfolio, linkaccountid, customerid) values "
                    + "('" + accountName + "'," + accType + "," + ConstantKey.OPEN + "," + accSubStatus
                    + ",'" + new java.sql.Date(dateDefault.getTimeInMillis()) + "'," + dateDefault.getTimeInMillis()
                    + ",'" + new java.sql.Date(dateNowLong) + "',0,0,0,'',0," + customer.getId() + ")";

            processUpdateDB(sqlCMD);

            return ConstantKey.NEW;
        } catch (Exception e) {
            logger.info("> addAccount exception " + e.getMessage());
        }
        return 0;
    }

    // result 1 = success, 2 = existed,  0 = fail
    public int addCustomerAccount(CustomerObj newCustomer, int plan) {
//        logger.info("> addCustomer " + newCustomer.getUsername());

        try {
            if (plan == -1) {
                switch (newCustomer.getType()) {
                    case CustomerObj.INT_ADMIN_USER:
                        newCustomer.setSubstatus(ConstantKey.INT_PP_PEMIUM);
                        newCustomer.setPayment(ConstantKey.INT_PP_PEMIUM_PRICE);
                        newCustomer.setBalance(0);
                        break;
                    case CustomerObj.INT_FUND_USER:
                        newCustomer.setSubstatus(ConstantKey.INT_PP_PEMIUM);
                        newCustomer.setPayment(ConstantKey.INT_PP_PEMIUM_PRICE);
                        newCustomer.setBalance(0);
                        break;
                    case CustomerObj.INT_GUEST_USER:
                        newCustomer.setSubstatus(ConstantKey.INT_PP_PEMIUM);
                        newCustomer.setPayment(ConstantKey.INT_PP_PEMIUM_PRICE);
                        newCustomer.setBalance(0);
                        break;
                    case CustomerObj.INT_API_USER:
                        newCustomer.setSubstatus(ConstantKey.INT_PP_API);
                        newCustomer.setPayment(ConstantKey.INT_PP_API_PRICE);
                        newCustomer.setBalance(0);
                        break;

                    default:
                        newCustomer.setSubstatus(ConstantKey.INT_PP_BASIC);
                        newCustomer.setPayment(ConstantKey.INT_PP_BASIC_PRICE);
                        newCustomer.setBalance(0);
                        break;
                }
            } else {
                newCustomer.setSubstatus(ConstantKey.INT_PP_BASIC);
                newCustomer.setPayment(ConstantKey.INT_PP_BASIC_PRICE);
                newCustomer.setBalance(0);
                if (plan == ConstantKey.INT_PP_STANDARD) {
                    newCustomer.setSubstatus(ConstantKey.INT_PP_STANDARD);
                    newCustomer.setPayment(ConstantKey.INT_PP_STANDARD_PRICE);
                    newCustomer.setBalance(0);
                } else if (plan == ConstantKey.INT_PP_PEMIUM) {
                    newCustomer.setSubstatus(ConstantKey.INT_PP_PEMIUM);
                    newCustomer.setPayment(ConstantKey.INT_PP_PEMIUM_PRICE);
                    newCustomer.setBalance(0);
                }
            }
            int result = addCustomer(newCustomer);
            if (result != 0) {
                if (result == ConstantKey.EXISTED) {
                    return result;
                }
                CustomerObj customer = getCustomer(newCustomer.getUsername(), null);
                if (customer.getType() == CustomerObj.INT_ADMIN_USER) {

                    String accountName = "acc-" + customer.getId() + "-" + AccountObj.ADMIN_ACCOUNT;
                    result = addAccountTypeSubStatus(customer, accountName, AccountObj.INT_ADMIN_ACCOUNT, ConstantKey.OPEN);

                } else if (customer.getType() == CustomerObj.INT_FUND_USER) {

                    String accountName = "acc-" + customer.getId() + "-" + AccountObj.MUTUAL_FUND_ACCOUNT;
                    result = addAccountTypeSubStatus(customer, accountName, AccountObj.INT_MUTUAL_FUND_ACCOUNT, ConstantKey.OPEN);
                    accountName = "acc-" + customer.getId() + "-" + AccountObj.TRADING_ACCOUNT;
                    result = addAccountTypeSubStatus(customer, accountName, AccountObj.INT_TRADING_ACCOUNT, ConstantKey.OPEN);
                    return 1;
                } else if (customer.getType() == CustomerObj.INT_GUEST_USER) {

                    String accountName = "acc-" + customer.getId() + "-" + AccountObj.TRADING_ACCOUNT;
                    result = addAccountTypeSubStatus(customer, accountName, AccountObj.INT_TRADING_ACCOUNT, ConstantKey.OPEN);

                }
                String accountName = "acc-" + customer.getId() + "-" + AccountObj.TRADING_ACCOUNT;
                result = addAccountTypeSubStatus(customer, accountName, AccountObj.INT_TRADING_ACCOUNT, ConstantKey.OPEN);
                return 1;

            }
        } catch (Exception e) {
            logger.info("> addCustomerTransactoin exception " + e.getMessage());
        }

        return 0;
    }

    private ArrayList getAllSymbolSQL(String sql) {
        if (checkCallRemoteSQL_Mysql() == true) {
            ArrayList nnList;
            try {
                nnList = remoteDB.getAllSymbolSqlRemoteDB_RemoteMysql(sql);
                return nnList;
            } catch (Exception ex) {
            }
            return null;
        }
        if (CKey.SQL_DATABASE == CKey.REMOTE_MS_SQL) {
            ArrayList nnList;
            try {
                nnList = remoteDB.getAllSymbolSqlRemoteDB_RemoteMysql(sql);
                return nnList;
            } catch (Exception ex) {

            }
            return null;
        }

        try {
            List<String> entries = new ArrayList<>();
            entries.clear();
            entries = this.jdbcTemplate.query(sql, new RowMapper() {
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    String name = rs.getString("symbol");
                    return name;
                }
            });
            return (ArrayList) entries;
        } catch (Exception e) {
            logger.info("> getAllSymbolSQL exception " + e.getMessage());
        }
        return null;
    }

    public ArrayList getAllIdSQL(String sql) {
        if (checkCallRemoteSQL_Mysql() == true) {
            ArrayList nnList;
            try {
                nnList = remoteDB.getAllIdSqlRemoteDB_RemoteMysql(sql);
                return nnList;
            } catch (Exception ex) {
            }
            return null;
        }
        if (CKey.SQL_DATABASE == CKey.REMOTE_MS_SQL) {
            ArrayList nnList;
            try {
                nnList = remoteDB.getAllIdSqlRemoteDB_RemoteMysql(sql);
                return nnList;
            } catch (Exception ex) {

            }
            return null;
        }

        try {
            List<String> entries = new ArrayList<>();
            entries.clear();
            entries = this.jdbcTemplate.query(sql, new RowMapper() {
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    String name = rs.getString("id");
                    return name;
                }
            });
            return (ArrayList) entries;
        } catch (Exception e) {
            logger.info("> getAllIdSQL exception " + e.getMessage());
        }
        return null;
    }

    public ArrayList getAllUserNameSQL(String sql) {
        if (checkCallRemoteSQL_Mysql() == true) {
            ArrayList nnList;
            try {
                nnList = remoteDB.getAllUserNameSqlRemoteDB_RemoteMysql(sql);
                return nnList;
            } catch (Exception ex) {
            }
            return null;
        }
        if (CKey.SQL_DATABASE == CKey.REMOTE_MS_SQL) {
            ArrayList nnList;
            try {
                nnList = remoteDB.getAllUserNameSqlRemoteDB_RemoteMysql(sql);
                return nnList;
            } catch (Exception ex) {

            }
            return null;
        }

        try {
            List<String> entries = new ArrayList<>();
            entries.clear();
            entries = this.jdbcTemplate.query(sql, new RowMapper() {
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    String name = rs.getString("username");
                    return name;
                }
            });
            return (ArrayList) entries;
        } catch (Exception e) {
            logger.info("> getAllUserNameSQL exception " + e.getMessage());
        }
        return null;
    }

    public int updateTransactionOrder(ArrayList transSQL) throws SQLException {
        if ((transSQL == null) || (transSQL.size() == 0)) {
            return 0;
        }
        if (true) {
            int ret = 1;
            for (int i = 0; i < transSQL.size(); i++) {
                String sql = (String) transSQL.get(i);
                try {
//                    logger.info("> updateTransactionOrder " + sql);

                    processUpdateDB(sql);
                } catch (Exception ex) {
                    ret = 0;
                    break;
                }
            }
            return ret;
        }

        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        try {
            dbConnection = this.dataSource.getConnection();
            dbConnection.setAutoCommit(false);

            for (int i = 0; i < transSQL.size(); i++) {
                String sql = (String) transSQL.get(i);
                preparedStatement = dbConnection.prepareStatement(sql);
                preparedStatement.executeUpdate();
            }
            dbConnection.commit();
            return 1;
        } catch (SQLException e) {
            logger.info(e.getMessage());
            dbConnection.rollback();
            return 0;
        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public void processUpdateDB(String sqlCMD) throws Exception {
        if (checkCallRemoteSQL_Mysql() == true) {
            int count = remoteDB.postExecuteRemoteDB_RemoteMysql(sqlCMD);
            return;
        }
        if (CKey.SQL_DATABASE == CKey.REMOTE_MS_SQL) {
            int count = remoteDB.postExecuteRemoteDB_RemoteMysql(sqlCMD);
            return;
        }
//        logger.info("> processUpdateDB " + sqlCMD);
        getJdbcTemplate().update(sqlCMD);
    }

    public void processExecuteDB(String sqlCMD) throws Exception {
//       logger.info("> processExecuteDB " + sqlCMD);
        if (checkCallRemoteSQL_Mysql() == true) {
            int count = remoteDB.postExecuteRemoteDB_RemoteMysql(sqlCMD);
            return;
        }
        if (CKey.SQL_DATABASE == CKey.REMOTE_MS_SQL) {
            int count = remoteDB.postExecuteRemoteDB_RemoteMysql(sqlCMD);
            return;
        }

        getJdbcTemplate().execute(sqlCMD);
    }

}
