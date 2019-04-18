package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @RequestMapping("/selectAll")
    @ResponseBody
    public Map selectAll(Integer page, Integer rows) {
        System.out.println(page + rows);
        Map map = bannerService.selectAll(page, rows);
        return map;
    }

    @RequestMapping("/insert")
    @ResponseBody
    public Map insert(Banner banner, MultipartFile file) throws Exception {
        Map map = new HashMap();
        String oldName = file.getOriginalFilename();
        /*String s= UUID.randomUUID().toString();
        int index=oldName.lastIndexOf(".");
        String newName=s+oldName.substring(index);*/
        /*file.transferTo(new File("G:/服务器/"+newName));*/
        file.transferTo(new File("G:/mavenCode/cmfz_yjw/src/main/webapp/main/image/" + oldName));
        String pic = "image/" + oldName;
        banner.setTime(new Date());
        banner.setPic(pic);
        try {
            bannerService.insert(banner);
            map.put("return", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("return", false);
        }
        return map;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public void delete(Banner banner) {
        bannerService.delete(banner);
    }

    /*@RequestMapping("/updateStatus")
    @ResponseBody
    public void updateStatus(Banner banner){
        bannerService.update(banner);
    }*/
    @RequestMapping("/update")
    @ResponseBody
    public Map update(Banner banner, MultipartFile file) throws Exception {
        System.out.println(banner);
        Map map = new HashMap();
        if (file != null) {
            String oldName = file.getOriginalFilename();
            file.transferTo(new File("G:/mavenCode/cmfz_yjw/src/main/webapp/main/image/" + oldName));
            String pic = "image/" + oldName;
            banner.setPic(pic);
        }
        try {
            bannerService.update(banner);
            map.put("return", true);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("return", false);
            return map;
        }
    }
}
