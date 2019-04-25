package com.baizhi.service.impl;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Override
    public Map selectAll(Integer page, Integer row) {
        PageHelper.startPage(page, row);
        PageInfo<Banner> pageInfo = new PageInfo<>(bannerDao.selectAll());
        List<Banner> list = pageInfo.getList();
        long total = pageInfo.getTotal();
        Map map = new HashMap();
        map.put("rows", list);
        map.put("total", total);
        return map;
    }

    @Override
    public void insert(Banner banner) {
        bannerDao.insert(banner);
    }

    @Override
    public Integer delete(Banner banner) {
        int delete = bannerDao.delete(banner);
        if (delete == 1) return 1;
        return 0;
    }

    @Override
    public void update(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
    }

    @Override
    public List<Banner> selectxsl() {
        List<Banner> list = bannerDao.selectAll();
        return list;
    }
}
