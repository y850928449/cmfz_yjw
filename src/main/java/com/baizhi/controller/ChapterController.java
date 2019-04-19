package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.CharpterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    private CharpterService charpterService;

    @RequestMapping("/insert")
    @ResponseBody
    public Map insert(Chapter chapter, MultipartFile file) {
        Map map = new HashMap();
        try {
            charpterService.insert(chapter, file);
            map.put("return", true);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("return", false);
            return map;
        }
    }

    @RequestMapping("/down")
    public void down(String title, String path, HttpServletResponse response) throws Exception {
        InputStream is = new FileInputStream("G:\\mavenCode\\cmfz_yjw\\src\\main\\webapp" + path);
        //响应头
        String s = URLEncoder.encode(title, "UTF-8");
        response.setHeader("content-disposition", "attachment;title=" + s);
        //取
        OutputStream os = response.getOutputStream();
        while (true) {
            int i = is.read();
            if (i == -1) {
                break;
            }
            os.write(i);
        }
    }
}
