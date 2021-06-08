package com.yanimetaxas.bookkeeping.service;

import com.afweb.util.TimeConvertion;
import com.google.common.collect.Lists;
import com.yanimetaxas.bookkeeping.dao.AccountDao;
import com.yanimetaxas.bookkeeping.dao.TransactionDao;
import com.yanimetaxas.bookkeeping.exception.AccountNotFoundException;
import com.yanimetaxas.bookkeeping.exception.InsufficientFundsException;
import com.yanimetaxas.bookkeeping.model.Transaction;
import com.yanimetaxas.bookkeeping.model.TransactionLeg;
import com.yanimetaxas.bookkeeping.model.TransferRequest;
import com.yanimetaxas.bookkeeping.validation.TransferValidator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implements the methods of the transfer service.
 *
 * @author yanimetaxas
 * @since 14-Nov-14
 */
public class TransferServiceImpl implements TransferService {

    private TransferValidator validator;
    private AccountDao accountDao;
    private TransactionDao transactionDao;

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void transferFunds(TransferRequest transferRequest)
            throws InsufficientFundsException, AccountNotFoundException {

        validateRequest(transferRequest);
        for (TransactionLeg leg : transferRequest.getLegs()) {
            accountDao.updateBalance(leg);
        }
        validator.validBalance(transferRequest.getLegs());
        storeTransaction(transferRequest);
    }

    private void validateRequest(TransferRequest request) {
        validator.validateTransferRequest(request);
        validator.isTransactionBalanced(request.getLegs());
        validator.transferRequestExists(request.getTransactionRef());
        validator.currenciesMatch(request.getLegs());
    }

    private void storeTransaction(TransferRequest request) {
        Date dd = new Date(TimeConvertion.currentTimeMillis());
        Transaction transaction = new Transaction(
                request.getTransactionRef(),
                request.getTransactionType(),
                dd, //new Date(),
                dd.getTime(),
                request.getComment(),
                request.getLegs()
        );
        transactionDao.storeTransaction(transaction);
    }

    @Override
    public List<Transaction> findTransactionsByAccountRef(String accountRef)
            throws AccountNotFoundException {
        if (!accountDao.accountExists(accountRef)) {
            throw new AccountNotFoundException(accountRef);
        }
        Set<String> transactionRefs = transactionDao.getTransactionRefsForAccount(accountRef);
        if (transactionRefs.isEmpty()) {
            return Lists.newArrayList();
        }
        return transactionDao.getTransactions(Lists.newArrayList(transactionRefs));
    }

    @Override
    public Transaction getTransactionByRef(String transactionRef) {
        return transactionDao.getTransactionByRef(transactionRef);
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setTransactionDao(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public void setValidator(TransferValidator validator) {
        this.validator = validator;
    }

}
