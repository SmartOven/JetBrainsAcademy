package account.model.salary;

import account.model.user.UserDetailsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_salary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSalaryOnPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserDetailsEntity user;

    @Column(name = "period", nullable = false)
    private LocalDate period;

    @Column(name = "salary", nullable = false)
    private Long salary;
}
