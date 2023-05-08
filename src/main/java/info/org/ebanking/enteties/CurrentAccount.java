package info.org.ebanking.enteties;

import info.org.ebanking.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@DiscriminatorValue("CA")
public class CurrentAccount extends BankAccount implements Serializable {
    private  double overDraft;
}
