package info.org.ebanking.services;

import info.org.ebanking.enteties.*;
import info.org.ebanking.enums.AccountStatus;
import info.org.ebanking.enums.OperationType;
import info.org.ebanking.exception.BalanceNotsufficientException;
import info.org.ebanking.exception.BankAccountNotExistException;
import info.org.ebanking.exception.CustomerExistException;
import info.org.ebanking.exception.CustomerNotFountException;
import info.org.ebanking.repositories.BankAccountRepository;
import info.org.ebanking.repositories.CustomerRepository;
import info.org.ebanking.repositories.OperationRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
// à vrai dire que toutes les opérations sont transactionnel -> à chaque fois que j'appelles
// l'opération, spring va démarrer la transaction (begin transactional try.. ) il appelle la méthode
// si la méthode génére une exception il fait rollback, si non il fait commit
@Slf4j  // --> (1)

public class BankAccountServiceImp implements BankAccountService{

    public CustomerRepository customerRepository;
    public BankAccountRepository bankAccountRepository;
    public OperationRepository operationRepository;

    public BankAccountServiceImp(CustomerRepository customerRepository,
                                 BankAccountRepository bankAccountRepository,
                                 OperationRepository operationRepository) {
        this.customerRepository = customerRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.operationRepository = operationRepository;
    }
    //Logger log= (Logger) LoggerFactory.getLogger(this.getClass().getName()); <--- (1)

    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("Saving a new Customer");
        customerRepository.findAll().forEach(cust-> {
            if(cust.getId()==customer.getId()) {

                try {
                    throw new CustomerExistException("this Customer exist");
                } catch (CustomerExistException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        Customer saveCustomer= customerRepository.save(customer);
    return saveCustomer;
    }

    @Override
    public CurrentAccount saveCurrentAccount(double intialBalance, double overDraft, Long customerId)throws CustomerNotFountException {
       Customer customer= customerRepository.findById(customerId).orElse(null);
       if (customer==null)
           throw new CustomerNotFountException("Cunstomer not found");
       CurrentAccount currentAccount = new CurrentAccount();
       currentAccount.setId(UUID.randomUUID().toString());
       currentAccount.setBalance(intialBalance);
       currentAccount.setStatus(AccountStatus.ACTIVATED);
       currentAccount.setCreatedAt(new Date());
       currentAccount.setCurrency("DH");
       currentAccount.setOverDraft(overDraft);
       currentAccount.setCustomer(customer);
      CurrentAccount saveBankAccount= bankAccountRepository.save(currentAccount);
        return saveBankAccount;
    }

    @Override
    public SavingAccount saveSavingAccount(double intialBalance, double interstRate, Long customerId) throws CustomerNotFountException {
        Customer customer= customerRepository.findById(customerId).orElse(null);
        if (customer==null)
            throw new CustomerNotFountException("Cunstomer not found");
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setBalance(intialBalance);
        savingAccount.setStatus(AccountStatus.ACTIVATED);
        savingAccount.setCreatedAt(new Date());
        savingAccount.setCurrency("DH");
        savingAccount.setInterestRate(interstRate);
        savingAccount.setCustomer(customer);
        SavingAccount saveBankAccount=  bankAccountRepository.save(savingAccount);
        return saveBankAccount;
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotExistException {
        BankAccount  bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        if (bankAccount==null)
            throw new BankAccountNotExistException("BankAccount Not Exist !!");

        return bankAccount;
    }

    @Override
    public List<Customer> listCustomer() {

        return customerRepository.findAll();
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BalanceNotsufficientException, BankAccountNotExistException {
        BankAccount bankAccount = getBankAccount(accountId);

        if (bankAccount.getBalance()<amount)
        throw new BalanceNotsufficientException("Banlance not sufficient !");
        Operation accountOperation =  new Operation();
        accountOperation.setBankaccount(bankAccount);
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDesciption(description);
        accountOperation.setDateOperation(new Date());
        operationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotExistException {
        BankAccount bankAccount = getBankAccount(accountId);

        Operation accountOperation =  new Operation();
        accountOperation.setBankaccount(bankAccount);
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDesciption(description);
        accountOperation.setDateOperation(new Date());
        operationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);;
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDistination, double amount) throws BankAccountNotExistException, BalanceNotsufficientException {
        debit(accountIdSource,amount,"Tranfert To"+accountIdDistination);
        credit(accountIdDistination,amount,"Transfert From"+accountIdSource);

    }
    @Override
    public List<BankAccount> listAcount(){
        return bankAccountRepository.findAll();
    }
}
