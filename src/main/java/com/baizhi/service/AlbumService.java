package com.baizhi.service;

import com.baizhi.dao.AlbumDao;

import java.util.List;

public interface AlbumService {
    public List<AlbumDao> selectAll();
}
