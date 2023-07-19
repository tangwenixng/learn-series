package com.twx.learn.learnwxjavamp.controller;

import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jsapi")
public class JsApiController {

    @Autowired
    WxMpService wxMpService;

    @GetMapping
    public String index() throws WxErrorException {
        String ticket = wxMpService.getJsapiTicket();
        System.out.println("ticket=>"+ticket);
        return ticket;
    }

    @GetMapping("/sign")
    public WxJsapiSignature sign() throws WxErrorException {
        WxJsapiSignature signature = wxMpService.createJsapiSignature("https://wx.tangwx.site/abcd");
        System.out.println("signature=>"+signature);
        return signature;
    }

}
