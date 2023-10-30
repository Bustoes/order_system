package group.ordersystem.pojo.form;

import lombok.Data;

import java.util.List;

@Data
public class OrderForm {
    private Integer         customer_id;
    private String          destination;
    private Integer         order_price;
    private List<Integer>   meals;
}
