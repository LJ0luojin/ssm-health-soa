package com.lh.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lh.pojo.Permission;
import com.lh.pojo.Role;
import com.lh.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Component
public class SecurityUserService implements UserDetailsService {
    @Reference
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //从数据库中取用户相关信息
        com.lh.pojo.User userPojo =  userService.findByUserName(username);
        //判空
        if(userPojo==null){
            return null;
        }
        //赋权
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Set<Role> roles = userPojo.getRoles();
        for (Role role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                grantedAuthorities.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }

        //创建Security User对象
        User userSecurity = new User(userPojo.getUsername(),userPojo.getPassword(),grantedAuthorities);
        return userSecurity;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("123456"));
    }
}
