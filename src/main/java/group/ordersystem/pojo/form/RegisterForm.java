package group.ordersystem.pojo.form;

import lombok.Data;

@Data
public class RegisterForm {
    private String  user_name;
    private int     identity;
    private String  account;
    private String  password;

}
