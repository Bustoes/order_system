package group.ordersystem.util.enums;

public enum MenuTypeEnum {
    COLD(1),
    HOT(2),
    SNACK(3),
    DRINK(4);


    private final Integer type;

    MenuTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
