package group.ordersystem.controller;

import group.ordersystem.pojo.*;
import group.ordersystem.pojo.form.CommentForm;
import group.ordersystem.pojo.res.GetOrdersRes;
import group.ordersystem.pojo.form.PostOrderForm;
import group.ordersystem.service.CustomerService;
import group.ordersystem.util.response.UniversalResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Resource
    private CustomerService customerService;

    /**
     * 查询所有菜品
     * 2.3.4.1 顾客查看系统显示的菜单
     *
     * @return 返回菜品列表
     * Author fireworkz
     */
    @GetMapping("/menu")
    @ResponseBody
    public UniversalResponse<List<Menu>> getMenus() {
        return customerService.getMenu();
    }

    /**
     * 根据用户查询其订单信息
     * 2.3.4.1顾客可以查看全部订单信息
     *
     * @return 历史订单信息
     * Author fireworkz
     */
    @GetMapping("/order")
    @ResponseBody
    public UniversalResponse<List<GetOrdersRes>> getOrders() {
        return customerService.getOrder();
    }

    /**
     * 2.3.4.2 顾客选菜
     * 2.3.4.3 顾客确认订单信息
     * 由前端实现
     * Author fireworkz
     */
    public void doNothinglol() {
    }

    /**
     * 建立订单
     * 2.3.4.4 顾客确认订单
     *
     * @param postOrderForm 前端传来的订单数据
     * @return 不返回数据
     * Author fireworkz
     */
    @PostMapping("/order")
    @ResponseBody
    public UniversalResponse<?> postOrder(@RequestBody PostOrderForm postOrderForm) {
        return customerService.insertOrder(postOrderForm);
    }

    /**
     * 建立订单
     * 2.3.4.4 顾客删除订单（商家接单前）
     *
     * @param order_id
     * @return null
     * Author fireworkz
     */
    @DeleteMapping("/order/{order_id}")
    public UniversalResponse<?> deleteOrder(@PathVariable Integer order_id) {
        return customerService.deleteOrderByCustomer(order_id);
    }


    /**
     * 根据无参请求返回查询到的全部订单评论
     * 2.3.4.6 顾客可以新增评论/修改评论
     *
     * @param commentForm
     * @return null
     * Author fireworkz
     */
    @PostMapping("/order/comment")
    @ResponseBody
    public UniversalResponse<?> postComment(@RequestBody CommentForm commentForm) {
        return customerService.updateComment(commentForm);
    }


}
