package account.model.authority.enums;

public enum Role {
    USER,
    ACCOUNTANT,
    ADMINISTRATOR,
    AUDITOR;

    public Authority getAsAuthority() {
        return Authority.valueOf("ROLE_" + name());
    }
}
