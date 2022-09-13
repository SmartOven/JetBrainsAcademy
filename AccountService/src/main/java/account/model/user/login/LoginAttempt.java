package account.model.user.login;

import account.model.user.UserDetailsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "USER_LOGIN_ATTEMPT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private UserDetailsEntity user;

    @Column(name = "attempts_count", nullable = false)
    private Long attemptsCount;
}
