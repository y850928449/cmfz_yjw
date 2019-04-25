package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSON;
import com.baizhi.entity.Album;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/selectAll")
    @ResponseBody
    public Map selectAll(Integer page, Integer rows) {
        System.out.println(page + rows);
        Map map = userService.selectAll(page, rows);
        return map;
    }

    @RequestMapping("/insert")
    @ResponseBody
    public Map insert(User user, MultipartFile file) throws Exception {
        Map map = new HashMap();
        String oldName = file.getOriginalFilename();
        String s = UUID.randomUUID().toString();
        int index = oldName.lastIndexOf(".");
        String newName = s + oldName.substring(index);
        file.transferTo(new File("G:/mavenCode/cmfz_yjw/src/main/webapp/main/image/user/" + newName));
        String pic = "image/user" + newName;
        user.setTime(new Date());
        user.setPic(pic);
        user.setSalt("1");
        try {
            userService.insert(user);
            System.out.println();
            map.put("return", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("return", false);
        }
        person();
        return map;
    }

    public void person() {
        Map map1 = userService.selectCount();
        String s1 = JSON.toJSONString(map1);
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-0e7dba6c10f440a2b5cb91a1244fc0a0");
        goEasy.publish("cmfz", s1);

        Map map2 = userService.selectByMan();
        String s2 = JSON.toJSONString(map2);
        goEasy.publish("cmfz2", s2);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(User user) {
        Integer delete = userService.delete(user);
        person();
        return delete;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Map update(User user, MultipartFile file) throws Exception {
        System.out.println(user);
        Map map = new HashMap();
        if (!file.getOriginalFilename().equalsIgnoreCase("")) {
            String oldName = file.getOriginalFilename();
            file.transferTo(new File("G:/mavenCode/cmfz_yjw/src/main/webapp/main/image/user/" + oldName));
            String pic = "image/user/" + oldName;
            user.setPic(pic);
        }
        try {
            userService.update(user);
            System.out.println();
            map.put("return", true);
            person();
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("return", false);
            return map;
        }
    }

    @RequestMapping("/selectByPerson")
    @ResponseBody
    public Map selectByPerson() {
        Map map = userService.selectByMan();
        return map;
    }

    @RequestMapping("selectCount")
    @ResponseBody
    public Map selectCount() {
        Map map = userService.selectCount();
        return map;
    }

    @RequestMapping("/xsl")
    @ResponseBody
    public Map xsl(HttpServletResponse response) {
        Map map = new HashMap();
        List<User> list = userService.selectMore();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户", "用户"), Album.class, list);
        OutputStream os = null;
        //响应头
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("content-disposition", "attachment;fileName=user.xsl");
        try {
            os = response.getOutputStream();
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Workbook workbook =  new HSSFWorkbook(new FileInputStream(new File("G:/album.xls")));*/
        return map;
    }
}
