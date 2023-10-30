package group.ordersystem.util.enums;

public enum OrderStatusEnum {

    /**
     * 订单状态枚举类
     *     CREATED：订单已创建状态（师傅正在炒菜）          code=1
     *     COOKED：菜品已经出餐（正在等待配送）             code=2
     *     ACCEPTED：订单正在配送（外卖小哥取到餐了）        code=3
     *     FINISHED：订单已经送达（顾客还没有评论           code=4
     *     COMMENTED：已经被顾客评论了                    code=5
     */

    CREATED(1),
    COOKED(2),
    ACCEPTED(3),
    FINISHED(4),
    COMMENTED(5);


    private final Integer code;

    OrderStatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
