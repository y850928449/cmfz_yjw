package com.baizhi.service.impl;

import com.baizhi.dao.UserMapper;
import com.baizhi.entity.User;
import com.baizhi.entity.UserMan;
import com.baizhi.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userDao;

    @Override
    public Map selectAll(Integer page, Integer row) {
        PageHelper.startPage(page, row);
        PageInfo<User> pageInfo = new PageInfo<>(userDao.selectAll());
        List<User> list = pageInfo.getList();
        long total = pageInfo.getTotal();
        Map map = new HashMap();
        map.put("rows", list);
        map.put("total", total);
        return map;
    }

    @Override
    public void insert(User user) {
        userDao.insertSelective(user);
    }

    @Override
    public Integer delete(User user) {
        int delete = userDao.delete(user);
        if (delete == 1) {
            return 1;
        } else return 0;
    }

    @Override
    public void update(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public List<User> selectxsl() {
        List<User> list = userDao.selectAll();
        return list;
    }

    @Override
    public Map selectByMan() {
        Map map = new HashMap();
        List<UserMan> list1 = userDao.selectByMan(1);
        map.put("man", list1);
        List<UserMan> list = userDao.selectByMan(0);
        map.put("woman", list);
        return map;
    }

    @Override
    public Map selectCount() {
        Integer c1 = userDao.selectCount1(7);
        Integer c2 = userDao.selectCount1(14);
        Integer c3 = userDao.selectCount1(21);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("intervals", new String[]{"一周", "两周", "三周"});
        map.put("counts", new int[]{c1, c2, c3});
        //Integer c = userDao.selectCount1(id);
        return map;
    }

    @Override
    public List<User> selectMore() {
        List<User> list = userDao.selectAll();
        return list;
    }

    @Override
    public User selectOne(User user) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone", user.getPhone());
        /*String s=user.getPhone();
        String s1 = s.substring(3, 7);
        String s2 = DigestUtils.md5Hex(s1 + s);*/
        criteria.andEqualTo("password", user.getPassword());
        User user1 = userDao.selectOneByExample(example);
        return user1;
    }
}
