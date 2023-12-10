package group.ordersystem.pojo.res;

import group.ordersystem.pojo.Menu;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class GetOrdersWithDRes {
    private Integer     order_id;
    private Integer     status;
    private String      destination;
    private String      delivery_name;
    private Integer     order_price;
    private String      order_comment;
}
