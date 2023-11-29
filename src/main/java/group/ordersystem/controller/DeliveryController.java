package group.ordersystem.controller;

import group.ordersystem.pojo.res.GetOrdersRes;
import group.ordersystem.service.DeliveryService;
import group.ordersystem.util.response.UniversalResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Resource
    private DeliveryService deliveryService;
    /**
     * 查询系统所有订单
     *
     * @return 返回系统订单
     * Author ruo371
     */
    @GetMapping("/order")
    @ResponseBody
    public UniversalResponse<List<GetOrdersRes>> getOrders1() {
        return deliveryService.getOrder();
    }
    /**
     * 查询自己已订订单
     *
     * @return 返回自己订单
     * Author ruo371
     */
    @GetMapping("/myorder")
    @ResponseBody
    public UniversalResponse<List<GetOrdersRes>> getOrders2() {
        return deliveryService.getmyOrder();
    }
    /**
     * 接单
     *
     * @return 无
     * Author ruo371
     */
    @PostMapping("/order/{order_id}")
    @ResponseBody
    public UniversalResponse<?> postOrder1(@PathVariable Integer order_id) {
        return deliveryService.take_order(order_id);
    }
    /**
     * 取餐
     *
     * @return 无
     * Author ruo371
     */
    @PostMapping("/take/{order_id}")
    @ResponseBody
    public UniversalResponse<?> postOrder2(@PathVariable Integer order_id) {
        return deliveryService.take_meal(order_id);
    }
    /**
     * 订单已送达
     *
     * @return 无
     * Author ruo371
     */
    @PostMapping("/delivery/{order_id}")
    @ResponseBody
    public UniversalResponse<?> postOrder3(@PathVariable Integer order_id) {
        return deliveryService.delivery_meal(order_id);
    }
    /**
     * 在去餐前，取消订单
     *
     * @return 无
     * Author ruo371
     */
    @PostMapping("/delete/{order_id}")
    @ResponseBody
    public UniversalResponse<?> postOrder4(@PathVariable Integer order_id) {
        return deliveryService.delete_meal(order_id);
    }
}
