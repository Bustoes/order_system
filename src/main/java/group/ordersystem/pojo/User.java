package group.ordersystem.pojo;

import lombok.Data;

@Data
public class User {
    private Integer     user_id;
    private String      user_name;
    private Integer     identity;
    private String      account;
    private String      password;
}
