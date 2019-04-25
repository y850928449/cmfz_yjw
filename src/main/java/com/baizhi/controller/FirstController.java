package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Chapter;
import com.baizhi.entity.User;
import com.baizhi.service.AlbumService;
import com.baizhi.service.BannerService;
import com.baizhi.service.CharpterService;
import com.baizhi.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("first")
public class FirstController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private CharpterService charpterService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "queryAll", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object queryAll(String uid, String type, String sub_type) {
        if (uid == null || type == null) {
            return new Error("参数不能为空");
        } else {
            if (type.equals("all")) {
                Map<String, Object> map = new HashMap<>();
                List<Banner> list1 = bannerService.selectxsl();
                List<Album> list2 = albumService.selectAll();
                List<Chapter> list3 = charpterService.select();
                map.put("banner", list1);
                map.put("album", list2);
                map.put("article", list3);
                return map;
            } else if (type.equals("wen")) {
                Map<String, Object> map = new HashMap<>();
                List<Album> list2 = albumService.selectAll();
                map.put("album", list2);
                return map;
            } else {
                if (sub_type == null) {
                    return new Error("思类型为空未查到数据");
                } else {
                    if (sub_type.equals("ssyj")) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("article", "当前上师发表的文章集合");
                        return map;
                    } else {
                        Map<String, Object> map = new HashMap<>();
                        map.put("article", "其他上师发表的文章集合");
                        return map;
                    }
                }
            }
        }
    }

    @RequestMapping(value = "queryWen", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object wen(Integer id, Integer uid) {
        Map map = new HashMap();
        if (uid == null || id == null) {
            return new Error("参数不能为空");
        } else {
            Album album = albumService.selectOne(id);
            if (album == null) {
                map.put("error", "对不起，没有此专辑");
                return map;
            }
            map.put("album", album);
            return map;
        }
    }

    @RequestMapping(value = "queryLogin", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object Login(String phone, String password) {
        Map map = new HashMap();
        if (phone == null || password == null) {
            return new Error("参数不能为空");
        } else {
            User user = new User();
            String s = phone;
            String s1 = s.substring(3, 7);
            String s2 = DigestUtils.md5Hex(password + s1);
            user.setPassword(s2);
            user.setPhone(phone);
            User user1 = userService.selectOne(user);
            if (user1 != null) {
                map.put("恭喜您登陆成功", user1);
                return map;
            } else {
                map.put("error", "-200");
                map.put("errmsg", "您输入的账号或密码有误");
                return map;
            }
        }
    }

    @RequestMapping(value = "queryRegist", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object Regist(String phone, String password) {
        /*String s= UUID.randomUUID().toString().replace("-","");*/
        String s1 = phone.substring(3, 7);
        User user = new User();
        user.setSalt(s1);
        String s2 = DigestUtils.md5Hex(password + s1);
        user.setPhone(phone);
        user.setPassword(s2);
        userService.insert(user);
        Map map = new HashMap();
        map.put("user", user);
        return map;
    }
}

