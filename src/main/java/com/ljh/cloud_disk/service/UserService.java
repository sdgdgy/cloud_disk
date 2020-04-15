package com.ljh.cloud_disk.service;


import com.ljh.cloud_disk.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    int addUser(User user);

    int deleteUser(@Param("id") String id);

    int updateUser(User user);

    List<User> queryUserById(@Param("id") String id);

    List<User> queryAllUser();

    List<User> verifyUser(User user);
}
