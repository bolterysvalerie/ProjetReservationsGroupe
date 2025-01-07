package be.icc.Pid_Reservations_2024.Enums;

public enum UserRoles {

    ADMIN("admin"),
    MEMBER("member"),
    AFFILIATE("affiliate"),
    PRESS("press"),
    PRODUCER("producer");

    private String role;

    UserRoles(String role) {
        this.role = role;
    }

    public String getValue() {
        return role;
    }

}

