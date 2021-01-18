package com.cssl.service.impl;

import com.cssl.service.UsersService;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl  implements UsersService {
    @Override
    public String denglu(String uname, String pwd) {
        if (uname.equals("高鑫") && pwd.equals("123")){
            return "nice";
        }
        return "no nice";
    }
}
