/*
 * Copyright 2021 Yani (Ioannis) Metaxas.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.afweb.accountapi;

import com.afweb.service.*;
import com.afweb.util.*;
import com.yanimetaxas.bookkeeping.*;
import com.yanimetaxas.bookkeeping.ChartOfAccounts.ChartOfAccountsBuilder;
import com.yanimetaxas.bookkeeping.model.TransferRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author eddy
 */
public class accAPI {

    /**
     * @return the ledger
     */
    public static Ledger getLedger() {
        return ledger;
    }

    /**
     * @param aLedger the ledger to set
     */
    public static void setLedger(Ledger aLedger) {
        ledger = aLedger;
    }

    protected static Logger logger = Logger.getLogger("accAPI");
    private static JdbcTemplate jdbcTemplate;
    private static DataSource dataSource;
    private ServiceRemoteDB remoteDB = new ServiceRemoteDB();

    /**
     * @return the jdbcTemplate
     */
    public static JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * @param aJdbcTemplate the jdbcTemplate to set
     */
    public static void setJdbcTemplate(JdbcTemplate aJdbcTemplate) {
        jdbcTemplate = aJdbcTemplate;
    }

    /**
     * @return the dataSource
     */
    public static DataSource getDataSource() {
        return dataSource;
    }

    /**
     * @param aDataSource the dataSource to set
     */
    public static void setDataSource(DataSource aDataSource) {
        dataSource = aDataSource;
    }

    public void setDataSource(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        setJdbcTemplate(jdbcTemplate);
        setDataSource(dataSource);

        DriverManagerDataSource dataS = (DriverManagerDataSource) dataSource;
        options = new ConnectionOptions(DataSourceDriver.JDBC_MYSQL)
                .url(dataS.getUrl())
                .username(dataS.getUsername())
                .password(dataS.getPassword());
    }
    //////////////////////

    public static ConnectionOptions options = null;
    public static ChartOfAccounts chartOfAccounts = null;
    private static Ledger ledger = null;

    //https://www.accountingcoach.com/accounting-basics/explanation/5
//Balance Sheet accounts:
//
//Asset accounts (Examples: Cash, Accounts Receivable, Supplies, Equipment)
//Liability accounts (Examples: Notes Payable, Accounts Payable, Wages Payable)
//Stockholders' Equity accounts (Examples: Common Stock, Retained Earnings)
    public static String Asset_accounts[] = {"cash", "acc_receivable", "equipment"};
    public static String Liability_accounts[] = {"notes_payable", "acc_payable", "wages_payable"};
    public static String Equity_accounts[] = {"common_stock", "retained_earnings"};

//Income Statement accounts:
//
//Revenue accounts (Examples: Service Revenues, Investment Revenues)
//Expense accounts (Examples: Wages Expense, Rent Expense, Depreciation Expense)
    public static String Revenue_accounts[] = {"revenues", "service_Revenues"};
    public static String Expense_accounts[] = {"expense", "depreciation", "Wages_expense"};

//https://accounting-simplified.com/financial/double-entry-accounting/    
    public int createAccountEntry(ServiceAFweb serviceAFWeb) {

        if (options == null) {
            return 0;
        }
        List<String> itemList = new ArrayList<String>();
        List<String> stringList = new ArrayList(Arrays.asList(Asset_accounts));
        itemList.addAll(stringList);
        stringList = new ArrayList(Arrays.asList(Liability_accounts));
        itemList.addAll(stringList);
        stringList = new ArrayList(Arrays.asList(Equity_accounts));
        itemList.addAll(stringList);
        stringList = new ArrayList(Arrays.asList(Revenue_accounts));
        itemList.addAll(stringList);
        stringList = new ArrayList(Arrays.asList(Expense_accounts));
        itemList.addAll(stringList);

        String[] itemsArray = new String[itemList.size()];
        itemsArray = itemList.toArray(itemsArray);

        for (int i = 0; i < itemsArray.length; i++) {
            String accN = itemsArray[i];
            try {
                ChartOfAccountsBuilder accBuilder = new ChartOfAccounts.ChartOfAccountsBuilder();
                accBuilder.create(accN, "0.00", "CAD");
                ChartOfAccounts chartOfAccountsCreate = accBuilder.build();
                Ledger ledgerCreate = new Ledger.LedgerBuilder(chartOfAccountsCreate)
                        .name("Ledger accounts")
                        .options(options)
                        .build()
                        .init();
            } catch (Exception ex) {
//                logger.info("> initAccountEntry exception " + ex);
            }
        }

//            chartOfAccounts = new ChartOfAccounts.ChartOfAccountsBuilder()
//                    .create("CASH_ACCOUNT_1", "0.00", "CAD")
//                    .create("REVENUE_ACCOUNT_1", "0.00", "CAD")
//                    .build();
//            Ledger ledger = new Ledger.LedgerBuilder(chartOfAccounts)
//                    .name("Ledger accounts")
//                    .options(options)
//                    .build()
//                    .init();
        return 1;

    }

    public int initLedgerEntry(ServiceAFweb serviceAFWeb) {
        try {
            if (options == null) {
                return 0;
            }
            List<String> itemList = new ArrayList<String>();
            List<String> stringList = new ArrayList(Arrays.asList(Asset_accounts));
            itemList.addAll(stringList);
            stringList = new ArrayList(Arrays.asList(Liability_accounts));
            itemList.addAll(stringList);
            stringList = new ArrayList(Arrays.asList(Equity_accounts));
            itemList.addAll(stringList);
            stringList = new ArrayList(Arrays.asList(Revenue_accounts));
            itemList.addAll(stringList);
            stringList = new ArrayList(Arrays.asList(Expense_accounts));
            itemList.addAll(stringList);

            String[] itemsArray = new String[itemList.size()];
            itemsArray = itemList.toArray(itemsArray);

            ChartOfAccountsBuilder accBuilder = new ChartOfAccounts.ChartOfAccountsBuilder();
            for (int i = 0; i < itemsArray.length; i++) {
                String accN = itemsArray[i];
                accBuilder.includeExisted(accN);
            }
            chartOfAccounts = accBuilder.build();

//                    chartOfAccounts = new ChartOfAccounts.ChartOfAccountsBuilder()
//                            .includeExisted("CASH_ACCOUNT_1")
//                            .includeExisted("REVENUE_ACCOUNT_1")
//                            .build();
            setLedger(new Ledger.LedgerBuilder(chartOfAccounts)
                    .name("Ledger accounts")
                    .options(options)
                    .build()
                    .init());
            return 1;
        } catch (Exception ex) {
            logger.info("> initLedgerEntry exception " + ex);
        }
        return 0;
    }

    public int addTransfer(String type, double amount) {
        try {
            Ledger ledgerTr = accAPI.getLedger();
            if (ledgerTr == null) {
                return 0;
            }
            TransferRequest transferRequest1 = ledgerTr.createTransferRequest()
                    .reference("T1")
                    .type(type)
                    .account("cash").debit("" + amount, "CAD")
                    .account("revenues").credit("" + amount, "CAD")
                    .build();

            ledgerTr.commit(transferRequest1);
        } catch (Exception ex) {
            logger.info("> addTransfer exception " + ex);
        }
        return 0;
    }

    //////////////////////
    public int initAccAPI_DB() {

        int total = 0;

        try {

            boolean initDBflag = false;
            if (initDBflag == true) {
                processExecuteDB("drop table if exists account_ac");
            }
            total = getCountRowsInTable(getJdbcTemplate(), "account_ac");
        } catch (Exception e) {
            logger.info("> initAccAPI_DB Table exception ");
            total = -1;
        }
        if (total >= 0) {
            return 1;  // already exist
        }

        try {

            ArrayList dropTableList = new ArrayList();
            dropTableList.add("drop table if exists client");
            dropTableList.add("drop table if exists account_ac");
            dropTableList.add("drop table if exists transaction_history");
            dropTableList.add("drop table if exists transaction_leg");

            boolean resultDropList = ExecuteSQLArrayList(dropTableList);

            ArrayList createTableList = new ArrayList();

            if ((CKey.SQL_DATABASE == CKey.DIRECT__MYSQL) || (CKey.SQL_DATABASE == CKey.REMOTE_PHP_MYSQL) || (CKey.SQL_DATABASE == CKey.LOCAL_MYSQL)) {
                createTableList.add(""
                        + " CREATE TABLE client (ref VARCHAR(100) NOT NULL, creation_date DATE NOT NULL,PRIMARY KEY(ref))");
                createTableList.add(""
                        + " CREATE TABLE account_ac (id INTEGER AUTO_INCREMENT,client_ref VARCHAR(100) NOT NULL,account_ref VARCHAR(20) NOT NULL, amount DECIMAL(20,2) NOT NULL, currency VARCHAR(3) NOT NULL,PRIMARY KEY (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8");
                createTableList.add(""
                        + " CREATE TABLE transaction_history (client_ref VARCHAR(100) NOT NULL,transaction_ref VARCHAR(20) NOT NULL,transaction_type VARCHAR(20) NOT NULL,transaction_date DATE NOT NULL, updatedatel bigint(20) not null, comment VARCHAR(255) NOT NULL,PRIMARY KEY(client_ref, transaction_ref))");
                createTableList.add(""
                        + " CREATE TABLE transaction_leg (client_ref VARCHAR(100) NOT NULL,transaction_ref VARCHAR(20) NOT NULL,account_ref VARCHAR(20) NOT NULL,amount DECIMAL(20,2) NOT NULL,currency VARCHAR(3) NOT NULL)");

            }

            boolean resultCreate = ExecuteSQLArrayList(createTableList);

            logger.info("> initAccAPI_DB Done - result " + resultCreate);
            total = getCountRowsInTable(getJdbcTemplate(), "account_ac");
            return 0;  // new database

        } catch (Exception e) {
            logger.info("> initAccAPI_DB Table exception " + e.getMessage());
        }
        return -1;
    }

    public int getCountRowsInTable(JdbcTemplate jdbcTemplate, String tableName) throws Exception {

        if (CKey.SQL_DATABASE == CKey.REMOTE_MS_SQL) {
            int count = remoteDB.getCountRowsRemoteDB_RemoteMysql(tableName);
            return count;
        }

        Integer result = jdbcTemplate.queryForObject("select count(0) from " + tableName, Integer.class);
        return (result != null ? result : 0);
    }

    public void processExecuteDB(String sqlCMD) throws Exception {
//        logger.info("> processExecuteDB " + sqlCMD);

        if (CKey.SQL_DATABASE == CKey.REMOTE_MS_SQL) {
//            logger.info("> processExecuteDB " + sqlCMD);
            int count = remoteDB.postExecuteRemoteDB_RemoteMysql(sqlCMD);
            return;
        }
        getJdbcTemplate().execute(sqlCMD);
    }

    private boolean ExecuteSQLArrayList(ArrayList SQLTran) {
        String SQL = "";
        try {
            for (int i = 0; i < SQLTran.size(); i++) {
                SQL = (String) SQLTran.get(i);
                logger.info("> ExecuteSQLArrayList " + SQL);
                processExecuteDB(SQL);
            }
            return true;
        } catch (Exception e) {
            logger.info("> ExecuteSQLArrayList exception " + e.getMessage());
        }
        return false;

    }

}
