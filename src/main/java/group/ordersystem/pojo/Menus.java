package group.ordersystem.pojo;

import lombok.Data;

@Data
public class Menus {
    private int     meal_id;
    private String  meal_name;
    private int     price;
    private String  image_path;
    private String  type;
    private int     sales;
}
