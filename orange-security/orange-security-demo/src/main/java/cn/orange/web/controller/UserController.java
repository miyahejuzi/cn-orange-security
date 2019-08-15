package cn.orange.web.controller;

import cn.orange.dto.User;
import cn.orange.web.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : kz
 * @date : 2019/7/20
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> queryUser(User user, @PageableDefault Pageable pageable) {
        return userService.findAll();
//        System.out.println("user = [" + user + "]");
    }

    @PostMapping
    public List<User> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().stream().forEach((e) -> System.out.println(e.getDefaultMessage()));
        }

        System.out.println("user = [" + user + "]");
        List<User> all = userService.findAll();
        all.add(user);
        return all;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().stream().forEach((e) -> System.out.println(e.getDefaultMessage()));
        }
        return user;
    }

    @PostMapping("/{id:\\d+}")
    public User getInfo(@PathVariable String id) {
        return new User();
    }

}
