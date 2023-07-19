package com.twx.learn.learnwxjavamp.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/qrCode")
public class QrCodeController {

    @Autowired
    WxMpService wxMpService;

    @GetMapping()
    public String get() {
        WxMpQrcodeService qrcodeService = wxMpService.getQrcodeService();
        try {
            WxMpQrCodeTicket ticket = qrcodeService.qrCodeCreateTmpTicket("twx1", 600);
            File file = qrcodeService.qrCodePicture(ticket);
            File dest = new File("/Users/twx/code-space/personal/learn-series/learn-wx-java-mp/files/"+file.getName());
            FileUtil.copy(file, dest, true);
            return "OK";
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

}
