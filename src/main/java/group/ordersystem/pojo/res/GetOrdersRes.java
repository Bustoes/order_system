package group.ordersystem.pojo.res;

import lombok.Data;
import group.ordersystem.pojo.Menu;
import java.sql.Timestamp;
import java.util.List;

@Data
public class GetOrdersRes {
    private Integer     order_id;
    private Integer     status;
    private Integer     deliver_id;
    private Timestamp   deliver_time;
    private String      destination;
    private Integer     order_price;
    private String      order_comment;
    private List<Menu>  menus;
}
