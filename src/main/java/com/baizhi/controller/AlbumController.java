package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.CharpterService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Controller
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private CharpterService charpterService;

    @RequestMapping("/selectAll")
    @ResponseBody
    public List<Album> selectAll() {
        List<Album> list = albumService.selectAll();
        for (Album album : list) {
            List<Chapter> chapters = charpterService.selectTwo(album.getId());
            album.setChildren(chapters);
        }
        return list;
    }

    @RequestMapping("/insert")
    @ResponseBody
    public Map insert(MultipartFile file, Album album) throws Exception {
        Map map = new HashMap();
        String oldName = file.getOriginalFilename();
        String s = UUID.randomUUID().toString();
        int index=oldName.lastIndexOf(".");
        String newName = s + oldName.substring(index);
        /*file.transferTo(new File("G:/服务器/"+newName));*/
        file.transferTo(new File("G:/mavenCode/cmfz_yjw/src/main/webapp/main/image/album/" + oldName));
        String pic = "image/album/" + oldName;
        album.setTime(new Date());
        album.setPic(pic);
        try {
            albumService.insert(album);
            map.put("return", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("return", false);
        }
        return map;
    }

    @RequestMapping("/xsl")
    @ResponseBody
    public Map xsl(HttpServletResponse response, HttpSession session) {
        Map map = new HashMap();
        String path = session.getServletContext().getRealPath("/main");
        List<Album> list = albumService.selectAll();
        for (Album album : list) {
            List<Chapter> chapters = charpterService.selectTwo(album.getId());
            album.setChildren(chapters);
            album.setPic(path + "/" + album.getPic());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("持明法洲专辑", "专辑"), Album.class, list);
        OutputStream os = null;
        //响应头
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("content-disposition", "attachment;fileName=album.xls");
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
