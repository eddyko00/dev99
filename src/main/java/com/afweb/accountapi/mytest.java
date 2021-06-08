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

import com.yanimetaxas.bookkeeping.ChartOfAccounts;
import com.yanimetaxas.bookkeeping.ChartOfAccounts.ChartOfAccountsBuilder;
import com.yanimetaxas.bookkeeping.ConnectionOptions;
import com.yanimetaxas.bookkeeping.DataSourceDriver;
import com.yanimetaxas.bookkeeping.Ledger;
import com.yanimetaxas.bookkeeping.Ledger.LedgerBuilder;
import com.yanimetaxas.bookkeeping.model.Transaction;
import com.yanimetaxas.bookkeeping.model.TransferRequest;
import java.util.List;

/**
 *
 * @author eddy
 */
public class mytest {

    public static accAPI accounting = new accAPI();


    public static void main(String[] args) {
        ConnectionOptions options = new ConnectionOptions(DataSourceDriver.JDBC_MYSQL)
                .url("jdbc:mysql://localhost:3306/sampledb?useSSL=false")
                .username("sa")
                .password("admin");
        ChartOfAccounts chartOfAccounts = null;
        int initRet = accounting.initAccAPI_DB();
        if (initRet == -1) {
            return;
        }
        boolean initFlag = true;
        if (initFlag == false) {

            chartOfAccounts = new ChartOfAccountsBuilder()
                    .create("CASH_ACCOUNT_1", "1000.00", "EUR")
                    .create("REVENUE_ACCOUNT_1", "0.00", "EUR")
                    //                    .includeExisted("ACCOUNT_1")
                    //                    .includeExisted("ACCOUNT_2")
                    .build();
        } else {
            chartOfAccounts = new ChartOfAccountsBuilder()
                    .includeExisted("CASH_ACCOUNT_1")
                    .includeExisted("REVENUE_ACCOUNT_1")
                    .build();
        }
        Ledger ledger = new LedgerBuilder(chartOfAccounts)
                .name("Ledger with both new and already created accounts")
                .options(options)
                .build()
                .init();
////////////////////////////////////////
        if (initFlag == false) {
            TransferRequest transferRequest1 = ledger.createTransferRequest()
                    .reference("T1")
                    .type("testing1")
                    .account("CASH_ACCOUNT_1").debit("5.00", "EUR")
                    .account("REVENUE_ACCOUNT_1").credit("5.00", "EUR")
                    .build();

            ledger.commit(transferRequest1);

            TransferRequest transferRequest2 = ledger.createTransferRequest()
                    .reference("T2")
                    .type("testing2")
                    .account("CASH_ACCOUNT_1").debit("10.50", "EUR")
                    .account("REVENUE_ACCOUNT_1").credit("10.50", "EUR")
                    .build();

            ledger.commit(transferRequest2);
        }

        List<Transaction> cashAccountTransactionList = ledger.findTransactions("CASH_ACCOUNT_1");
        List<Transaction> revenueAccountTransactionList = ledger.findTransactions("REVENUE_ACCOUNT_1");

        Transaction transaction1 = ledger.getTransactionByRef("T1");
        Transaction transaction2 = ledger.getTransactionByRef("T2");
        ledger.printHistoryLog();
    }
}

//
//CREATE TABLE accountdb.client (
//   ref VARCHAR(100) NOT NULL,
//   creation_date DATE NOT NULL,
//
//   PRIMARY KEY(ref)
//);
//
//CREATE TABLE accountdb.account_ac (
//  id INTEGER AUTO_INCREMENT,
//  client_ref VARCHAR(100) NOT NULL,
//  account_ref VARCHAR(20) NOT NULL,
//  amount DECIMAL(20,2) NOT NULL,
//  currency VARCHAR(3) NOT NULL,
//  PRIMARY KEY (id)
//) ENGINE=InnoDB DEFAULT CHARSET=utf8;
//
//CREATE TABLE accountdb.transaction_history (
//  client_ref VARCHAR(100) NOT NULL,
//  transaction_ref VARCHAR(20) NOT NULL,
//  transaction_type VARCHAR(20) NOT NULL,
//  transaction_date DATE NOT NULL,
//
//  PRIMARY KEY(client_ref, transaction_ref)
//);
//
//CREATE TABLE accountdb.transaction_leg (
//	client_ref VARCHAR(100) NOT NULL,
//	transaction_ref VARCHAR(20) NOT NULL,
//	account_ref VARCHAR(20) NOT NULL,
//	amount DECIMAL(20,2) NOT NULL,
//	currency VARCHAR(3) NOT NULL
//);
