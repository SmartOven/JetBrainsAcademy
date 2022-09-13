package account.model.authority.enums;

public enum Authority {
    ROLE_USER,
    ROLE_ACCOUNTANT,
    ROLE_ADMINISTRATOR,
    ROLE_AUDITOR;

    public Role getAsRole() {
        return Role.valueOf(name().substring(5));
    }
}
