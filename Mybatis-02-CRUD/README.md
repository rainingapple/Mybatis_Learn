# Mybatis-02-CRUD

In computing, **CRUD** is an acronym for **create**, **Retrieve**, **update**, and **delete**. 

It is used to refer to the basic functions of a database or **persistence layer** in a software system.

- C reate new records

- R etrieve existing records

- U pdate existing records

- D elete existing records.

本文介绍使用Mybatis进行基本的CRUD操作

<!--more-->

**参考**

**Mybatis官方文档** 

https://mybatis.org/mybatis-3/zh/sqlmap-xml.html#

**狂神说博客**

https://www.cnblogs.com/renxuw/p/13047424.html

https://mp.weixin.qq.com/s/efqEupNSPZUKqrRoGAnxzQ

**Github仓库**

https://github.com/rainingapple/Mybatis_Learn

## 查询（Retrieve）

实现不同的操作，工具类与pojo是不需要改变的。

只需要在UserMapper类和UserMapper.xml进行修改即可，并对应编写测试类

### UserMapper

```java
public interface UserMapper {
    List<User> selectuser();

    User selectuserbyid(int id);
}
```

### UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="cn.UserMapper">
    <select id="selectuser" resultType="cn.User">
        select * from user
    </select>

    <select id="selectuserbyid" resultType="cn.User">
        <!--使用#{}来对应表示参数-->
        select * from user where id = #{id}
    </select>
</mapper>
```

### Mytest

```java
    @Test
    public void testselectuserbyid() {
        SqlSession sqlSession = MybatisUtils.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectuserbyid(2);
        System.out.println(user);
        sqlSession.close();
    }
```

## 增加（Create）

### UserMapper

```java
int adduser(User user)
```

### UserMapper.xml

```xml
    <insert id="adduser" parameterType="cn.User">
        insert into user (id,name,pwd)
        values (#{id},#{name},#{pwd});
    </insert>
```

### Mytest

一定注意增删改需要提交事务

```java
    @Test
    public void testadduser() {
        SqlSession sqlSession = MybatisUtils.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.adduser(new User(5,"赵七","136s598"));
        //一定注意增删改需要提交事务
        sqlSession.commit();
        sqlSession.close();
    }
```

## 更新（Update）

### UserMapper

```java
int updateuser(User user);
```

### UserMapper.xml

```xml
    <update id="updateuser" parameterType="cn.User">
        update user
        set pwd = #{pwd}
        where id = #{id};
    </update>
```

### Mytest

```java
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
```

## 删除（Delete）

### UserMapper

```java
int deleteuser(int id);
```

### UserMapper.xml

```xml
    <delete id="deleteuser" parameterType="int">
        delete from user where id = #{id}
    </delete>
```

### Mytest

```java
    @Test
    public void testdeleteuser() {
        SqlSession sqlSession = MybatisUtils.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.deleteuser(4);
        //一定注意增删改需要提交事务
        sqlSession.commit();
        sqlSession.close();
    }
```

## 常见问题

- 配置文件不符合xml格式，导致的NullPointer错误
- namespace命名空间应该以.分割，不应该使用/，因为它是类。
- 相应的mapper.xml注册应该用/，因为他是文件。
- insert、select、update、delete混用导致问题
- maven资源没有过滤导致的导出的问题（解决方案见上一篇日志）

## 扩展

### 使用map来传递参数

在进行查询操作时，我们先前是生成一个实体类，将它传递作为参数。

但是在实际项目中，参数可能很多，我们一一填入效率很低。

并且很多时候我们希望可以取别名来传参。

通过map来传参完美解决了上述的问题

#### UserMapper

```java
User selectuser_map(Map<String,String> map);
```

#### UserMapper.xml

这样在传参时需要传递的是键名，而不需要一定是原来的pojo对应的属性名

```xml
    <select id="selectuser_map" parameterType="map" resultType="cn.User">
        select *
        from user where id = #{userid} and pwd = #{userpwd};
    </select>
```

#### Mytest

创建包含我们需要字段的map进行传递即可

```java
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
```

### 模糊查询

使用Mybatis进行模糊查询操作基本类似，仅在sql语句部分有所区别。

出于安全考虑，不要再mapper.xml中加入通配符。

#### UserMapper

```java
List<User> selectuser_like(String value);
```

#### UserMapper.xml

这样在传参时需要传递的是键名，而不需要一定是原来的pojo对应的属性名

```xml
    <select id="selectuser_like" resultType="cn.User">
        select *
        from user where name like #{value};
    </select>
```

#### Mytest

创建包含我们需要字段的map进行传递即可

```java
    @Test
    public void testselectuser_like() {
        SqlSession sqlSession = MybatisUtils.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> user = mapper.selectuser_like("%张%");
        System.out.println(user);
        sqlSession.close();
    }
```
