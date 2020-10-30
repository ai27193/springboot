package com.ou.springbootmybatis.entity;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="注册对象模型")
@Builder
public class User {
    private Integer id;
    private String userId;
    private String userName;
    private String password;
    private String userRemarks;
    public static User getUser(String userId,String userName, String encryptPassword, String remark) {
        return User.builder().userId(userId).password(encryptPassword).userRemarks(remark).userName(userName).build();
    }
}