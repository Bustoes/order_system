package group.ordersystem.mapper;

import group.ordersystem.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuMapper {
    /**
     * 查询全部菜单
     *
     * @return 全部菜单
     */
    @Select("select * from menu")
    List<Menu> selectMenu();

    /**
     * 返回某订单点的菜品
     *
     * @param order_id 订单号
     * @return 订单号对应的点单菜品列表
     */
    @Select("SELECT * FROM menu WHERE meal_id in (SELECT meal_id FROM menu_order WHERE order_id = #{order_id})")
    List<Menu> getMenusByOrderId(Integer order_id);

    /**
     * 根据菜品id返回菜品价格
     * @param meal_id
     * @return
     */
    @Select("select meal_price from menu where meal_id = #{meal_id}")
    Integer getMealPriceByMealId(Integer meal_id);
}
