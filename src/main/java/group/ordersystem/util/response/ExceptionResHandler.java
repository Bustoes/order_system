package group.ordersystem.util.response;

import group.ordersystem.util.enums.ResponseEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionResHandler {
    @ExceptionHandler(ResponseException.class)
    public UniversalResponse<?> handlerRes(ResponseException e) {
        return new UniversalResponse<>(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public UniversalResponse<?> handler(Exception e) {
        return new UniversalResponse<>(ResponseEnum.SERVER_BUSY.getCode(), ResponseEnum.SERVER_BUSY.getMsg());
    }
}
