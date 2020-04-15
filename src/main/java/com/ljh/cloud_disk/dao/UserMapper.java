package com.ljh.cloud_disk.dao;


import com.ljh.cloud_disk.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    int addUser(User user);

    int deleteUser(@Param("id") String id);

    int updateUser(User user);

    List<User> queryUserById(@Param("id") String id);

    List<User> queryAllUser();

    List<User> verifyUser(User user);
}
