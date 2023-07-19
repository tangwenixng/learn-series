package com.twx.learn.learnwxjavamp.controller;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.service.WxOAuth2Service;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private WxMpService mpService;

    @Autowired
    private WxMpMessageRouter router;

    @GetMapping(produces = "text/plain;charset=utf-8")
    public String index(@RequestParam("signature") String signature,
                        @RequestParam("timestamp") String timestamp,
                        @RequestParam("nonce") String nonce,
                        @RequestParam("echostr") String echostr) throws WxErrorException {
        System.out.println("index...");
        if (!mpService.checkSignature(timestamp, nonce, signature)) {
            // 消息不合法
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }
        return echostr;
    }

    @PostMapping(produces = "application/xml; charset=UTF-8")
    public String message(@RequestBody String requestBody,
                          @RequestParam(value = "signature",required = false) String signature,
                          @RequestParam(value = "timestamp", required = false) String timestamp,
                          @RequestParam(value = "nonce",required = false) String nonce) {
        System.out.println("signature="+signature+" timestamp="+timestamp+" nonce="+nonce);
        System.out.println("body=>"+requestBody);
        if (!mpService.checkSignature(timestamp, nonce, signature)) {
            // 消息不合法
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }
        WxMpXmlMessage inMsg = WxMpXmlMessage.fromXml(requestBody);
        WxMpXmlOutMessage outMsg = null;
        try {
            outMsg = router.route(inMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outMsg == null ? "" : outMsg.toXml();
    }


    @GetMapping("createMenu")
    public String createMenu() throws WxErrorException {
        System.out.println("创建菜单!");
        // 创建菜单对象
        WxMenu menu = new WxMenu();
        // 创建按钮1
        WxMenuButton button1 = new WxMenuButton();
        button1.setType(WxConsts.MenuButtonType.CLICK);
        button1.setName("今日歌曲");
        button1.setKey("V1001_TODAY_MUSIC");
        // 创建按钮2
        WxMenuButton button2 = new WxMenuButton();
        button2.setName("菜单");
        // 创建按钮2的子按钮1
        WxMenuButton button21 = new WxMenuButton();
        button21.setType(WxConsts.MenuButtonType.VIEW);
        button21.setName("搜索");
        button21.setUrl("https://www.baidu.com/");
        // 创建按钮2的子按钮2
        WxMenuButton button22 = new WxMenuButton();
        button22.setType(WxConsts.MenuButtonType.VIEW);
        button22.setName("视频");
        button22.setUrl("https://v.qq.com/");
        // 创建按钮2的子按钮3
        WxMenuButton button23 = new WxMenuButton();
        button23.setType(WxConsts.MenuButtonType.CLICK);
        button23.setName("赞一下我们");
        button23.setKey("V1001_GOOD");
        // 将子按钮添加到按钮2
        button2.getSubButtons().add(button21);
        button2.getSubButtons().add(button22);
        button2.getSubButtons().add(button23);
        // 将按钮1和你按钮2添加到菜单
        menu.getButtons().add(button1);
        menu.getButtons().add(button2);
        // 创建按钮
        String result = mpService.getMenuService().menuCreate(menu);
        System.out.println("创建菜单结果!"+result);
        return result;
    }

    /**
     * 网页授权链接
     * @return
     */
    @GetMapping("buildAuthPage")
    public String auth() {
        WxOAuth2Service oAuth2Service = mpService.getOAuth2Service();
        // 构建授权url
        String url = oAuth2Service.buildAuthorizationUrl("https://wx.tangwx.site/demo/callback",
                WxConsts.OAuth2Scope.SNSAPI_BASE, null);
        System.out.println("auth url=>"+url);
        return url;
    }

    /**
     * 网页授权获取用户信息
     * @param code
     * @return
     * @throws WxErrorException
     */
    @GetMapping("callback")
    public WxOAuth2UserInfo callback(String code) throws WxErrorException {
        System.out.println("授权code="+code);
        WxOAuth2Service oAuth2Service = mpService.getOAuth2Service();
        // 利用code获取accessToken
        WxOAuth2AccessToken accessToken = oAuth2Service.getAccessToken(code);
        System.out.println("网页授权token=>"+accessToken);
        // 利用accessToken获取用户信息
        WxOAuth2UserInfo userInfo = oAuth2Service.getUserInfo(accessToken, null);
        System.out.println("用户信息=>"+userInfo);
        return userInfo;
    }

    /**
     * 发送模板消息
     * @param name
     * @return
     * @throws WxErrorException
     */
    @GetMapping("sendTemplateMessage")
    public String sendTemplateMessage(String name) throws WxErrorException {
        // 创建模板消息，设置模板id、指定模板消息要发送的目标用户
        WxMpTemplateMessage wxMpTemplateMessage = WxMpTemplateMessage.builder()
                .templateId("UUZnOvFj0oc2DlU3FMfpSko7aC1VplehervSj4sQDj8")
                .toUser("o9rxU6rF7UuUyEVjhFC0H7y96XSg")
                .build();
        // 填充模板消息中的变量
        wxMpTemplateMessage.addData(new WxMpTemplateData("name", name));
        // 发送模板消息，返回消息id
        return mpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
    }


}
