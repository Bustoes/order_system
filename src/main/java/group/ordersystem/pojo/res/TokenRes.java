package group.ordersystem.pojo.res;

import lombok.Data;

@Data
public class TokenRes {
    private String token;
    private Integer identity;

    public TokenRes(String token, Integer identity) {
        this.token = token;
        this.identity = identity;
    }
}
