package com.twx.learn.learnwxjavamp.controller;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private WxMpService wxMpService;

    /**
     * 用户列表
     *
     * @return
     * @throws WxErrorException
     */
    @GetMapping("/list")
    public WxMpUserList list() throws WxErrorException {
        WxMpUserList list = wxMpService.getUserService().userList(null);
        System.out.println("用户列表=>" + list);
        return list;
    }

    /**
     * 设置用户备注名
     * @return
     * @throws WxErrorException
     */
    @GetMapping("/updateremark")
    public String UpdatereMark() throws WxErrorException {
        wxMpService.getUserService().userUpdateRemark("o9rxU6k1hJPBM15gkQaLB8Eu6I4o", "twx");
        return "OK";
    }

    @GetMapping("/info")
    public WxMpUser userInfo() throws WxErrorException {
        WxMpUser userInfo = wxMpService.getUserService().userInfo("o9rxU6k1hJPBM15gkQaLB8Eu6I4o");
        System.out.println("用户信息=>"+userInfo);
        return userInfo;
    }
}
