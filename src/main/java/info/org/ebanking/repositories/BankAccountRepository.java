package info.org.ebanking.repositories;

import info.org.ebanking.enteties.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
