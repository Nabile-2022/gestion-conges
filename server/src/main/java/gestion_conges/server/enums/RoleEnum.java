package gestion_conges.server.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RoleEnum {

    ROLE_ADMIN ("ROLE_ADMIN"),
    ROLE_MANAGER ("ROLE_MANAGER"),
    ROLE_EMPLOYEE ("ROLE_EMPLOYEE");

    private final String name;

    public String toString() {
        return this.name;
    }
}
