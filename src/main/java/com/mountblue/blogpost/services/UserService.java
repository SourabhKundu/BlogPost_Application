package com.mountblue.blogpost.services;

import com.mountblue.blogpost.model.User;
import com.mountblue.blogpost.controller.web.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUser();

    User save(UserRegistrationDto registrationDto);

    User findUserByEmail(String email);
}