# Mybatis-01-Mybatis_HelloWorld

## 什么是MyBatis

- MyBatis 是一款优秀的**持久层框架**
- MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集的过程
- MyBatis 可以使用简单的 XML 或注解来配置和映射原生信息，
- 方便的将接口和 Java 的 实体类 【Plain Old Java Objects,普通的 Java对象】映射成数据库中的记录。
- MyBatis 本是apache的一个开源项目ibatis, 2010年这个项目由apache 迁移到了google code，改名为MyBatis 。

<!--more-->

**参考**

**Mybatis官方文档** 

https://mybatis.org/mybatis-3/zh/getting-started.html

**狂神说博客**

https://www.cnblogs.com/renxuw/p/13047424.html

https://mp.weixin.qq.com/s/vy-TUFa1Rb69ekxiEYGRqw

**Github仓库**

https://github.com/rainingapple/Mybatis_Learn

## 持久化与持久层

### 持久化

#### 持久化是将程序数据在持久状态和瞬时状态间转换的机制。

- 即把数据（如内存中的对象）保存到可永久保存的存储设备中（如磁盘）。
- 持久化的主要应用是将内存中的对象存储在数据库中，或者存储在磁盘文件中、XML数据文件中等等。
- JDBC就是一种持久化机制。文件IO也是一种持久化机制。

#### 为什么需要持久化服务呢？

- 内存断电后数据会丢失，但有一些对象是无论如何都不能丢失的，比如银行账号等，遗憾的是，人们还无法保证内存永不掉电。
- 内存过于昂贵，与硬盘、光盘等外存相比，内存的价格要高2~3个数量级，而且维持成本也高，至少需要一直供电吧。所以即使对象不需要永久保存，也会因为内存的容量限制不能一直呆在内存中，需要持久化来缓存到外存。

### 持久层

- 完成持久化工作的代码块 .  ---->  dao层 【DAO (Data Access Object)  数据访问对象】
- 大多数情况下特别是企业级应用，数据持久化往往也就意味着将内存中的数据保存到磁盘上加以固化，而持久化的实现过程则大多通过各种**关系数据库**来完成。
- 不过这里有一个字需要特别强调，也就是所谓的“层”。对于应用系统而言，数据持久功能大多是必不可少的组成部分。也就是说，我们的系统中，已经天然的具备了“持久层”概念？也许是，但也许实际情况并非如此。之所以要独立出一个“持久层”的概念,而不是“持久模块”，“持久单元”，也就意味着，我们的系统架构中，应该有一个相对独立的逻辑层面，专注于数据持久化逻辑的实现.
- 与系统其他部分相对而言，这个层面应该具有一个较为清晰和严格的逻辑边界。

## 为什么需要Mybatis

- Mybatis帮助程序员将数据存入数据库中 , 和从数据库中取数据 .

- 传统的jdbc操作 , 有很多重复代码块 .比如封装 , 建立连接等等 , 通过框架可以减少重复代码,提高开发效率 .

- MyBatis 是一个半自动化的**ORM框架 (Object Relationship Mapping) -->对象关系映射**

- MyBatis的优点

- - **简单易学**：本身就很小且简单。没有任何第三方依赖，最简单安装只要两个jar文件+配置几个sql映射文件就可以了，易于学习，易于使用，通过文档和源代码，可以比较完全的掌握它的设计思路和实现。
  - **灵活**：mybatis不会对应用程序或者数据库的现有设计强加任何影响。sql写在xml里，便于统一管理和优化。通过sql语句可以满足操作数据库的所有需求。
  - **sql与程序代码解耦合**：通过提供DAO层，将业务逻辑和数据访问逻辑分离，使系统的设计更清晰，更易维护，更易单元测试。sql和代码的分离，提高了可维护性。
  - **提供xml标签，支持编写动态sql**。

## Mybatis_HelloWorld

### 搭建实验数据库

实验环境为云数据库MySQL，通过外网地址连接即可。随后执行下列SQL语句：

```sql
CREATE DATABASE `mybatis`;

USE `mybatis`;

CREATE TABLE `user` (
`id` int(20) NOT NULL,
`name` varchar(30) DEFAULT NULL,
`pwd` varchar(30) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into `user`(`id`,`name`,`pwd`) values 
(1,'狂神','123456'),
(2,'张三','abcdef'),
(3,'李四','987654');
```

### 导入相关依赖

```xml
   <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.1</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.6</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
    </dependencies>
```

### 编写Mybatis核心配置文件

对应将url，密码替换为自己数据库的密码即可

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://你的url?
                useSSL=false&amp;
                useUnicode=true&amp;
                characterEncoding=utf8"/>
                <property name="username" value="root"/>
                <property name="password" value="你的密码"/>
            </dataSource>
        </environment>
    </environments>
</configuration>
```

### 编写Mybatis工具类

```java
public class MybatisUtils {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSession getSession(){
        return sqlSessionFactory.openSession();
    }
}
```

### 创建实体类

```java
public class User {
    private int id;
    private String name;
    private String pwd;

    public User(int id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
```

### 编写Mapper接口类

```java
public interface UserMapper {
    List<User> selectuser();
}
```

### 编写Mapper.xml配置文件

这个配置就好比我们编写了一个UserDao的实现类，有一个叫selectUser的方法

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="cn.UserMapper">
    <select id="selectUser" resultType="cn.User">
        select * from user
    </select>
</mapper>
```

### 在核心配置文件中注册Mapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://rm-bp1y5p52ds3x2u07sfo.mysql.rds.aliyuncs.com:3306/mybatis?
                useSSL=false&amp;
                useUnicode=true&amp;
                characterEncoding=utf8"/>
                <property name="username" value="root"/>
                <property name="password" value="@appleis555"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="cn/rainingapple/dao/UserMapper.xml"></mapper>
    </mappers>
</configuration>
```

### MyTest

#### 解决静态资源的问题

老问题，由于MAVEN约定只导出resources下的资源文件，所以本项目编写在java下的xml无法导出。

所以我们需要在pom中配置导出对应目录下的资源

```xml
   <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
        </resources>
    </build>
```

#### 测试类—法一

通过获取Mapper获取对象再执行方法

```java
public class MyTest {
    public static void main(String[] args) {
        SqlSession sqlSession = MybatisUtils.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.selectuser();

        for (User user: users){
            System.out.println(user);
        }
        sqlSession.close();
    }
}
```

#### 测试类—法二

直接写到方法层次，getList()

```java
public class MyTest {
    public static void main(String[] args) {
        SqlSession sqlSession = MybatisUtils.getSession();
        
        List<User> users = sqlSession.selectList("cn.UserMapper.selectuser");

        for (User user: users){
            System.out.println(user);
        }
        sqlSession.close();
    }
}
```

