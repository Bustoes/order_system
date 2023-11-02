package group.ordersystem.mapper;


import group.ordersystem.pojo.Menu;
import group.ordersystem.pojo.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

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
     * @param orders
     */
    @Insert("insert into orders (status,customer_id,destination,order_price) values (#{status},#{customer_id},#{destination},#{order_price})")
    @Options(useGeneratedKeys = true, keyProperty = "order_id", keyColumn = "order_id")
    void insertOrderDB(Orders orders);


}
