package com.kedacom.user.service;

import com.kedacom.user.model.User;

public interface UserService {
    User findByName(String name);

    User save(User user);
}
