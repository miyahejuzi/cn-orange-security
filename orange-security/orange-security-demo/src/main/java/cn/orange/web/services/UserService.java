package cn.orange.web.services;

import cn.orange.dto.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : kz
 * @date : 2019/7/21
 */
@Service
public class UserService {

    private static List<User> users = new ArrayList<User>() {
        {
            add(new User());
            add(new User());
            add(new User());
        }
    };

    public List<User> findAll() {
        User user = new User();
        user.setUsername("username");
        users.add(user);
        return users;
    }
}
