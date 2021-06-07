package com.yanimetaxas.bookkeeping.model;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Immutable value object representing an account
 *
 * @author yanimetaxas
 * @since 14-Nov-14
 */
public class Account {

  private final String accountRef;
  private final Money balance;
  private static final Account NULL_ACCOUNT = new Account("",
      new Money(new BigDecimal("0.00"), Currency.getInstance("XXX")));

  public Account(String accountRef, Money balance) {
    this.accountRef = accountRef;
    this.balance = balance;
  }

  public static Account nullAccount() {
    return NULL_ACCOUNT;
  }

  public String getAccountRef() {
    return accountRef;
  }

  public Money getBalance() {
    return balance;
  }

  public Currency getCurrency() {
    return balance.getCurrency();
  }

  public boolean isOverdrawn() {
    return balance.getAmount().doubleValue() < 0.0;
  }

  public boolean isNullAccount() {
    return this.equals(NULL_ACCOUNT);
  }

}