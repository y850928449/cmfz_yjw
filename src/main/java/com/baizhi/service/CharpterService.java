package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CharpterService {
    public List<Chapter> selectTwo(int id);

    public void insert(Chapter chapter, MultipartFile file);
}
