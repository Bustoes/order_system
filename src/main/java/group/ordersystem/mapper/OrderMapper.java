package group.ordersystem.mapper;


import group.ordersystem.pojo.Menu;
import group.ordersystem.pojo.Orders;
import org.apache.ibatis.annotations.*;
import org.hibernate.criterion.Order;

import java.util.List;

@Mapper
public interface OrderMapper {


    /**
     * 根据用户id查询其全部订单
     *
     * @param customer_id 用户id
     * @return 全部订单
     */
    @Select("select * from orders where customer_id=#{customer_id}")
    List<Orders> getOrdersByUserId(Integer customer_id);

    /**
     * 在订单表中添加订单信息（订单创建时）
     *
     * @param orders
     */
    @Insert("insert into orders (status,customer_id,destination,order_price) values (#{status},#{customer_id},#{destination},#{order_price})")
    @Options(useGeneratedKeys = true, keyProperty = "order_id", keyColumn = "order_id")
    void insertOrderDB(Orders orders);


    /**
     * 根据订单id查询订单数据
     *
     * @param order_id 用户id
     * @return 全部订单
     */
    @Select("select * from orders where order_id=#{order_id}")
    Orders getOrdersByOrderId(Integer order_id);

    /**
     * 更新订单评论
     */
    @Update("update orders set order_comment=#{order_comment} where order_id=#{order_id}")
    void updateOrderComment(String order_comment, Integer order_id);
    /**
     * 删除订单
     */
    @Delete("delete from orders where order_id=#{order_id}")
    void deleteOrder(Integer order_id);

    /**
     * 根据订单id更新订单状态
     */
    @Update("update orders set status=#{status} where order_id=#{order_id}")
    void updateOrderStatus(Integer status,Integer order_id);
}
