package com.pj.ConService.pjj;

import com.pj.uitl.newuser;

public interface userService {

    int register(String name, String pwd, String phone);


    newuser findname(String name);
}
