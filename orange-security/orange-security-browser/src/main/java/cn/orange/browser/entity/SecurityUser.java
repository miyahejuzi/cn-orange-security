package cn.orange.browser.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author : kz
 * @date : 2019/7/30
 */
@Data
@AllArgsConstructor
public class SecurityUser {

    private String username;

    private String password;

    private String[] roles;

}
