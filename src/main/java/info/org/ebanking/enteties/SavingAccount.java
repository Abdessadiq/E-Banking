package info.org.ebanking.enteties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;
@Entity
@DiscriminatorValue("SA")
@Data @NoArgsConstructor  @AllArgsConstructor
public class SavingAccount extends BankAccount implements Serializable {
    private  double interestRate;

}
