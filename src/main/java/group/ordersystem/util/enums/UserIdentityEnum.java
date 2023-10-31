package group.ordersystem.util.enums;

public enum UserIdentityEnum {
    CUSTOMER(1),
    STAFF(2),
    DELIVER(3);


    private final Integer identity;

    UserIdentityEnum(Integer identity) {
        this.identity = identity;
    }

    public Integer getIdentity() {
        return identity;
    }
}
