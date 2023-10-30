package group.ordersystem;

import group.ordersystem.mapper.MenuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class MapperTests {

    @Resource
    MenuMapper menuMapper;
    @Test
    void OrderMapper(){
        System.out.println(menuMapper.getMenu());

    }
}
