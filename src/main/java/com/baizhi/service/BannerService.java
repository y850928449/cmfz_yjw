package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.Map;

public interface BannerService {
    public Map selectAll(Integer page, Integer row);

    public void insert(Banner banner);

    public void delete(Banner banner);

    public void update(Banner banner);
}
