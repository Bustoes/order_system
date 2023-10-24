package group.ordersystem.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Orders {
    private int     order_id;
    private int     status;
    private int     customer_id;
    private int     deliver_id;
    private Date    deliver_time;
    private String  destination;
    private int     order_price;
    private String  order_comment;
}
