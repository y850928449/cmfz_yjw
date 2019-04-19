package com.baizhi.entity;

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
public class Album {
    @Id
    private Integer id;
    private String title;
    private Integer amount;
    private String pic;
    private String score;
    private String author;
    private String boardcast;
    private Date time;
    private String brief;
    private List<Chapter> children;

}
