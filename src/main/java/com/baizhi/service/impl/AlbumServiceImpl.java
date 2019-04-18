package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;

    @Override
    public List<AlbumDao> selectAll() {
        List<AlbumDao> list = albumDao.selectAll();
        return list;
    }
}
