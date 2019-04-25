package com.baizhi;

import com.baizhi.entity.Student;
import com.baizhi.service.StudentService;
import com.baizhi.service.UserService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzYjwApplicationTests {
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;
    @Test
    public void contextLoads() {
    }

    @Test
    public void login() {
    }

    @Test
    public void Poi() {
        List<Student> students = studentService.selectAll();
        //创建工作簿
        Workbook workbook = new HSSFWorkbook();
        //创建字体
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setItalic(true);
        font.setColor(Font.COLOR_RED);
        //创建日期格式
        DataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd");
        //创建样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //创建日期格式的样式
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle.setDataFormat(format);
        //创建工作表
        Sheet sheet = workbook.createSheet("student");
        //第一个参数给那个列设置宽度  下标   第二个参数  宽度设置为多少  需要乘以256
        sheet.setColumnWidth(4, 20 * 256);
        //编号、名字、年龄
        String[] strings = {"编号", "名字", "年龄"};
        //创建行  参数第几行  下标
        Row row = sheet.createRow(0);
        for (int i = 0; i < strings.length; i++) {
            //创建单元格
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            //赋值
            cell.setCellValue(strings[i]);
        }
        for (int i = 0; i < students.size(); i++) {
            Row row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(students.get(i).getId());
            row1.createCell(1).setCellValue(students.get(i).getName());
            row1.createCell(2).setCellValue(students.get(i).getAge());
            Cell cell = row1.createCell(3);
        }
        try {
            ((HSSFWorkbook) workbook).write(new FileOutputStream(new File("G:/student.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
