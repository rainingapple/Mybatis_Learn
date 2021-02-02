import cn.rainingapple.dao.UserMapper;
import cn.rainingapple.pojo.User;
import cn.rainingapple.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.*;

public class MyTest {
    @Test
    public void testselect() {
        SqlSession sqlSession = MybatisUtils.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> selectuser = mapper.selectuser();
        System.out.println(selectuser);
        sqlSession.close();
    }

    @Test
    public void testselectuserbyid() {
        SqlSession sqlSession = MybatisUtils.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectuserbyid(2);
        System.out.println(user);
        sqlSession.close();
    }

    @Test
    public void testadduser() {
        SqlSession sqlSession = MybatisUtils.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.adduser(new User(4,"张七","136s598"));
        //一定注意增删改需要提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testupdateuser() {
        SqlSession sqlSession = MybatisUtils.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectuserbyid(1);
        user.setPwd("99999");
        mapper.updateuser(user);
        //一定注意增删改需要提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testdeleteuser() {
        SqlSession sqlSession = MybatisUtils.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.deleteuser(5);
        //一定注意增删改需要提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testselectuser_map() {
        SqlSession sqlSession = MybatisUtils.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Map<String,String> map = new HashMap<String, String>();
        map.put("userid","1");
        map.put("userpwd","99999");
        User user = mapper.selectuser_map(map);
        System.out.println(user);
        sqlSession.close();
    }

    @Test
    public void testselectuser_like() {
        SqlSession sqlSession = MybatisUtils.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> user = mapper.selectuser_like("%张%");
        System.out.println(user);
        sqlSession.close();
    }
}
