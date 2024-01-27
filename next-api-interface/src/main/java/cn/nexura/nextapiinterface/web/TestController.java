package cn.nexura.nextapiinterface.web;

import cn.nexura.nextapiinterface.model.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author PeiYP
 * @since 2024年01月23日 13:16
 */
@RestController
@RequestMapping("/name")
public class TestController {

    @GetMapping
    public String getNameUsingGet(String name) {
        return "GET你的名字是：" + name;
    }

    @PostMapping
    public String getNameUsingPost(@RequestParam String name) {
        return "POST你的名字是：" + name;
    }

    @PostMapping("/json")
    public String getNameBody(@RequestBody User user) {
        return "User你的名字是：" + user.getUserName();
    }

}
