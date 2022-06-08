package com.twx.learn.learnsatoken;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/doLogin")
    public SaResult doLogin(String username, String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);

            StpUtil.getSession().set("username", "twx-1");
            Map<String, String> result = new HashMap<>(2);
            result.putIfAbsent("tokenName", StpUtil.getTokenName());
            result.putIfAbsent("tokenVal", StpUtil.getTokenValue());
            return SaResult.data(result);
        }
        return SaResult.error("login failure");
    }


    @RequestMapping("/isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

    @RequestMapping("/tokenInfo")
    public SaResult tokenInfo() {
        if (StpUtil.hasPermission("user-add")) {
            return SaResult.data(StpUtil.getTokenInfo());
        }
        return SaResult.ok("No Permission");
    }

    @RequestMapping("/kickOut")
    public SaResult kickOut() {
        StpUtil.kickout(10001);
        return SaResult.ok();
    }

    @RequestMapping("/logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }


    // 登录认证：只有登录之后才能进入该方法
    @SaCheckLogin
    @RequestMapping("/info")
    public String info() {
        System.out.println(StpUtil.getSession().getString("username"));
        return "查询用户信息";
    }

    // 角色认证：必须具有指定角色才能进入该方法
    @SaCheckRole("/super-admin1")
    @RequestMapping("/add")
    public String add() {
        return "用户增加";
    }
}
