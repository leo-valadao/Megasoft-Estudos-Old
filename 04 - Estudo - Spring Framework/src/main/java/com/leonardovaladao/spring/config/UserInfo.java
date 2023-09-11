package com.leonardovaladao.spring.config;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.leonardovaladao.spring.domains.User;
import com.leonardovaladao.spring.services.UserService;

@Component
public class UserInfo implements UserDetailsService {

    private UserService userService;

    public UserInfo(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.findByName(username);

        return user.map(UserInfoDetails::new).orElseThrow(() -> new UsernameNotFoundException("Este usuário não foi encontrado na base de dados local!"));
    }
    
}
