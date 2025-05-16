package com.pj.mapper;

import com.pj.uitl.newuser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface UserSql {
    @Insert("insert into user (username,pwd,phone) values (#{name},#{pwd},#{phone})")
    int register(String name, String pwd, String phone);

    @Select("select * from user where phone=#{name}")
    newuser findname(String name);
}
