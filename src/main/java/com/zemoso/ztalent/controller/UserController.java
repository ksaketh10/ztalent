package com.zemoso.ztalent.controller;

import com.zemoso.ztalent.models.User;
import com.zemoso.ztalent.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @PostMapping ("/user/create")
    public Object createUser(@RequestBody User user) {
        try  {
            userService.insertNewUser(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("createUser "+ e.getMessage());
            throw e;
        }
    }

    @PostMapping("/user/check")
    public Object checkUser(@RequestBody User user) {
        try  {
            userService.validateUser(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("checkUser "+ e.getMessage());
            throw e;
        }
    }
}
