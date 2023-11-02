package group.ordersystem.pojo.form;

import lombok.Data;

import java.util.List;

@Data
public class PostOrderForm {
    private String          destination;
    private List<Integer>   meals;
}
