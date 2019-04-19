package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.CharpterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        /*String s= UUID.randomUUID().toString();
        int index=oldName.lastIndexOf(".");
        String newName=s+oldName.substring(index);*/
        /*file.transferTo(new File("G:/服务器/"+newName));*/
        file.transferTo(new File("G:/mavenCode/cmfz_yjw/src/main/webapp/main/image/album" + oldName));
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
    /*@RequestMapping("selectByAlbum")
    @ResponseBody
    public Map selectByAlbum(Map map){
        List<Album> list = albumService.selectAll();
        map.put("list",list);
        return map;
    }*/
}
