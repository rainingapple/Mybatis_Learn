import cn.rainingapple.dao.UserMapper;
import cn.rainingapple.pojo.User;
import cn.rainingapple.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class MyTest {
    @Test
    public void testselect() {
        SqlSession sqlSession = MybatisUtils.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> selectuser = mapper.selectuser();
        System.out.println(selectuser);
        sqlSession.close();
    }
}
