package com.ou.springbootmybatis.mapper;

import com.ou.springbootmybatis.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Set;

@Repository
public interface UserMapper {

    User selectById(@Param("userId") String userId);

    LinkedHashMap<String, Object> selectUserPermissionById(@Param("userId") String userId);

    User selectByName(@Param("userName") String userName);

    LinkedHashMap<String, Object> selectUserPermissionByName(@Param("userName") String userId);

    void insetUser(@Param("user") User user);

    Set<String> selectByNameRole(@Param("userName") String userName);

    Set<String> selectByNamePermission(@Param("roleName") String roleName);
}