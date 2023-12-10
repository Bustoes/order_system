package group.ordersystem.mapper;

import group.ordersystem.pojo.Menu;
import group.ordersystem.pojo.Menu_Order;
import group.ordersystem.util.response.UniversalResponse;
import org.apache.ibatis.annotations.*;

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

    /**
     * 根据菜品id更改菜品价格
     * @param meal_id, new_price
     * @return
     */
    @Update("update menu set meal_price = #{new_price} where meal_id = #{meal_id}")
    void updateMealPriceByMealId(Integer meal_id, Integer new_price);

    /**
     * 增加新的菜品信息
     * @param meal_name, meal_price, type
     * @return
     */
    @Insert("insert into menu values (null, #{meal_name}, #{meal_price}, #{image_path}, #{type}, null)")
    void insertMealInformation(String meal_name, Integer meal_price, String type, String image_path);

    /**
     * 根据菜品id删除菜品
     * @param meal_id
     * @return
     */
    @Delete("delete from menu where meal_id = #{meal_id}")
    void deleteMealByMealId(Integer meal_id);

    @Select("select meal_name from menu where meal_id = #{meal_id}")
    String getNameByMealId(Integer meal_id);
}
