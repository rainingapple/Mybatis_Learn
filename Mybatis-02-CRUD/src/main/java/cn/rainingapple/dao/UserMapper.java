package cn.rainingapple.dao;

import cn.rainingapple.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<User> selectuser();

    User selectuserbyid(int id);

    User selectuser_map(Map<String,String> map);

    List<User> selectuser_like(String value);

    int adduser(User user);

    int updateuser(User user);

    int deleteuser(int id);
}
