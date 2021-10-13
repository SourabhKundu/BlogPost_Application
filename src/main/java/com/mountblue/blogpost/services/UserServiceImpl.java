package com.mountblue.blogpost.services;

import com.mountblue.blogpost.model.Role;
import com.mountblue.blogpost.model.User;
import com.mountblue.blogpost.controller.web.UserRegistrationDto;
import com.mountblue.blogpost.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user = new User(registrationDto.getName(),
                registrationDto.getEmail(), registrationDto.getPassword(),
                List.of(new Role("ROLE_USER")));
        return userRepository.save(user);
    }
//passwordEncoder.encode
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                mapRolesToAuthority(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthority(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = null;

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            System.out.println("Current User Details: " + username);
            user = userRepository.findByEmail(username);
        }
        return user;
    }
}