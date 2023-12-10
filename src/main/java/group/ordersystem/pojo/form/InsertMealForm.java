package group.ordersystem.pojo.form;

import lombok.Data;

@Data
public class InsertMealForm {
    private String meal_name;
    private Integer meal_price;
    private String type;
    private String image_path;
}
