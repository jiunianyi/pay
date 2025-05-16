package com.pj.ConService.pjj;

import com.pj.mapper.UserSql;
import com.pj.uitl.newuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userServiceimpl implements userService {

    @Autowired
    private UserSql userSql;
    @Override
    public int register(String name, String pwd, String phone) {
       int  str =  userSql.register(name,pwd,phone);
       // System.out.println(str);
       return str;
    }

    @Override
    public newuser findname(String name) {
            newuser str =userSql.findname(name);
            return str;
    }


}
