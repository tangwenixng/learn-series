package com.twx.learn.learnwxjavamp.config;

import com.twx.learn.learnwxjavamp.handler.*;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxJavaConfig {
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private TextHandler textHandler;

    @Autowired
    private ImageHandler imageHandler;

    @Autowired
    private ProjectHandler projectHandler;

    @Autowired
    private SubscribeHandler subscribeHandler;
    @Autowired
    private UnSubscribeHandler unSubscribeHandler;

    @Bean
    public WxMpMessageRouter router() {
        WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);
        router.rule().async(false)
                .msgType(WxConsts.XmlMsgType.TEXT)
                .handler(textHandler)
                .end();
        router.rule().async(false)
                .msgType(WxConsts.XmlMsgType.IMAGE)
                .handler(imageHandler)
                .end();
        //方案
        router.rule().async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SCAN)
                .handler(projectHandler)
                .end();

        router.rule().async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SUBSCRIBE)
                .handler(subscribeHandler)
                .end();
        router.rule().async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.UNSUBSCRIBE)
                .handler(unSubscribeHandler)
                .end();
        return router;
    }
}
