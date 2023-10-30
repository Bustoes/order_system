package group.ordersystem.controller;

import group.ordersystem.pojo.*;
import group.ordersystem.pojo.res.GetOrdersRes;
import group.ordersystem.pojo.form.PostOrderForm;
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
     * 查询所有菜品
     * @return 返回菜品列表
     */
    @GetMapping("/menus")
    @ResponseBody
    public UniversalResponse<List<Menu>> getMenus() {
    return customerService.getMenus();
    }

    /**
     * 建立订单
     * @param postOrderForm 前端传来的订单数据
     * @return 不返回数据
     */
    @PostMapping("/order")
    @ResponseBody
    public UniversalResponse<?> postOrder(@RequestBody PostOrderForm postOrderForm) {
        return customerService.insertOrder(postOrderForm);
    }

    /**
     * 根据用户查询其订单信息
     * @return 返回该用户的订单列表
     */
    @GetMapping("/order")
    @ResponseBody
    public UniversalResponse<List<GetOrdersRes>> getOrders(){
        return customerService.getOrder();
    }
}
