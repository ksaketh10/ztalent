package com.zemoso.ztalent.service;

import com.zemoso.ztalent.controller.exceptions.custom.DuplicateEntryException;
import com.zemoso.ztalent.controller.exceptions.custom.InvalidCredentialsException;
import com.zemoso.ztalent.db.UserRepository;
import com.zemoso.ztalent.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    //Insert new tool user
    @Override
    public void insertNewUser(User user) {
        if (userRepository.findIdByEmail(user.getEmail().trim()) != null) throw new DuplicateEntryException();
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userRepository.save(user);
    }

    //Validate existing user by email and password
    @Override
    public void validateUser(User user) {
        for (User user1 : userRepository.findAll()) {
            if (user1.getEmail().equals(user.getEmail().trim()) && BCrypt.checkpw(user.getPassword().trim(), user1.getPassword())) {
                return;
            }
        }
        throw new InvalidCredentialsException();
    }
}
