package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    public Integer delete(Banner banner) {
        int delete = bannerService.delete(banner);
        return delete;
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
        if (!file.getOriginalFilename().equalsIgnoreCase("")) {
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

    @RequestMapping("/xsl")
    @ResponseBody
    public Map xsl(HttpServletResponse response) {
        List<Banner> bannerList = bannerService.selectxsl();
//1.创建工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
//2.创建工作表
        HSSFSheet bannerSheet = workbook.createSheet("Banner");
        bannerSheet.setColumnWidth(2, 20 * 256);
//3.创建行,创建第几行
        HSSFRow row = bannerSheet.createRow(0);
//第一行字段
        String[] titles = {"编号", "图片名称", "创建时间", "状态"};
//创建单元格对象
        HSSFCell cell = null;
        for (int i = 0; i < titles.length; i++) {
            //i 标示列索引
            cell = row.createCell(i);
            cell.setCellValue(titles[i]);
        }
//处理数据行
        for (int i = 0; i < bannerList.size(); i++) {
            Row row1 = bannerSheet.createRow(i + 1);
            row1.createCell(0).setCellValue(bannerList.get(i).getId());
            row1.createCell(1).setCellValue(bannerList.get(i).getTitle());
            Cell cell2 = row1.createCell(2);
            cell2.setCellValue(bannerList.get(i).getTime());
            HSSFDataFormat dataFormat = workbook.createDataFormat();
            short format = dataFormat.getFormat("yyyy年MM月dd日");
            CellStyle cellStyle1 = workbook.createCellStyle();
            cellStyle1.setDataFormat(format);

            cell2.setCellStyle(cellStyle1);

            //row1.createCell(2).setCellValue(bannerList.get(i).getCreate_date());

            row1.createCell(3).setCellValue(bannerList.get(i).getStatus());
            //日期
    /*HSSFCell cell=row.createCell(0);
    cell.setCellValue(new Date());

    style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
    cell.setCellStyle(style);*/
        }
        Map map = new HashMap();
        OutputStream os = null;
        try {
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("content-disposition", "attachment;fileName=banner.xsl");
            os = response.getOutputStream();
            workbook.write(os);
            workbook.close();
            map.put("isOk", "true");
        } catch (IOException e) {
            e.printStackTrace();
            map.put("isOk", "false");
        }
        return map;
        /*ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}
