package group.ordersystem.controller;

import group.ordersystem.pojo.*;
import group.ordersystem.pojo.form.OrderForm;
import group.ordersystem.service.CustomerService;
import group.ordersystem.util.response.UniversalResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class CustomerController {
    @Resource
    private CustomerService customerService;

    /**
     * 获取所有菜品
     * @return 返回菜品列表
     */
    @GetMapping("/menus")
    @ResponseBody
    public UniversalResponse<List<Menu>> getMenus() {
        return null;
    }

    /**
     * 建立订单
     * @param orderForm 前端传来的订单数据
     * @return 不返回数据
     */
    @PostMapping("/order")
    @ResponseBody
    public UniversalResponse<?> postOrder(@RequestBody OrderForm orderForm) {
        return null;
    }
}
