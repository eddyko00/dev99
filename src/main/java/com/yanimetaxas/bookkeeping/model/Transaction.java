package com.yanimetaxas.bookkeeping.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Value object representing a monetary transaction between two or more accounts. Each
 * account transaction is represented by a transaction leg.
 *
 * @author yanimetaxas
 * @see TransactionLeg
 * @since 14-Nov-14
 */
public final class Transaction implements Serializable {

    /**
     * @return the updatedatel
     */
    public long getUpdatedatel() {
        return updatedatel;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }


  private static final long serialVersionUID = 1L;

  private final String transactionRef;

  private final String transactionType;

  private final Date transactionDate;

  private final long updatedatel;

  private final String comment;
  
  private final List<TransactionLeg> legs;

  public Transaction(String transactionRef, String transactionType, Date transactionDate, long updatedatel, String comment,
      List<TransactionLeg> legs) {
    this.transactionRef = transactionRef;
    this.transactionType = transactionType;
    this.transactionDate = transactionDate;
    this.updatedatel = updatedatel;
    this.comment = comment;
            
    this.legs = legs;
  }

  public String getTransactionRef() {
    return transactionRef;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public List<TransactionLeg> getLegs() {
    return Collections.unmodifiableList(legs);
  }

  public Date getTransactionDate() {
    return new Date(transactionDate.getTime());
  }
}
