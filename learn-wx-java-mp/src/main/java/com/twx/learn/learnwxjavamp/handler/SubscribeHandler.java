package com.twx.learn.learnwxjavamp.handler;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class SubscribeHandler implements WxMpMessageHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map,
                                    WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        System.out.println("SubscribeHandler调用");
        String eventKey = wxMpXmlMessage.getEventKey();
        String[] arr = eventKey.split("_");
        String sceneId;
        if (arr.length > 1){
            sceneId = arr[1];
        }else {
            sceneId = arr[0];
        }
        if ("twx1".equals(sceneId)) {
            System.out.println("关注后进行方案推送...");
            WxMpXmlOutMessage out = genMsg(wxMpXmlMessage);
            return out;
        }
        return WxMpXmlOutMessage.TEXT().fromUser(wxMpXmlMessage.getToUser()).toUser(wxMpXmlMessage.getFromUser())
                .content("欢迎关注").build();
    }

    private WxMpXmlOutNewsMessage genMsg(WxMpXmlMessage wxMessage) {
        WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
        item.setDescription("蓝波兔");
        item.setPicUrl("https://img1.baidu.com/it/u=1459759203,398990576&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500");
        item.setTitle("蓝波兔体态测试");
        item.setUrl("https://www.baidu.com/");

        WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser())
                .addArticle(item)
                .build();
        return m;
    }
}
