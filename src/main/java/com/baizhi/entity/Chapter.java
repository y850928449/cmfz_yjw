package com.baizhi.entity;

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
    private String id;
    private String title;
    private String size;
    private String duration;
    private Date time;
    private Integer albumId;
    private String path;
}
