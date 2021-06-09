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
    public static String A_CASH = "cash";
    public static String A_ACC_RECEIVABLE = "acc_receivable";
    public static String A_EQUIPMENT = "equipment";
    public static String L_ACC_PAYABLE = "acc_payable";
    public static String E_RET_EARNING = "retained_earnings";
    public static String B_BUSINESS = "profit_loss_acc";

    public static String Asset_accounts[] = {A_CASH, A_ACC_RECEIVABLE, A_EQUIPMENT};
    public static String Liability_accounts[] = {L_ACC_PAYABLE};
    public static String Equity_accounts[] = {E_RET_EARNING};

    public static String Business_accounts[] = {B_BUSINESS};

    //
    //Revenue accounts (Examples: Service Revenues, Investment Revenues)
    //Expense accounts (Examples: Wages Expense, Rent Expense, Depreciation Expense)
    public static String R_REVENUE = "revenues";
    public static String R_F_INCOME = "finance_income";
    public static String R_SRV_REVENUE = "service_Revenues";
    public static String EX_EXPENSE = "expense";
    public static String EX_DEPRECIATION = "depreciation";
    public static String EX_WAGES = "Wages_expense";

    public static String Revenue_accounts[] = {R_REVENUE, R_F_INCOME, R_SRV_REVENUE};
    public static String Expense_accounts[] = {EX_EXPENSE, EX_DEPRECIATION, EX_WAGES};

//////////////////////////////////////////////////
//https://www.double-entry-bookkeeping.com/retained-earnings/retained-earnings-statement/   
//http://www.accounting-basics-for-students.com/-recording-retained-earnings-in-the-journal-.html    
//If you made a profit for the year, the profit and loss account would have a credit balance   
    public int addRetainEarningProfit(String ref, String type, double amount, String comment) {
        try {
            Ledger ledgerTr = accAPI.getLedger();
            if (ledgerTr == null) {
                return 0;
            }
            TransferRequest transferRequest1 = ledgerTr.createTransferRequest()
                    .reference(ref)
                    .type(type)
                    .account(B_BUSINESS).debit("" + amount, "CAD")
                    .account(E_RET_EARNING).credit("" + amount, "CAD")
                    .build();

            transferRequest1.setComment(comment);
            ledgerTr.commit(transferRequest1);
        } catch (Exception ex) {
            logger.info("> addTransferRevenue exception " + ex);
        }
        return 0;
    }

//If, however, the business made a loss for the year, the profit and loss account would have a debit balance.    
    public int addRetainEarningLoss(String ref, String type, double amount, String comment) {
        try {
            Ledger ledgerTr = accAPI.getLedger();
            if (ledgerTr == null) {
                return 0;
            }
            TransferRequest transferRequest1 = ledgerTr.createTransferRequest()
                    .reference(ref)
                    .type(type)
                    .account(E_RET_EARNING).debit("" + amount, "CAD")
                    .account(B_BUSINESS).credit("" + amount, "CAD")
                    .build();

            transferRequest1.setComment(comment);
            ledgerTr.commit(transferRequest1);
        } catch (Exception ex) {
            logger.info("> addTransferRevenue exception " + ex);
        }
        return 0;
    }

//https://accounting-simplified.com/financial/double-entry-accounting/    
//////////////////////////////////////////////////////////////////
    public int addTransferRevenue(String ref, String type, double amount, String comment) {
        try {
            Ledger ledgerTr = accAPI.getLedger();
            if (ledgerTr == null) {
                return 0;
            }
            TransferRequest transferRequest1 = ledgerTr.createTransferRequest()
                    .reference(ref)
                    .type(type)
                    .account(A_CASH).debit("" + amount, "CAD")
                    .account(R_REVENUE).credit("" + amount, "CAD")
                    .build();

            transferRequest1.setComment(comment);
            ledgerTr.commit(transferRequest1);
        } catch (Exception ex) {
            logger.info("> addTransferRevenue exception " + ex);
        }
        return 0;
    }

    //Interest received on bank deposit account
    public int addTransferIncome(String ref, String type, double amount, String comment) {
        try {
            Ledger ledgerTr = accAPI.getLedger();
            if (ledgerTr == null) {
                return 0;
            }
            TransferRequest transferRequest1 = ledgerTr.createTransferRequest()
                    .reference(ref)
                    .type(type)
                    .account(A_CASH).debit("" + amount, "CAD")
                    .account(R_F_INCOME).credit("" + amount, "CAD")
                    .build();

            transferRequest1.setComment(comment);
            ledgerTr.commit(transferRequest1);
        } catch (Exception ex) {
            logger.info("> addTransferIncome exception " + ex);
        }
        return 0;
    }

    //
    ////////////////////////////////////////////////
    //https://www.double-entry-bookkeeping.com/accounts-receivable/accounts-receivable-journal-entries/
    //https://www.double-entry-bookkeeping.com/accounts-payable/accounts-payable-journal-entries/
    public int addAccReceivableRecordSale(String ref, String type, double amount, String comment) {
        try {
            Ledger ledgerTr = accAPI.getLedger();
            if (ledgerTr == null) {
                return 0;
            }
            TransferRequest transferRequest1 = ledgerTr.createTransferRequest()
                    .reference(ref)
                    .type(type)
                    .account(A_ACC_RECEIVABLE).debit("" + amount, "CAD")
                    .account(R_REVENUE).credit("" + amount, "CAD")
                    .build();

            transferRequest1.setComment(comment);
            ledgerTr.commit(transferRequest1);
        } catch (Exception ex) {
            logger.info("> addAccReceivableSale exception " + ex);
        }
        return 0;
    }

    public int addAccReceivableCashPayment(String ref, String type, double amount, String comment) {
        try {
            Ledger ledgerTr = accAPI.getLedger();
            if (ledgerTr == null) {
                return 0;
            }
            TransferRequest transferRequest1 = ledgerTr.createTransferRequest()
                    .reference(ref)
                    .type(type)
                    .account(A_CASH).debit("" + amount, "CAD")
                    .account(A_ACC_RECEIVABLE).credit("" + amount, "CAD")
                    .build();

            transferRequest1.setComment(comment);
            ledgerTr.commit(transferRequest1);
        } catch (Exception ex) {
            logger.info("> addAccReceivableSale exception " + ex);
        }
        return 0;
    }

    ////////////////////////////////////////////////
    // Examples of Payroll Journal Entries For Wages
    public int addTransferPayroll(String ref, String type, double amount, String comment) {
        try {
            Ledger ledgerTr = accAPI.getLedger();
            if (ledgerTr == null) {
                return 0;
            }
            // Debit Utility Expense

            TransferRequest transferRequest1 = ledgerTr.createTransferRequest()
                    .reference(ref)
                    .type(type)
                    .account(EX_WAGES).debit("" + amount, "CAD")
                    .account(A_CASH).credit("" + amount, "CAD")
                    .build();

            transferRequest1.setComment(comment);
            ledgerTr.commit(transferRequest1);
        } catch (Exception ex) {
            logger.info("> addTransferPayroll exception " + ex);
        }
        return 0;
    }

    // Payment of utility bills
    public int addTransferExpense(String ref, String type, double amount, String comment) {
        try {
            Ledger ledgerTr = accAPI.getLedger();
            if (ledgerTr == null) {
                return 0;
            }
            // Debit Utility Expense

            TransferRequest transferRequest1 = ledgerTr.createTransferRequest()
                    .reference(ref)
                    .type(type)
                    .account(EX_EXPENSE).debit("" + amount, "CAD")
                    .account(A_CASH).credit("" + amount, "CAD")
                    .build();

            transferRequest1.setComment(comment);
            ledgerTr.commit(transferRequest1);
        } catch (Exception ex) {
            logger.info("> addTransferExpense exception " + ex);
        }
        return 0;
    }

    public static String TYPE_EQUIPMENT_10 = "equipment10";
    public static String TYPE_EQUIPMENT_20 = "equipment20";
    public static String TYPE_EQUIPMENT_30 = "equipment30";
    public static String TYPE_EQUIPMENT_50 = "equipment50";
    public static String TYPE_EQUIPMENT_100 = "equipment100";

    // Purchase of Equipment by cash
    public int addTransferEquipment(String ref, String type, double amount, String comment) {
        try {
            Ledger ledgerTr = accAPI.getLedger();
            if (ledgerTr == null) {
                return 0;
            }
            // Debit Utility Expense

            TransferRequest transferRequest1 = ledgerTr.createTransferRequest()
                    .reference(ref)
                    .type(type)
                    .account(A_EQUIPMENT).debit("" + amount, "CAD")
                    .account(A_CASH).credit("" + amount, "CAD")
                    .build();

            transferRequest1.setComment(comment);
            ledgerTr.commit(transferRequest1);
        } catch (Exception ex) {
            logger.info("> addTransferEquipment exception " + ex);
        }
        return 0;
    }

    // search equipment to find if it still has value for Depreciation 
    public int addTransferDepreciation(String ref, String type, double amount, String comment) {
        try {
            Ledger ledgerTr = accAPI.getLedger();
            if (ledgerTr == null) {
                return 0;
            }
            // Debit Utility Expense

            TransferRequest transferRequest1 = ledgerTr.createTransferRequest()
                    .reference(ref)
                    .type(type)
                    .account(EX_DEPRECIATION).debit("" + amount, "CAD")
                    .account(A_EQUIPMENT).credit("" + amount, "CAD")
                    .build();

            transferRequest1.setComment(comment);
            ledgerTr.commit(transferRequest1);
        } catch (Exception ex) {
            logger.info("> addTransferDepreciation exception " + ex);
        }
        return 0;
    }

//////////////////////////////////////////////////////////////////
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
        stringList = new ArrayList(Arrays.asList(Business_accounts));
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
            stringList = new ArrayList(Arrays.asList(Business_accounts));
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
