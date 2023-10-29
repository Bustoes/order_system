package group.ordersystem.util.response;

import lombok.Data;

@Data
public class UniversalResponse<T> {
    private Integer code;
    private String msg;
    private T data;

    public UniversalResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public UniversalResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
