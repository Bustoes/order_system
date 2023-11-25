package group.ordersystem.util.enums;

public enum MenuTypeEnum {
    COLD("凉菜"),
    HOT("热菜"),
    SNACK("小吃"),
    DRINK("饮料"),
    MAIN("主食");


    private final String type;

    MenuTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
