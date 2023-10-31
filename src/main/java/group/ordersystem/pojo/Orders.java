package group.ordersystem.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;


@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer     order_id;       // 数据库自动将order_id获取到对象中
    private Integer     status;
    private Integer     customer_id;
    private Integer     deliver_id;
    private Timestamp   deliver_time;
    private String      destination;
    private Integer     order_price;
    private String      order_comment;

    public Orders(Integer status, Integer customer_id, String destination, Integer order_price) {
        this.status = status;
        this.customer_id = customer_id;
        this.destination = destination;
        this.order_price = order_price;
    }
}
