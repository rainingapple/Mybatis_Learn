package cn.rainingapple.dao;

import cn.rainingapple.pojo.User;

import java.util.List;

public interface UserMapper {
    List<User> selectuser();

    User selectuserbyid(int id);

    int adduser(User user);

    int updateuser(User user);

    int deleteuser(int id);
}
