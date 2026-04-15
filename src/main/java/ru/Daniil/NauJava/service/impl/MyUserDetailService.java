package ru.Daniil.NauJava.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.Daniil.NauJava.model.User;
import ru.Daniil.NauJava.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public MyUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User appUser = userService.getUserByUsername(username);

        return new org.springframework.security.core.userdetails.User(
                appUser.getEmail(),
                appUser.getPassword(),
                mapRoles(appUser)
        );
    }

    private List<SimpleGrantedAuthority> mapRoles(User appUser) {
        return List.of(new SimpleGrantedAuthority(appUser.getRoleName()));
    }
}