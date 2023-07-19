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
public class TextHandler implements WxMpMessageHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map,
                                    WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        // 接收的消息内容
        String inContent = wxMpXmlMessage.getContent();
        // 响应的消息内容
        String outContent="";
        // 根据不同的关键字回复消息
        if (inContent.contains("游戏")){
            outContent = "仙剑奇侠传";
        }else if (inContent.contains("动漫")){
            outContent = "进击的巨人";
        }
        else {
            outContent = inContent;
        }
        return WxMpXmlOutMessage.TEXT().content(outContent)
                .fromUser(wxMpXmlMessage.getToUser())
                .toUser(wxMpXmlMessage.getFromUser())
                .build();
    }
}
