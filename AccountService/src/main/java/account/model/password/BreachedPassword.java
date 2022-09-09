package account.model.password;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "breached_passwords")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BreachedPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "password", unique = true)
    private String password;
}
