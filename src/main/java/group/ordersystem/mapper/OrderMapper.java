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
     * 返回某订单点的菜品
     *
     * @param order_id 订单号
     * @return 订单号对应的点单菜品列表
     */
    @Select("SELECT * FROM menu WHERE meal_id in (SELECT meal_id FROM menu_order WHERE order_id = #{order_id})")
    List<Menu> getMenusByOrderId(Integer order_id);


    /**
     * 在订单表中添加订单信息（订单创建时）
     */
    @Insert("insert into orders (status,customer_id,destination,order_price) values (#{status},#{customer_id},#{destination},#{order_price})")
    @Options(useGeneratedKeys = true, keyProperty = "order_id", keyColumn = "order_id")
    void insertOrderDB(Orders orders);

    /**
     * 将订单信息中的菜品信息添加到order_menu表中
     */
    @Insert("insert into menu_order (order_id,meal_id) values (#{order_id},#{meal_id})")
    void insertMealsInOrder(Integer order_id, Integer meal_id);


}
