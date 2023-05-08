package info.org.ebanking.repositories;

import info.org.ebanking.enteties.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,Long> {

}
