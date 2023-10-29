package group.ordersystem.util.response;

import lombok.Getter;

@Getter
public class ResponseException extends RuntimeException {
    private final Integer code;
    private final String message;

    public ResponseException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}