package com.ou.springbootmybatis.controller;


import com.ou.springbootmybatis.common.CommonConstant;
import com.ou.springbootmybatis.entity.User;
import com.ou.springbootmybatis.mapper.RegisterMapper;
import com.ou.springbootmybatis.service.impl.UserServiceImpl;
import com.ou.springbootmybatis.util.JwtUtil;
import com.ou.springbootmybatis.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.jsp.jstl.sql.Result;
import javax.validation.Valid;
import java.util.Set;

/**
 * (Student)表控制层
 *
 * @author 全栈学习笔记
 * @since 2020-04-14 11:39:20
 */
@Slf4j
@RequestMapping("/user")
@CrossOrigin
@RestController
@Api(value = "my-shiro接口测试")
public class UserController {
    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    private RedisUtil redisUtil;
    //此为shiro开放端口
    @PostMapping(value = "/register",name = "用户注册")
    @ApiOperation(value="注册接口", notes="输入手机号、密码、用户名、备注")
    public String userRegister(@RequestBody @Valid RegisterMapper registerMapper, BindingResult results) throws Exception {
        if (results.hasErrors()){
            return results.getFieldError().getDefaultMessage();
        }
        try {
            userServiceImpl.register(registerMapper.getUserId(),registerMapper.getUserName(),registerMapper.getPassword(),registerMapper.getRemark());
        }
        catch (Exception e){
            throw new Exception();
        }
        return "注册成功";
    }
    @ApiOperation(value="登录接口", notes="用户登录、登录名、密码")
    @PostMapping(value = "/userLogin")
    public String toLogin(@RequestBody User loginUser) throws Exception {
        String userName = loginUser.getUserName();
        String passWord = loginUser.getPassword();

        User user=userServiceImpl.selectByName(userName);
        if (user == null) {
            log.info("该用户不存在");
            return "该用户不存在";
        }
        //我的密码是使用uuid作为盐值加密的，所以这里登陆时候还需要做一次对比
        SimpleHash simpleHash = new SimpleHash("MD5", passWord,  user.getUserId(), 1024);
        if(!simpleHash.toHex().equals(user.getPassword())){
            log.info("密码不正确");
            return "密码不正确";
        }
        // 生成token
        String token = JwtUtil.sign(userName, simpleHash.toHex());
        JSONObject obj = new JSONObject();
        obj.put("token", token);
        obj.put("userInfo", user);
        return obj.toString();
    }

    @ApiOperation(value="查询角色", notes="登录账号")
    @PostMapping(value = "/selectByName")
    public String selectByName(@RequestBody User loginUser) throws Exception {
        //查询角色
        Set<String> list=userServiceImpl.selectByNameRole(loginUser.getUserName());
        return list.toString();
    }
}