package group.ordersystem.controller;

import group.ordersystem.pojo.*;
import group.ordersystem.pojo.form.*;
import group.ordersystem.pojo.res.*;
import group.ordersystem.pojo.res.OrderSellRes;
import group.ordersystem.service.StaffService;
import group.ordersystem.util.response.UniversalResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {
    @Resource
    private StaffService staffService;

    /**
     * 查询全部菜单
     *
     * @return 全部菜单
     */
    @GetMapping("/menu")
    @ResponseBody
    public UniversalResponse<List<Menu>> getMenus() {

        return staffService.getMenu();
    }

    /**
     * 根据菜品id更改菜品价格
     * @param updateMealForm
     * @return
     */
    @PostMapping("/menu/update")
    @ResponseBody
    public UniversalResponse<?> updateMeal(@RequestBody UpdateMealForm updateMealForm) {
        return staffService.updateMeal(updateMealForm);
    }

    /**
     * 增加新的菜品信息
     * @param insertMealForm
     * @return
     */
    @PostMapping("/menu/insert")
    @ResponseBody
    public UniversalResponse<?> insertMeal(@RequestBody InsertMealForm insertMealForm) {
        return staffService.insertMeal(insertMealForm);
    }

    /**
     * 根据菜品id删除菜品
     * @param deleteMealForm
     * @return
     */
    @PostMapping("/menu/delete")
    @ResponseBody
    public UniversalResponse<?> deleteMeal(@RequestBody DeleteMealForm deleteMealForm) {
        return staffService.deleteMeal(deleteMealForm);
    }

    @GetMapping("/sale")
    @ResponseBody
    public UniversalResponse<List<OrderSellRes>> getMenuSale(){
        return staffService.getMenuSale();
    }

    @GetMapping("/order")
    @ResponseBody
    public UniversalResponse<List<Menu_Order>> getMenuOrder(){
        return staffService.getMenuOrder();
    }

    @PostMapping("/accept")
    @ResponseBody
    public UniversalResponse<List<Orders>> acceptOrder(@RequestBody UpdateStatusForm updateStatusForm){
        return staffService.acceptOrder(updateStatusForm);
    }
}
