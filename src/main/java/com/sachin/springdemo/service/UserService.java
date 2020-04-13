package com.sachin.springdemo.service;

import com.sachin.springdemo.entity.User;
import com.sachin.springdemo.user.CrmUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUserName(String userName);

    void save(CrmUser crmUser);
}
