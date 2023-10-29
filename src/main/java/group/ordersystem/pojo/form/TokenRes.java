package group.ordersystem.pojo.form;

import lombok.Data;

@Data
public class TokenRes {
    private String token;

    public TokenRes(String token) {
        this.token = token;
    }
}
