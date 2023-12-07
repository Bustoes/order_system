package group.ordersystem.mapper;

import group.ordersystem.pojo.Menu_Order;
import group.ordersystem.pojo.Orders;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuOrderMapper {
    /**
     * 将对应订单信息中的菜品信息添加到order_menu表中
     * @param order_id
     * @param meal_id
     */
    @Insert("insert into menu_order (order_id,meal_id) values (#{order_id},#{meal_id})")
    void insertMealsInOrder(Integer order_id, Integer meal_id);
    /**
     * 根据订单id删除关联表项
     */
    @Delete("delete from menu_order where order_id=#{order_id}")
    void deleteMealsInOrder(Integer order_id);

    /**
     * 查询销售情况
     *
     * @return 销售情况
     */
    @Select("select * from menu_order")
    List<Menu_Order> selectMenuOrder();
}
