package group.ordersystem.pojo;

import lombok.Data;

@Data
public class Menu {
    private Integer     meal_id;
    private String      meal_name;
    private Integer     price;
    private String      image_path;
    private String      type;
    private Integer     sales;
}
