package group.ordersystem.pojo.res;

import lombok.Data;

@Data
public class TokenRes {
    private String token;

    public TokenRes(String token) {
        this.token = token;
    }
}
