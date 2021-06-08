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

import java.util.ArrayList;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author eddy
 */
public class accAPI {

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
    }
    //////////////////////
    public static ConnectionOptions options = null;
    public static ChartOfAccounts chartOfAccounts = null;

    public int initAccountEntry(ServiceAFweb serviceAFWeb) {
        try {
            DriverManagerDataSource dataSource = (DriverManagerDataSource) accAPI.getDataSource();

            options = new ConnectionOptions(DataSourceDriver.JDBC_MYSQL)
                    .url(dataSource.getUrl())
                    .username(dataSource.getUsername())
                    .password(dataSource.getPassword());

            chartOfAccounts = new ChartOfAccounts.ChartOfAccountsBuilder()
                    .create("CASH_ACCOUNT_1", "0.00", "CAD")
                    .create("REVENUE_ACCOUNT_1", "0.00", "CAD")
                    .build();

            return 1;
        } catch (Exception ex) {
            logger.info("> initAccountEntry exception " + ex);
        }
        return 0;
    }

    public int initLedgerEntry(ServiceAFweb serviceAFWeb) {
        try {
            if (options == null) {
                return 0;
            }
            if (chartOfAccounts == null) {
                chartOfAccounts = new ChartOfAccounts.ChartOfAccountsBuilder()
                        .includeExisted("CASH_ACCOUNT_1")
                        .includeExisted("REVENUE_ACCOUNT_1")
                        .build();
            }
            Ledger ledger = new Ledger.LedgerBuilder(chartOfAccounts)
                    .name("Ledger accounts")
                    .options(options)
                    .build()
                    .init();

            return 1;
        } catch (Exception ex) {
            logger.info("> initAccountEntry exception " + ex);
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
                        + " CREATE TABLE transaction_history (client_ref VARCHAR(100) NOT NULL,transaction_ref VARCHAR(20) NOT NULL,transaction_type VARCHAR(20) NOT NULL,transaction_date DATE NOT NULL,PRIMARY KEY(client_ref, transaction_ref))");
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
