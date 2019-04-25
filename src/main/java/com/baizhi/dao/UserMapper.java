package com.baizhi.dao;

import com.baizhi.entity.User;
import com.baizhi.entity.UserMan;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    public List<UserMan> selectByMan(int sex);

    public Integer selectCount1(int i);
}
