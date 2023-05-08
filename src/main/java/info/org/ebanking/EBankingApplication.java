package info.org.ebanking;

import info.org.ebanking.enteties.*;
import info.org.ebanking.enums.AccountStatus;
import info.org.ebanking.enums.OperationType;
import info.org.ebanking.exception.BalanceNotsufficientException;
import info.org.ebanking.exception.BankAccountNotExistException;
import info.org.ebanking.exception.CustomerNotFountException;
import info.org.ebanking.repositories.BankAccountRepository;
import info.org.ebanking.repositories.CustomerRepository;
import info.org.ebanking.repositories.OperationRepository;
import info.org.ebanking.services.BankAccountServiceImp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.Basic;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankingApplication.class, args);

    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountServiceImp bankAccountServiceImp,
                            CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository){
        return args -> {
            Stream.of("Hassan","Lahcen","Oumaima").forEach(name->{
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+".22@gmail.com");
                bankAccountServiceImp.saveCustomer(customer);
            });
            bankAccountServiceImp.listCustomer().forEach(customer -> {
                try {
                    bankAccountServiceImp.saveCurrentAccount(12000+Math.random()*90000, 10000,customer.getId());
                    bankAccountServiceImp.saveSavingAccount(10000+Math.random()*1000, 2.2,customer.getId());
                    List<BankAccount> bankAccountList= bankAccountServiceImp.listAcount();
                    for (BankAccount bankAccount :bankAccountList) {
                        for (int i = 0; i < 10; i++) {
                            bankAccountServiceImp.debit(bankAccount.getId(),3000,"This is a debit");
                            bankAccountServiceImp.credit(bankAccount.getId(),4000,"This is a credit");
                        }
                    }

                } catch (CustomerNotFountException | BalanceNotsufficientException | BankAccountNotExistException e) {
                   e.printStackTrace();
                }
            });


        };
    };


    /*@Bean
    CommandLineRunner start(BankAccountRepository bankAccountRepository,
                            CustomerRepository customerRepository,
                            OperationRepository operationRepository){
        return  args ->{
            Stream.of("Hassan","Aicha","Lahcen").forEach(name->{
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"22@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cus->{
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*9000);
                currentAccount.setCurrency("DH");
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.ACTIVATED);
                currentAccount.setOverDraft(Math.random()*12000);
                currentAccount.setCustomer(cus);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*9000);
                savingAccount.setCurrency("DH");
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.ACTIVATED);
                savingAccount.setInterestRate(2.4);
                savingAccount.setCustomer(cus);
                System.out.println("***************"+savingAccount.getClass().getSimpleName()+"**************");
                bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(acc->{
                for (int i = 0; i < 10; i++) {
                    Operation  operation= new Operation();
                    operation.setDateOperation(new Date());
                    operation.setType(Math.random()>0.5?OperationType.CREDIT:OperationType.DEBIT);
                    operation.setAmount(Math.random()*10000);
                    operation.setBankaccount(acc);
                    operationRepository.save(operation);
                }
            });




        };
    }*/
}
