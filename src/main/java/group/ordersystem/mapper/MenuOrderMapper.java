package group.ordersystem.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuOrderMapper {
    /**
     * 将对应订单信息中的菜品信息添加到order_menu表中
     * @param order_id
     * @param meal_id
     */
    @Insert("insert into menu_order (order_id,meal_id) values (#{order_id},#{meal_id})")
    void insertMealsInOrder(Integer order_id, Integer meal_id);
}
