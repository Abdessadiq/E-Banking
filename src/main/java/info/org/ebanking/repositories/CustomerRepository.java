package info.org.ebanking.repositories;

import info.org.ebanking.enteties.BankAccount;
import info.org.ebanking.enteties.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
