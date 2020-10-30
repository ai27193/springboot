package com.ou.springbootmybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleMapper {
    void insert(@Param("userId") String userId, @Param("roleId") Integer roleId);
}

