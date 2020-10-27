package com.ou.springbootmybatis.service.impl;

import com.ou.springbootmybatis.mapper.UserRoleMapper;
import com.ou.springbootmybatis.service.UserService;
import com.ou.springbootmybatis.entity.User;
import com.ou.springbootmybatis.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * (Student)表服务实现类
 *
 * @author 全栈学习笔记
 * @since 2020-04-14 11:39:19
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public void register(String userId, String userName, String password, String remark) {
        // 首先检查此用户是否在数据库
        if (userMapper.selectById(userId) != null){
            log.info("手机已注册");
        };
        // 制作用户密码,然后将用户插入user表中
        String encryptPassword = String.valueOf(new SimpleHash("MD5", password,userId, 1024));
        System.out.println("加密之后的用户密码是:"+encryptPassword);
        userMapper.insetUser(User.getUser(userId, userName, encryptPassword, remark));
        // 增加用户角色中间表,注册最基本角色
        userRoleMapper.insert(userId, 200);
    }

    @Override
    public User selectByName(String userName) {
        return userMapper.selectByName(userName);
    }

    @Override
    public Set<String> selectByNameRole(String userName) {
        return userMapper.selectByNameRole(userName);
    }

    @Override
    public Set<String> selectByNamePermission(String roleid) {
        return userMapper.selectByNamePermission(roleid);
    }
}