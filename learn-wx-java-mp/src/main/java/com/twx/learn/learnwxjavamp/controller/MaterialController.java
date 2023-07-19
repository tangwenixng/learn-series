package com.twx.learn.learnwxjavamp.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialFileBatchGetResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/material")
public class MaterialController {
    @Autowired
    private WxMpService wxMpService;

    /**
     * 上传图片文件
     * https://developers.weixin.qq.com/doc/offiaccount/Asset_Management/Adding_Permanent_Assets.html
     *
     * @param file
     * @return
     * @throws IOException
     * @throws WxErrorException
     */
    @PostMapping("/upload-img")
    public String uploadImage(@RequestPart MultipartFile file) throws IOException, WxErrorException {
        String path = "/Users/twx/code-space/personal/learn-series/learn-wx-java-mp/files/";
        String fileName = file.getOriginalFilename();
        String filePath = path + fileName;

        File dest = new File(filePath);
        FileUtil.writeFromStream(file.getInputStream(), dest);
        WxMediaImgUploadResult result = wxMpService.getMaterialService().mediaImgUpload(dest);
        System.out.println("upload result=>" + result);
        return result.getUrl();
    }

    @GetMapping("/add-material")
    public String addMaterial() throws WxErrorException {
        WxMpMaterial material = new WxMpMaterial();
        material.setFile(new File("/Users/twx/Pictures/nn.jpeg"));
        material.setName("勇敢牛牛");
        WxMpMaterialUploadResult result = wxMpService.getMaterialService().materialFileUpload(WxConsts.MediaFileType.IMAGE, material);
        System.out.println("result=>"+result);
        return result.toString();
    }

    /**
     * 获取永久素材的列表
     * @return
     * @throws WxErrorException
     */
    @GetMapping("/batchget_material")
    public String batchgetMaterial() throws WxErrorException {
        WxMpMaterialFileBatchGetResult result = wxMpService.getMaterialService()
                .materialFileBatchGet(WxConsts.MediaFileType.IMAGE, 0, 10);
        System.out.println("result=>"+ JSONUtil.toJsonPrettyStr(result));
        return result.toString();
    }



}
