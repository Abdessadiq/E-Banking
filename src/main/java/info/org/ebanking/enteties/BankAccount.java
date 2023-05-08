package info.org.ebanking.enteties;

import info.org.ebanking.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //indiquer qu'on va utiliser la stratégie single table dans la base de données
@DiscriminatorColumn(name = "TYPE",length = 4) // le nom de la column pour spécifier le type du compte...
@Data @NoArgsConstructor @AllArgsConstructor
public class BankAccount implements Serializable {
    @Id
    private String id;
    private Date createdAt;
    private double balance;
    private String currency; //devise
    @Enumerated(EnumType.STRING)
    private AccountStatus status;


    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankaccount")
    private List<Operation> operations;

}
