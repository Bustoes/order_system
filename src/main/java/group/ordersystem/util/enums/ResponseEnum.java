package group.ordersystem.util.enums;

public enum ResponseEnum {
    SUCCESS(1000, "success"),
    FAILED(1001, "错误"),

    /**
     * 参数错误 1002-1999
     */
    PARAM_IS_INVALID(1002, "参数无效"),
    PARAM_IS_BLANK(1003, "参数为空"),
    PARAM_TYPE_BIND_ERROR(1004, "参数类型错误"),
    PARAM_IS_OVERLONG(1005,"参数过长"),

    /**
     * 用户错误 2000-2999
     */
    USER_LOGIN_SUCCESS(2000, "登录成功"),
    USER_TOKEN_ERROR(2001, "用户token错误"),
    USER_LOGIN_ERROR(2002, "账号不存在或密码错误"),
    USER_ACCOUNT_EXISTS(2003, "账户已存在"),
    USER_NO_PERMISSION(2004, "用户权限不足"),
    USER_MATCH_ERROR(2005,"用户登录状态和参数不匹配"),
    USER_HAVE_EXIST(2000, "用户登录成功,非首次登录"),

    /**
     * 状态错误
     */
    ORDER_STATE_ERROR(3000,"订单状态不匹配"),

    /**
     * 服务器错误
     */
    SERVER_BUSY(5000, "服务器繁忙，请稍后重试"),

    /**
     * 评论修改成功和评论成功（和之前是否为null有关）
     *
     */
    ADD_COMMENT(1500,"添加评论成功"),
    CHANGE_COMMENT(1501, "修改评论成功");


    private final Integer code;
    private final String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }



    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
