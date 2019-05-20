package com.zemoso.ztalent.service;

import com.zemoso.ztalent.models.User;

public interface IUserService {

    void insertNewUser(User user);

    void validateUser(User user);
}
