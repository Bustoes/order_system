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

}
