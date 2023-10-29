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

    /**
     * 用户错误 2000-2999
     */
    USER_LOGIN_SUCCESS(2000, "登录成功"),
    USER_TOKEN_ERROR(2001, "用户token错误"),
    USER_LOGIN_ERROR(2002, "账号不存在或密码错误"),
    USER_ACCOUNT_EXISTS(2003, "账户已存在"),
    USER_NO_PERMISSION(2004, "用户权限不足"),
    USER_HAVE_EXIST(2000, "用户登录成功,非首次登录"),

    /**
     * 服务器错误
     */
    SERVER_BUSY(5000, "服务器繁忙，请稍后重试");


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
