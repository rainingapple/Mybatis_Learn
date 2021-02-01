import cn.rainingapple.dao.UserMapper;
import cn.rainingapple.pojo.User;
import cn.rainingapple.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class MyTest {
    public static void main(String[] args) {
        SqlSession sqlSession = MybatisUtils.getSession();

        //法一，先获取Mapper对象再直接调方法
        /*UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.selectuser();*/

        //法二，直接对应的getlist()
        List<User> users = sqlSession.selectList("cn.rainingapple.dao.UserMapper.selectuser");

        for (User user: users){
            System.out.println(user);
        }
        sqlSession.close();
    }
}
