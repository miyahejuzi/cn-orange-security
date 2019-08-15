package cn.orange.browser.security;

import cn.orange.browser.entity.SecurityUser;
import cn.orange.browser.service.SecurityService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * @author : kz
 * @date : 2019/7/30
 */
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SecurityUser byUsername = securityService.findByUsername(s);
        if (byUsername == null) {
            throw new UsernameNotFoundException("The username of the user not found...");
        }
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(byUsername.getRoles());
        String encodePassword = bCryptPasswordEncoder.encode(byUsername.getPassword());
        return new User(byUsername.getUsername(), encodePassword, authorities);
    }
}
