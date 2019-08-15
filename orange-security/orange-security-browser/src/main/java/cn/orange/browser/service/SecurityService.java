package cn.orange.browser.service;

import cn.orange.browser.entity.SecurityUser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回一些角色权限
 *
 * @author : kz
 * @date : 2019/7/30
 */
@Service
public class SecurityService {

    static Map<String, SecurityUser> userMap = new HashMap<String, SecurityUser>(6) {{
        this.put("123456", new SecurityUser("123456", "123456", new String[]{"ROLE_user", "ROLE_admin"}));
        this.put("123", new SecurityUser("123", "123", new String[]{"ROLE_user"}));
        this.put("456", new SecurityUser("456", "456", new String[]{"ROLE_admin"}));
        this.put("123456789", new SecurityUser("456", "", new String[]{"ROLE_admin"}));
    }};

    public SecurityUser findByUsername(String username) {
        return userMap.get(username);
    }

}
