package com.javamike.securingweb;

import com.javamike.entity.User;
import com.javamike.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取账号
        String userName = authentication.getName();
        //获取密码
        String password = authentication.getCredentials().toString();
        User user = userRepository.findByUsername(userName);
        if (null == user) {
            throw new UsernameNotFoundException("user not found");
        }
        System.out.println("password:" + passwordEncoder.encode(user.getPassword()));
        if (!passwordEncoder.matches(password, passwordEncoder.encode(user.getPassword()))) {
            throw new BadCredentialsException("user name or password is invalid");
        }

        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("USERS"));
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                authentication.getName(), authentication.getCredentials(), roles);

        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 表示这个AuthenticationProvider支持简单的用户名密码登录
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
