/*
package com.baizhi.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/login")
    @ResponseBody
    public Map login(Admin admin, String code) {
        Map map = new HashMap();
        Admin a = adminService.login(admin);
        if (a == null) {
            map.put("return", false);
            return map;
        } else {
            map.put("return", true);
            return map;
        }
    }
    @RequestMapping("born")
    public void imgBorn(HttpSession session, HttpServletResponse response) throws IOException {

        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(80, 30, 4, 20);

        //LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(120,150);

        //System.out.println("系统生成的验证am============"+lineCaptcha.getCode());
        session.setAttribute("img",captcha.getCode());

        OutputStream os = response.getOutputStream();

        captcha.write(os);
    }

}
*/
package com.baizhi.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    @ResponseBody
    public String login(Admin admin, String enCode, HttpSession session, Map map) {
        String code = (String) session.getAttribute("code");
        if (!enCode.equalsIgnoreCase(code)) {
            return "1";
        }
        Admin admin1 = adminService.login(admin);
        if (admin1 == null) {
            return "2";
        } else {
            session.setAttribute("name", admin.getName());
            return "3";
        }
    }

    /**
     * 当前浏览器请求这个方法时，我们会生成验证码图片，并且响应给浏览器
     */
    @RequestMapping("captchaCode")
    public void out(HttpSession session, HttpServletResponse response) throws IOException {
        //1.生成验证码
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(80, 40, 4, 10);

        //2.将验证码存储到session
        session.setAttribute("code", lineCaptcha.getCode());

        //3.将验证码图片响应给浏览器
        OutputStream os = response.getOutputStream();
        lineCaptcha.write(os);

    }

}
