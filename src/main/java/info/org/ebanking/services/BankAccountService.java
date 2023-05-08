package info.org.ebanking.services;

import info.org.ebanking.enteties.BankAccount;
import info.org.ebanking.enteties.CurrentAccount;
import info.org.ebanking.enteties.Customer;
import info.org.ebanking.enteties.SavingAccount;
import info.org.ebanking.exception.BalanceNotsufficientException;
import info.org.ebanking.exception.BankAccountNotExistException;
import info.org.ebanking.exception.CustomerExistException;
import info.org.ebanking.exception.CustomerNotFountException;

import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer) ;
    CurrentAccount saveCurrentAccount(double intialBalance, double overDraft, Long customerId) throws CustomerNotFountException;
    SavingAccount saveSavingAccount(double intialBalance, double interstRate, Long customerId) throws CustomerNotFountException;
    BankAccount getBankAccount(String accountId) throws BankAccountNotExistException;
    List<Customer> listCustomer();
    void debit(String accountId, double amount, String  description) throws BankAccountNotExistException, BalanceNotsufficientException;
    void credit(String accountId, double amount, String  description) throws BankAccountNotExistException;
    void  transfer(String accountIdSource, String accountIdDistination, double amount) throws BankAccountNotExistException, BalanceNotsufficientException;

    List<BankAccount> listAcount();
}
