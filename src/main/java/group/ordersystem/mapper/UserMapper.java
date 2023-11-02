package group.ordersystem.mapper;

import group.ordersystem.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    /**
     * 通过账户名获取用户
     * @param account
     * @return
     */
    @Select("select * from user where account = #{account}")
    User getUserByAccount(String account);

    /**
     * 添加用户，主键自增
     * @param user
     */
    @Insert("insert into user(user_name, identity, account, password) values(#{user_name}, #{identity}, #{account}, #{password})")
    void insertUser(User user);
}
