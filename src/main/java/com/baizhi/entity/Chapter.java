package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chapter")
public class Chapter {
    @Id
    /*@GeneratedValue(generator="UUID")*/
    @Excel(name = "章节编号")
    private String id;
    @Excel(name = "章节名称")
    private String title;
    @Excel(name = "章节大小")
    private String size;
    @Excel(name = "章节时长")
    private String duration;
    @Excel(name = "章节添加时间", format = "YYYY年MM月dd日HH时mm分ss秒")
    private Date time;
    @ExcelIgnore
    private Integer albumId;
    @Excel(name = "章节路径")
    private String path;
}
