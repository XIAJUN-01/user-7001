package com.cssl.controller;

import com.cssl.pojo.Users;
import com.cssl.service.UsersService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rx.observables.SyncOnSubscribe;

import java.util.Map;

@RestController
public class UsersController {

    @Value("${server.port}")//port是yml文件中的端口号
    private String port;


    @Autowired
    private UsersService usersService;

    @HystrixCommand(fallbackMethod = "haha2")//如果发生异常就熔断 进入haha2方法 防止报错
    @RequestMapping("/haha")
    public String haha(String name,String pwd){
        System.out.println("提供者haha方法：" + name);
        if (name.equals("高薪")){
            int a=10/0;
        }
        return "port:::"+port;
    }

    //熔断方法（兜底） 参数和上面一致
    public String haha2(String name,String pwd){
        System.out.println("由于haha出现异常 现在进入haha2熔断方法：" + name);
        return "haha熔断方法";
    }

    @HystrixCommand(fallbackMethod = "findUsers2")
    @RequestMapping("/find")
    public Users findUsers(@RequestBody Users users) throws InterruptedException {
        System.out.println("提供者findusers方法:" + users);
        if (users.getUname().equals("高三金")){
            Thread.sleep(1000);//休眠1秒
        }
        return users;
    }
    //熔断方法
    public Users findUsers2(@RequestBody Users users) throws InterruptedException {
        System.out.println("提供者findusers熔断方法:" + users);

        return new Users(3,"被熔断","123");
    }

    @RequestMapping("/denglu")
    public String denglu(String uname,String pwd){
        System.out.println("提供者：" + pwd);
        return usersService.denglu(uname,pwd);
    }

    @RequestMapping("/map")
    //提供者中 参数为map的话 必须写@Requestparam注解
    public Map<String,Object> map(@RequestParam Map<String,Object> map){
        System.out.println("提供者的map方法：" + map);
        return map;
    }

    @RequestMapping("/users")
    //提供者中 参数为对象的话 必须写@RequestBody注解
    public Users getUsers(@RequestBody Users users){
        System.out.println("提供者的users对象方法：" + users);
        return users;
    }
}
