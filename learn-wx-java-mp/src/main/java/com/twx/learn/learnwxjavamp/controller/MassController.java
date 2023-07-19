package com.twx.learn.learnwxjavamp.controller;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mass")
public class MassController {

    @Autowired
    WxMpService wxMpService;

    @GetMapping("/send-txt")
    public String sendTxt() throws WxErrorException {
        WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
        massMessage.setMsgType(WxConsts.MassMsgType.TEXT);
        massMessage.setContent("测试群发内容");
        List<String> toUsers = massMessage.getToUsers();
        toUsers.add("o9rxU6k1hJPBM15gkQaLB8Eu6I4o");
        toUsers.add("o9rxU6rF7UuUyEVjhFC0H7y96XSg");

        WxMpMassSendResult massResult = wxMpService.getMassMessageService().massOpenIdsMessageSend(massMessage);
        System.out.println("群发结果=>"+massResult);
        return massResult.toString();
    }
}
