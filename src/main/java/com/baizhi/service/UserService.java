package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public Map selectAll(Integer page, Integer row);

    public void insert(User user);

    public Integer delete(User user);

    public void update(User user);

    public List<User> selectxsl();

    public Map selectByMan();

    public Map selectCount();

    public List<User> selectMore();

    public User selectOne(User user);
}
