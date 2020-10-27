package com.ou.springbootmybatis.service;

import com.ou.springbootmybatis.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * (Student)表服务接口
 *
 * @author 全栈学习笔记
 * @since 2020-04-14 11:39:19
 */
public interface UserService {
    @Transactional
    void register( String userId, String userName,String password, String remark);

    User selectByName(String userName);
    Set<String> selectByNameRole(String userName);
    Set<String> selectByNamePermission(String roleid);
}