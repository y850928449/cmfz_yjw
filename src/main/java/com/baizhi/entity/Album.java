package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "album")
@ExcelTarget(value = "album")
public class Album {
    @Id
    @Excel(name = "专辑编号", needMerge = true)
    private Integer id;
    @Excel(name = "专辑名称", needMerge = true)
    private String title;
    @Excel(name = "专辑集数", needMerge = true)
    private Integer amount;
    @Excel(name = "图片路径", type = 2, needMerge = true)
    private String pic;
    @Excel(name = "专辑星数", needMerge = true)
    private String score;
    @Excel(name = "专辑作者", needMerge = true)
    private String author;
    @Excel(name = "专辑声优", needMerge = true)
    private String boardcast;
    @Excel(name = "专辑添加时间", needMerge = true, format = "YYYY年MM月dd日HH时mm分ss秒")
    private Date time;
    @Excel(name = "专辑简介", needMerge = true)
    private String brief;
    @ExcelCollection(name = "章节")
    private List<Chapter> children;

}
