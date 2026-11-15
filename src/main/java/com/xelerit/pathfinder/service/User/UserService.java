package com.xelerit.pathfinder.service.User;

import com.xelerit.pathfinder.model.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    User saveUser(User user);

    User updateUser(User user);

    User findByEmail(String email);

    List<User> findAllUsers();
}
