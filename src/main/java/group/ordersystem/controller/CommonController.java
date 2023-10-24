package group.ordersystem.controller;

import group.ordersystem.util.response.UniversalResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CommonController {

    @GetMapping
    @ResponseBody
    public UniversalResponse<Object> index() {
        return null;
    }
}
