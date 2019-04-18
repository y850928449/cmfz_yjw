package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "album")
public class Album {
    private Integer id;
    private String title;
    private Integer amount;
    private String pic;
    private Integer score;
    private String author;
    private String boardcast;

}
