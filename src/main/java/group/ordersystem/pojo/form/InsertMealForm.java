package group.ordersystem.pojo.form;

import lombok.Data;

@Data
public class InsertMealForm {
    private Integer meal_id;
    private String meal_name;
    private Integer meal_price;
    private String type;
}
