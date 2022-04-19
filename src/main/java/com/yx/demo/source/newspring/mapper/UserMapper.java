package com.yx.demo.source.newspring.mapper;



import com.yx.demo.source.newspring.YxComponent;
import com.yx.demo.source.newspring.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;


import java.util.List;


@YxComponent(value = "userMapper")
public interface UserMapper {
    /**
     * insertUser
     * @param user user
     * @return Integer
     */
    @Insert("INSERT INTO person(name,age) VALUES (#{name},#{age}")
    Integer insertUser(User user);

    /**
     * selectList
     * @return List<User>
     */
    @Select("select * from test where user=1")
    List<User> selectList();

    /**
     * selectUser
     * @return User
     */
    @Select("select * from test where user=1")
    User selectUser();
}
