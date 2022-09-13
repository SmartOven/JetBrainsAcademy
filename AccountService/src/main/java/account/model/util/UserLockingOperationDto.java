package account.model.util;

import account.model.util.enums.LockingOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLockingOperationDto {
    private String user; // email
    private String operation; // LockingOperation
}
