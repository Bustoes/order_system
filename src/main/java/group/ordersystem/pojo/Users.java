package group.ordersystem.pojo;

import lombok.Data;

@Data
public class Users {
    private int     user_id;
    private String  user_name;
    private int     identity;
    private String  account;
    private String  password;
}
