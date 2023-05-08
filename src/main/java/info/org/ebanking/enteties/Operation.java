package info.org.ebanking.enteties;

import info.org.ebanking.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Data  @NoArgsConstructor @AllArgsConstructor

public class Operation implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateOperation;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    private String desciption;

    @ManyToOne
    private BankAccount bankaccount;
}
